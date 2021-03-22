package com.portale.model;

import java.util.Date;
import java.util.List;

public class UserObject {

	private Long usr_id;
	private String usr_username;
	private String usr_password;
	private String usr_oldpassword;
	private String usr_organization;
	//authorities = ROLE_...
	private String authorities;
	//role_name Utente/Admin
	private String role_name;
	private Long role_id;
	private Boolean locked;
	
	private List<String> usr_store;
	private String nome;
	private String cognome;
	private String email;
	private String indirizzo;
	private String citta;
	private String provincia;
	private Long codicePostale;
	private String telefono;
	private String codiceFiscale;
	private Date data_registrazione;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public Long getUsr_id() {
		return usr_id;
	}
	public void setUsr_id(Long usr_id) {
		this.usr_id = usr_id;
	}
	public String getUsr_username() {
		return usr_username;
	}
	public void setUsr_username(String usr_username) {
		this.usr_username = usr_username;
	}
	public String getUsr_password() {
		return usr_password;
	}
	public void setUsr_password(String usr_password) {
		this.usr_password = usr_password;
	}
	public String getAuthorities() {
		return authorities;
	}
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	public String getUsr_organization() {
		return usr_organization;
	}
	public void setUsr_organization(String usr_organization) {
		this.usr_organization = usr_organization;
	}
	public Boolean getLocked() {
		return locked;
	}
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	public Long getCodicePostale() {
		return codicePostale;
	}
	public void setCodicePostale(Long codicePostale) {
		this.codicePostale = codicePostale;
	}
	public List<String> getUsr_store() {
		return usr_store;
	}
	public void setUsr_store(List<String> usr_store) {
		this.usr_store = usr_store;
	}
	public Date getData_registrazione() {
		return data_registrazione;
	}
	public void setData_registrazione(Date data_registrazione) {
		this.data_registrazione = data_registrazione;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	public String getUsr_oldpassword() {
		return usr_oldpassword;
	}
	public void setUsr_oldpassword(String usr_oldpassword) {
		this.usr_oldpassword = usr_oldpassword;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

}
