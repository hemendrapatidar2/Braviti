package com.tm.braveti.predictivemodel;

import java.util.HashMap;
import java.util.Map;

public class CategoryCache {
	
	public static Map<Integer, String> CategoryMap= new HashMap<>();
	
	public static void loadCategoryCache() {
	CategoryMap.put(1, "Electronics");
	CategoryMap.put(2, "clothing");
	CategoryMap.put(3, "footware");
	CategoryMap.put(4, "toys n games");
	CategoryMap.put(5, "sport n fitness");
	CategoryMap.put(6, "stationary");
	CategoryMap.put(7, "home n kitchen");
	CategoryMap.put(8, "Gift");
	CategoryMap.put(9, "Kindle E-Readers & Books");
	CategoryMap.put(10, "Grocery");
	CategoryMap.put(11, "Beauty Products");
	CategoryMap.put(12, "Jewelry");
	CategoryMap.put(13, "Handmade Craft");
	CategoryMap.put(14, "Crokery");
	CategoryMap.put(15, "Home Services");
	CategoryMap.put(16, "Automotive");
	CategoryMap.put(17, "Credit And Payment Products");
	CategoryMap.put(18, "Garden & Tools");
	CategoryMap.put(19, "Movies & Music");
	CategoryMap.put(20, "Mobile Accessories");
	CategoryMap.put(21, "Travel Accessories");
	CategoryMap.put(22, "Appstore For Android");
	CategoryMap.put(23, "Prime Photos And Prints");
	CategoryMap.put(24, "Fire TV");
	CategoryMap.put(25, "Amazon Video");
	}

	public static String getCategoryNameForId(Integer categoryId) {
		if(CategoryMap.isEmpty()){
			loadCategoryCache();
			return CategoryMap.get(categoryId);
		}
		return CategoryMap.get(categoryId);
		
		
	}
}
