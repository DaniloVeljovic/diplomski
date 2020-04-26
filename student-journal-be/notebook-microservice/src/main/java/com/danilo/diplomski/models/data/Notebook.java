package com.danilo.diplomski.models.data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Notebook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3036990122873613467L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private Student student; 

	private String course; 

	private String text;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
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
	
	
}
