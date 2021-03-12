package com.portale.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portale.model.NotificationObject;
import com.portale.model.PaginationObject;
import com.portale.security.model.AuthenticatedUser;
import com.portale.services.NotificationService;
import com.portale.services.UserService;

@RestController
public class NotificationController {
	@Resource
	private NotificationService notificationService;
	@Resource
	private UserService userService;

	@RequestMapping(value = "/notifications/all", method = RequestMethod.GET)
	public ResponseEntity<?> GetAllNotifications(
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "per_page", defaultValue = "10", required = false) int per_page,
			Authentication authentication) {

		PaginationObject obj = new PaginationObject();
		List<NotificationObject> notification = new ArrayList<NotificationObject>();
		try {
			page = page > 0 ? (page-1) : 0;
			AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();
			obj.setTotalResult(notificationService.GetNotificationsCount((int)u.getUsr_id()));
			notification = notificationService.GetAllNotifications((int)u.getUsr_id(), per_page, (page * per_page));
			obj.setData(notification);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/notifications/{notificationId}", method = RequestMethod.GET)
	public ResponseEntity<?> GetNotificationById(@PathVariable("notificationId") Long notificationId,
			Authentication authentication) {
		try {
			AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();

			NotificationObject notification = notificationService.GetNotificationById(u.getUsr_id(), notificationId);
			if (notification != null) {
				return new ResponseEntity<>(notification, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/notifications/{notificationId}", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> PostNotificationResponse(@PathVariable("notificationId") Long notificationId,
			Authentication authentication) {
		try {
			AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();

			notificationService.SetNotificationAsSeen(u.getUsr_id(), notificationId);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/notifications/check", method = RequestMethod.GET)
	public ResponseEntity<?> CheckForUnseenNotifications(Authentication authentication) {
		try {
			AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();
			Boolean any = notificationService.AnyUnseenNotifications(u.getUsr_id());
			return new ResponseEntity<>(any, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/notifications/create", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> CreateNotification(Authentication authentication, HttpServletRequest request,
			@RequestBody NotificationObject notification) {
		try {
			AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();

			notificationService.CreateNotification(notification, notification.getTitle(), notification.getMessage(),
					notification.getImportancyLevel(), u.getUsr_id());

			if (request.isUserInRole("ROLE_ADMIN")) {
				if (notification.getMto().size() > 1) {
					for (int rec = 0; rec < notification.getMto().size(); rec++) {
						notificationService.AppendNotificationToUser(
								Long.parseLong(notification.getMto().get(rec).getId(), 10),
								notification.getNotification_id());
					}
				} else {
					List<Integer> receviers = new ArrayList<Integer>();
					switch (notification.getMto().get(0).getId()) {
					case "All":
						receviers = userService.GetUsersIdListByRole(null);
						break;
					case "Admins":
						receviers = userService.GetUsersIdListByRole("ROLE_ADMIN");
						break;
					case "Users":
						receviers = userService.GetUsersIdListByRole("ROLE_USER");
						break;
					default:
						receviers.add(Integer.parseInt(notification.getMto().get(0).getId()));
					}
					for (int a = 0; a < receviers.size(); a++) {
						Long currentU = Long.parseLong(String.valueOf(receviers.get(a)));
						notificationService.AppendNotificationToUser(currentU, notification.getNotification_id());
					}
				}
			} else {
				List<Integer> U = userService.GetUsersIdListByRole("ROLE_ADMIN");
				for (int a = 0; a < U.size(); a++) {
					Long currentU = Long.parseLong(String.valueOf(U.get(a)));
					notificationService.AppendNotificationToUser(currentU, notification.getNotification_id());
				}
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
