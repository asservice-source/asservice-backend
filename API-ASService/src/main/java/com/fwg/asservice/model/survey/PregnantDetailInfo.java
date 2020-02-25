package com.fwg.asservice.model.survey;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fwg.asservice.model.Home;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.PregnantSurveyType;
import com.fwg.asservice.sql.CallableStatementUtil;

@Entity
@Table(name = "PregnantDetailInfo")
public class PregnantDetailInfo {
	
	@Id
	@Column(name = "RowGUID")
	private String rowGUID;
	
	@Column(name = "DocumentID")
	private String documentId;
	
	@Column(name = "OSMID")
	private String osmId;
	
	@Column(name = "OperationDate")
	private String operationDate;
	
	@Column(name = "HomeID")
	private Integer homeId;
	
	@Column(name = "MasterGUID")
	private String masterGUID;
	
	@Column(name = "PersonID")
	private String personId;
	
	@Column(name = "WombNo")
	private Integer wombNo;
	
	@Column(name = "BornDueDate")
	private String bornDueDate;
	
	@Column(name = "PSurveyTypeCode")
	private String pSurveyTypeCode;

	@JsonProperty(value="isDeleted")
	@Column(name = "IsDeleted")
	private boolean isDeleted;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="OSMID", insertable = false, updatable = false)
	private Person osm;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="HomeID", insertable = false, updatable = false)
	private Home homePerson;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PersonID", insertable = false, updatable = false)
	private Person person;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PSurveyTypeCode", insertable = false, updatable = false)
	private PregnantSurveyType pregnantSurveyType;

	// For Receive Parameter
	@Transient
	private Child[] childs;

	@Transient
	CallableStatementUtil callableStatementUtil;
	
	public String getRowGUID() {
		return rowGUID;
	}

	public void setRowGUID(String rowGUID) {
		this.rowGUID = rowGUID;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getOsmId() {
		return osmId;
	}

	public void setOsmId(String osmId) {
		this.osmId = osmId;
	}

	public String getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}

	public Integer getHomeId() {
		return homeId;
	}

	public void setHomeId(Integer homeId) {
		this.homeId = homeId;
	}

	public String getMasterGUID() {
		return masterGUID;
	}

	public void setMasterGUID(String masterGUID) {
		this.masterGUID = masterGUID;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Integer getWombNo() {
		return wombNo;
	}

	public void setWombNo(Integer wombNo) {
		this.wombNo = wombNo;
	}

	public String getBornDueDate() {
		return bornDueDate;
	}

	public void setBornDueDate(String bornDueDate) {
		this.bornDueDate = bornDueDate;
	}

	public String getpSurveyTypeCode() {
		return pSurveyTypeCode;
	}

	public void setpSurveyTypeCode(String pSurveyTypeCode) {
		this.pSurveyTypeCode = pSurveyTypeCode;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Person getOsm() {
		return osm;
	}

	public void setOsm(Person osm) {
		this.osm = osm;
	}

	public Home getHomePerson() {
		return homePerson;
	}

	public void setHomePerson(Home homePerson) {
		this.homePerson = homePerson;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public PregnantSurveyType getPregnantSurveyType() {
		return pregnantSurveyType;
	}

	public void setPregnantSurveyType(PregnantSurveyType pregnantSurveyType) {
		this.pregnantSurveyType = pregnantSurveyType;
	}

	public Child[] getChilds() {
		return childs;
	}

	public void setChilds(Child[] childs) {
		this.childs = childs;
	}

	public CallableStatementUtil getCallableStatementUtil() {
		return callableStatementUtil;
	}

	public void setCallableStatementUtil(CallableStatementUtil callableStatementUtil) {
		this.callableStatementUtil = callableStatementUtil;
	}
	
}
