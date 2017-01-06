package com.tm.braveti.predictivemodel;

import java.io.Serializable;
import java.util.List;

public class UserPreferecesJson implements Serializable {
	private String userId;
	private List<String> categories;
	private List<String> priceRage;

	public UserPreferecesJson(String userId, List<String> categories, List<String> priceRage) {
		super();
		this.userId = userId;
		this.categories = categories;
		this.priceRage = priceRage;
	}

	public UserPreferecesJson() {
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

	public List<String> getPriceRage() {
		return priceRage;
	}

	public void setPriceRage(List<String> priceRage) {
		this.priceRage = priceRage;
	}

	@Override
	public String toString() {
		return "UserPreferecesJson [userId=" + userId + ", categories=" + categories + ", priceRage=" + priceRage + "]";
	}

}
