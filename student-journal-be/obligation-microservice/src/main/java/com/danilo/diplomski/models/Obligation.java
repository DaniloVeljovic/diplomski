package com.danilo.diplomski.models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Obligation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	//fk user
	//fk teacher
	//fk course 
	
	@NotNull(message="Date of posting cannot be null.")
	private Date dateOfPosting;
	
	private String description;
	
	@NotNull(message="Type of obligation cannot be null.")
	private String type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateOfPosting() {
		return dateOfPosting;
	}

	public void setDateOfPosting(Date dateOfPosting) {
		this.dateOfPosting = dateOfPosting;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	public Obligation()
	{
		
	}

	public Obligation(@NotNull(message = "Date of posting cannot be null.") Date dateOfPosting, String description,
			@NotNull(message = "Type of obligation cannot be null.") String type) {
		super();
		this.dateOfPosting = dateOfPosting;
		this.description = description;
		this.type = type;
	}
	
	
	
}
