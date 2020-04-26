package com.danilo.diplomski.models.kafka;

public class TeacherKafkaModel {

	private String userID;
	private String role;
	private String name;
	private String surname;
	
	public TeacherKafkaModel() {

	}

	public TeacherKafkaModel(String userID, String role, String name, String surname) {
		this.userID = userID;
		this.role = role;
		this.name = name;
		this.surname = surname;
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
		return "TEACHER [userID=" + userID + ", role=" + role + ", name=" + name + "]";
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	

}
