package com.fwg.asservice.model.survey;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fwg.asservice.model.BloodType;
import com.fwg.asservice.model.BornLocation;
import com.fwg.asservice.model.BornType;
import com.fwg.asservice.model.Gender;
import com.fwg.asservice.model.Prefix;

@Entity
@Table(name = "Child")
public class Child {
	
	@Id
	@Column(name = "RowGUID")
	private String rowGUID;
	
	@Column(name = "ReferenceID")
	private String referenceId;
	
	@Column(name = "ChildNo")
	private Integer childNo;
	
	@Column(name = "BloodTypeID")
	private Integer bloodTypeId;
	
	@Column(name = "BornTypeID")
	private Integer bornTypeId;
	
	@Column(name = "BirthDate")
	private String birthDate;
	
	@Column(name = "Weight")
	private Integer weight;
	
	@Column(name = "PrefixCode")
	private String prefixCode;
	
	@Column(name = "FirstName")
	private String firstName;
	
	@Column(name = "LastName")
	private String lastName;
	
	@Column(name = "GenderID")
	private Integer genderId;
	
	@Column(name = "BornLocationID")
	private Integer bornLocationId;
	
	@Column(name = "BornCitizenID")
	private String bornCitizenId;
	
	@Column(name = "AbortionCause")
	private String abortionCause;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ReferenceID", insertable = false, updatable = false)
	private PregnantDetailInfo pregnantDetailInfo;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="BloodTypeID", insertable = false, updatable = false)
	private BloodType bloodType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="BornTypeID", insertable = false, updatable = false)
	private BornType bornType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PrefixCode", insertable = false, updatable = false)
	private Prefix prefix;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="GenderID", insertable = false, updatable = false)
	private Gender gender;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="BornLocationID", insertable = false, updatable = false)
	private BornLocation bornLocation;
	
	@Transient
	private String personIdChild;
	
	@Transient
	private String motherCID;
	
	@Transient
	private Integer homeID;

	
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

	public Integer getChildNo() {
		return childNo;
	}

	public void setChildNo(Integer childNo) {
		this.childNo = childNo;
	}

	public Integer getBloodTypeId() {
		return bloodTypeId;
	}

	public void setBloodTypeId(Integer bloodTypeId) {
		this.bloodTypeId = bloodTypeId;
	}

	public Integer getBornTypeId() {
		return bornTypeId;
	}

	public void setBornTypeId(Integer bornTypeId) {
		this.bornTypeId = bornTypeId;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getPrefixCode() {
		return prefixCode;
	}

	public void setPrefixCode(String prefixCode) {
		this.prefixCode = prefixCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getGenderId() {
		return genderId;
	}

	public void setGenderId(Integer genderId) {
		this.genderId = genderId;
	}

	public Integer getBornLocationId() {
		return bornLocationId;
	}

	public void setBornLocationId(Integer bornLocationId) {
		this.bornLocationId = bornLocationId;
	}

	public String getBornCitizenId() {
		return bornCitizenId;
	}

	public void setBornCitizenId(String bornCitizenId) {
		this.bornCitizenId = bornCitizenId;
	}

	public String getAbortionCause() {
		return abortionCause;
	}

	public void setAbortionCause(String abortionCause) {
		this.abortionCause = abortionCause;
	}

	public PregnantDetailInfo getPregnantDetailInfo() {
		return pregnantDetailInfo;
	}

	public void setPregnantDetailInfo(PregnantDetailInfo pregnantDetailInfo) {
		this.pregnantDetailInfo = pregnantDetailInfo;
	}

	public BloodType getBloodType() {
		return bloodType;
	}

	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}

	public BornType getBornType() {
		return bornType;
	}

	public void setBornType(BornType bornType) {
		this.bornType = bornType;
	}

	public Prefix getPrefix() {
		return prefix;
	}

	public void setPrefix(Prefix prefix) {
		this.prefix = prefix;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public BornLocation getBornLocation() {
		return bornLocation;
	}

	public void setBornLocation(BornLocation bornLocation) {
		this.bornLocation = bornLocation;
	}

	public String getPersonIdChild() {
		return personIdChild;
	}

	public void setPersonIdChild(String personIdChild) {
		this.personIdChild = personIdChild;
	}

	public String getMotherCID() {
		return motherCID;
	}

	public void setMotherCID(String motherCID) {
		this.motherCID = motherCID;
	}

	public Integer getHomeID() {
		return homeID;
	}

	public void setHomeID(Integer homeID) {
		this.homeID = homeID;
	}

	
}
