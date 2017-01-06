package com.tm.braveti.predictivemodel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class PreferencesUtility {
	// private static final String USERPREF_RESOURCES =
	// "./com/tm/braveti/resources/userPreferences.json";
	private static final String USERPREF_RESOURCES = "D:/userPreferences.json";
	private static BufferedWriter bw = null;
	private static FileWriter fw = null;
	private static BufferedReader br = null;
	private static FileReader fr = null;

	public UserPreferencesJson readUserPreferences(String userName) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		UserPreferencesJson userPreferences = mapper.readValue(new File(USERPREF_RESOURCES), UserPreferencesJson.class);
		return userPreferences;
	}

	private static void writePreferences(String content) throws IOException {
		fw = new FileWriter(USERPREF_RESOURCES);
		bw = new BufferedWriter(fw);
		bw.write(content);
		if (bw != null)
			bw.close();

		if (fw != null)
			fw.close();
	}

	public static void setUserPreferences(String userId, String categaoryStr, String priceRangeStr) {
		try {
			List<String> categoryList = Arrays.asList(categaoryStr.split(","));
			List<String> priceRangeList = Arrays.asList(priceRangeStr.split(","));
			categoryList=categoryList.get(0).isEmpty() ? new ArrayList<String>() : categoryList;
			priceRangeList=priceRangeList.get(0).isEmpty() ? new ArrayList<String>() : priceRangeList;
			
			UserPreferencesJson userPreferecesJson = new UserPreferencesJson(userId, categoryList, priceRangeList);
			System.out.println(userPreferecesJson);
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(userPreferecesJson);
			writePreferences(jsonInString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String args[]) {
		try {
			PreferencesUtility preferencesUtility = new PreferencesUtility();
			preferencesUtility.setUserPreferences("latesh", "1,2,3", "High,Low");
			preferencesUtility.readUserPreferences("latesh");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
