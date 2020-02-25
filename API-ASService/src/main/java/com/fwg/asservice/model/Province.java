package com.fwg.asservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Province")
public class Province {

	@Id
	@Column(name = "Code")
	private String code;
	
	@Column(name = "Name")
	private String name;
	
	
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
}
