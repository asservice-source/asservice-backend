package com.fwg.asservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "UserMapping")
public class UserMapping {
	
	@Id
	@Column(name = "UserLoginID")
	private String userLoginID;

	@Column(name = "PersonID")
	private String personID;

	@Column(name = "UserRoleID")
	private Integer userRoleID;

	@Column(name = "HospitalCode")
	private String hospitalCode;
	
	@Transient
	private Integer villageId;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PersonID", insertable = false, updatable = false)
	private Person person;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="UserRoleID", insertable = false, updatable = false)
	private UserRoles userRoles;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="HospitalCode", insertable = false, updatable = false)
	private Hospital hospital;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="UserLoginID", insertable = false, updatable = false)
	private UserLogin userLogin;

	public String getUserLoginID() {
		return userLoginID;
	}

	public void setUserLoginID(String userLoginID) {
		this.userLoginID = userLoginID;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

	public Integer getUserRoleID() {
		return userRoleID;
	}

	public void setUserRoleID(Integer userRoleID) {
		this.userRoleID = userRoleID;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public Person getPerson() {
		return person == null?new Person():person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public UserRoles getUserRoles() {
		return userRoles == null?new UserRoles():userRoles;
	}

	public void setUserRoles(UserRoles userRoles) {
		this.userRoles = userRoles;
	}

	public Hospital getHospital() {
		return hospital ==null?new Hospital():hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public UserLogin getUserLogin() {
		return userLogin ==null?new UserLogin():userLogin;
	}

	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public Integer getVillageId() {
		return villageId;
	}

	public void setVillageId(Integer villageId) {
		this.villageId = villageId;
	}
	
}