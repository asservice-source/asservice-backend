package com.fwg.asservice.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "HomeMembers")
public class HomeMembers implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8437958716639455105L;

	@Id
	@Column(name = "HomeID")
	private Integer homeId;
	
	@Id
	@Column(name = "PersonID")
	private String personId;
	
	@Column(name = "FamilyStatusID")
	private Integer familyStatusId;
	
	@Column(name = "DischargeID")
	private Integer dischargeId;
	
	@Column(name = "DischargeDate") 
	private Date dischargeDate;
	
	@JsonProperty(value="isGuest")
	@Column(name = "IsGuest")
	private boolean isGuest;
	
	@JsonProperty(value="isExists")
	@Column(name = "IsExists")
	private boolean isExists;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FamilyStatusID", insertable = false, updatable = false)
	private FamilyStatus familyStatus;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="DischargeID", insertable = false, updatable = false)
	private Discharge discharge;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PersonID", insertable = false, updatable = false)
	private Person person;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="HomeID", insertable = false, updatable = false)
	private Home home;

	public int getHomeId() {
		return homeId;
	}

	public void setHomeId(int homeId) {
		this.homeId = homeId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Integer getFamilyStatusId() {
		return familyStatusId;
	}

	public void setFamilyStatusId(Integer familyStatusId) {
		this.familyStatusId = familyStatusId;
	}

	public Integer getDischargeId() {
		return dischargeId;
	}

	public void setDischargeId(Integer dischargeId) {
		this.dischargeId = dischargeId;
	}

	public Date getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(Date dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	public boolean isGuest() {
		return isGuest;
	}

	public void setGuest(boolean isGuest) {
		this.isGuest = isGuest;
	}
	
	public FamilyStatus getFamilyStatus() {
		return familyStatus == null ? new FamilyStatus() : familyStatus;
	}

	public void setFamilyStatus(FamilyStatus familyStatus) {
		this.familyStatus = familyStatus;
	}
	

	public Discharge getDischarge() {
		return discharge == null ? new Discharge() : discharge;
	}

	public void setDischarge(Discharge discharge) {
		this.discharge = discharge;
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

	public boolean isExists() {
		return isExists;
	}

	public void setExists(boolean isExists) {
		this.isExists = isExists;
	}
	
}
