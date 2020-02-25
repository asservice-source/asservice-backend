package com.fwg.asservice.model.survey;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fwg.asservice.model.Education;
import com.fwg.asservice.model.FamilyStatus;
import com.fwg.asservice.model.Occupation;
import com.fwg.asservice.model.Person;

@Entity
@Table(name = "PopulationDetailInfo")
public class PopulationDetailInfo implements Serializable { 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3758809739048322133L;
	
	@Column(name = "RowGUID")
	private String rowGUID;
	
	@Id
	@Column(name = "ReferenceID")
	private String referenceId;
	
	@Id
	@Column(name = "PersonID")
	private String personId;
	
	@Column(name = "BirthDate")
	private Date birthDate;
	
	@Column(name = "OccupCode")
	private String occupCode;
	
	@Column(name = "EducationCode")
	private String educationCode;
	
	@Column(name = "CongenitalDisease")
	private String congenitalDisease;
	
	@Column(name = "FamilyStatusID")
	private Integer familyStatusId;
	
	@Transient
	private Integer dischargeId;
	@Transient
	private Date dischargeDate;
	
	@JsonProperty(value="isGuest")
	@Column(name = "IsGuest")
	private boolean isGuest;
	
	@JsonProperty(value="isExists")
	@Column(name = "IsExists")
	private boolean isExists;
	
	@Column(name = "Remark")
	private String remark;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PersonID", insertable = false, updatable = false)
	private Person person;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FamilyStatusID", insertable = false, updatable = false)
	private FamilyStatus familyStatus;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="EducationCode", insertable = false, updatable = false)
	private Education education;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="OccupCode", insertable = false, updatable = false)
	private Occupation occupation;

	public String getRowGUID() {
		return rowGUID;
	}

	public void setRowGUID(String rowGUID) {
		this.rowGUID = rowGUID;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getOccupCode() {
		return occupCode;
	}

	public void setOccupCode(String occupCode) {
		this.occupCode = occupCode;
	}

	public String getEducationCode() {
		return educationCode;
	}

	public void setEducationCode(String educationCode) {
		this.educationCode = educationCode;
	}

	public String getCongenitalDisease() {
		return congenitalDisease;
	}

	public void setCongenitalDisease(String congenitalDisease) {
		this.congenitalDisease = congenitalDisease;
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

	public boolean isExists() {
		return isExists;
	}

	public void setExists(boolean isExists) {
		this.isExists = isExists;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public FamilyStatus getFamilyStatus() {
		return familyStatus == null ? new FamilyStatus() : familyStatus;
	}

	public void setFamilyStatus(FamilyStatus familyStatus) {
		this.familyStatus = familyStatus;
	}

	public Education getEducation() {
		return education == null ? new Education() : education;
	}

	public void setEducation(Education education) {
		this.education = education;
	}

	public Occupation getOccupation() {
		return occupation == null ? new Occupation() : occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}
	
}

