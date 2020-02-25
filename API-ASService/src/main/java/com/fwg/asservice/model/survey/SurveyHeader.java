package com.fwg.asservice.model.survey;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fwg.asservice.model.Hospital;

@Entity
@Table(name = "SurveyHeader")
public class SurveyHeader {

	@Id
	@Column(name = "RowGUID")
	private String rowGUID;
	
	@Column(name = "HeaderTypeCode")
	private String headerTypeCode;
	
	@Column(name = "HospitalCode")
	private String hospitalCode;
	
	@Column(name = "Round")
	private String round;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "StartDate")
	private Date startDate;
	
	@Column(name = "EndDate")
	private Date endDate;
	
	@Column(name = "Status")
	private String status;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="HeaderTypeCode", insertable = false, updatable = false)
	private SurveyHeaderType surveyHeaderType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="HospitalCode", insertable = false, updatable = false)
	private Hospital hospital;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="Status", insertable = false, updatable = false)
	private SurveyStatus surveyStatus;

	public String getRowGUID() {
		return rowGUID;
	}

	public void setRowGUID(String rowGUID) {
		this.rowGUID = rowGUID;
	}

	public String getHeaderTypeCode() {
		return headerTypeCode;
	}

	public void setHeaderTypeCode(String headerTypeCode) {
		this.headerTypeCode = headerTypeCode;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SurveyHeaderType getSurveyHeaderType() {
		return surveyHeaderType;
	}

	public void setSurveyHeaderType(SurveyHeaderType surveyHeaderType) {
		this.surveyHeaderType = surveyHeaderType;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public SurveyStatus getSurveyStatus() {
		return surveyStatus;
	}

	public void setSurveyStatus(SurveyStatus surveyStatus) {
		this.surveyStatus = surveyStatus;
	}
	
}
