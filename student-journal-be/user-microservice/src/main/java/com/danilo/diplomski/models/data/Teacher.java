package com.danilo.diplomski.models.data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "teacher")
public class Teacher extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1216469240286064995L;
	private Integer numOfPublishedWorks;

	public Integer getNumOfPublishedWorks() {
		return numOfPublishedWorks;
	}

	public void setNumOfPublishedWorks(Integer numOfPublishedWorks) {
		this.numOfPublishedWorks = numOfPublishedWorks;
	}
	
	
}
