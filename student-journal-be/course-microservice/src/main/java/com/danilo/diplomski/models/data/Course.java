package com.danilo.diplomski.models.data;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.danilo.diplomski.models.data.junctiontables.Registration;

@Entity
public class Course implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4121667487412425695L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer id;

	private String name;

	private String semester;

	private String description;

	private String courseID;

	@OneToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Registration> registratedStudents;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;

	@OneToMany(mappedBy = "course", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Obligation> obligations;

	public Set<Registration> getRegistratedStudents() {
		return registratedStudents;
	}

	public void setRegistratedStudents(Set<Registration> registratedStudents) {
		this.registratedStudents = registratedStudents;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Set<Obligation> getObligations() {
		return obligations;
	}

	public void setObligations(Set<Obligation> obligations) {
		this.obligations = obligations;
	}

	public String getSemester() {
		return semester;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
