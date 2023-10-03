package com.portale.model;

public class UserObject {

	private int usr_id;
	private String usr_username;
	//ROLE_ADMIN nel caso isAdmin TRUE dal DB
	private String authorities;
	// TODO Da impostare il locked
	private Boolean locked;

	private String nomecognome;
	private String email;
	private String telefono;
	private String wallet_address;

	private int room_id;
	private int room_level;
	private int rank_level;
	private String rank_name;
	
	private Boolean isAdmin;

	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

	public int getRoom_level() {
		return room_level;
	}

	public void setRoom_level(int room_level) {
		this.room_level = room_level;
	}

	public int getRank_level() {
		return rank_level;
	}

	public void setRank_level(int rank_level) {
		this.rank_level = rank_level;
	}

	public String getRank_name() {
		return rank_name;
	}

	public void setRank_name(String rank_name) {
		this.rank_name = rank_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getUsr_id() {
		return usr_id;
	}

	public void setUsr_id(int usr_id) {
		this.usr_id = usr_id;
	}

	public String getUsr_username() {
		return usr_username;
	}

	public void setUsr_username(String usr_username) {
		this.usr_username = usr_username;
	}

	public String getAuthorities() {
		return authorities;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String getWallet_address() {
		return wallet_address;
	}

	public void setWallet_address(String wallet_address) {
		this.wallet_address = wallet_address;
	}

	public String getNomecognome() {
		return nomecognome;
	}

	public void setNomecognome(String nomecognome) {
		this.nomecognome = nomecognome;
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

}
