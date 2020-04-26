package com.danilo.diplomski.models.kafka;

public class StudentKafkaModel {

	private String userID;
	private String role;
	private String name;
	private String surname;
	private Integer numIndex;

	public StudentKafkaModel() {

	}

	public StudentKafkaModel(String userID, String role, String name, String surname, Integer numIndex) {
		this.userID = userID;
		this.role = role;
		this.name = name;
		this.setSurname(surname);
		this.setNumIndex(numIndex);
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return " STUDENT [userID=" + userID + ", role=" + role + ", name=" + name + "]";
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
