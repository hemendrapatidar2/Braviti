package com.tm.braveti.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "OfferDTO")
public class OfferDTO implements Serializable{

	private static final long serialVersionUID = 2897173067718842394L;
	private String storeName;
	private List<OfferCategory> offerList;
	private Double latitude;
	private Double langitude;

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public List<OfferCategory> getOfferList() {
		return offerList;
	}

	public void setOfferList(List<OfferCategory> offerMap) {
		this.offerList = offerMap;
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
