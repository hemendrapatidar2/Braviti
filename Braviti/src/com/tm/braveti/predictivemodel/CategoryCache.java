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
	}

	public static String getCategoryNameForId(Integer categoryId) {
		if(CategoryMap.isEmpty()){
			loadCategoryCache();
			return CategoryMap.get(categoryId);
		}
		return CategoryMap.get(categoryId);
		
		
	}
}
