package com.tm.braveti.predictivemodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tm.braveti.model.OfferCategory;
import com.tm.braveti.model.OfferDTO;
import com.tm.braveti.model.Outlet;
import com.tm.braveti.service.LoadStaticData;

public class OfferStoresHelper {
	
	
	private List<OfferDTO> finalOfferSuggestion=new ArrayList<OfferDTO>();
	
	private List<Outlet> getOfferOutletsForLocation(LoadStaticData staticData,String location ){
		
		List<Outlet> outletsPerLocation=new ArrayList<>();
		List<Outlet> outlets = staticData.getOutlets();
		for (Outlet outlet : outlets) {
			if(outlet.getLocation().equalsIgnoreCase(location)){
				outletsPerLocation.add(outlet);
			}
		}
		
		return outletsPerLocation;
		
		
	}
	
	private void applyFilters(FilterCriteria filterName,List<Outlet> offers){
		
		for (Outlet outlet : offers) {
			if(outlet.getCategary().equalsIgnoreCase(filterName.getCategoryName())&& outlet.getPrice().equalsIgnoreCase(filterName.getPriceSegement())){
				
				if(finalOfferSuggestion.isEmpty()){
					 createNewOfferDTO(outlet.getName());
					
				}
				if(checkIfStoreAdded(outlet.getName())){
					
					addToFinalOfferDTOList( outlet.getName(),outlet.getCategary(),outlet.getOfferdesc());
				}
				else{
					createNewOfferDTO(outlet.getName());
					addToFinalOfferDTOList( outlet.getName(),outlet.getCategary(),outlet.getOfferdesc());
				}
			}
		}
	}
	
	private boolean checkIfStoreAdded(String storeName) {
		if(finalOfferSuggestion.isEmpty())
			return false;
		
		for (OfferDTO offerDTO : finalOfferSuggestion) {
			if(offerDTO.getStoreName().equalsIgnoreCase(storeName)){
				return true;
			}
		}
		return false;
	}

	private void addToFinalOfferDTOList(String outletName, String categary, String offerdesc) {
	
		for (OfferDTO offerDTO : finalOfferSuggestion) {
			
			if(offerDTO.getStoreName().equalsIgnoreCase(outletName)){
				addNewCategory(offerDTO.getOfferMap(),categary,offerdesc);
			}
		}
		
	}
	
	private void createNewOfferDTO(String name) {
		OfferDTO offerDTO=new OfferDTO();
		List<OfferCategory> offerMap=new ArrayList<OfferCategory>();
		offerDTO.setStoreName(name);
		offerDTO.setOfferMap(offerMap);
		finalOfferSuggestion.add(offerDTO);
	}

	private void addNewCategory(List<OfferCategory> offerMap,String categary, String offerdesc) {
        OfferCategory offerCategory =new OfferCategory();
        offerCategory.setCategoryName(categary);
        offerCategory.setOfferDescription(offerdesc);
		offerMap.add(offerCategory);
		
	}
		


	public  List<OfferDTO> getOfferSuggestion(List<FilterCriteria> filters, LoadStaticData staticData,String location){
		List<Outlet> outletsPerLocation = getOfferOutletsForLocation(staticData,location);
		for (FilterCriteria filterCriteria : filters) {
			if(filterCriteria.getCategoryName().equalsIgnoreCase(PredictiveEngineConstants.LOCATION)){
				convertToOfferDTO(outletsPerLocation);
			}else if(filterCriteria.getCategoryName().equalsIgnoreCase(PredictiveEngineConstants.BIRTHDAY)){
				
				applyFilters(filterCriteria, outletsPerLocation);
			}
			else{
				applyFilters(filterCriteria, outletsPerLocation);
			}
		}
		return finalOfferSuggestion;
		
	}

	private void convertToOfferDTO(List<Outlet> outletsPerLocation) {
		// TODO Auto-generated method stub
		
		for (Outlet outlet : outletsPerLocation) {
			if(finalOfferSuggestion.isEmpty()){
				 createNewOfferDTO(outlet.getName());
				
			}
			if(checkIfStoreAdded(outlet.getName())){
				
				addToFinalOfferDTOList( outlet.getName(),outlet.getCategary(),outlet.getOfferdesc());
			}
			else{
				createNewOfferDTO(outlet.getName());
				addToFinalOfferDTOList( outlet.getName(),outlet.getCategary(),outlet.getOfferdesc());
			}
		}
	}

}
