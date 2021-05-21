package com.portale.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLoginDetails {

	private int userId;
	private String idToken;
	private String userName;
	private String userRole;

	public UserLoginDetails(int userId, String idToken, String userName, String userRole) {
		this.userId = userId;
		this.idToken = idToken;
		this.userName = userName;
		this.userRole = userRole;
	}

	@JsonProperty("token")
	String getIdToken() {
		return idToken;
	}

	void setIdToken(String idToken) {
		this.idToken = idToken;
	}

	@JsonProperty("username")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty("role")
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	@JsonProperty("id")
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
