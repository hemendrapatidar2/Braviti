package com.tm.braveti.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.tm.braveti.exception.UserNotFoundException;
import com.tm.braveti.model.OfferDTO;
import com.tm.braveti.model.Outlet;
import com.tm.braveti.predictivemodel.OfferPredictionEngine;
import com.tm.braveti.service.LoadStaticData;
@Path("/useroffers")
public class PredictiveController {

	private LoadStaticData staticData = new LoadStaticData();

	/**
	 * Return the User predictive offers
	 * 
	 */
	@GET
	public Response  getUserOffer(@QueryParam("userName") String userName,@QueryParam("location") String location) {
		System.out.println("inside getUserOffer Method "+userName +"Location " +location);
		OfferPredictionEngine offerPredictionEngine = new OfferPredictionEngine(userName,location,this.staticData);
		List<OfferDTO> offerListDTO=new ArrayList<OfferDTO>();
		List<Outlet> showOfferPredictions = null;
		try {
			showOfferPredictions = offerPredictionEngine.showOfferPredictions();
			 offerListDTO=convertToDTO(showOfferPredictions);
		} catch (UserNotFoundException e) {
			System.out.println("User not present");
		}
		
		
		for (OfferDTO offerDTO : offerListDTO) {
			System.out.println("outlet Name:: " +offerDTO.getStoreName());
			Set<String> keySet = offerDTO.getOfferMap().keySet();
			for (String string : keySet) {
				System.out.println("category for this store : "+ string + " offer :: "+ offerDTO.getOfferMap().get(string));
			}
		}
		return Response.status(200).entity(staticData.getOutlets()).build();
	}

	/**
	 * convert the outlet list on offer list DTO ,which is sent to the UI layer
	 * @param showOfferPredictions
	 * @return
	 */
	private List<OfferDTO> convertToDTO(List<Outlet> showOfferPredictions) {
		
		List<OfferDTO> offerDTOList=new ArrayList<OfferDTO>();
	
		OfferDTO offerDTO;
		
		for (Outlet outlet : showOfferPredictions) {
			
			if(!checkIfOuletAdded(outlet,offerDTOList)){
				offerDTO=createNewOfferDTO(outlet.getName());
				offerDTOList.add(offerDTO);
				offerDTOList=addNewCategory(outlet.getName(),offerDTOList,outlet.getCategary(),outlet.getOfferdesc());
			}else{
				offerDTOList=addNewCategory(outlet.getName(),offerDTOList,outlet.getCategary(),outlet.getOfferdesc());
			}
			
		}
		return offerDTOList;
	}

	private OfferDTO createNewOfferDTO(String name) {
		OfferDTO offerDTO=new OfferDTO();
		Map<String, String> offerMap=new HashMap<String, String>();
		offerDTO.setStoreName(name);
		offerDTO.setOfferMap(offerMap);
		
		return offerDTO;
	}

	private List<OfferDTO> addNewCategory(String storeName, List<OfferDTO> offerDTOList,String categary, String offerdesc) {
		
		for (OfferDTO offerDTO : offerDTOList) {
			if(offerDTO.getStoreName().equals(storeName)){
				offerDTO.getOfferMap().put(categary, offerdesc);
			}
		}
		
		return offerDTOList;
	}

	private boolean checkIfOuletAdded(Outlet outlet, List<OfferDTO> offerDTOList) {
		
		if(offerDTOList.isEmpty()){
			return false;
		}
		for (OfferDTO offerDTO : offerDTOList) {
			if(offerDTO.getStoreName().equals(outlet.getName())){
				return true;
			}
		}
		return false;
		
	}

	/*
	 * Apply Predictive analysis formula
	 */
	private void predictiveEngine() {
		System.out.println("inside predictiveEngine Method");
		
	}
	
	public static void main(String[] args) {
		PredictiveController p=new PredictiveController();
		p.getUserOffer("Raj", "ShivajiNagar");
	}
}
