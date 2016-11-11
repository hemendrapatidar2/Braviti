package com.tm.braveti.predictivemodel;

import java.util.ArrayList;
import java.util.List;

import com.tm.braveti.exception.UserNotFoundException;
import com.tm.braveti.model.OfferDTO;
import com.tm.braveti.model.Outlet;
import com.tm.braveti.model.TransactionHistory;
import com.tm.braveti.model.User;
import com.tm.braveti.service.LoadStaticData;

public class OfferPredictionEngine {
	
	private String userName;
	private String location;
	private LoadStaticData staticData;
	private List<FilterCriteria> filtersForSuggestions=new ArrayList<>();

	public OfferPredictionEngine(String userName, String location,LoadStaticData staticData) {
		this.userName = userName;
		this.location = location;
		this.staticData = staticData;
	}

	/**
	 * this is the main method exposed to the rest API for the user.
	 * it analyzes the user's buying pattern and generates the list of 
	 * offers suggestion which the user may be interested in.
	 * @return
	 * @throws UserNotFoundException
	 */
	public List<OfferDTO> showOfferPredictions() throws UserNotFoundException {
		User user = CustomerInfoHelper.getUser(this.userName,staticData);
		if(user==null){
			throw new UserNotFoundException("this user does not exist");
		}
		
		if(CustomerInfoHelper.checkForBithdayWeek(user))
		{
			FilterCriteria criteria=new FilterCriteria();
			criteria.setCategoryName(PredictiveEngineConstants.GIFTS);
			String priceSegement = classifySegementBasedOnIncomeGroup(user);
			if(priceSegement.isEmpty()){
				priceSegement="low";
			}
			criteria.setPriceSegement(priceSegement);
			filtersForSuggestions.add(criteria);
		}
		
		
		List<TransactionHistory>transactionHistoriesForUser=getTransactionHistoryForUser(staticData.getTransactionHistories(),user.getId());
		if(transactionHistoriesForUser.isEmpty()){
			FilterCriteria criteria=new FilterCriteria();
			criteria.setCategoryName(PredictiveEngineConstants.LOCATION);
			filtersForSuggestions.add(criteria);
		}else{
			
			filtersForSuggestions=findFiltersFromTransactionsHistory(filtersForSuggestions,transactionHistoriesForUser);
		}
		OfferStoresHelper offerStoreHelper=new OfferStoresHelper();
		
		List<OfferDTO> offerSuggestion = offerStoreHelper.getOfferSuggestion(filtersForSuggestions,this.staticData,location);
		return offerSuggestion;
	
	}

	/**
	 * classify the users buying ability based on his income group.
	 * rules:
	 * 1. if the income is less than 5 ,we will chose to show him low range products offers,unless he has some high range products from the 
	 * any category in his buying history.
	 * 2.if the income is between 5 and 10,he belongs to mid range product offers.
	 * 3.if the income is greater than 10,he belongs to high rang product offers.
	 * @param user
	 * @return
	 */
	private String classifySegementBasedOnIncomeGroup(User user) {
		String incomegrp = user.getIncomegrp();
		Double income=Double.parseDouble(incomegrp);
		if(income<5){
			return "low";
		}else if(income>5 && income<11){
			return "mid";
		}else if(income>10){
			return "high";
		}
		return "";
	}

	/**
	 * this method gets the transaction history for the consumer logged in.
	 * @param transactionHistories
	 * @param id
	 * @return
	 */
	private List<TransactionHistory> getTransactionHistoryForUser(List<TransactionHistory> transactionHistories, String id) {
		List<TransactionHistory>transactionHistoryList=new ArrayList<TransactionHistory>();
		for (TransactionHistory transactionHistory : transactionHistories) {
			if(transactionHistory.getUserid().equals(id)){
				transactionHistoryList.add(transactionHistory);
			}
		}
		return transactionHistoryList;
	}

	/**
	 * find the filter criteria from user buying habit,which is then used to make offer recommendation in his locality.
	 * @param filterCriteriaList
	 * @param transactionHistories
	 * @return
	 */
	private List<FilterCriteria> findFiltersFromTransactionsHistory(List<FilterCriteria> filterCriteriaList,List<TransactionHistory> transactionHistories) {
		List<String> processedCategory=new ArrayList<>();
		
		for (TransactionHistory transactionHistory : transactionHistories) {
			
			String categoryName=transactionHistory.getDescription(); 
			String date = transactionHistory.getDate();
			
			/*transactions older than two months wont be used to make suggestions*/
			
//			checkDatevalidity();
			if(processedCategory.contains(categoryName.toLowerCase())){
				continue;
			}
			else{
				processedCategory.add(categoryName.toLowerCase());
			}
			String amount = transactionHistory.getAmount();
			String priceSegement=classifyPriceSegement(categoryName,Double.parseDouble(amount));
			
			/*setting the filter criteria
			 * 1. category name
			 * 2. price segement
			 * */
			
			FilterCriteria filterCriteria=new FilterCriteria();
			filterCriteria.setCategoryName(categoryName);
			filterCriteria.setPriceSegement(priceSegement);
			filterCriteriaList.add(filterCriteria);
		}
		
		
		return filterCriteriaList;
	}

	
	/**
	 * classify the customer buying segment base on his buying history.
	 * this method is given preference over the other method that decides based on income of consumer
	 * @param categoryName
	 * @param amount
	 * @return
	 */
	private String classifyPriceSegement(String categoryName , double amount) {
		String segement = null;
		
		switch(categoryName){
		case PredictiveEngineConstants.ELECTRONICS:
			segement=defineSegement(amount,15000,35000);
			break;
		case PredictiveEngineConstants.CLOTHING:
			segement=defineSegement(amount,1000,3000);
			break;
		case PredictiveEngineConstants.FOOTWEAR:
			segement=defineSegement(amount,1000,3000);
			break;
		case PredictiveEngineConstants.GIFTS:
			segement=defineSegement(amount,1000,5000);
			break;
		case PredictiveEngineConstants.STATIONARY:
			segement=defineSegement(amount,100,500);
			break;
		case PredictiveEngineConstants.HOME_AND_KITCHEN:
			segement=defineSegement(amount,10000,30000);
			break;
		case PredictiveEngineConstants.TOYS_AND_GAMES:
			segement=defineSegement(amount,500,2000);
			break;
		case PredictiveEngineConstants.SPORTS_AND_FITNESS:
			segement=defineSegement(amount,1000,4000);
			break;
		default:


		}
		return segement;
	}

	private String defineSegement(double amount,double lowerLimt,double middleLimit) {
		if(amount<lowerLimt){
			return "low";
		}else if(amount>lowerLimt && amount<middleLimit){
			return "mid";
		}else if(amount>middleLimit){
			return "high";
		}
		return "";
	}



	

}
