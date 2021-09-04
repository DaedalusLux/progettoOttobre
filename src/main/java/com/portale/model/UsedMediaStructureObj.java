package com.portale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class UsedMediaStructureObj {
	private int media_id;
	private String media_name;
	private String media_path;
	private JSONObject folder_tree;
	private int mf_owner;
	private String item_preview_media;
	private String storage_media;
	private String store_media;
	
	public int getMedia_id() {
		return media_id;
	}
	public void setMedia_id(int media_id) {
		this.media_id = media_id;
	}
	public String getMedia_name() {
		return media_name;
	}
	public void setMedia_name(String media_name) {
		this.media_name = media_name;
	}
	public String getItem_preview_media() {
		return item_preview_media;
	}
	public void setItem_preview_media(String item_preview_media) {
		this.item_preview_media = item_preview_media;
	}
	public String getStorage_media() {
		return storage_media;
	}
	public void setStorage_media(String storage_media) {
		this.storage_media = storage_media;
	}
	public String getStore_media() {
		return store_media;
	}
	public void setStore_media(String store_media) {
		this.store_media = store_media;
	}
	@JsonIgnore
	public boolean isAnyUsed() {
		if(this.item_preview_media == null && this.storage_media == null && this.store_media == null) {
			return false;
		}
		return true;
	}
	public String getMedia_path() {
		return media_path;
	}
	public void setMedia_path(String media_path) {
		this.media_path = media_path;
	}
	public JSONObject getFolder_tree() {
		return folder_tree;
	}
	public void setFolder_tree(String folder_tree) throws ParseException {
		JSONParser parser = new JSONParser();
		this.folder_tree = (JSONObject) parser.parse(folder_tree);
	}
	public int getMf_owner() {
		return mf_owner;
	}
	public void setMf_owner(int mf_owner) {
		this.mf_owner = mf_owner;
	}
}
