package com.danilo.diplomski.models.data;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "student")
public class Student extends User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4923943327826513061L;
	private Integer yearOfStudy;
	//treba da se stave default vrednosti 0 za avg grade i numofnotebooks
	private Integer numIndex;
	
	private Float avgGrade;
	
	private Integer numOfNotebooks;

	public Integer getNumOfNotebooks() {
		return numOfNotebooks;
	}

	public void setNumOfNotebooks(Integer numOfNotebooks) {
		this.numOfNotebooks = numOfNotebooks;
	}
	
	public Integer getYearOfStudy() {
		return yearOfStudy;
	}

	public void setYearOfStudy(Integer yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
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
