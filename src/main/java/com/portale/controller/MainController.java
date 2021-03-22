package com.portale.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.management.Notification;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.portale.model.EmailObj;
import com.portale.model.NotificationObject;
import com.portale.model.UserLoginDetails;
import com.portale.model.UserObject;
import com.portale.security.services.JwtTokenGenerator;
import com.portale.services.EmailService;
import com.portale.services.NotificationService;
import com.portale.services.UserService;

@RestController
public class MainController {
	
	@Resource
	private UserService loginService;
	@Resource
	private NotificationService notificationService;
	@Resource
	private EmailService emailService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> AuthUser(@RequestBody UserObject _userauth) {
		UserObject User = new UserObject();
		String token = null;
		HttpHeaders httpHeaders = new HttpHeaders();

		try {
			User = loginService.GetUserData(_userauth.getUsr_username(), _userauth.getUsr_password());

			if (User != null) {
				if (User.getLocked() == false) {
					new JwtTokenGenerator();
					token = JwtTokenGenerator.generateToken(User);

					httpHeaders.add("Authorization", "Bearer " + token);
					return new ResponseEntity<>(
							new UserLoginDetails(User.getUsr_id(), token, User.getUsr_username(), User.getAuthorities()), httpHeaders,
							HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				}

			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> RegisterUser(@RequestBody UserObject _userauth) {
		try {
			NotificationObject notification = new NotificationObject();
			
			notification.setImportancyLevel(new Long(1));
			notification.setTitle("Nuova richiesta di registrazione");
			notification.setMessage("Una nuova rihiesta di registrazione &eacute; stata effettuata con i seguenti dati: <br>"
					+ "<br><b>Nome:</b> " + _userauth.getNome()
					+ "<br><b>Cognome:</b> " + _userauth.getCognome()
					+ "<br><b>Indirizzo Email:</b> " + _userauth.getEmail()
					+ "<br><b>Numero di telefono:</b> " + _userauth.getTelefono()
					+ "<br><b>Indirizzo:</b> " + _userauth.getIndirizzo()
					+ "<br><b>Citt&aacute;:</b> " + _userauth.getCitta()
					+ "<br><b>Provincia:</b> " + _userauth.getProvincia()
					+ "<br><b>CAP:</b> " + _userauth.getCodicePostale()
					+ "<br><b>Codice Fiscale:</b> " + _userauth.getCodiceFiscale());
			notificationService.CreateNotification(notification, notification.getTitle(), notification.getMessage(),
					notification.getImportancyLevel(), new Long(0));
			
			List<Integer> U = loginService.GetUsersIdListByRole("ROLE_ADMIN");
			for (int a = 0; a < U.size(); a++) {
				Long currentU = Long.parseLong(String.valueOf(U.get(a)));
				notificationService.AppendNotificationToUser(currentU, notification.getNotification_id());
			}
			List<String> mivEmail = new ArrayList<String>();
			mivEmail.add("mariuscezar9@gmail.com");
			EmailObj eo = new EmailObj();
			emailService.Send(notification.getTitle(), notification.getMessage(), mivEmail);
			
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
}