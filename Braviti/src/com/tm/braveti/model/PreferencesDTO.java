package com.tm.braveti.model;

import java.util.List;

public class PreferencesDTO {
	private List<Category> categoryList;
	private List<PriceRange> priceRangeList;
	
	
	public List<Category> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	public List<PriceRange> getPriceRangeList() {
		return priceRangeList;
	}
	public void setPriceRangeList(List<PriceRange> priceRangeList) {
		this.priceRangeList = priceRangeList;
	}
	
	
	
	
	
}
