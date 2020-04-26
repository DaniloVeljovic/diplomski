package com.danilo.diplomski.models.kafka;

public class RegistrationKafkaModel {

	private String userID;
	private String courseID;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

}
