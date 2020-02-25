package com.fwg.asservice.model.survey;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fwg.asservice.model.CancerType;
import com.fwg.asservice.model.Person;

@Entity
@Table(name = "DeathDetailInfo")
public class DeathDetailInfo {

	@Column(name = "RowGUID")
	private String rowGUID;
	
	@Column(name = "DocumentID")
	private String documentId;
	
	@Column(name = "OSMID")
	private String osmId;
	
	@Column(name = "OperationDate")
	private String operationDate;
	
	@Id
	@Column(name = "PersonID")
	private String personId;
	
	@Column(name = "DeathDate")
	private String deathDate;
	
	@Column(name = "HospDeath")
	private String hospDeath;
	
	@Column(name = "Age")
	private Integer age;

	@JsonProperty(value="isDiabetes")
	@Column(name = "IsDiabetes")
	private boolean isDiabetes;

	@JsonProperty(value="isHypertension")
	@Column(name = "IsHypertension")
	private boolean isHypertension;

	@JsonProperty(value="isAccident")
	@Column(name = "IsAccident")
	private boolean isAccident;

	@JsonProperty(value="isCancer")
	@Column(name = "IsCancer")
	private boolean isCancer;
	
	@Column(name = "CancerTypeID")
	private Integer cancerTypeId;
	
	@Column(name = "CauseOther")
	private String causeOther;

	@JsonProperty(value="isNoDisease")
	@Column(name = "IsNoDisease")
	private boolean isNoDisease;
	
	@Column(name = "DeathPlace")
	private String deathPlaceCode;
	
	@Column(name = "PlaceOther")
	private String placeOther;

	@JsonProperty(value="isDeleted")
	@Column(name = "IsDeleted")
	private boolean isDeleted;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PersonID", insertable = false, updatable = false)
	private Person person;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="CancerTypeID", insertable = false, updatable = false)
	private CancerType cancerType;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="DeathPlace", insertable = false, updatable = false)
	private DeathPlace deathPlace;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="OSMID", insertable = false, updatable = false)
	private Person osm;

	
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

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	}

	public String getHospDeath() {
		return hospDeath;
	}

	public void setHospDeath(String hospDeath) {
		this.hospDeath = hospDeath;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public boolean isDiabetes() {
		return isDiabetes;
	}

	public void setDiabetes(boolean isDiabetes) {
		this.isDiabetes = isDiabetes;
	}

	public boolean isHypertension() {
		return isHypertension;
	}

	public void setHypertension(boolean isHypertension) {
		this.isHypertension = isHypertension;
	}

	public boolean isAccident() {
		return isAccident;
	}

	public void setAccident(boolean isAccident) {
		this.isAccident = isAccident;
	}

	public boolean isCancer() {
		return isCancer;
	}

	public void setCancer(boolean isCancer) {
		this.isCancer = isCancer;
	}

	public Integer getCancerTypeId() {
		return cancerTypeId;
	}

	public void setCancerTypeId(Integer cancerTypeId) {
		this.cancerTypeId = cancerTypeId;
	}

	public String getCauseOther() {
		return causeOther;
	}

	public void setCauseOther(String causeOther) {
		this.causeOther = causeOther;
	}

	public boolean isNoDisease() {
		return isNoDisease;
	}

	public void setNoDisease(boolean isNoDisease) {
		this.isNoDisease = isNoDisease;
	}

	public String getDeathPlaceCode() {
		return deathPlaceCode;
	}

	public void setDeathPlaceCode(String deathPlaceCode) {
		this.deathPlaceCode = deathPlaceCode;
	}

	public String getPlaceOther() {
		return placeOther;
	}

	public void setPlaceOther(String placeOther) {
		this.placeOther = placeOther;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public CancerType getCancerType() {
		return cancerType;
	}

	public void setCancerType(CancerType cancerType) {
		this.cancerType = cancerType;
	}

	public DeathPlace getDeathPlace() {
		return deathPlace;
	}

	public void setDeathPlace(DeathPlace deathPlace) {
		this.deathPlace = deathPlace;
	}

	public Person getOsm() {
		return osm;
	}

	public void setOsm(Person osm) {
		this.osm = osm;
	}
	
}
