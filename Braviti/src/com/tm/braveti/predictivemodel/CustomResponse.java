package com.tm.braveti.predictivemodel;

import java.io.Serializable;

public class CustomResponse implements Serializable{
	private Boolean status;
	private String error;

	public CustomResponse(Boolean status, String error) {
		super();
		this.status = status;
		this.error = error;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
