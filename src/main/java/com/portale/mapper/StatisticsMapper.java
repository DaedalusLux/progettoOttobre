package com.portale.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.portale.model.Statistics_Views;

public interface StatisticsMapper {
	public void AddWebSiteVisit(@Param("host") String host, @Param("datetime") Long datetime);
	public List<Statistics_Views> GetVisitStatus();
}
