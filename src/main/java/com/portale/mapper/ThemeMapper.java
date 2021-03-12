package com.portale.mapper;

import org.apache.ibatis.annotations.Param;

import com.portale.model.HomeSettingsObj;
import com.portale.model.ThemeObject;

public interface ThemeMapper {
	public void AddNewTheme(@Param("themeObj") ThemeObject themeObj, @Param("themeName") String themeName,
			@Param("themeImage") String themeImage, @Param("themeCSS") String themeCSS);

	public void updateHomeSettings(@Param("col_num") int col_num, @Param("isfirstbanner") Boolean isfirstbanner);

	public HomeSettingsObj getHomeSettings();
}