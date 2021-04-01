package com.portale.model;

import java.util.Date;
import java.util.List;

//webstatistics_visits
public class Statistics_Views {
	private Long statistic_id;
	private String ip_address;
	private Date datetime;
	private Long msdatetime;
	private List<StatisticDetailsObj> sdo;
	
	public Long getStatistic_id() {
		return statistic_id;
	}

	public void setStatistic_id(Long statistic_id) {
		this.statistic_id = statistic_id;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Long getMsdatetime() {
		return msdatetime;
	}

	public void setMsdatetime(Long msdatetime) {
		this.msdatetime = msdatetime;
	}

	public List<StatisticDetailsObj> getSdo() {
		return sdo;
	}

	public void setSdo(List<StatisticDetailsObj> sdo) {
		this.sdo = sdo;
	}

}
