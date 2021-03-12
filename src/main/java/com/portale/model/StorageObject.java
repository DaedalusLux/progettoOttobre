package com.portale.model;

import java.util.List;

public class StorageObject {
	private Long storage_id;
	private int store_ref;
	private Long substorage_ref;
	private String storage_name;

	private MediaObject storage_media;

	private List<ItemObject> items;

	private ThemeObject theme;

	public Long getStorage_id() {
		return storage_id;
	}

	public void setStorage_id(Long storage_id) {
		this.storage_id = storage_id;
	}

	public int getStore_ref() {
		return store_ref;
	}

	public void setStore_ref(int store_ref) {
		this.store_ref = store_ref;
	}

	public List<ItemObject> getProduct() {
		return items;
	}

	public void setProduct(List<ItemObject> items) {
		this.items = items;
	}

	public String getStorage_name() {
		return storage_name;
	}

	public void setStorage_name(String storage_name) {
		this.storage_name = storage_name;
	}

	public ThemeObject getTheme() {
		return theme;
	}

	public void setTheme(ThemeObject theme) {
		this.theme = theme;
	}

	public Long getSubstorage_ref() {
		return substorage_ref;
	}

	public void setSubstorage_ref(Long substorage_ref) {
		this.substorage_ref = substorage_ref;
	}

	public MediaObject getStorage_media() {
		return storage_media;
	}

	public void setStorage_media(MediaObject storage_media) {
		this.storage_media = storage_media;
	}
}
