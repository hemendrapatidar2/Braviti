package com.tm.braveti.predictivemodel;

import java.util.HashMap;
import java.util.List;

public class UserPreferences {
	
	private String userID;
	
	
	/*
	 * store data in following format:
	 * 
	 * key:category::value:categoryId1,categoryId2(list)
	 * key:priceRange:: value:low,high(list)
	 * */
	private HashMap<String, List<String>> preferenceMap;

	public HashMap<String, List<String>> getPreferenceMap() {
		return preferenceMap;
	}

	public void setPreferenceMap(HashMap<String, List<String>> preferenceMap) {
		this.preferenceMap = preferenceMap;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

}
