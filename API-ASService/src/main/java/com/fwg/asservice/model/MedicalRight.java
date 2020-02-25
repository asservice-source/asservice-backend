package com.fwg.asservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medicalright")
public class MedicalRight implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7052949139795288961L;

	@Column(name = "ParentCode")
	private String parentCode;
	
	@Column(name = "Level")
	private Integer level;
	
	@Id
	@Column(name = "Code")
	private String code;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "IsActive")
	private boolean isActive;

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}
