package com.portale.services;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class EmailService {
	
	List<String> recipients = new ArrayList<String>();
	String from = "materialeinvendita@yahoo.com";
	String host = "smtp.mail.yahoo.com";
	
	public boolean CheckForSpam(String text_toCheck, String BASE64) {
		String text_toAvoid = new String(Base64.getDecoder().decode(BASE64));
		if(text_toCheck.contains(text_toAvoid))
			return true;
		else return false;
	}
	
	
	public void Send(String messageTitle, String messageContent, List<String> emailAdress) {
		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("materialeinvendita@yahoo.com", "hmicgbnrxahugnyl");
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			for (int i = 0; i < emailAdress.size(); i++) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAdress.get(i)));
			}

			message.setSubject(messageTitle);
			message.setContent(messageContent, "text/html");

			Transport.send(message);

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
	
}
