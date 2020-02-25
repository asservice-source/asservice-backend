package com.fwg.asservice.model.survey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DeathPlace")
public class DeathPlace {
	
	@Id
	@Column(name = "Code")
	private String code;
	
	@Column(name = "Name")
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
