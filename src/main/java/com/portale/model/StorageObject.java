package com.portale.model;

import java.util.List;

public class StorageObject {
	private Long storage_id;
	private int store_ref;
	private Long substorage_ref;
	private String storage_name;
	private String ref_store_name;
	private int store_items_type;
	private String store_items_type_desc;

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

	public String getRef_store_name() {
		return ref_store_name;
	}

	public void setRef_store_name(String ref_store_name) {
		this.ref_store_name = ref_store_name;
	}

	public int getStore_items_type() {
		return store_items_type;
	}

	public void setStore_items_type(int store_items_type) {
		this.store_items_type = store_items_type;
	}

	public String getStore_items_type_desc() {
		return store_items_type_desc;
	}

	public void setStore_items_type_desc(String store_items_type_desc) {
		this.store_items_type_desc = store_items_type_desc;
	}
}
