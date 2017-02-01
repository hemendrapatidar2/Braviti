package com.tm.braveti.predictivemodel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import com.tm.braveti.exception.UserNotFoundException;
import com.tm.braveti.model.OfferCategory;
import com.tm.braveti.model.OfferDTO;
import com.tm.braveti.model.Outlet;
import com.tm.braveti.model.PriceRange;
import com.tm.braveti.model.TransactionHistory;
import com.tm.braveti.model.User;


public class SparkRecommender implements Serializable {
	
	private static final String BRAVITI_RESOURCES = "com/tm/braveti/resources";
	private static final long serialVersionUID = -8760307916460712075L;
	private static List<OfferDTO> finalOfferSuggestion = new ArrayList<OfferDTO>();
	private String userId;
	private JavaRDD<User> userData;
	private JavaRDD<TransactionHistory> transactionData;
	private JavaRDD<Outlet> outletData;
	private List<FilterCriteria> filterCriteriaList = new ArrayList<>();
	
	public List<OfferDTO> recommendOffers(final String userName,final String location) throws Exception {

		 SparkConf conf;
		 JavaSparkContext jsc;
		//configuring the logger
		configLogger();

		//Initializing spark context
		conf = new SparkConf().setMaster("local").setAppName("Java Collaborative Filtering Example");
		conf.set("spark.driver.allowMultipleContexts", "true");
		jsc = new JavaSparkContext(conf);

		//Loading data from csv files in spark RDD objects
		try {
			loadDataInRDD(jsc);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Applying filters on data loaded in RDD objects
		userId=getLoggedInUser(userName);
		if(!StringUtils.isNoneBlank(userId)){
			throw new UserNotFoundException("user not found");
		}
		
		
		
		// Step 3 : Filter User Specific Data in Java Rdd Object
	List<TransactionHistory> userSpecificRecords =  getTransactionHistoryForUser(userId);
		
		for (TransactionHistory txr : userSpecificRecords) {
			FilterCriteria filterCriteria = new FilterCriteria();
			filterCriteria.setCategoryName(txr.getCategoryid());
			String categoryId = txr.getCategoryid();
			String amount = txr.getAmount();
			String priceSegement = OfferPredictionEngine.classifyPriceSegement(categoryId, Double.parseDouble(amount));
			filterCriteria.setPriceSegement(priceSegement);
			filterCriteriaList.add(filterCriteria);
			
		}
		
		/*empty the list ,if not*/
		if (!finalOfferSuggestion.isEmpty()) {
			finalOfferSuggestion.clear();
			
		}
		System.out.println("outlet data is :: " + outletData.count());
		// Step 3 : Filter User Specific Data in Java Rdd Object
		JavaRDD<Outlet> recommendedOffers = outletData.filter(new Function<Outlet, Boolean>() {
			public Boolean call(Outlet outlet) {
				if (outlet.getLocation().equalsIgnoreCase(location)) {
					
					for (FilterCriteria filterCriteria : filterCriteriaList) {
						if (outlet.getCategary().equalsIgnoreCase(filterCriteria.getCategoryName())
								&& outlet.getPrice().equalsIgnoreCase(filterCriteria.getPriceSegement())) {
							System.out.println("adding offer now...");
							if (finalOfferSuggestion.isEmpty()) {
								createNewOfferDTO(outlet);
								
							}
							if (checkIfStoreAdded(outlet.getName())) {
								
								addToFinalOfferDTOList(outlet);
							} else {
								createNewOfferDTO(outlet);
								addToFinalOfferDTOList(outlet);
							}
						}
						
					}
					return true;
				}
				return false;
				
			}
		});
		System.out.println("Userid = " + userId + " Records = " + recommendedOffers.count());
	
		/*check if user has any specific preferences*/
		PreferencesUtility preferenceUtil=new PreferencesUtility();
		
		UserPreferencesJson userPreferences = getUserPreferences(userName);
		//UserPreferencesJson userPreferences = preferenceUtil.readUserPreferences(userName);
		System.out.println(userPreferences);
//		UserPreferecesJson userPreferences=new UserPreferecesJson();
//		userPreferences.setUserId("Raj");
//		HashMap<String, List<String>> preferenceMap=new HashMap<>();
//		List<String> categoryList=new ArrayList<>();
//		categoryList.add("1");
//		categoryList.add("2");
////		categoryList.add("3");
//		List<String> priceRange=new ArrayList<>();
//		priceRange.add("low");
//		priceRange.add("mid");
//		userPreferences.setCategories(categoryList);
//		userPreferences.setPriceRage(priceRange);
		if(userPreferences!=null && 
				(userPreferences.getCategories()!=null || userPreferences.getPriceRange()!=null)){
			
			List<OfferDTO> applyPreferences = applyPreferences(jsc,finalOfferSuggestion,userPreferences);
			for (OfferDTO offerDTO : applyPreferences) {
				System.out.println("outlet Name:: " + offerDTO.getStoreName());
				for (OfferCategory offerCategory : offerDTO.getOfferList()) {
					System.out.println("category for this store : "
							+ offerCategory.getCategoryName() + " offer :: "
							+ offerCategory.getOfferDescription());
				}
			}
			return applyPreferences;
		}
		else{
			return finalOfferSuggestion;
		}
	}



	public List<TransactionHistory> getTransactionHistoryForUser(final String userId) {
		final List<String> processedCategory = new ArrayList<>();
		JavaRDD<TransactionHistory> userSpecificRecords = transactionData.filter(new Function<TransactionHistory, Boolean>() {
			public Boolean call(TransactionHistory th) {
				if (th.getUserid().trim().equalsIgnoreCase(userId)) {
					String categoryId = th.getCategoryid();
					if (!processedCategory.contains(categoryId)) {
						processedCategory.add(categoryId);
						return true;
					}
				}
				return false;
			}
		});
		return  userSpecificRecords.collect();
	}
	


	private List<OfferDTO> applyPreferences(JavaSparkContext jsc,List<OfferDTO> recommendedList, UserPreferencesJson userPrefereces) {
		
		List<OfferDTO> recommendedListClone=new ArrayList<>();
		boolean isCategoryPreference = userPrefereces.getCategories()!=null && !userPrefereces.getCategories().isEmpty();
		boolean isPricePreference = userPrefereces.getPriceRange()!=null && !userPrefereces.getPriceRange().isEmpty();
		
		for (OfferDTO offerDTO : recommendedList) {
			List<OfferCategory> offerList = offerDTO.getOfferList();
			List<OfferCategory> offerListClone = new ArrayList<>(offerList);
			
			for (OfferCategory offerCategory : offerList) {
				if(isCategoryPreference){
					if(!userPrefereces.getCategories().contains(offerCategory.getCategoryId())){

						offerListClone.remove(offerCategory);
					}

				}
				if(isPricePreference){
					if(!userPrefereces.getPriceRange().contains(offerCategory.getPriceRange())){
						offerListClone.remove(offerCategory);
					}
				}
			}
			if(!offerListClone.isEmpty()){
				offerDTO.setOfferList(offerListClone);
				recommendedListClone.add(offerDTO);
			}
		}
		
		return recommendedListClone;
	}


	/**
	 * @param userName
	 * @return
	 */
	public String getLoggedInUser(final String userName) {
		JavaRDD<User> userRecords = userData.filter(new Function<User, Boolean>() {
			public Boolean call(User user) {
				if (user.getFname().trim().equalsIgnoreCase(userName)) {
					return true;
				}
				return false;
			}
		});
		for(User user:userRecords.collect()){
			return user.getId();
		}
		return "";
	}
	
	/**
	 * @param jsc
	 * @throws URISyntaxException 
	 */
	private void loadDataInRDD(JavaSparkContext jsc) throws URISyntaxException {
		 String fileLocation = this.getClass().getClassLoader().getResource(BRAVITI_RESOURCES).toString();
		 String txhPath = fileLocation+File.separator+"txHistory.csv";
		 String outletPath = fileLocation+File.separator+"outlet.csv";
		 String usersPath = fileLocation+File.separator+"users.csv";
		// Step : Load the users data in Java Rdd object
		userData = jsc.textFile(usersPath).map(new Function<String, User>() {
			public User call(String s) {
				String[] data = s.split(",");
				return new User(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
			}
		});		
		
		// Step : Load the transaction data in Java Rdd object
		 transactionData = jsc.textFile(txhPath).map(new Function<String, TransactionHistory>() {
			public TransactionHistory call(String s) {
				String[] data = s.split(",");
				return new TransactionHistory(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
			}
		});
		// Step : Load the outlet data in Java Rdd object
		outletData = jsc.textFile(outletPath).map(new Function<String, Outlet>() {
			public Outlet call(String s) {
				String[] data = s.split(",");
				return new Outlet(data[0], data[1], data[2], data[3], data[4], data[5], Double.valueOf(data[6]),
						Double.valueOf(data[7]));
			}
		});
	}
	/**
	 * 
	 */
	private void configLogger() {
		PatternLayout layout = new PatternLayout();
		String conversionPattern = "%-7p %d [%t] %c %x - %m%n";
		layout.setConversionPattern(conversionPattern);
		
		FileAppender fileAppender = new FileAppender();
		fileAppender.setFile("applog3.txt");
		fileAppender.setLayout(layout);
		fileAppender.activateOptions();
		
		Logger rootLogger = Logger.getRootLogger();
		rootLogger.addAppender(fileAppender);
	}

	public void createNewOfferDTO(Outlet outlet) {
		OfferDTO offerDTO = new OfferDTO();
		List<OfferCategory> offerList = new ArrayList<OfferCategory>();
		offerDTO.setStoreName(outlet.getName());
		offerDTO.setLangitude(outlet.getLangitude());
		offerDTO.setLatitude(outlet.getLatitude());
		offerDTO.setOfferList(offerList);
		finalOfferSuggestion.add(offerDTO);
	}

	private void addNewCategory(List<OfferCategory> offerMap, String categoryId, String offerdesc, String priceRange) {
		OfferCategory offerCategory = new OfferCategory();
		offerCategory.setCategoryId(categoryId);
		offerCategory.setPriceRange(priceRange);
		offerCategory.setCategoryName(CategoryCache.getCategoryNameForId(Integer.parseInt(categoryId)));
		offerCategory.setOfferDescription(offerdesc);
		offerMap.add(offerCategory);

	}

	public  void addToFinalOfferDTOList(Outlet outlet) {
		for (OfferDTO offerDTO : finalOfferSuggestion) {
			if (offerDTO.getStoreName().equalsIgnoreCase(outlet.getName())) {
				addNewCategory(offerDTO.getOfferList(), outlet.getCategary(), outlet.getOfferdesc(),outlet.getPrice());
			}
		}

	}

	public  boolean checkIfStoreAdded(String storeName) {
		if (finalOfferSuggestion.isEmpty())
			return false;

		for (OfferDTO offerDTO : finalOfferSuggestion) {
			if (offerDTO.getStoreName().equalsIgnoreCase(storeName)) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public UserPreferencesJson getUserPreferences(final String userName) throws Exception
	{
		
		BufferedReader br = null;
		FileReader fr = null;
		UserPreferencesJson prefDto = null ;
		
		URL url = this.getClass().getClassLoader().getResource("com\\tm\\braveti\\resources\\");
		String parentDirectory = new File(new URI(url.toString())).getAbsolutePath();
		System.out.println("parentDirectory: "+parentDirectory);
		File fin = new File(parentDirectory +"\\userPref.csv");

		try {
			
			if (!fin.createNewFile()){
			    System.out.println("getUserPreferences :: File already exists.");
			  }
			
			HashMap preferenceMap;
			String sCurrentLine;
			br = new BufferedReader(new FileReader(fin));
			List<String> priceList=new ArrayList<String>();
			
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				 String data[] = StringUtils.split(sCurrentLine, "|");
				 
				 if(userName.equalsIgnoreCase(data[0]))
				 {
					 prefDto=new UserPreferencesJson();
					 preferenceMap=new HashMap<String, List<String>>();
					 prefDto.setUserId(data[0]);
					 
					 if(null != data[1]  &&  StringUtils.isNotEmpty(data[1]))
					 prefDto.setCategories( Arrays.asList(StringUtils.split(data[1], ',')));
					 // Below condition is to ensure exception not thrown in case none of the price range is selected
					 if(data.length>2)
					 {
					 if(null != data[2] && StringUtils.isNotEmpty(data[2]))
					 {
					 priceList=Arrays.asList(StringUtils.split(data[2], ','));
					 prefDto.setPriceRange(priceList);
					 }
					 }
					 else
					 {
						 prefDto.setPriceRange(priceList);
					 }
				 }
			}
		} catch (IOException e) {
					e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
						ex.printStackTrace();
			}

		}
		return prefDto;
	}
	
	public boolean setUserPreference(final String userName, String category , String price) throws Exception
	{
		boolean prefStatus =false;
		File file=null;
		String strCat=StringUtils.remove(category, '[');
		String strCat1=StringUtils.remove(strCat, ']');
		String strCat2=StringUtils.remove(strCat1, '"');
		
		String strPrice=StringUtils.remove(price, '[');
		String strPrice1=StringUtils.remove(strPrice, ']');
		String strPrice2=StringUtils.remove(strPrice1, '"');
		
		String strTxt=userName +"|" + strCat2 + "|"+ strPrice2;
		
		try {
			URL url = this.getClass().getClassLoader().getResource("com\\tm\\braveti\\resources\\");
			String parentDirectory = new File(new URI(url.toString())).getAbsolutePath();
			System.out.println("parentDirectory: "+parentDirectory);
			file = new File(parentDirectory +"\\userPref.csv");
			file.createNewFile();
			System.out.println("FileLocation Is :: "+ file.getAbsolutePath());
			List<String> lines = FileUtils.readLines(file);
			if(!lines.isEmpty())
			{
				int i=0;
				for(String s: lines)
				{
					
					String data[] = StringUtils.split(s, "|");
	            	if (userName.equals(data[0].trim())) 
					{
						lines.set(i,strTxt);
					}					
					i++;
				}
			}
			else
			{
				lines.add(strTxt);
			}
				FileUtils.writeLines(file, lines);
				prefStatus=true;
		}catch(Exception e){
			e.printStackTrace();
			prefStatus=false;
		}
			
		return prefStatus;
	}
	
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		SparkRecommender test = new SparkRecommender();
		try {
			UserPreferencesJson preferences=new UserPreferencesJson();
			preferences.setUserId("Raj");
			HashMap<String, List<String>> preferenceMap=new HashMap<>();
			List<String> categoryList=new ArrayList<>();
			categoryList.add("1");
			categoryList.add("2");
//			categoryList.add("3");
			List<String> priceRange=new ArrayList<>();
			priceRange.add("low");
			priceRange.add("mid");
			preferences.setCategories(categoryList);
			preferences.setPriceRange(priceRange);
			
			try {
				test.recommendOffers("Raj", "ShivajiNagar");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
