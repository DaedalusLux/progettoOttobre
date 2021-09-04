package com.portale.config;

public class Globals {
	public enum DELETE_MODE {ALL,SAFE,ONLY_NOTUSED,CUSTOM;}
	public static DELETE_MODE getDeleteMode(int mode) {
		switch (mode) {
		case 1:
			return DELETE_MODE.ALL;
		case 2:
			return DELETE_MODE.ONLY_NOTUSED;
		case 3:
			return DELETE_MODE.CUSTOM;
		default:
			return DELETE_MODE.SAFE;
		}
	}
}