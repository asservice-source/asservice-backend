package com.fwg.asservice.model.survey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SurveyStatus")
public class SurveyStatus {
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "Name")
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
