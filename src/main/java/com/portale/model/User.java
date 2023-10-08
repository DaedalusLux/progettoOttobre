package com.portale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {

	private int id;
	private String username;

	private String name;
	private String email;
	private String phone;
	private String wallet_address;

	//Il suo rank (Gifter, ...)
	private Rank rank;
	
	private Boolean isAdmin;
	private Boolean isLocked;
	
	//ROLE_ADMIN nel caso isAdmin TRUE dal DB (Solo per TokenJWT Backend)
	@JsonIgnore
	private String authorities;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthorities() {
		return authorities;
	}

	public String getWallet_address() {
		return wallet_address;
	}

	public void setWallet_address(String wallet_address) {
		this.wallet_address = wallet_address;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
		if(this.isAdmin) {
			this.authorities = "ROLE_ADMIN";
		}
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
