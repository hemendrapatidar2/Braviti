package com.tm.braveti.predictivemodel;

import java.io.Serializable;

public class FilterCriteria implements Serializable{

	private String categoryName;
	private String priceSegement;
	private String location;
	private String birthday;
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getPriceSegement() {
		return priceSegement;
	}
	public void setPriceSegement(String priceSegement) {
		this.priceSegement = priceSegement;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
}
