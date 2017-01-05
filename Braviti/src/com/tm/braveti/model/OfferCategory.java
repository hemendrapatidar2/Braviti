package com.tm.braveti.model;

import java.io.Serializable;

public class OfferCategory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String categoryId;
	private String categoryName;
    private String priceRange;
    private String offerDescription;

    public String getCategoryName() {
           return categoryName;
    }

    public void setCategoryName(String categoryName) {
           this.categoryName = categoryName;
    }

    public String getOfferDescription() {
           return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
           this.offerDescription = offerDescription;
    }

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

}