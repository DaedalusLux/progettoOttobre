package com.portale.model;

import java.util.Date;
import java.util.List;

public class NotificationObject {
	private int notification_id;
	private int from;
	private List<NotificationRecevier> mto;
	private Date date;
	private Boolean seen;
	
	private String title;
	private String message;
	private int importancyLevel; //0 Default (Logs/Updates); 1 Important (Modify data); 2 Urgent (Payments)
	private int type;
	private String fromUserFullname;

	public int getNotification_id() {
		return notification_id;
	}

	public void setNotification_id(int notification_id) {
		this.notification_id = notification_id;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public Boolean getSeen() {
		return seen;
	}

	public void setSeen(Boolean seen) {
		this.seen = seen;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public List<NotificationRecevier> getMto() {
		return mto;
	}

	public void setMto(List<NotificationRecevier> mto) {
		this.mto = mto;
	}

	public int getImportancyLevel() {
		return importancyLevel;
	}

	public void setImportancyLevel(int importancyLevel) {
		this.importancyLevel = importancyLevel;
	}

	public String getFromUserFullname() {
		return fromUserFullname;
	}

	public void setFromUserFullname(String fromUserFullname) {
		this.fromUserFullname = fromUserFullname;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
