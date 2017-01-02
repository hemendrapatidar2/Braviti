package com.tm.braveti.model;

import java.io.Serializable;

public class Outlet implements Serializable{
	private String id;
	private String name;
	private String location;
	private String categary;
	private String price;
	private String offerdesc;
	private Double latitude;
	private Double langitude;

	public Outlet() {
		// TODO Auto-generated constructor stub
	}

	public Outlet(String id, String name, String location, String categary, String price, String offerdesc,
			Double latitude, Double langitude) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.categary = categary;
		this.price = price;
		this.offerdesc = offerdesc;
		this.latitude = latitude;
		this.langitude = langitude;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCategary() {
		return categary;
	}

	public void setCategary(String categary) {
		this.categary = categary;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getOfferdesc() {
		return offerdesc;
	}

	public void setOfferdesc(String offerdesc) {
		this.offerdesc = offerdesc;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLangitude() {
		return langitude;
	}

	public void setLangitude(Double langitude) {
		this.langitude = langitude;
	}

	@Override
	public String toString() {
		return "Outlet [id=" + id + ", name=" + name + ", location=" + location + ", categary=" + categary + ", price="
				+ price + ", offerdesc=" + offerdesc + ", latitude=" + latitude + ", langitude=" + langitude + "]";
	}

}
