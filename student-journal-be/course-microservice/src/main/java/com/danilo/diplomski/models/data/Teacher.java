package com.danilo.diplomski.models.data;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue(value="teacher")
public class Teacher extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6539697312351128718L;
	@OneToMany(mappedBy="teacher", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private Set<Course> courses;

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
	
}
