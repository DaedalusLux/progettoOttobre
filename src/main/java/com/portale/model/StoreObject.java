package com.portale.model;

import java.util.Date;
import java.util.List;

public class StoreObject {
	private Long store_id;
	private String store_name;
	private Long store_owner;
	private String owner_name;
	private String owner_surname;
	private Boolean open;
	private Boolean confirmed;
	private Long store_depth;
	private Long store_place;
	private Date place_expire;
	private String category;
	private Date creation;
	private Date valid_until;
	
	public Long getStore_place() {
		return store_place;
	}

	public void setStore_place(Long store_place) {
		this.store_place = store_place;
	}

	public Date getPlace_expire() {
		return place_expire;
	}

	public void setPlace_expire(Date place_expire) {
		this.place_expire = place_expire;
	}

	
	private List<StorageObject> Storage;
	
	private MediaObject media;
	
	private ThemeObject theme;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getValid_until() {
		return valid_until;
	}

	public void setValid_until(Date valid_until) {
		this.valid_until = valid_until;
	}

	public Long getStore_id() {
		return store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public Long getStore_owner() {
		return store_owner;
	}

	public void setStore_owner(Long store_owner) {
		this.store_owner = store_owner;
	}

	public ThemeObject getTheme() {
		return theme;
	}

	public void setTheme(ThemeObject theme) {
		this.theme = theme;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public String getOwner_surname() {
		return owner_surname;
	}

	public void setOwner_surname(String owner_surname) {
		this.owner_surname = owner_surname;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public List<StorageObject> getStorage() {
		return Storage;
	}

	public void setStorage(List<StorageObject> storage) {
		Storage = storage;
	}

	public Long getStore_depth() {
		return store_depth;
	}

	public void setStore_depth(Long store_depth) {
		this.store_depth = store_depth;
	}

	public MediaObject getMedia() {
		return media;
	}

	public void setMedia(MediaObject media) {
		this.media = media;
	}

}
