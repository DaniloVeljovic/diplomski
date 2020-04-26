package com.danilo.diplomski.models.DTO;

import java.io.Serializable;

//TODO:VALIDATION
public class TeacherDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6484696512328553013L;

	private String name;
	
	private String surname;
	
	private String email;
	
	private String password;
	
	private Integer numOfPublishedWorks;
	
	private String encryptedPassword;
	
	private String userID;
	
	private String role;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getNumOfPublishedWorks() {
		return numOfPublishedWorks;
	}

	public void setNumOfPublishedWorks(Integer numOfPublishedWorks) {
		this.numOfPublishedWorks = numOfPublishedWorks;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}
