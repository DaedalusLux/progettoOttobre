package com.portale.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.portale.model.StatisticDetailsObj;
import com.portale.model.Statistics_Views;

public interface StatisticsMapper {
	public void AddWebSiteVisit(@Param("host") String host, @Param("datetime") Long datetime);
	public List<Statistics_Views> GetVisitStatus();
	
	//public void AddAddWSV_Page(@Param("host") String host, @Param("datetime") Long datetime);
	public StatisticDetailsObj GetWSV_Prod(@Param("wvs_obj_id") int wvs_obj_id, @Param("wvs_details") int wvs_details);
	public void AddWSV_Prod(@Param("wvs_obj_id") int wvs_obj_id, @Param("wvs_details") int wvs_details);
	public void CreateWSV_Prod(@Param("wvs_obj_id") int wvs_obj_id, @Param("wvs_details") int wvs_details);
	public void DeleteWSV_Prod(@Param("wvs_obj_id") int wvs_obj_id, @Param("wvs_details") int wvs_details);

}
