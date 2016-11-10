package com.tm.braveti.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.tm.braveti.service.LoadStaticData;
@Path("/useroffers")
public class PredictiveController {

	private LoadStaticData staticData = new LoadStaticData();

	/**
	 * Return the User predictive offers
	 * 
	 */
	@GET
	public Response  getUserOffer(@QueryParam("id") String userId,@QueryParam("location") String location) {
		System.out.println("inside getUserOffer Method "+userId +"Location " +location);
		return Response.status(200).entity(staticData.getOutlets()).build();
	}

	/*
	 * Apply Predictive analysis formula
	 */
	private void predictiveEngine() {
		System.out.println("inside predictiveEngine Method");

	}
}
