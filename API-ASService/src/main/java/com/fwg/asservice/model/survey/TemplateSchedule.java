package com.fwg.asservice.model.survey;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TemplateSchedule")
public class TemplateSchedule {
	
	@Id
	@Column(name = "HeaderTypeCode")
	private String headerTypeCode;
	
	@Column(name = "PatternName")
	private String patternName;
	
	@Column(name = "PatternDescription")
	private String patternDescription;
	
	@Column(name = "TemplateTypeID")
	private int templateTypeId;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_TemplateTypeID")
	private TemplateType templateType;

	public String getHeaderTypeCode() {
		return headerTypeCode;
	}

	public void setHeaderTypeCode(String headerTypeCode) {
		this.headerTypeCode = headerTypeCode;
	}

	public String getPatternName() {
		return patternName;
	}

	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}

	public String getPatternDescription() {
		return patternDescription;
	}

	public void setPatternDescription(String patternDescription) {
		this.patternDescription = patternDescription;
	}

	public int getTemplateTypeId() {
		return templateTypeId;
	}

	public void setTemplateTypeId(int templateTypeId) {
		this.templateTypeId = templateTypeId;
	}

	public TemplateType getTemplateType() {
		return templateType;
	}

	public void setTemplateType(TemplateType templateType) {
		this.templateType = templateType;
	}
	
	

}
