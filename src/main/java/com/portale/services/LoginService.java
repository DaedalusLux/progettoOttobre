package com.portale.services;

import java.sql.Date;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portale.mapper.LoginMapper;
import com.portale.mapper.RoomMapper;
import com.portale.model.ManagedException;
import com.portale.model.SecretCode;
import com.portale.model.User;
import com.portale.model.UserAuth;

@Service
@Repository
public class LoginService {
	@Autowired
	private LoginMapper mapper;

	@Autowired
	private RoomMapper room_mapper;
	
	@Value("#{AppProperties.frontendURL}")
	private String frontendURL;

	@Value("#{AppProperties.mailHost}")
	private String mailHost;

	@Value("#{AppProperties.mailUsername}")
	private String mailUsername;

	@Value("#{AppProperties.mailPassword}")
	private String mailPassword;

	@Transactional
	public UUID setRegistration(UserAuth _userauth) throws Exception {
		String dublicate_mail = mapper.getUserByEmail(_userauth.getEmail());
		User dublicate_username = mapper.getUserByUsername(_userauth.getUsername());
		if (dublicate_mail != null || dublicate_username != null) {
			throw new ManagedException("registration.dublicate");
		}
		
		UUID guid = UUID.randomUUID();
		String secret_code = String.valueOf(new Random().nextInt(900000) + 100000);

		long millis = System.currentTimeMillis() + 3600000; // Un'ora di tempo per la conferma account
		Date expiration = new Date(millis);

		if (sendRegistrationEmail(secret_code, _userauth.getEmail())) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			_userauth.setPassword(passwordEncoder.encode(_userauth.getPassword()));
			mapper.setRegistration(guid.toString(), secret_code, expiration, _userauth);
			mapper.dleteOldRequests();
			return guid;
		} 
		throw new ManagedException("Mail exception, something wrong with configuration...");
	}

	public boolean setRegistrationConfirmation(String guid, SecretCode sc) throws Exception {
		String secret_code_to_confirm = mapper.getConfirmRegistration(guid);
		if (secret_code_to_confirm != null) {
			if (secret_code_to_confirm.equals(sc.getSecret_code())) {
				// Se ritorna true l'utente ha la possibilita' di inserire i dettagli account
				// per la creazione
				mapper.setConfirmRegistrationTrue(guid);
				return true;
			} else {
				throw new ManagedException("registration.wrong.secretcode");
			}
		} else {
			throw new ManagedException("registration.wrong.or.expired");
		}
	}

	@Transactional
	public UserAuth addNewUser(User user_details, String username) {
		UserAuth user_basis = mapper.getUserAuthByUsername(username);
		if (user_basis == null) {
			throw new ManagedException("registration.username.not.registered");
		}
		mapper.addNewUser(user_details, user_basis);
		mapper.dleteOldRequestsByUsername(username);
		user_basis.setAuthorities("COSTUMER");
		room_mapper.setUserToRandomAvaibleGifterRoom(user_details.getId());
		user_basis.setId(user_details.getId());
		return user_basis;
	}

	public User getUser(String username) {
		return mapper.getUserByUsername(username);
	}

	public UserAuth CheckUserForLogin(UserAuth userLoginRequest) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserAuth userAuth = mapper.getUserForLogin(userLoginRequest.getUsername());
		if (userAuth != null) {
			if (passwordEncoder.matches(userLoginRequest.getPassword(), userAuth.getPassword())) {
				if (userAuth.getIsAdmin()) {
					userAuth.setAuthorities("ADMIN");
				} else {
					userAuth.setAuthorities("COSTUMER");
				}
				return userAuth;
			} else {
				throw new ManagedException("user.wrong.password");
			}
		} else {
			UserAuth userAuth_notComplete = mapper
					.getUserAuthByUsername(userLoginRequest.getUsername());
			if (userAuth_notComplete != null) {
				if (passwordEncoder.matches(userLoginRequest.getPassword(), userAuth_notComplete.getPassword())) {
					throw new ManagedException("user.not.complete");
				}
			}
			throw new ManagedException("user.not.found");
		}
	}

	public User getUserData(UserAuth uauth) {
		return mapper.getUserByUsername(uauth.getUsername());
	}

	private boolean sendRegistrationEmail(String secret_code, String email) throws Exception {
		String to = email;

		// Configurazione posta invio
		String from = "Ottobre";
		String host = "smtp.gmail.com";
		Properties properties = System.getProperties();

		switch (mailHost) {
		case "gmail":
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.port", "465");
			properties.put("mail.smtp.ssl.enable", "true");
			properties.put("mail.smtp.auth", "true");
			break;

		default:
			throw new ManagedException("No mail service provided in config.");
		}

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailUsername, mailPassword);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Codice conferma registrazione www.ottobre.com");
			message.setText("Codice conferma: " + secret_code);
			System.out.println("Invio nuova mail a: " + to);
			Transport.send(message);
			System.out.println("Invio email andato a buon fine per: " + to);
			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
		return false;
	}

}
