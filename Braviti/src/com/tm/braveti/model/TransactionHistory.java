package com.tm.braveti.model;

import java.io.Serializable;

public class TransactionHistory implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4907924537233179650L;
	private String id;
	private String userid;
	private String date;
	private String amount;
	private String outletid;
	private String description;
	private String categoryid;

	
	
	public TransactionHistory(String id, String userid, String date, String amount, String outletid, String description,
			String categoryid) {
		super();
		this.id = id;
		this.userid = userid;
		this.date = date;
		this.amount = amount;
		this.outletid = outletid;
		this.description = description;
		this.categoryid = categoryid;
	}

	
	public TransactionHistory() {
		// TODO Auto-generated constructor stub
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOutletid() {
		return outletid;
	}

	public void setOutletid(String outletid) {
		this.outletid = outletid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}

	@Override
	public String toString() {
		return "TransactionHistory [id=" + id + ", userid=" + userid + ", date=" + date + ", amount=" + amount
				+ ", outletid=" + outletid + ", description=" + description + ", categoryid=" + categoryid + "]";
	}

}
