package com.portale.model;

public class PaginationSupportObject {
	
	public PaginationSupportObject() {
		this.totalResults = 0;
		this.lastResultID = 0;
	}
	
	private int totalResults;
	private int lastResultID;

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	public int getLastResultID() {
		return lastResultID;
	}

	public void setLastResultID(int lastResultID) {
		this.lastResultID = lastResultID;
	}
}
