package com.fwg.asservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Prefix")
public class Prefix {
	
	@Id
	@Column(name = "Code")
	private String code;
	
	@Column(name = "ShortName")
	private String shortName;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name ="GenderID")
	private Integer genderId;
	
	@Column(name = "IsActive")
	private boolean isActive;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="GenderID", insertable = false, updatable = false)
	private Gender gender;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGenderId() {
		return genderId;
	}

	public void setGenderId(Integer genderId) {
		this.genderId = genderId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Gender getGender() {
		return gender==null?new Gender():gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
}
