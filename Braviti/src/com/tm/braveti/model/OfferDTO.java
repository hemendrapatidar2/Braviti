package com.tm.braveti.model;

import java.util.List;

public class OfferDTO {
	
    private String storeName;
    private List<OfferCategory> offerMap;

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


	

}
