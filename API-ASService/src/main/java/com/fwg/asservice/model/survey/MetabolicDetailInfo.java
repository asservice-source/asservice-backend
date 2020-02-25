package com.fwg.asservice.model.survey;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fwg.asservice.model.DrinkingStatus;
import com.fwg.asservice.model.HealthInsuranceType;
import com.fwg.asservice.model.Home;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.SmokingStatus;

@Entity
@Table(name = "MetabolicDetailInfo")
public class MetabolicDetailInfo implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7224709073289802199L;
	
	@Column(name = "RowGUID")
	private String rowGUID;
	
	@Id
	@Column(name = "DocumentID")
	private String documentId;
	
	@Column(name = "OSMID")
	private String osmId;
	
	@Column(name = "OperationDate")
	private String operationDate;
	
	@Id
	@Column(name = "PersonID")
	private String personId;
	
	@Column(name = "HomeID")
	private Integer homeId;
	
	@Column(name = "HInsuranceTypeID")
	private Integer hInsuranceTypeId;
	
	@JsonProperty(value="isHeredityMetabolic")
	@Column(name = "IsHeredityMetabolic")
	private boolean isHeredityMetabolic;
	
	@JsonProperty(value="isWaistlineOver")
	@Column(name = "IsWaistlineOver")
	private boolean isWaistlineOver;
	
	@JsonProperty(value="isBPOver")
	@Column(name = "IsBPOver")
	private boolean isBPOver;
	
	@JsonProperty(value="isFBS")
	@Column(name = "IsFBS")
	private boolean isFBS;
	
	@JsonProperty(value="isCholesterol")
	@Column(name = "IsCholesterol")
	private boolean isCholesterol;
	
	@JsonProperty(value="isNewborn4kg")
	@Column(name = "IsNewborn4kg")
	private boolean isNewborn4kg;
	
	@JsonProperty(value="isHeredityHypertension")
	@Column(name = "IsHeredityHypertension")
	private boolean isHeredityHypertension;
	
	@Column(name = "SmokingStatusID")
	private Integer smokingStatusId;
	
	@Column(name = "RollPerDay")
	private Integer rollPerDay;
	
	@Column(name = "PackPerYear")
	private Integer packPerYear;
	
	@Column(name = "DrinkingStatusID")
	private Integer drinkingStatusId;
	
	@Column(name = "OftenPerWeek")
	private Integer oftenPerWeek;
	
	@Column(name = "Weight")
	private Integer weight;
	
	@Column(name = "Height")
	private Integer height;
	
	@Column(name = "BMI")
	private String bmi;
	
	@Column(name = "Waistline")
	private Integer waistline;
	
	@Column(name = "BP1")
	private String bp1;
	
	@Column(name = "BP2")
	private String bp2;
	
	@Column(name = "FBS")
	private Integer fbs;
	
	@JsonProperty(value="isMetabolic")
	@Column(name = "IsMetabolic")
	private boolean isMetabolic;
	
	@JsonProperty(value="isHypertension")
	@Column(name = "IsHypertension")
	private boolean isHypertension;
	
	@JsonProperty(value="isEyeComplication")
	@Column(name = "IsEyeComplication")
	private boolean isEyeComplication;
	
	@JsonProperty(value="isKidneyComplication")
	@Column(name = "IsKidneyComplication")
	private boolean isKidneyComplication;
	
	@JsonProperty(value="isPeripheralNeuropathy")
	@Column(name = "IsPeripheralNeuropathy")
	private boolean isPeripheralNeuropathy;
	
	@Column(name = "PeripheralName")
	private String peripheralName;
	
	@JsonProperty(value="isNeuropathy")
	@Column(name = "IsNeuropathy")
	private boolean isNeuropathy;
	
	@JsonProperty(value="isOther")
	@Column(name = "IsOther")
	private boolean isOther;
	
	@Column(name = "OtherComplication")
	private String otherComplication;

	@JsonProperty(value="isDeleted")
	@Column(name = "IsDeleted")
	private boolean isDeleted;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PersonID", insertable = false, updatable = false)
	private Person person;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="HomeID", insertable = false, updatable = false)
	private Home home;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="OSMID", insertable = false, updatable = false)
	private Person osm;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="HInsuranceTypeID", insertable = false, updatable = false)
	private HealthInsuranceType healthInsuranceType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="DrinkingStatusID", insertable = false, updatable = false)
	private DrinkingStatus drinkingStatus;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="SmokingStatusID", insertable = false, updatable = false)
	private SmokingStatus smokingStatus;

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
	
	public Integer getHomeId() {
		return homeId;
	}

	public void setHomeId(Integer homeId) {
		this.homeId = homeId;
	}

	public Integer gethInsuranceTypeId() {
		return hInsuranceTypeId;
	}

	public void sethInsuranceTypeId(Integer hInsuranceTypeId) {
		this.hInsuranceTypeId = hInsuranceTypeId;
	}

	public boolean isHeredityMetabolic() {
		return isHeredityMetabolic;
	}

	public void setHeredityMetabolic(boolean isHeredityMetabolic) {
		this.isHeredityMetabolic = isHeredityMetabolic;
	}

	public boolean isWaistlineOver() {
		return isWaistlineOver;
	}

	public void setWaistlineOver(boolean isWaistlineOver) {
		this.isWaistlineOver = isWaistlineOver;
	}

	public boolean isBPOver() {
		return isBPOver;
	}

	public void setBPOver(boolean isBPOver) {
		this.isBPOver = isBPOver;
	}

	public boolean isFBS() {
		return isFBS;
	}

	public void setFBS(boolean isFBS) {
		this.isFBS = isFBS;
	}

	public boolean isCholesterol() {
		return isCholesterol;
	}

	public void setCholesterol(boolean isCholesterol) {
		this.isCholesterol = isCholesterol;
	}

	public boolean isNewborn4kg() {
		return isNewborn4kg;
	}

	public void setNewborn4kg(boolean isNewborn4kg) {
		this.isNewborn4kg = isNewborn4kg;
	}

	public boolean isHeredityHypertension() {
		return isHeredityHypertension;
	}

	public void setHeredityHypertension(boolean isHeredityHypertension) {
		this.isHeredityHypertension = isHeredityHypertension;
	}

	public Integer getSmokingStatusId() {
		return smokingStatusId;
	}

	public void setSmokingStatusId(Integer smokingStatusId) {
		this.smokingStatusId = smokingStatusId;
	}

	public Integer getRollPerDay() {
		return rollPerDay;
	}

	public void setRollPerDay(Integer rollPerDay) {
		this.rollPerDay = rollPerDay;
	}

	public Integer getPackPerYear() {
		return packPerYear;
	}

	public void setPackPerYear(Integer packPerYear) {
		this.packPerYear = packPerYear;
	}

	public Integer getDrinkingStatusId() {
		return drinkingStatusId;
	}

	public void setDrinkingStatusId(Integer drinkingStatusId) {
		this.drinkingStatusId = drinkingStatusId;
	}

	public Integer getOftenPerWeek() {
		return oftenPerWeek;
	}

	public void setOftenPerWeek(Integer oftenPerWeek) {
		this.oftenPerWeek = oftenPerWeek;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getBmi() {
		return bmi;
	}

	public void setBmi(String bmi) {
		this.bmi = bmi;
	}

	public Integer getWaistline() {
		return waistline;
	}

	public void setWaistline(Integer waistline) {
		this.waistline = waistline;
	}

	public String getBp1() {
		return bp1;
	}

	public void setBp1(String bp1) {
		this.bp1 = bp1;
	}

	public String getBp2() {
		return bp2;
	}

	public void setBp2(String bp2) {
		this.bp2 = bp2;
	}

	public Integer getFbs() {
		return fbs;
	}

	@JsonProperty(value="fbs")
	public void setFbs(Integer fbs) {
		this.fbs = fbs;
	}

	public boolean isMetabolic() {
		return isMetabolic;
	}

	public void setMetabolic(boolean isMetabolic) {
		this.isMetabolic = isMetabolic;
	}

	public boolean isHypertension() {
		return isHypertension;
	}

	public void setHypertension(boolean isHypertension) {
		this.isHypertension = isHypertension;
	}

	public boolean isEyeComplication() {
		return isEyeComplication;
	}

	public void setEyeComplication(boolean isEyeComplication) {
		this.isEyeComplication = isEyeComplication;
	}

	public boolean isKidneyComplication() {
		return isKidneyComplication;
	}

	public void setKidneyComplication(boolean isKidneyComplication) {
		this.isKidneyComplication = isKidneyComplication;
	}

	public boolean isPeripheralNeuropathy() {
		return isPeripheralNeuropathy;
	}

	public void setPeripheralNeuropathy(boolean isPeripheralNeuropathy) {
		this.isPeripheralNeuropathy = isPeripheralNeuropathy;
	}

	public String getPeripheralName() {
		return peripheralName;
	}

	public void setPeripheralName(String peripheralName) {
		this.peripheralName = peripheralName;
	}

	public boolean isNeuropathy() {
		return isNeuropathy;
	}

	public void setNeuropathy(boolean isNeuropathy) {
		this.isNeuropathy = isNeuropathy;
	}

	public boolean isOther() {
		return isOther;
	}

	public void setOther(boolean isOther) {
		this.isOther = isOther;
	}

	public String getOtherComplication() {
		return otherComplication;
	}

	public void setOtherComplication(String otherComplication) {
		this.otherComplication = otherComplication;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Person getPerson() {
		return person == null ? new Person() : person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public Home getHome() {
		return home == null ? new Home() : home;
	}

	public void setHome(Home home) {
		this.home = home;
	}

	public Person getOsm() {
		return osm == null ? new Person() : osm;
	}

	public void setOsm(Person osm) {
		this.osm = osm;
	}

	public HealthInsuranceType getHealthInsuranceType() {
		return healthInsuranceType == null ? new HealthInsuranceType() : healthInsuranceType;
	}

	public void setHealthInsuranceType(HealthInsuranceType healthInsuranceType) {
		this.healthInsuranceType = healthInsuranceType;
	}

	public DrinkingStatus getDrinkingStatus() {
		return drinkingStatus == null ? new DrinkingStatus() : drinkingStatus;
	}

	public void setDrinkingStatus(DrinkingStatus drinkingStatus) {
		this.drinkingStatus = drinkingStatus;
	}

	public SmokingStatus getSmokingStatus() {
		return smokingStatus == null ? new SmokingStatus() : smokingStatus;
	}

	public void setSmokingStatus(SmokingStatus smokingStatus) {
		this.smokingStatus = smokingStatus;
	}

}