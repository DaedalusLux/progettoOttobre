package com.portale.model;

import java.util.Date;
import java.util.List;

public class MediaObject {
	private Long media_id;
	private String media_name;
	private String media_path;
	private Long media_owner;
	private Date media_pubblication_date;
	private boolean media_hasthumbnail;
	private String media_extension;
	
	private List<Long> selected_media_id;
	
	public Long getMedia_id() {
		return media_id;
	}
	public void setMedia_id(Long media_id) {
		this.media_id = media_id;
	}
	public String getMedia_name() {
		return media_name;
	}
	public void setMedia_name(String media_name) {
		this.media_name = media_name;
	}
	public String getMedia_path() {
		return media_path;
	}
	public void setMedia_path(String media_path) {
		this.media_path = media_path;
	}
	public Long getMedia_owner() {
		return media_owner;
	}
	public void setMedia_owner(Long media_owner) {
		this.media_owner = media_owner;
	}
	public Date getMedia_pubblication_date() {
		return media_pubblication_date;
	}
	public void setMedia_pubblication_date(Date media_pubblication_date) {
		this.media_pubblication_date = media_pubblication_date;
	}
	public List<Long> getSelected_media_id() {
		return selected_media_id;
	}
	public void setSelected_media_id(List<Long> selected_media_id) {
		this.selected_media_id = selected_media_id;
	}

	public String getMedia_extension() {
		return media_extension;
	}
	public void setMedia_extension(String media_extension) {
		this.media_extension = media_extension;
	}
	public boolean isMedia_hasthumbnail() {
		return media_hasthumbnail;
	}
	public void setMedia_hasthumbnail(boolean media_hasthumbnail) {
		this.media_hasthumbnail = media_hasthumbnail;
	}
}
