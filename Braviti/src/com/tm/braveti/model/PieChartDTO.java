package com.tm.braveti.model;

public class PieChartDTO {
	private String userid;
	private String category;
	private float totalAmtPerCategory;
	private float percentAmt;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public float getTotalAmtPerCategory() {
		return totalAmtPerCategory;
	}
	public void setTotalAmtPerCategory(float totalAmtPerCategory) {
		this.totalAmtPerCategory = totalAmtPerCategory;
	}
	public float getPercentAmt() {
		return percentAmt;
	}
	public void setPercentAmt(float percentAmt) {
		this.percentAmt = percentAmt;
	}
	
	
	
}
