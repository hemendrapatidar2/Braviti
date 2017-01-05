package com.tm.braveti.predictivemodel;

import java.util.ArrayList;
import java.util.List;

import com.tm.braveti.model.PieChartDTO;
import com.tm.braveti.model.TransactionHistory;
import com.tm.braveti.model.User;
import com.tm.braveti.service.LoadStaticData;

public class OfferPredictionEngine {

	private String userName;

	public OfferPredictionEngine(String userName) {
		this.userName = userName;
	}

	
	/**
	 * classify the users buying ability based on his income group. rules: 1. if
	 * the income is less than 5 ,we will chose to show him low range products
	 * offers,unless he has some high range products from the any category in
	 * his buying history. 2.if the income is between 5 and 10,he belongs to mid
	 * range product offers. 3.if the income is greater than 10,he belongs to
	 * high rang product offers.
	 * 
	 * @param user
	 * @return
	 */
	private String classifySegementBasedOnIncomeGroup(User user) {
		String incomegrp = user.getIncomegrp();
		Double income = Double.parseDouble(incomegrp);
		if (income < 5) {
			return "low";
		} else if (income > 5 && income < 11) {
			return "mid";
		} else if (income > 10) {
			return "high";
		}
		return "";
	}

	/**
	 * this method gets the transaction history for the consumer logged in.
	 * 
	 * @param transactionHistories
	 * @param id
	 * @return
	 */
	private List<TransactionHistory> getTransactionHistoryForUser(List<TransactionHistory> transactionHistories,
			String id) {
		List<TransactionHistory> transactionHistoryList = new ArrayList<TransactionHistory>();
		for (TransactionHistory transactionHistory : transactionHistories) {
			if (transactionHistory.getUserid().equalsIgnoreCase(id)) {
				transactionHistoryList.add(transactionHistory);
			}
		}
		return transactionHistoryList;
	}


	/**
	 * classify the customer buying segment base on his buying history. this
	 * method is given preference over the other method that decides based on
	 * income of consumer
	 * 
	 * @param categoryName
	 * @param amount
	 * @return
	 */
	public static String classifyPriceSegementForName(String categoryName, double amount) {
		String segement = null;

		switch (categoryName) {
		case PredictiveEngineConstants.ELECTRONICS:
			segement = defineSegement(amount, 15000, 35000);
			break;
		case PredictiveEngineConstants.CLOTHING:
			segement = defineSegement(amount, 1000, 3000);
			break;
		case PredictiveEngineConstants.FOOTWEAR:
			segement = defineSegement(amount, 1000, 3000);
			break;
		case PredictiveEngineConstants.GIFTS:
			segement = defineSegement(amount, 1000, 5000);
			break;
		case PredictiveEngineConstants.STATIONARY:
			segement = defineSegement(amount, 100, 500);
			break;
		case PredictiveEngineConstants.HOME_AND_KITCHEN:
			segement = defineSegement(amount, 10000, 30000);
			break;
		case PredictiveEngineConstants.TOYS_AND_GAMES:
			segement = defineSegement(amount, 500, 2000);
			break;
		case PredictiveEngineConstants.SPORTS_AND_FITNESS:
			segement = defineSegement(amount, 1000, 4000);
			break;
		default:

		}
		return segement;
	}
	/**
	 * classify the customer buying segment base on his buying history. this
	 * method is given preference over the other method that decides based on
	 * income of consumer
	 * 
	 * @param categoryId
	 * @param amount
	 * @return
	 */
	public static String classifyPriceSegement(String categoryId, double amount) {
		String segement = null;

		switch (categoryId) {
		case PredictiveEngineConstants.ELECTRONICS:
			segement = defineSegement(amount, 15000, 35000);
			break;
		case PredictiveEngineConstants.CLOTHING:
			segement = defineSegement(amount, 1000, 3000);
			break;
		case PredictiveEngineConstants.FOOTWEAR:
			segement = defineSegement(amount, 1000, 3000);
			break;
		case PredictiveEngineConstants.GIFTS:
			segement = defineSegement(amount, 1000, 5000);
			break;
		case PredictiveEngineConstants.STATIONARY:
			segement = defineSegement(amount, 100, 500);
			break;
		case PredictiveEngineConstants.HOME_AND_KITCHEN:
			segement = defineSegement(amount, 10000, 30000);
			break;
		case PredictiveEngineConstants.TOYS_AND_GAMES:
			segement = defineSegement(amount, 500, 2000);
			break;
		case PredictiveEngineConstants.SPORTS_AND_FITNESS:
			segement = defineSegement(amount, 1000, 4000);
			break;
		default:

		}
		return segement;
	}

