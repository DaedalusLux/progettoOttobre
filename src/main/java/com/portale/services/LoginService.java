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
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.portale.mapper.LoginMapper;
import com.portale.model.UserAuth;

@Service
@Repository
public class LoginService {
	@Autowired
	private LoginMapper mapper;

	
	public void setRegistration(UserAuth _userauth) throws Exception {
		UUID guid =  UUID.randomUUID();
		String secret_code = String.valueOf(new Random().nextInt(900000) + 100000);
		
		long millis=System.currentTimeMillis() + 3600000; //Un'ora di tempo per la conferma account  
		Date expiration = new Date(millis);
		
		if(sendRegistrationEmail(guid, secret_code, _userauth.getEmail())) {
			mapper.setRegistration(guid.toString(), secret_code, expiration, _userauth);
		} else {
			throw new Exception("Errore invio email");
		}
	}


	private boolean sendRegistrationEmail(UUID guid, String secret_code, String email) {
		String to = email;
		
		//Configurazione posta invio
		String from = "xyz@gmail.com";
		String host = "smtp.gmail.com";
		Properties properties = System.getProperties();
		
		 properties.put("mail.smtp.host", host);
	     properties.put("mail.smtp.port", "465");
	     properties.put("mail.smtp.ssl.enable", "true");
	     properties.put("mail.smtp.auth", "true");
	     
	     //Configurazione link invio frontend
	     String sito_web_conferma = "www.ottobre.com/conferma?guid=" + guid;
	     
	     Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication("bachecasteel@gmail.com", "xxxxx");
	            }
	        });
	     
	     try {
	            MimeMessage message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(from));
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	            message.setSubject("Codice conferma registrazione www.ottobre.com");
	            message.setText("Codice conferma: " + secret_code + "\b Inserisci questo codice qui: " + sito_web_conferma);
	            System.out.println("Invio nuova mail a: " + to);
	            Transport.send(message);
	            System.out.println("Invio email andato a buon fine per: " +  to);
	            return true;
	        } catch (MessagingException mex) {
	            mex.printStackTrace();
	        }
	     return false;
	}
	

}
