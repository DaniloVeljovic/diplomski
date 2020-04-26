package com.danilo.diplomski.models.data;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.danilo.diplomski.models.data.junctiontables.Registration;

@Entity
@DiscriminatorValue(value = "student")
public class Student extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1257919118607843116L;

	private Integer numIndex;
	
	public Set<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(Set<Registration> registrations) {
		this.registrations = registrations;
	}

	public Integer getNumIndex() {
		return numIndex;
	}

	public void setNumIndex(Integer numIndex) {
		this.numIndex = numIndex;
	}

	@OneToMany(mappedBy="student", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
	private Set<Registration> registrations;
}
