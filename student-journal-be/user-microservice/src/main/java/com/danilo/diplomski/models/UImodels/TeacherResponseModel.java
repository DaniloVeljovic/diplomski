package com.danilo.diplomski.models.UImodels;

public class TeacherResponseModel {

	private String name;
	
	private String surname;
	
	private String email;
	
	private String encryptedPassword;
	
	private Integer numOfPublishedWorks;
	
	private String role;
	
	private String userID;

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

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
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
