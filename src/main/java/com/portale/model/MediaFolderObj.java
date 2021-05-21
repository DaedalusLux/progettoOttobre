package com.portale.model;

import java.util.List;

public class MediaFolderObj {
	private int media_folder_id;
	private int media_folder_reference;
	private String media_folder_name;
	private int media_folder_owner;
	private String media_folder_color;
	private String media_folder_path;
	
	private List<MediaFolderObj> media_folder_subfolder;
	private List<MediaObject> media_folder_media;
	
	public int getMedia_folder_id() {
		return media_folder_id;
	}
	public void setMedia_folder_id(int media_folder_id) {
		this.media_folder_id = media_folder_id;
	}
	public int getMedia_folder_reference() {
		return media_folder_reference;
	}
	public void setMedia_folder_reference(int media_folder_reference) {
		this.media_folder_reference = media_folder_reference;
	}
	public String getMedia_folder_name() {
		return media_folder_name;
	}
	public void setMedia_folder_name(String media_folder_name) {
		this.media_folder_name = media_folder_name;
	}
	public int getMedia_folder_owner() {
		return media_folder_owner;
	}
	public void setMedia_folder_owner(int media_folder_owner) {
		this.media_folder_owner = media_folder_owner;
	}
	public List<MediaObject> getMedia_folder_media() {
		return media_folder_media;
	}
	public void setMedia_folder_media(List<MediaObject> media_folder_media) {
		this.media_folder_media = media_folder_media;
	}
	public String getMedia_folder_color() {
		return media_folder_color;
	}
	public void setMedia_folder_color(String media_folder_color) {
		this.media_folder_color = media_folder_color;
	}
	public List<MediaFolderObj> getMedia_folder_subfolder() {
		return media_folder_subfolder;
	}
	public void setMedia_folder_subfolder(List<MediaFolderObj> media_folder_subfolder) {
		this.media_folder_subfolder = media_folder_subfolder;
	}
	public String getMedia_folder_path() {
		return media_folder_path;
	}
	public void setMedia_folder_path(String media_folder_path) {
		this.media_folder_path = media_folder_path;
	}
}
