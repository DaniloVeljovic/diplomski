package com.danilo.diplomski.models.DTO;

import java.io.Serializable;

public class StudentDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7074583061993032834L;

	private Integer id;
	
	private String userID;
	
	private String name;
	
	private String surname;
	
	private Integer numIndex;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
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
