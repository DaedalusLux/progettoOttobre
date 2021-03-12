package com.portale.model;

import java.util.Date;

public class HomeSettingsObj {
	private int id_homepage;
	private int col_num;
	private Boolean is_first_banner;

	public int getId_homepage() {
		return id_homepage;
	}

	public void setId_homepage(int id_homepage) {
		this.id_homepage = id_homepage;
	}

	public int getCol_num() {
		return col_num;
	}

	public void setCol_num(int col_num) {
		this.col_num = col_num;
	}

	public Boolean getIs_first_banner() {
		return is_first_banner;
	}

	public void setIs_first_banner(Boolean is_first_banner) {
		this.is_first_banner = is_first_banner;
	}

}
