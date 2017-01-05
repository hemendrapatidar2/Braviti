package com.tm.braveti.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tm.braveti.exception.UserNotFoundException;
import com.tm.braveti.model.OfferCategory;
import com.tm.braveti.model.OfferDTO;
import com.tm.braveti.model.PieChartDTO;
import com.tm.braveti.predictivemodel.OfferPredictionEngine;
import com.tm.braveti.predictivemodel.SparkRecommender;

@Path("/useroffers")
public class PredictiveController {

//	private LoadStaticData staticData = new LoadStaticData();

	/**
	 * Return the User predictive offers
	 * @throws UserNotFoundException 
	 * 
	 */
	@GET
//	@Produces(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_XML)
	public Response getUserOffer(@QueryParam("userName") String userName,
			@QueryParam("location") String location) throws UserNotFoundException {
		System.out.println("inside getUserOffer Method " + userName
				+ "Location " + location);
//		OfferPredictionEngine offerPredictionEngine = new OfferPredictionEngine(
//				userName, location, this.staticData);
		List<OfferDTO> offerListDTO = new ArrayList<OfferDTO>();
//		try {
//			offerListDTO = offerPredictionEngine.showOfferPredictions();
//		} catch (UserNotFoundException e) {
//			System.out.println("User not present");
//		}

		

		SparkRecommender recommender=new SparkRecommender();
		offerListDTO=recommender.recommendOffers(location, location,null);
		for (OfferDTO offerDTO : offerListDTO) {
		System.out.println("outlet Name:: " + offerDTO.getStoreName());
		for (OfferCategory offerCategory : offerDTO.getOfferList()) {
			System.out.println("category for this store : "
					+ offerCategory.getCategoryName() + " offer :: "
					+ offerCategory.getOfferDescription());
		}
	}
			
			return Response.status(200).entity(offerListDTO).build();
			
	}
	
	/**
	 * Return the User predictive offers
	 * 
	 */
	@GET
	@Path("/pieChartData")
	public Response getPieChartTransData(@QueryParam("userName") String userName,
			@QueryParam("location") String location) {
		System.out.println("inside getPieChartTransData Method " + userName
				+ " Location " + location);
		OfferPredictionEngine offerPredictionEngine = new OfferPredictionEngine(userName);
		List<PieChartDTO> pieChartDataList = offerPredictionEngine.getPieChartData();
		for (PieChartDTO pieChartDTO : pieChartDataList) {
			System.out.println("Catagory :: "+pieChartDTO.getCategory());
			System.out.println("TotalAmtPerCategory :: "+pieChartDTO.getTotalAmtPerCategory());
			System.out.println("PercentAmt :: "+pieChartDTO.getPercentAmt());
		}
		return Response.status(200).entity(pieChartDataList).build();
	}
	
	public static void main(String[] args) {
		PredictiveController p = new PredictiveController();
		//p.getUserOffer("Raj", "ShivajiNagar");
		p.getPieChartTransData("Raj", "Kothrud");
	}
}
