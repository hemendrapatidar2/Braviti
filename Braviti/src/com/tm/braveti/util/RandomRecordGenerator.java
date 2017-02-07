package com.tm.braveti.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormatSymbols;
import java.util.GregorianCalendar;

import com.tm.braveti.predictivemodel.CategoryCache;
import com.tm.braveti.predictivemodel.PredictiveEngineConstants;

public class RandomRecordGenerator {
	
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String COMMA_DELIMITER = ",";

	
public static void writeRecord() {
	  FileWriter fileWriter = null;
	  File file=new File("transactionHistory.csv");
	  try {
		fileWriter = new FileWriter(file);
	
	for (int i = 1; i <=10000; i++) {
		StringBuilder finalRecord = new StringBuilder("");
		
		String randomPurchaseDate = getRandomPurchaseDate();
		String randomeUserId = getRandomUserId();
		String randomPurchaseCategoryId = getRandomPurchaseCategory();
		String randomOutletId = getRandomOutletId();
		String getRandomCategoryNameGenerated = getrandomCategoryNameGenerated(randomPurchaseCategoryId);
		String randomPurchaseAmount = getRandomPurchaseAmount(randomPurchaseCategoryId);
		
		finalRecord.append(String.valueOf(i));
		finalRecord.append(COMMA_DELIMITER);
		finalRecord.append(randomeUserId);
		finalRecord.append(COMMA_DELIMITER);
		finalRecord.append(randomPurchaseDate);
		finalRecord.append(COMMA_DELIMITER);
		finalRecord.append(randomPurchaseAmount);
		finalRecord.append(COMMA_DELIMITER);
		finalRecord.append(randomOutletId);
		finalRecord.append(COMMA_DELIMITER);
		finalRecord.append(getRandomCategoryNameGenerated);
		finalRecord.append(COMMA_DELIMITER);
		finalRecord.append(randomPurchaseCategoryId);
		finalRecord.append(NEW_LINE_SEPARATOR);
		
//writing to the file now
		fileWriter.append(finalRecord);
		System.out.println(i);
	}
		
	} catch (IOException e) {
		
	}finally {
		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
}

private static String getrandomCategoryNameGenerated(String randomPurchaseCategoryId) {
	return CategoryCache.getCategoryNameForId(Integer.parseInt(randomPurchaseCategoryId));	 
}

private static String getRandomPurchaseAmount(String getRandomCategoryNameGenerated) {
	
	int amount = 0;
	
	switch (getRandomCategoryNameGenerated) {
	case PredictiveEngineConstants.ELECTRONICS:
		amount = randBetween(15000, 35000);
		break;
	case PredictiveEngineConstants.CLOTHING:
		amount = randBetween(1000, 3000);
		break;
	case PredictiveEngineConstants.FOOTWEAR:
		amount = randBetween(1000, 3000);
		break;
	case PredictiveEngineConstants.GIFTS:
		amount = randBetween(1000, 5000);
		break;
	case PredictiveEngineConstants.STATIONARY:
		amount = randBetween(100, 500);
		break;
	case PredictiveEngineConstants.HOME_AND_KITCHEN:
		amount = randBetween(15000, 30000);
		break;
	case PredictiveEngineConstants.TOYS_AND_GAMES:
		amount = randBetween(500, 3000);
		break;
	case PredictiveEngineConstants.SPORTS_AND_FITNESS:
		amount = randBetween(1000, 4000);
		break;
	default:

	}
	
	return String.valueOf(amount);
	
}

private static String getRandomOutletId() {
	 int outletId = randBetween(1, 25);
	 return String.valueOf(outletId);
}

private static String getRandomPurchaseCategory() {
	 int categoryId = randBetween(1, 8);
	 return String.valueOf(categoryId);
	
}

private static String getRandomUserId() {
	
	 int userId = randBetween(1, 100);
	 return String.valueOf(userId);
	
}

private static String getRandomPurchaseDate() {
	 GregorianCalendar gc = new GregorianCalendar();
	 DateFormatSymbols dfs = new DateFormatSymbols();
     String[] months = dfs.getMonths();
     int year = randBetween(2000, 2017);
     gc.set(gc.YEAR, year);
     int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
     gc.set(gc.DAY_OF_YEAR, dayOfYear);
     int month=gc.get(gc.MONTH) ;
     String date = gc.get(gc.DAY_OF_MONTH)+"-"+months[month]+ "-" +gc.get(gc.YEAR);
	return date;

}
public static int randBetween(int start, int end) {
    return start + (int)Math.round(Math.random() * (end - start));
}

public static void main(String[] args) {
	RandomRecordGenerator rrg=new RandomRecordGenerator();
	rrg.writeRecord();
}
}
