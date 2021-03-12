package com.portale.model;

public class ThemeObject {
	private Long themeId;
	private String themeName;
	private String themePreviewImage;
	private Long depth;//Store tree depth
	//A single store can contain products (no depth/ 0)
	//A single store can contain subcategories (depth = 1)
	//Subcategories contains products
	
	public Long getThemeId() {
		return themeId;
	}
	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}
	public String getThemeName() {
		return themeName;
	}
	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}
	public String getThemePreviewImage() {
		return themePreviewImage;
	}
	public void setThemePreviewImage(String themePreviewImage) {
		this.themePreviewImage = themePreviewImage;
	}
	public Long getDepth() {
		return depth;
	}
	public void setDepth(Long depth) {
		this.depth = depth;
	}
}
