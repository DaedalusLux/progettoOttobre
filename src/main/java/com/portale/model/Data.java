package com.portale.model;

import java.util.List;

public class Data {
	private int current_page;
	private int results_per_page;
	private List<?> data;
	private int total_results;
	
	public int getCurrent_page() {
		return current_page;
	}
	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}
	public int getResults_per_page() {
		return results_per_page;
	}
	public void setResults_per_page(int results_per_page) {
		this.results_per_page = results_per_page;
	}
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	public int getTotal_results() {
		return total_results;
	}
	public void setTotal_results(int total_results) {
		this.total_results = total_results;
	}
}
