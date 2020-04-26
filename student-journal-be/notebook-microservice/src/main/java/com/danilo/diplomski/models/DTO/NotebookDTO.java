package com.danilo.diplomski.models.DTO;

import java.io.Serializable;

public class NotebookDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6447859568508503897L;

	private Integer id;

	private String course;

	private String text;

	private String userID;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
}
