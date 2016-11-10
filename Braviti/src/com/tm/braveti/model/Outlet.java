package com.tm.braveti.model;

public class Outlet {
	private String id;
	private String name;
	private String location;
	private String categary;
	private String price;
	private String offerdesc;

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

	@Override
	public String toString() {
		return "Outlet [id=" + id + ", name=" + name + ", location=" + location + ", categary=" + categary + ", price="
				+ price + ", offerdesc=" + offerdesc + "]";
	}

}
