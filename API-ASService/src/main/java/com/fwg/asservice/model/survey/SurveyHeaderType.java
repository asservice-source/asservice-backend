package com.fwg.asservice.model.survey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SurveyHeaderType")
public class SurveyHeaderType {
	
	@Id
	@Column(name = "Code")
	private String code;
	
	@Column(name = "DetailTable")
	private String detailTable;
	
	@Column(name = "Description")
	private String description;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDetailTable() {
		return detailTable;
	}

	public void setDetailTable(String detailTable) {
		this.detailTable = detailTable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
