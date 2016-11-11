package com.tm.braveti.predictivemodel;

import java.util.ArrayList;
import java.util.List;

import com.tm.braveti.model.Outlet;
import com.tm.braveti.service.LoadStaticData;

public class OfferStoresHelper {
	
	private List<Outlet> finalOutletsSuggestions=new ArrayList<Outlet>();
	
	private List<Outlet> getOfferOutletsForLocation(LoadStaticData staticData,String location ){
		
		List<Outlet> outletsPerLocation=new ArrayList<>();
		List<Outlet> outlets = staticData.getOutlets();
		for (Outlet outlet : outlets) {
			if(outlet.getLocation().equals(location)){
				outletsPerLocation.add(outlet);
			}
		}
		
		return outletsPerLocation;
		
		
	}
	
	private List<Outlet> applyFilters(FilterCriteria filterName,List<Outlet> offers){
		
		for (Outlet outlet : offers) {
			if(outlet.getCategary().equals(filterName.getCategoryName())&& outlet.getPrice().equals(filterName.getPriceSegement())){
			finalOutletsSuggestions.add(outlet);	
			}
		}
		return offers;
	}
	
	public  List<Outlet> getOfferSuggestion(List<FilterCriteria> filters, LoadStaticData staticData,String location){
		List<Outlet> outletsPerLocation = getOfferOutletsForLocation(staticData,location);
		for (FilterCriteria filterCriteria : filters) {
			if(filterCriteria.getCategoryName().equals(PredictiveEngineConstants.LOCATION)){
				
			}else if(filterCriteria.getCategoryName().equals(PredictiveEngineConstants.BIRTHDAY)){
				
			}
			else{
				outletsPerLocation=applyFilters(filterCriteria, outletsPerLocation);
			}
		}
		return outletsPerLocation;
		
	}

}
