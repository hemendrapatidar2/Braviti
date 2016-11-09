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
		
	}
	/*
	 * Apply Predictive analysis formula
	 * */
	
	private void predictiveEngine(){
		
	}
	/**
	 *Load static data from CSV and initialize user tx histories,outlets models 
	 */
	 private void loadStaticData(){
		
	}
}
