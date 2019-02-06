/**
 * 
 */
package com.BookKart.models;

import java.io.Serializable;

@SuppressWarnings("serial")
/**
 * @author rocky
 *
 */
public class Person implements Serializable {

	/**
	 * 
	 */
	public Person() {
	}

	private String firstName;
	private String lastName;
	private String emailAddress;
	private String password;
	private long contact;
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

}