package com.portale.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class EmailObj {
	private String email_sendername;
	private String email_from;
	private String email_to;
	private String email_title;
	private String email_body;
	
	
	public String getEmail_from() {
		return email_from;
	}

	public void setEmail_from(String email_form) {
		this.email_from = email_form;
	}

	public String getEmail_to() {
		return email_to;
	}

	public void setEmail_to(String email_to) {
		this.email_to = email_to;
	}

	public String getEmail_title() {
		return email_title;
	}

	public void setEmail_title(String email_title) {
		this.email_title = email_title;
	}

	public String getEmail_body() {
		return email_body;
	}

	public void setEmail_body(String email_body) {
		this.email_body = email_body;
	}

	
	public String ServiceContantRequest(String user_fullname, String sendername, String senderemail, String sendermessage) {
		DateTime dateTime = new DateTime();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/YYYY");
		String strDateOnly = fmt.print(dateTime);
		
		return "<div style=\"width: 100%;\">\r\n" + "<div style=\"max-width: 750px; margin: auto;\">\r\n"
				+ "<h3>Materialeinvendita</h3>\r\n"
				+ "<div><a style=\"color: #0050c8; text-decoration: none; cursor: pointer;\" href=\"#\">Sito</a> <a style=\"color: #0050c8; text-decoration: none; cursor: pointer; margin: 20px;\" href=\"#\">Area Clienti</a></div>\r\n"
				+ "<p><strong> Gentile " + user_fullname + ", </strong></p>\r\n"
				+"<br><p>Ti comunichiamo il messaggio che &eacute; stato inviato dal nostro sito il " + strDateOnly + "</p>"
				+"<br><p>Messaggio inviato da: " + sendername + "</p>"
				+"<p>E-mail: " + senderemail + "</p>"
				+"<br><p>Messaggio: <br>" + sendermessage + "</p>"
				+"<br><p>Hai domande? Scrivici!</p>"
				+ "</div></div>";
	}
	
	public static String registrationSuccessResponse(String user_fullname, String username, String password) {
		return "<div style=\"width: 100%;\">\r\n" + "<div style=\"max-width: 750px; margin: auto;\">\r\n"
				+ "<h3>Materialeinvendita</h3>\r\n"
				+ "<div><a style=\"color: #0050c8; text-decoration: none; cursor: pointer;\" href=\"#\">Sito</a> <a style=\"color: #0050c8; text-decoration: none; cursor: pointer; margin: 20px;\" href=\"#\">Area Clienti</a></div>\r\n"
				+ "<p><strong> Gentile " + user_fullname + ", </strong></p>\r\n"
				+"<p>Grazie per la registrazione!</p>"
				+"<p>Il tuo account è stato creato, puoi accedere al tuo account personale utilizzando i seguenti dati:</p>"
				+"<p>L'account personale si trova su: <a href='https://tuttocitta.data-smart.it/login'>https://tuttocitta.data-smart.it/login</a></p>"
				+"<p><b>Utente:</b> " + username + "</p>"
				+"<p><b>Password:</b> " + password + "</p>"
				+"<p>Hai domande? Scrivici!</p>"
				+ "</div></div>";
	}
	
	public static String storePayReminder(String user_fullname, String orderId, String renew_price, String pay_method,
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
	
	
	
	
	public String SpamSecurity() { return "Y2F6em9jYXp6aSBtYWRvbm5hIGNhc2lubyBkaW8gc3Ryb256byBzdHJvbnppIGJhbGxhIHBhbGxhIHN0cm9uemF0YSBzdHJvbnphdGUgY29nbGlvbmUgY29nbGlvbmkgY3VsbyBjdWxpIHZhZ2luZSB2YWdpbmUgaW5jYXp6YXJlIGluY2F6emF0byBzY29wYXJlIG5lZ3JvIG1lcmRhIG1lcmRlIGltYmVjaWxlIGltYmVjaWxpIGNyZXRpbm8gY3JldGluaSBkZWZpY2llbnRlIGRlZmljaWVudGkgcG9yY28gZnJlZ2FyZSB2YWZmYW5jdWxvIHBvcmNpIGNhZ2FyZSBjYWNhcmUgcGlybGEgcGlybGUgcHV0dGFuYSBwdXR0YW5lIGJvcmRlbGxvIGJvcmRlbGxpIHNlZ2EgZnJvY2lvIGZyb2NjaSBtb3J0YWNjaSBiYXN0YXJkaSBiYXN0YXJkbyBiZXJuYXJkYSBjYXp6YXRhIGNhenphdGUgZmlnYSBwb21waW5vIHB1dHRhbmF0ZSB0cm9tYmF0YSB2YWNjYSBiaXNjaGVybyBjZXNzbyBjdWxhdHRvbmUgaW5jdWxhdG8gY3Vsb25lIGZvdHR1dG8gcGlwcHBhIHJvbXBpYmFsbGUgcm9tcGlwYWxsZSByb21waWNvZ2xvbmkgc2NhenppIHNmaWdhIHRyb2lhIHNlc3Nv"; }

	public String getEmail_sendername() {
		return email_sendername;
	}

	public void setEmail_sendername(String email_sendername) {
		this.email_sendername = email_sendername;
	}
}
