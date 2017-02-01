/**
 * 
 */
package com.tm.braveti.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.tm.braveti.model.Category;
import com.tm.braveti.model.PreferencesDTO;
import com.tm.braveti.model.PriceRange;
import com.tm.braveti.predictivemodel.PredictiveEngineConstants;


/**
 * @author PK00480313
 *
 */
public class PreferencesService {
	
//	private Map<String, List> prefData = new HashMap<>();

	public PreferencesService() {}
	
//	public List<Category> getCategories() {
//		return prefData.get("CATEGORY");
//	}
//	
	public List<Category> getCategoryListFromCSV(){
		List<Category> categoryList = new ArrayList<Category>();
		BufferedReader br;
		try {		
				URL url = this.getClass().getClassLoader().getResource("com\\tm\\braveti\\resources\\");
				String parentDirectory = new File(new URI(url.toString())).getAbsolutePath();
				System.out.println("parentDirectory: "+parentDirectory);	
				
				br = new BufferedReader(new FileReader(new File(parentDirectory+"\\category.csv")));
				String line;		
				while ((line = br.readLine()) != null) {
				    String[] entries = line.split(",");
				    Category category = new Category();
				    category.setId(entries[0]);
				    category.setName(entries[1]);
				    categoryList.add(category);
				}
		}   catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return categoryList;
	}
	
	

	public PreferencesDTO getPreferencesData() {
		PreferencesDTO preferencesDTO = new PreferencesDTO();
		List<PriceRange> priceRangeList =getPriceRange();
		List<Category> categoryList = getCategoryListFromCSV(); 
		for (Category user : categoryList) {
			System.out.println(user.toString());
		}
		//List<Outlet> outletData = getOutlets(); 
//		for (Outlet outlet : outletData) {	
//			if (!priceRangeList.contains(outlet.getPrice())) {
//				priceRangeList.add(outlet.getPrice());
//			}	
//		}
		preferencesDTO.setCategoryList(categoryList);
		preferencesDTO.setPriceRangeList(priceRangeList);
		
		return preferencesDTO;
	}
	
	private List<PriceRange> getPriceRange()
	{
		List<PriceRange> priceRangeList = new ArrayList<PriceRange>();
		PriceRange priceRange=new PriceRange();
		priceRange.setRange(PredictiveEngineConstants.HIGH);
		priceRangeList.add(priceRange);
		PriceRange priceRange1=new PriceRange();
		priceRange1.setRange(PredictiveEngineConstants.MID);
		priceRangeList.add(priceRange1);
		PriceRange priceRange2=new PriceRange();
		priceRange2.setRange(PredictiveEngineConstants.LOW);
		priceRangeList.add(priceRange2);
		return priceRangeList;

	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PreferencesService prefData = new PreferencesService();
		PreferencesDTO preferencesDTO = prefData.getPreferencesData();
		System.out.println("Catagory :: "+preferencesDTO.getCategoryList());
		System.out.println("TotalAmtPerCategory :: "+preferencesDTO.getPriceRangeList());			
		
	
	}

}
