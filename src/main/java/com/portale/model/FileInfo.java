package com.portale.model;

public class FileInfo {
	private String name;
	private String path;
	private Long storageId;
	
	  public FileInfo(String name, String path, Long storageId) {
	    this.name = name;
	    this.path = path;
	    this.storageId = storageId;
	  }

	  public String getName() {
	    return this.name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public String getUrl() {
	    return this.path;
	  }

	  public void setUrl(String path) {
	    this.path = path;
	  }

	public Long getStorageId() {
		return storageId;
	}

	public void setStorageId(Long storageId) {
		this.storageId = storageId;
	}
}
