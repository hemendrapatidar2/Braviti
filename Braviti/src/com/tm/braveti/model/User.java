package com.tm.braveti.model;

import java.io.Serializable;

public class User implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1090034370101988290L;
	private String id;
	private String fname;
	private String lname;
	private String gender;
	private String dob;
	private String cclimit;
	private String incomegrp;

	public User(String id, String fname, String lname, String gender, String dob, String cclimit, String incomegrp) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.gender = gender;
		this.dob = dob;
		this.cclimit = cclimit;
		this.incomegrp = incomegrp;
	}
	public User() {
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getCclimit() {
		return cclimit;
	}

	public void setCclimit(String cclimit) {
		this.cclimit = cclimit;
	}

	public String getIncomegrp() {
		return incomegrp;
	}

	public void setIncomegrp(String incomegrp) {
		this.incomegrp = incomegrp;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fname=" + fname + ", lname=" + lname + ", gender=" + gender + ", dob=" + dob
				+ ", cclimit=" + cclimit + ", incomegrp=" + incomegrp + "]";
	}

}
