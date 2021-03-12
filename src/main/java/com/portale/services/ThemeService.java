package com.portale.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.portale.mapper.StoreMapper;
import com.portale.mapper.ThemeMapper;
import com.portale.model.HomeSettingsObj;
import com.portale.model.ThemeObject;

@Service
@Repository
public class ThemeService {
	@Autowired
	private StoreMapper storeMapper;

	@Autowired
	private ThemeMapper themeMapper;
	
	public HomeSettingsObj homeSettingMem;

	public List<ThemeObject> GetStoreThemesData() {
		List<ThemeObject> Themes = new ArrayList<ThemeObject>();
		Themes = (List<ThemeObject>) storeMapper.GetThemesData();
		return Themes;
	}

	public void AddNewTheme(ThemeObject theme, String themeName, String themeImage, String themeCSS) {
		themeMapper.AddNewTheme(theme, themeName, themeImage, themeCSS);
	}

	public HomeSettingsObj getHomeSettingsObjs() {
		return themeMapper.getHomeSettings();
	}

	public void updateHomePage(int col_num, Boolean isFirstBanner) {
		themeMapper.updateHomeSettings(col_num, isFirstBanner);
	}
}
