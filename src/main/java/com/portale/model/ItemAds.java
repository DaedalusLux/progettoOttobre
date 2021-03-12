package com.portale.model;

public class ItemAds {
	private Long item_id;
	private Long storage_ref;

	private MediaObject item_media;

	public Long getItem_id() {
		return item_id;
	}

	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}

	public Long getStorage_ref() {
		return storage_ref;
	}

	public void setStorage_ref(Long storage_ref) {
		this.storage_ref = storage_ref;
	}

	public MediaObject getItem_media() {
		return item_media;
	}

	public void setItem_media(MediaObject item_media) {
		this.item_media = item_media;
	}
}
