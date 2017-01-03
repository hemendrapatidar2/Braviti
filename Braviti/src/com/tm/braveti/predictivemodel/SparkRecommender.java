package com.tm.braveti.predictivemodel;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import com.tm.braveti.model.OfferCategory;
import com.tm.braveti.model.OfferDTO;
import com.tm.braveti.model.Outlet;
import com.tm.braveti.model.TransactionHistory;
import com.tm.braveti.model.User;


public class SparkRecommender implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8760307916460712075L;

	final static List<OfferDTO> finalOfferSuggestion = new ArrayList<OfferDTO>();
	String userId;

	public List<OfferDTO> recommendationEngine(final String userName, final String location) {
		Logger logger = Logger.getLogger(SparkRecommender.class);
		PatternLayout layout = new PatternLayout();
		String conversionPattern = "%-7p %d [%t] %c %x - %m%n";
		layout.setConversionPattern(conversionPattern);

		FileAppender fileAppender = new FileAppender();
		fileAppender.setFile("applog3.txt");
		fileAppender.setLayout(layout);
		fileAppender.activateOptions();

		Logger rootLogger = Logger.getRootLogger();
		rootLogger.addAppender(fileAppender);

		SparkConf conf = new SparkConf().setMaster("local").setAppName("Java Collaborative Filtering Example");
		conf.set("spark.driver.allowMultipleContexts", "true");
		JavaSparkContext jsc = new JavaSparkContext(conf);
				
		// Step 1 : Initialize the Java Rdd object to csv file
		
		URL fileLocation = SparkRecommender.class.getProtectionDomain().getCodeSource().getLocation();
		String txhPath = fileLocation+"\\com\\tm\\braveti\\resources\\txHistory.csv";
		String outletPath = fileLocation+"\\com\\tm\\braveti\\resources\\outlet.csv";
		String usersPath = fileLocation+"\\com\\tm\\braveti\\resources\\users.csv";
		
//		InputStream ExcelFileToRead = this.getClass().getClassLoader()
//				.getResourceAsStream("com\\tm\\braveti\\resources\\Braviti.xls");
		
		JavaRDD<String> transactionData = jsc.textFile(txhPath);
		JavaRDD<String> outletRdd = jsc.textFile(outletPath);
		JavaRDD<String> userDataFile = jsc.textFile(usersPath);		
		
		JavaRDD<User> userData = userDataFile.map(new Function<String, User>() {
			public User call(String s) {
				String[] data = s.split(",");
				return new User(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
			}
		});
		
		JavaRDD<User> userRecords = userData.filter(new Function<User, Boolean>() {
			public Boolean call(User user) {
				if (user.getFname().trim().equalsIgnoreCase(userName)) {
					return true;
				}
				return false;
			}
		});
		System.out.println("user record "+ userRecords.count());
		for(User user:userRecords.collect()){
			userId=user.getId();
			System.out.println("user id ::"+ userId);
		}
		// Step : Load the transaction history data in Java Rdd object
		JavaRDD<TransactionHistory> txData = transactionData.map(new Function<String, TransactionHistory>() {
			public TransactionHistory call(String s) {
				String[] data = s.split(",");
				return new TransactionHistory(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
			}
		});

		
		final List<String> processedCategory = new ArrayList<>();
		final List<FilterCriteria> filterCriteriaList = new ArrayList<>();

		// Step 3 : Filter User Specific Data in Java Rdd Object
		JavaRDD<TransactionHistory> userSpecificRecords = txData.filter(new Function<TransactionHistory, Boolean>() {
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
		System.out.println("Total Records = " + txData.count());
		System.out.println("Userid = " + userId + " Records = " + userSpecificRecords.count());
		
		for (TransactionHistory txr : userSpecificRecords.collect()) {
			FilterCriteria filterCriteria = new FilterCriteria();
			filterCriteria.setCategoryName(txr.getCategoryid());
			String categoryId = txr.getCategoryid();
			String amount = txr.getAmount();
			String priceSegement = OfferPredictionEngine.classifyPriceSegement(categoryId, Double.parseDouble(amount));
			filterCriteria.setPriceSegement(priceSegement);
			filterCriteriaList.add(filterCriteria);

		}

		// Step : Load the outlet data in Java Rdd object
		JavaRDD<Outlet> outletData = outletRdd.map(new Function<String, Outlet>() {
			public Outlet call(String s) {
				String[] data = s.split(",");
				return new Outlet(data[0], data[1], data[2], data[3], data[4], data[5], Double.valueOf(data[6]),
						Double.valueOf(data[7]));
			}
		});
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
		System.out.println("offer size :" + finalOfferSuggestion.size());
		
		for (OfferDTO offerDTO : finalOfferSuggestion) {
			System.out.println("outlet Name:: " + offerDTO.getStoreName());
			for (OfferCategory offerCategory : offerDTO.getOfferMap()) {
				System.out.println("category for this store : "
						+ offerCategory.getCategoryName() + " offer :: "
						+ offerCategory.getOfferDescription());
			}
		}
		return finalOfferSuggestion;
	}

	public static void main(String[] args) {
		SparkRecommender test = new SparkRecommender();
		test.recommendationEngine("Raj", "Kothrud");
	}

	public static void createNewOfferDTO(Outlet outlet) {
		OfferDTO offerDTO = new OfferDTO();
		List<OfferCategory> offerMap = new ArrayList<OfferCategory>();
		offerDTO.setStoreName(outlet.getName());
		offerDTO.setLangitude(outlet.getLangitude());
		offerDTO.setLatitude(outlet.getLatitude());
		offerDTO.setOfferMap(offerMap);
		finalOfferSuggestion.add(offerDTO);
	}

	private static void addNewCategory(List<OfferCategory> offerMap, String categary, String offerdesc) {
		OfferCategory offerCategory = new OfferCategory();
		offerCategory.setCategoryName(CategoryCache.getCategoryNameForId(Integer.parseInt(categary)));
		offerCategory.setOfferDescription(offerdesc);
		offerMap.add(offerCategory);

	}

	public static void addToFinalOfferDTOList(Outlet outlet) {

		for (OfferDTO offerDTO : finalOfferSuggestion) {

			if (offerDTO.getStoreName().equalsIgnoreCase(outlet.getName())) {
				addNewCategory(offerDTO.getOfferMap(), outlet.getCategary(), outlet.getOfferdesc());
			}
		}

	}

	public static boolean checkIfStoreAdded(String storeName) {
		if (finalOfferSuggestion.isEmpty())
			return false;

		for (OfferDTO offerDTO : finalOfferSuggestion) {
			if (offerDTO.getStoreName().equalsIgnoreCase(storeName)) {
				return true;
			}
		}
		return false;
	}

}
