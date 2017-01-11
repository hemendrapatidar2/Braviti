package com.tm.braveti.predictivemodel;

import java.io.Serializable;
import java.util.List;

public class UserPreferencesJson implements Serializable {
	private String userId;
	private List<String> categories;
	private List<String> priceRange;

	public List<String> getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(List<String> priceRange) {
		this.priceRange = priceRange;
	}

	public UserPreferencesJson(String userId, List<String> categories, List<String> priceRange) {
		super();
		this.userId = userId;
		this.categories = categories;
		this.priceRange = priceRange;
	}

	public UserPreferencesJson() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	

	@Override
	public String toString() {
		return "UserPreferecesJson [userId=" + userId + ", categories=" + categories + ", priceRange=" + priceRange + "]";
	}

}
