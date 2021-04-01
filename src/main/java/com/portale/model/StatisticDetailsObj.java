package com.portale.model;

public class StatisticDetailsObj {
	private int wvs_obj_id;
	private int wvs_details;
	
	public int getWvs_obj_id() {
		return wvs_obj_id;
	}
	public void setWvs_obj_id(int wvs_obj_id) {
		this.wvs_obj_id = wvs_obj_id;
	}
	public int getWvs_details() {
		return wvs_details;
	}
	public void setWvs_details(int wvs_details) {
		this.wvs_details = wvs_details;
	}
	
	@Override
	public String toString() {
		return "StatisticDetailsObj [wvs_obj_id=" + wvs_obj_id + ", wvs_details=" + wvs_details + "]";
	}
}
