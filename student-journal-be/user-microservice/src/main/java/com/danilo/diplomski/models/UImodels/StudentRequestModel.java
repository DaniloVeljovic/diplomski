package com.danilo.diplomski.models.UImodels;

public class StudentRequestModel {

	private String name;

	private String surname;

	private String email;

	private String password;

	private Integer yearOfStudy;

	private Integer numIndex;

	private String role;
	
	private Float avgGrade;

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

	public Integer getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(Integer yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getNumIndex() {
		return numIndex;
	}

	public void setNumIndex(Integer numIndex) {
		this.numIndex = numIndex;
	}

	public Float getAvgGrade() {
		return avgGrade;
	}

	public void setAvgGrade(Float avgGrade) {
		this.avgGrade = avgGrade;
	}

}
