package com.tm.braveti.controller;

import java.util.ArrayList;
import java.util.List;

import com.tm.braveti.model.Categary;
import com.tm.braveti.model.Outlet;
import com.tm.braveti.model.TransactionHistory;
import com.tm.braveti.model.User;

public class PredictiveController {
	private List<User> users =new ArrayList<User>();
	private List<Outlet> outlets =new ArrayList<Outlet>();
	private List<TransactionHistory> transactionHistories =new ArrayList<TransactionHistory>();
	private List<Categary> categaries =new ArrayList<Categary>();
	
	/**
	 * Return the User predictive offers
	 * 
	 */
	public void getUserOffer(){
		System.out.println("inside getUserOffer Method");
	}
	
	/*
	 * Apply Predictive analysis formula
	 * */
	@GET
	@Path("/useroffers/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	private void predictiveEngine(@PathParam("id") int userId){
		System.out.println("inside predictiveEngine Method");

		List dataList = new ArrayList();
		dataList.add(new PredictiveData("Radhika","Electronics", "2999", "upto 20% off"));
		dataList.add(new PredictiveData("Dmart","clothing", "3000", "Buy one Get one"));
		dataList.add(new PredictiveData("Central Mall","footware", "2000", "Flat 500 off"));

		return dataList;
	}
	
	/**
	 *Load static data from CSV and initialize user tx histories,outlets models 
	 */
	
	 private void loadStaticData(){
		 System.out.println("inside loadStaticData Method");
	}
}
