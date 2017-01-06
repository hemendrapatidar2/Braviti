package com.tm.braveti.predictivemodel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.codehaus.jackson.map.ObjectMapper;

public class PreferencesUtility {
	// private static final String USERPREF_RESOURCES =
	// "./com/tm/braveti/resources/userPreferences.json";
	private static final String USERPREF_RESOURCES = "D:/userPreferences.json";
	private static BufferedWriter bw = null;
	private static FileWriter fw = null;
	private static BufferedReader br = null;
	private static FileReader fr = null;

	public UserPreferecesJson readUserPreferences(String userName) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		UserPreferecesJson userPreferences = mapper.readValue(new File(USERPREF_RESOURCES), UserPreferecesJson.class);
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
			UserPreferecesJson userPreferecesJson = new UserPreferecesJson(userId, Arrays.asList(categaoryStr.split(",")), Arrays.asList(priceRangeStr.split(",")));
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