	public static String defineSegement(double amount, double lowerLimt, double middleLimit) {
		if (amount < lowerLimt) {
			return "low";
		} else if (amount > lowerLimt && amount < middleLimit) {
			return "mid";
		} else if (amount > middleLimit) {
			return "high";
		}
		return "";
	}

	
	public List<PieChartDTO> getPieChartData() {
		SparkRecommender sparkEngine=new SparkRecommender();
		String userId=sparkEngine.getLoggedInUser(userName);
		float totalAmt = 0;
		List<TransactionHistory> transactionHistoriesForUser = getTransactionHistoryForUser(sparkEngine.getTransactionHistoryForUser(userId),userId);
		PieChartDTO pieChartDTO = new PieChartDTO();
		List<PieChartDTO> pieChartList = new ArrayList<PieChartDTO>();
		if (!transactionHistoriesForUser.isEmpty()) {
			pieChartDTO = getTotalAmtPerCategory(PredictiveEngineConstants.ELECTRONICS, transactionHistoriesForUser);
			if(pieChartDTO != null){ pieChartList.add(pieChartDTO);}
			pieChartDTO = getTotalAmtPerCategory(PredictiveEngineConstants.CLOTHING, transactionHistoriesForUser);
			if(pieChartDTO != null){ pieChartList.add(pieChartDTO);}
			pieChartDTO = getTotalAmtPerCategory(PredictiveEngineConstants.FOOTWEAR, transactionHistoriesForUser);
			if(pieChartDTO != null){ pieChartList.add(pieChartDTO);}
			pieChartDTO = getTotalAmtPerCategory(PredictiveEngineConstants.HOME_AND_KITCHEN, transactionHistoriesForUser);
			if(pieChartDTO != null){ pieChartList.add(pieChartDTO);}
			pieChartDTO = getTotalAmtPerCategory(PredictiveEngineConstants.SPORTS_AND_FITNESS, transactionHistoriesForUser);
			if(pieChartDTO != null){ pieChartList.add(pieChartDTO);}
			pieChartDTO = getTotalAmtPerCategory(PredictiveEngineConstants.STATIONARY, transactionHistoriesForUser);
			if(pieChartDTO != null){ pieChartList.add(pieChartDTO);}
			pieChartDTO = getTotalAmtPerCategory(PredictiveEngineConstants.TOYS_AND_GAMES, transactionHistoriesForUser);
			if(pieChartDTO != null){ pieChartList.add(pieChartDTO);}
			pieChartDTO = getTotalAmtPerCategory(PredictiveEngineConstants.GIFTS, transactionHistoriesForUser);
			if(pieChartDTO != null){ pieChartList.add(pieChartDTO);}
		
//			pieChartList.add(getTotalAmtPerCategory(PredictiveEngineConstants.ELECTRONICS, transactionHistoriesForUser));
//			pieChartList.add(getTotalAmtPerCategory(PredictiveEngineConstants.CLOTHING, transactionHistoriesForUser));
//			pieChartList.add(getTotalAmtPerCategory(PredictiveEngineConstants.FOOTWEAR, transactionHistoriesForUser));
//			pieChartList.add(getTotalAmtPerCategory(PredictiveEngineConstants.HOME_AND_KITCHEN, transactionHistoriesForUser));
//			pieChartList.add(getTotalAmtPerCategory(PredictiveEngineConstants.SPORTS_AND_FITNESS, transactionHistoriesForUser));
//			pieChartList.add(getTotalAmtPerCategory(PredictiveEngineConstants.STATIONARY, transactionHistoriesForUser));
//			pieChartList.add(getTotalAmtPerCategory(PredictiveEngineConstants.TOYS_AND_GAMES, transactionHistoriesForUser));
//			pieChartList.add(getTotalAmtPerCategory(PredictiveEngineConstants.GIFTS, transactionHistoriesForUser));
			
			for (PieChartDTO pieData : pieChartList) {
				totalAmt = totalAmt + pieData.getTotalAmtPerCategory();			
			}
			
			for (PieChartDTO pieData : pieChartList) {
				float percentAmt = (pieData.getTotalAmtPerCategory()/totalAmt)*100;
				pieData.setPercentAmt(percentAmt);	
			}
		}
		return pieChartList;

	}

	private PieChartDTO getTotalAmtPerCategory(String category, List<TransactionHistory> transactionHistoriesForUser) {
		float totalAmtPerCategory = 0;
		PieChartDTO pieObj = null;
		for (TransactionHistory transHistory : transactionHistoriesForUser) {
			if (transHistory.getDescription().equalsIgnoreCase(category)) {
				totalAmtPerCategory += Float.parseFloat(transHistory.getAmount());
			}
		}
		if(totalAmtPerCategory > 0){
			pieObj = new PieChartDTO();
			pieObj.setCategory(category);
			pieObj.setTotalAmtPerCategory(totalAmtPerCategory);
		}		
		return pieObj;
	}

}
