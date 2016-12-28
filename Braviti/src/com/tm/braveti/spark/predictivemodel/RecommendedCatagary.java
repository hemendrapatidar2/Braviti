package com.tm.braveti.spark.predictivemodel;

public class RecommendedCatagary {
	private Integer userid;
	private Integer catagaryid;

	public RecommendedCatagary(Integer userid, Integer catagaryid) {
		this.userid = userid;
		this.catagaryid = catagaryid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getCatagaryid() {
		return catagaryid;
	}

	public void setCatagaryid(Integer catagaryid) {
		this.catagaryid = catagaryid;
	}
	
}
