package com.tm.braveti.model;

import java.util.List;

public class PreferencesDTO {
	private List<Category> categoryList;
	private List<String> priceRangeList;
	
	
	public List<Category> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	public List<String> getPriceRangeList() {
		return priceRangeList;
	}
	public void setPriceRangeList(List<String> priceRangeList) {
		this.priceRangeList = priceRangeList;
	}
	
	
	
	
	
}
