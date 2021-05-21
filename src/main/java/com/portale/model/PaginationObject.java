package com.portale.model;

public class PaginationObject {
	
	public PaginationObject() {
		this.PSO = new PaginationSupportObject();
		this.data = new Object();
	}
	
	private PaginationSupportObject PSO;
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public PaginationSupportObject getPSO() {
		return PSO;
	}

	public void setPSO(PaginationSupportObject pSO) {
		PSO = pSO;
	}
}
