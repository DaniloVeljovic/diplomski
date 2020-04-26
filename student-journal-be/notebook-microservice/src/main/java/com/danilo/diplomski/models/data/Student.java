package com.danilo.diplomski.models.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Student implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2483188361223545995L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id; 
	
	private String userID;
	
	private String name;
	
	private String surname;
	
	private Integer numIndex;

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
	
	@OneToMany(mappedBy = "student",fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private List<Notebook> hasNotebooks;

	public List<Notebook> getHasNotebooks() {
		return hasNotebooks;
	}

	public void setHasNotebooks(List<Notebook> hasNotebooks) {
		this.hasNotebooks = hasNotebooks;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Integer getNumIndex() {
		return numIndex;
	}

	public void setNumIndex(Integer numIndex) {
		this.numIndex = numIndex;
	}
	
}
