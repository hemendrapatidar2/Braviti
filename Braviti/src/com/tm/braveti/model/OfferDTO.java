package com.tm.braveti.model;

import java.util.Map;

public class OfferDTO {
	
	private String storeName;
	private Map<String,String> offerMap;
	
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public Map<String, String> getOfferMap() {
		return offerMap;
	}
	public void setOfferMap(Map<String, String> offerMap) {
		this.offerMap = offerMap;
	}
	

}
