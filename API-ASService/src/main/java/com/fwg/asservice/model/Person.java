package com.fwg.asservice.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fwg.asservice.utility.GlobalFunction;

@Entity
@Table(name = "Person")
public class Person implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8808382024612121635L; 

	@Id
	@Column(name = "PersonID")
	private String personId;
	
	@Column(name = "CitizenID")
	private String citizenId;
	
	@Column(name = "FirstName")
	private String firstName;
	
	@Column(name = "LastName")
	private String lastName;
	
	@Column(name = "NickName")
	private String nickName;
	
	@Column(name = "GenderID")
	private Integer genderId;
	
	@Column(name = "PrefixCode")
	private String prefixCode;
	
	@Column(name = "BirthDate")
	private Date birthDate;
	
	@Column(name = "MStatusCode")
	private String mStatusCode;
	
	@Column(name = "OccupCode")
	private String occupCode;
	
	@Column(name = "RaceCode")
	private String raceCode;
	
	@Column(name = "NationCode")
	private String nationCode;
	
	@Column(name = "ReligionCode")
	private String religionCode;
	
	@Column(name = "EducationCode")
	private String educationCode;

	@Column(name = "FatherCID")
	private String fatherCid;
	
	@Column(name = "MotherCID")
	private String motherCid;
	
	@Column(name = "CoupleCID")
	private String coupleCid;
	
	@Column(name = "VStatusCode")
	private String vStatusCode;
	
	@Column(name = "BloodTypeID")
	private Integer bloodTypeId;
	
	@Column(name = "RHGroupID")
	private Integer rhGroupId;
	
	@Column(name = "LaborCode")
	private String laborCode;
	
	@Column(name = "Passport")
	private String passport;
	
	@JsonProperty(value="isDead")
	@Column(name = "IsDead")
	private boolean isDead;
	
	@Column(name = "DeadDate")
	private Date deadDate;
	
	@Column(name = "PicturePath")
	private String picturePath;
	
	@Column(name = "MedicalRightCode")
	private String medicalRightCode;
	
	@Column(name = "CongenitalDisease")
	private String congenitalDisease;
	
	@Column(name = "Remark")
	private String remark;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="GenderID", insertable = false, updatable = false)
	private Gender gender;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PrefixCode", insertable = false, updatable = false)
	private Prefix prefix;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="MStatusCode", insertable = false, updatable = false)
	private MaritalStatus maritalStatus;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="OccupCode", insertable = false, updatable = false)
	private Occupation occupation;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="RaceCode", insertable = false, updatable = false)
	private Race race;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="NationCode", insertable = false, updatable = false)
	private Nationality nationality;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ReligionCode", insertable = false, updatable = false)
	private Religion religion;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="EducationCode", insertable = false, updatable = false)
	private Education education;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="VStatusCode", insertable = false, updatable = false)
	private VillageStatus villageStatus;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="BloodTypeID", insertable = false, updatable = false)
	private BloodType bloodType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="RHGroupID", insertable = false, updatable = false)
	private RHGroup rhGroup;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="LaborCode", insertable = false, updatable = false)
	private Labor labor;
	
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "PersonID", referencedColumnName = "PersonID", insertable=false, updatable=false)
	private Address address;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "MedicalRightCode", referencedColumnName = "Code", insertable=false, updatable=false)
	private MedicalRight medicalRight;

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getCitizenId() {
		return citizenId;
	}

	public void setCitizenId(String citizenId) {
		this.citizenId = citizenId;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getGenderId() {
		return genderId;
	}

	public void setGenderId(Integer genderId) {
		this.genderId = genderId;
	}

	public String getPrefixCode() {
		return prefixCode;
	}

	public void setPrefixCode(String prefixCode) {
		this.prefixCode = prefixCode;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getmStatusCode() {
		return mStatusCode;
	}

	public void setmStatusCode(String mStatusCode) {
		this.mStatusCode = mStatusCode;
	}

	public String getOccupCode() {
		return occupCode;
	}

	public void setOccupCode(String occupCode) {
		this.occupCode = occupCode;
	}

	public String getRaceCode() {
		return raceCode;
	}

	public void setRaceCode(String raceCode) {
		this.raceCode = raceCode;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getReligionCode() {
		return religionCode;
	}

	public void setReligionCode(String religionCode) {
		this.religionCode = religionCode;
	}

	public String getEducationCode() {
		return educationCode;
	}

	public void setEducationCode(String educationCode) {
		this.educationCode = educationCode;
	}

	public String getFatherCid() {
		return fatherCid;
	}

	public void setFatherCid(String fatherCid) {
		this.fatherCid = fatherCid;
	}

	public String getMotherCid() {
		return motherCid;
	}

	public void setMotherCid(String motherCid) {
		this.motherCid = motherCid;
	}

	public String getCoupleCid() {
		return coupleCid;
	}

	public void setCoupleCid(String coupleCid) {
		this.coupleCid = coupleCid;
	}

	public String getvStatusCode() {
		return vStatusCode;
	}

	public void setvStatusCode(String vStatusCode) {
		this.vStatusCode = vStatusCode;
	}

	public Integer getBloodTypeId() {
		return bloodTypeId;
	}

	public void setBloodTypeID(Integer bloodTypeID) {
		this.bloodTypeId = bloodTypeID;
	}

	public Integer getrhGroupId() {
		return rhGroupId;
	}

	public void setrhGroupId(Integer rhGroupId) {
		this.rhGroupId = rhGroupId;
	}

	public String getLaborCode() {
		return laborCode;
	}

	public void setLaborCode(String laborCode) {
		this.laborCode = laborCode;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public Date getDeadDate() {
		return deadDate;
	}

	public void setDeadDate(Date deadDate) {
		this.deadDate = deadDate;
	}

	public Gender getGender() {
		return gender == null ? new Gender() : gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Prefix getPrefix() {
		return prefix == null ? new Prefix() : prefix;
	}

	public void setPrefix(Prefix prefix) {
		this.prefix = prefix;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus == null ? new MaritalStatus() : maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Occupation getOccupation() {
		return occupation == null ? new Occupation() : occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public Race getRace() {
		return race == null ? new Race() : race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Nationality getNationality() {
		return nationality == null ? new Nationality() : nationality;
	}

	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}

	public Religion getReligion() {
		return religion == null ? new Religion() : religion;
	}

	public void setReligion(Religion religion) {
		this.religion = religion;
	}

	public Education getEducation() {
		return education == null ? new Education() : education;
	}

	public void setEducation(Education education) {
		this.education = education;
	}

	public VillageStatus getVillageStatus() {
		return villageStatus == null ? new VillageStatus() : villageStatus;
	}

	public void setVillageStatus(VillageStatus villageStatus) {
		this.villageStatus = villageStatus;
	}

	public BloodType getBloodType() {
		return bloodType == null ? new BloodType() : bloodType;
	}

	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}

	public RHGroup getRhGroup() {
		return rhGroup == null ? new RHGroup() : rhGroup;
	}

	public void setRhGroup(RHGroup rhGroup) {
		this.rhGroup = rhGroup;
	}

	public Labor getLabor() {
		return labor == null ? new Labor() : labor;
	}

	public void setLabor(Labor labor) {
		this.labor = labor;
	}

	public Address getAddress() {
		return address == null ? new Address() : address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPicturePath() {
		return GlobalFunction.isEmpty(picturePath) ? "" : GlobalFunction.getBaseUrl()+picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getMedicalRightCode() {
		return medicalRightCode;
	}

	public void setMedicalRightCode(String medicalRightCode) {
		this.medicalRightCode = medicalRightCode;
	}

	public MedicalRight getMedicalRight() {
		return medicalRight == null ? new MedicalRight() : medicalRight;
	}

	public void setMedicalRight(MedicalRight medicalRight) {
		this.medicalRight = medicalRight;
	}

	public String getCongenitalDisease() {
		return congenitalDisease;
	}

	public void setCongenitalDisease(String congenitalDisease) {
		this.congenitalDisease = congenitalDisease;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
