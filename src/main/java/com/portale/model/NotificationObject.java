package com.portale.model;

import java.util.List;

public class NotificationObject {
	private Long notification_id;
	private Long from;
	private List<NotificationRecevier> mto;
	private Long date;
	private Boolean seen;
	
	private String title;
	private String message;
	private Long importancyLevel; //0 Default (Logs/Updates); 1 Important (Modify data); 2 Urgent (Payments)

	private String fromUserName;
	private String fromUserSurname;
	private String toUserName;
	private String toUserSurname;

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getFromUserSurname() {
		return fromUserSurname;
	}

	public void setFromUserSurname(String fromUserSurname) {
		this.fromUserSurname = fromUserSurname;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getToUserSurname() {
		return toUserSurname;
	}

	public void setToUserSurname(String toUserSurname) {
		this.toUserSurname = toUserSurname;
	}

	public Long getNotification_id() {
		return notification_id;
	}

	public void setNotification_id(Long notification_id) {
		this.notification_id = notification_id;
	}

	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
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

	public Long getImportancyLevel() {
		return importancyLevel;
	}

	public void setImportancyLevel(Long importancyLevel) {
		this.importancyLevel = importancyLevel;
	}
}
