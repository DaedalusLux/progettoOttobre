package com.portale.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.portale.model.EmailObj;
import com.portale.model.NotificationObject;
import com.portale.services.EmailService;
import com.portale.services.ErrorHandlerService;
import com.portale.services.NotificationService;
import com.portale.services.UserService;

@RestController
public class EmailController {
	@Resource
	private EmailService emailService;
	@Resource
	private NotificationService notificationService;
	@Resource
	private ErrorHandlerService errorHandlerService;
	@Resource
	private UserService userService;
	
	@RequestMapping(value = "/email-management/send", method = RequestMethod.POST)
	public ResponseEntity<?> SendContactEmail(HttpServletRequest request, @RequestBody EmailObj emailObj) {
		try {
			
			if(!emailService.CheckForSpam(emailObj.getEmail_body(), emailObj.SpamSecurity())){
				String message = emailObj.ServiceContantRequest("Giuseppe Centurami", emailObj.getEmail_sendername(), emailObj.getEmail_from(), emailObj.getEmail_body());
				List<String> emailAdress = new ArrayList<>();
				emailAdress.add(emailObj.getEmail_to()); 
				emailService.Send("Comunicazione  Messaggio Clienti", message, emailAdress);
			} else {
				NotificationObject notification = new NotificationObject();
				
				notification.setImportancyLevel(1);
				notification.setTitle("Invio di richiesta contatti sospettosa!");
				notification.setMessage("Si &eacute; stata verificato un'invio di richiesta contatti sospettosa!" 
						+ "Si richiede di verificare il contenuto<br>"
						+ "<br><b>Nome mittente:</b> <span data-field='nome'>" + emailObj.getEmail_sendername() + "</span>"
						+ "<br><b>Email mittente:</b> <span data-field='cognome'>" + emailObj.getEmail_from() + "</span>"
						+ "<br><b>Messaggio:</b> <span data-field='email'>" + emailObj.getEmail_body() + "</span>"
						);
				notificationService.CreateNotification(notification, notification.getTitle(), notification.getMessage(),
						notification.getImportancyLevel(), 0, 2);
				
				List<Integer> U = userService.GetUsersIdListByRole("ROLE_ADMIN");
				for (int a = 0; a < U.size(); a++) {
					int currentU = U.get(a);
					notificationService.AppendNotificationToUser(currentU, notification.getNotification_id());
				}
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			//errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
