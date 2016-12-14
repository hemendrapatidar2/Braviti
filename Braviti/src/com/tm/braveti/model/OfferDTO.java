package com.tm.braveti.model;

import java.util.List;

public class OfferDTO {

	private String storeName;
	private List<OfferCategory> offerMap;
	private Double latitude;
	private Double langitude;

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public List<OfferCategory> getOfferMap() {
		return offerMap;
	}

	public void setOfferMap(List<OfferCategory> offerMap) {
		this.offerMap = offerMap;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLangitude() {
		return langitude;
	}

	public void setLangitude(Double langitude) {
		this.langitude = langitude;
	}

}
