package com.fwg.asservice.model.survey;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fwg.asservice.model.CancerType;
import com.fwg.asservice.model.DisabilityCauseType;
import com.fwg.asservice.model.DisabilityType;
import com.fwg.asservice.model.DiseaseStatusType;
import com.fwg.asservice.model.HealthInsuranceType;
import com.fwg.asservice.model.Home;
import com.fwg.asservice.model.PatientSurveyType;
import com.fwg.asservice.model.PatientType;
import com.fwg.asservice.model.Person;

@Entity
@Table(name = "PatientDetailInfo")
public class PatientDetailInfo {
	
	@Id
	@Column(name = "RowGUID")
	private String rowGUID;
	
	@Column(name = "DocumentID")
	private String documentId;
	
	@Column(name = "OSMID")
	private String osmId;
	
	@Column(name = "HomeID")
	private Integer homeId;
	
	@Column(name = "OperationDate")
	private String operationDate;
	
	@Column(name = "PersonID")
	private String personId;
	
	@Column(name = "CancerTypeID")
	private Integer cancerTypeId;
	
	@Column(name = "DiseaseStatusTypeID")
	private Integer diseaseStatusTypeId;
	
	@Column(name = "PatientDate")
	private Date patientDate;
	
	@Column(name = "PatientTypeID")
	private Integer patientTypeId;
	
	@Column(name = "HInsuranceTypeID")
	private Integer hInsuranceTypeId;
	
	@Column(name = "PatientSurveyTypeCode")
	private String patientSurveyTypeCode;
	
	@Column(name = "DisabilityTypeID")
	private Integer disabilityTypeId;
	
	@Column(name = "DisabilityCauseTypeID")
	private Integer disabilityCauseTypeId;
	
	@Column(name = "TreatmentPlace")
	private String treatmentPlace;
	
	@Column(name = "Remark")
	private String remark;
	
	@Column(name = "Telephone")
	private String telephone;

	@Column(name = "Longitude")
	private String longitude;

	@Column(name = "Latitude")
	private String latitude;
	
	@JsonProperty(value="isDeleted")
	@Column(name = "IsDeleted")
	private boolean isDeleted;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="OSMID", insertable = false, updatable = false)
	private Person osm;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="HomeID", insertable = false, updatable = false)
	private Home home;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PersonID", insertable = false, updatable = false)
	private Person person;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="CancerTypeID", insertable = false, updatable = false)
	private CancerType cancerType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="DiseaseStatusTypeID", insertable = false, updatable = false)
	private DiseaseStatusType diseaseStatusType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PatientTypeID", insertable = false, updatable = false)
	private PatientType patientType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="HInsuranceTypeID", insertable = false, updatable = false)
	private HealthInsuranceType healthInsuranceType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PatientSurveyTypeCode", insertable = false, updatable = false)
	private PatientSurveyType patientSurveyType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="DisabilityTypeID", insertable = false, updatable = false)
	private DisabilityType disabilityType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="DisabilityCauseTypeID", insertable = false, updatable = false)
	private DisabilityCauseType disabilityCauseType;
	
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

	public Integer getHomeId() {
		return homeId;
	}

	public void setHomeId(Integer homeId) {
		this.homeId = homeId;
	}

	public String treatmentPlace() {
		return operationDate;
	}

	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}

	public String getOperationDate() {
		return operationDate;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Integer getCancerTypeId() {
		return cancerTypeId;
	}

	public void setCancerTypeId(Integer cancerTypeId) {
		this.cancerTypeId = cancerTypeId;
	}

	public Integer getDiseaseStatusTypeId() {
		return diseaseStatusTypeId;
	}

	public void setDiseaseStatusTypeId(Integer diseaseStatusTypeId) {
		this.diseaseStatusTypeId = diseaseStatusTypeId;
	}

	public Date getPatientDate() {
		return patientDate;
	}

	public void setPatientDate(Date patientDate) {
		this.patientDate = patientDate;
	}

	public Integer getPatientTypeId() {
		return patientTypeId;
	}

	public void setPatientTypeId(Integer patientTypeId) {
		this.patientTypeId = patientTypeId;
	}

	public Integer gethInsuranceTypeId() {
		return hInsuranceTypeId;
	}

	public void sethInsuranceTypeId(Integer hInsuranceTypeId) {
		this.hInsuranceTypeId = hInsuranceTypeId;
	}

	public String getPatientSurveyTypeCode() {
		return patientSurveyTypeCode;
	}

	public void setPatientSurveyTypeCode(String patientSurveyTypeCode) {
		this.patientSurveyTypeCode = patientSurveyTypeCode;
	}

	public Integer getDisabilityTypeId() {
		return disabilityTypeId;
	}

	public void setDisabilityTypeId(Integer disabilityTypeId) {
		this.disabilityTypeId = disabilityTypeId;
	}

	public Integer getDisabilityCauseTypeId() {
		return disabilityCauseTypeId;
	}

	public void setDisabilityCauseTypeId(Integer disabilityCauseTypeId) {
		this.disabilityCauseTypeId = disabilityCauseTypeId;
	}

	public String getTreatmentPlace() {
		return treatmentPlace;
	}

	public void setTreatmentPlace(String treatmentPlace) {
		this.treatmentPlace = treatmentPlace;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Person getPerson() {
		return person == null? new Person():person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public CancerType getCancerType() {
		return cancerType == null ?new CancerType():cancerType;
	}

	public void setCancerType(CancerType cancerType) {
		this.cancerType = cancerType;
	}

	public DiseaseStatusType getDiseaseStatusType() {
		return diseaseStatusType == null?new DiseaseStatusType():diseaseStatusType;
	}

	public void setDiseaseStatusType(DiseaseStatusType diseaseStatusType) {
		this.diseaseStatusType = diseaseStatusType;
	}

	public PatientType getPatientType() {
		return patientType == null?new PatientType():patientType;
	}

	public void setPatientType(PatientType patientType) {
		this.patientType = patientType;
	}

	public HealthInsuranceType getHealthInsuranceType() {
		return healthInsuranceType == null ? new  HealthInsuranceType():healthInsuranceType;
	}

	public void setHealthInsuranceType(HealthInsuranceType healthInsuranceType) {
		this.healthInsuranceType = healthInsuranceType;
	}

	public PatientSurveyType getPatientSurveyType() {
		return patientSurveyType == null? new PatientSurveyType():patientSurveyType;
	}

	public void setPatientSurveyType(PatientSurveyType patientSurveyType) {
		this.patientSurveyType = patientSurveyType;
	}

	public DisabilityType getDisabilityType() {
		return disabilityType == null ?new DisabilityType():disabilityType;
	}

	public void setDisabilityType(DisabilityType disabilityType) {
		this.disabilityType = disabilityType;
	}

	public DisabilityCauseType getDisabilityCauseType() {
		return disabilityCauseType == null?new DisabilityCauseType():disabilityCauseType;
	}

	public void setDisabilityCauseType(DisabilityCauseType disabilityCauseType) {
		this.disabilityCauseType = disabilityCauseType;
	}

	public Person getOsm() {
		return osm==null?new Person() :osm;
	}

	public void setOsm(Person osm) {
		this.osm = osm;
	}

	public Home getHome() {
		return home == null ? new Home():home;
	}

	public void setHome(Home home) {
		this.home = home;
	}
	
}