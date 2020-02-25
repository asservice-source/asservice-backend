package com.fwg.asservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Tumbol")
public class Tumbol {

	@Id
	@Column(name = "Code")
	private String code;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "AmphurCode")
	private String amphurCode;
	
	
	public String getCodeId() {
		return code;
	}

	public void setCodeId(String code) {
		this.code = code;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAmphurCode() {
		return amphurCode;
	}

	public void setAmphurCode(String amphurCode) {
		this.amphurCode = amphurCode;
	}
}
