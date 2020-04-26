package com.danilo.diplomski.models.DTO;

import java.io.Serializable;

public class CourseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9070717960851054426L;
	
	private String name;

	private String semester;

	private String description;

	private String courseID;
	
	private String teacherName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

}
