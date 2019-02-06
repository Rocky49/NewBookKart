package com.BookKart.models;

public class LoginModel {

	public LoginModel(String emailAddress,String password) {
		super();
		this.userName=emailAddress;
		this.password=password;
	}

	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 */

	private String userName;
	private String password;

}
