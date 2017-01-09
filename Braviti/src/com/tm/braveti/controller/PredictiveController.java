package com.tm.braveti.controller;

import java.io.IOException;
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
import com.tm.braveti.model.UserPrefResponse;
import com.tm.braveti.predictivemodel.OfferPredictionEngine;
import com.tm.braveti.predictivemodel.SparkRecommender;
import com.tm.braveti.predictivemodel.UserPreferencesJson;

import parquet.org.codehaus.jackson.map.ObjectMapper;

@Path("/useroffers")
public class PredictiveController {

//	private LoadStaticData staticData = new LoadStaticData();

	/**
	 * Return the User predictive offers
	 * @throws UserNotFoundException 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 * 
	 */
	@GET
//	@Produces(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_XML)
	public Response getUserOffer(@QueryParam("userName") String userName,
			@QueryParam("location") String location) throws UserNotFoundException, NumberFormatException, IOException {
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
		offerListDTO=recommender.recommendOffers(location, location);
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
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/setUserPreferences")
	public Response setUserPreferences(
			@QueryParam("category") String category ,
			@QueryParam("price") String price,
			@QueryParam("userName") String userName
			) throws Exception
		{
		String jsonInString=null;
		
	try{			
		System.out.println("inside setUserPreferences Method: " + userName
				+ "category " + category +" price "+ price);
		SparkRecommender recommender=new SparkRecommender();
		boolean prefStatus= recommender.setUserPreference(userName, category ,price);
		UserPrefResponse userPrefRes=new UserPrefResponse();
		ObjectMapper mapper = new ObjectMapper();
		
			if(prefStatus)
			{
				userPrefRes.setStatus(true);
				userPrefRes.setStatusMsg("Sucessfully Saved User Preferences");
				jsonInString = mapper.writeValueAsString(userPrefRes);
				
			}
			else
			{
				userPrefRes.setStatus(true);
				userPrefRes.setStatusMsg("Error In Saving User Preferences Plesae Try Again");
				jsonInString = mapper.writeValueAsString(userPrefRes);
			}
			System.out.println(jsonInString);
			
	}catch(Exception e)
	{
				e.printStackTrace();	
	}
	return Response.status(200).entity(jsonInString).build();
	}
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/getUserPreferences")
	public Response getUserPreferences(
			@QueryParam("userName") String userName
			) throws Exception
		{
		String jsonInString=null;
		System.out.println("inside gettUserPreferences Method: " + userName);
		SparkRecommender recommender=new SparkRecommender();
		UserPreferencesJson prefDto = recommender.getUserPreferences(userName);
		ObjectMapper mapper = new ObjectMapper();
		
		
			if(null != prefDto)
			{
				jsonInString = mapper.writeValueAsString(prefDto);
				return Response.ok().status(200).entity(jsonInString).build();
			}
			else
			{
				jsonInString = mapper.writeValueAsString(prefDto);
				return Response.ok().status(200).entity(jsonInString).build();
		}
			
	}
	public static void main(String[] args) {
		PredictiveController p = new PredictiveController();
		//p.getUserOffer("Raj", "ShivajiNagar");
		p.getPieChartTransData("Raj", "Kothrud");
	}
	
	
	
	
}
