package com.danilo.diplomski.models.DTO;

import java.io.Serializable;

public class StudentDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2967242697468576636L;

	private String userID;

	private String name;

	private String role;
	
	private String surname;
	
	private Integer numIndex;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Integer getNumIndex() {
		return numIndex;
	}

	public void setNumIndex(Integer numIndex) {
		this.numIndex = numIndex;
	}

}
