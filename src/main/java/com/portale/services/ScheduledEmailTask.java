package com.portale.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

//@Configuration
//@EnableScheduling
public class ScheduledEmailTask {
/*
	@Scheduled(fixedDelay = 36000)
	public void performCleanTempData() {
		System.out.println("Oh..");
		
		

		List<String> recipients = new ArrayList<String>();
		recipients.add("mariuscezar9@gmail.com");

		String user_fullname = "Grasu Marius Cezar";
		String orderId = "64284124";
		String renew_price = "15.99";
		String pay_method = "Visa Card";
		String creation_date = "09/01/2020";
		String expiration_date = "30/01/2020";
		String store_link = "https://tuttocitta.data-smart.it/user-controlpanel?negozi=52&sname=test";
		String store_name = "test";
		String remain_days = "15";

		String email_subject = "Promemoria scadenza negozio.";

		String email_content = storePayReminder(user_fullname, orderId, renew_price, pay_method, creation_date,
				expiration_date, store_link, store_name, remain_days);

		Send(email_subject, email_content, recipients);
	}

	String from = "materialeinvendita@yahoo.com";
	String host = "smtp.mail.yahoo.com";

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

	private static String storePayReminder(String user_fullname, String orderId, String renew_price, String pay_method,
			String creation_date, String expiration_date, String store_link, String store_name, String remain_days) {
		return "<div style=\"width: 100%;\">\r\n" + "<div style=\"max-width: 750px; margin: auto;\">\r\n"
				+ "<h3>Materialeinvendita</h3>\r\n"
				+ "<div><a style=\"color: #0050c8; text-decoration: none; cursor: pointer;\" href=\"#\">Sito</a> <a style=\"color: #0050c8; text-decoration: none; cursor: pointer; margin: 20px;\" href=\"#\">Area Clienti</a></div>\r\n"
				+ "<p><strong> Gentile " + user_fullname + ", </strong></p>\r\n"
				+ "<p>Questo avviso &egrave; una promemoria che il tuo negozio che &egrave; stato generato il "
				+ creation_date + " &egrave; in scadenza tra " + remain_days + " giorni.</p>\r\n" + "<p>Ordine: "
				+ orderId + " <br />Saldo dovuto: " + renew_price
				+ " &euro; <br />Il tuo metodo di pagamento &egrave;: " + pay_method + " <br />Data di scadenza: "
				+ expiration_date + "</p>\r\n" + "<p><strong> Voci di fattura </strong></p>\r\n"
				+ "<p>Negozio, <a href=\"" + store_link + "\">" + store_name + "</a> <br /><br /></p>\r\n"
				+ "</div>\r\n" + "</div>";
	}
*/
}