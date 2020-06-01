package com.sreyasvpariyath.webservices.restfulwebservicesboot.dto;

public class SampleBean {

	private String message;
	public SampleBean(String string) {
		
		message=string;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "SampleBean [message=" + message + "]";
	}
	public String getMessage() {
		return message;
	}

	
}
