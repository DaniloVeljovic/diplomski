package com.danilo.diplomski.models.data;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Obligation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7216743027011110115L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private LocalDateTime date;
	
	private String type;
	
	private String description;
	
	private String obligationID;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="course_id")
	private Course course;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Course getCourses() {
		return course;
	}

	public void setCourses(Course courses) {
		this.course = courses;
	}

	public String getObligationID() {
		return obligationID;
	}

	public void setObligationID(String obligationID) {
		this.obligationID = obligationID;
	}
	
}
