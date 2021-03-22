package com.portale.model;

public class EmailObj {
	
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
}
