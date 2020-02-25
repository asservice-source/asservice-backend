package com.fwg.asservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "Hospital")
public class Hospital {
	
	@Id
	@Column(name = "Code5")
	private String code5;
	
	@Column(name = "Code9")
	private String code9;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "HospitalTypeCode")
	private String hospitalTypeCode;
	
	@Column(name = "Address")
	private String address;
	
	@Column(name = "TumbolCode")
	private String tumbolCode;
	
	@Column(name = "AmphurCode")
	private String amphurCode;
	
	@Column(name = "ProvinceCode")
	private String provinceCode;
	
	@Column(name = "MooNo")
	private String mooNo;
	
	@Column(name = "Telephone")
	private String telephone;
	
	@Column(name = "Fax")
	private String fax;
	
	@Column(name = "ZipCode")
	private String zipCode;
	
	@Column(name = "HospitalMasterCode")
	private String hospitalMasterCode;
	
	@Column(name = "ContactPrefix")
	private String contactPrefix;
	
	@Column(name = "ContactFirstName")
	private String contactFirstName;
	
	@Column(name = "ContactLastName")
	private String contactLastName;
	
	@Column(name = "ContactCitizenID")
	private String contactCitizenId;
	
	@Column(name = "ContactTelephone") 
	private String contactTelephone;
	
	@Column(name = "ContactEmail")
	@Email
	private String contactEmail;
	
	@Column(name = "IsActive")
	private boolean isActive;
	
	@Column(name = "IsRegister")
	private boolean isRegister;
	
	@Column(name = "TokenID") 
	private String tokenId;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ProvinceCode", insertable = false, updatable = false)
	private Province province;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="AmphurCode", insertable = false, updatable = false)
	private Amphur amphur;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="TumbolCode", insertable = false, updatable = false)
	private Tumbol tumbol;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ContactPrefix", insertable = false, updatable = false)
	private Prefix prefix;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="HospitalTypeCode", insertable = false, updatable = false)
	private HospitalType hospitalType;

	public String getCode5() {
		return code5;
	}

	public void setCode5(String code5) {
		this.code5 = code5;
	}

	public String getCode9() {
		return code9;
	}

	public void setCode9(String code9) {
		this.code9 = code9;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHospitalTypeCode() {
		return hospitalTypeCode;
	}

	public void setHospitalTypeCode(String hospitalTypeCode) {
		this.hospitalTypeCode = hospitalTypeCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTumbolCode() {
		return tumbolCode;
	}

	public void setTumbolCode(String tumbolCode) {
		this.tumbolCode = tumbolCode;
	}

	public String getAmphurCode() {
		return amphurCode;
	}

	public void setAmphurCode(String amphurCode) {
		this.amphurCode = amphurCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getMooNo() {
		return mooNo;
	}

	public void setMooNo(String mooNo) {
		this.mooNo = mooNo;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getHospitalMasterCode() {
		return hospitalMasterCode;
	}

	public void setHospitalMasterCode(String hospitalMasterCode) {
		this.hospitalMasterCode = hospitalMasterCode;
	}

	public String getContactPrefix() {
		return contactPrefix;
	}

	public void setContactPrefix(String contactPrefix) {
		this.contactPrefix = contactPrefix;
	}

	public String getContactFirstName() {
		return contactFirstName;
	}

	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}

	public String getContactLastName() {
		return contactLastName;
	}

	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	public String getContactCitizenId() {
		return contactCitizenId;
	}

	public void setContactCitizenId(String contactCitizenId) {
		this.contactCitizenId = contactCitizenId;
	}

	public String getContactTelephone() {
		return contactTelephone;
	}

	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isRegister() {
		return isRegister;
	}

	public void setRegister(boolean isRegister) {
		this.isRegister = isRegister;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Province getProvince() {
		return province == null ? new Province() : province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public Amphur getAmphur() {
		return amphur == null ? new Amphur() : amphur;
	}

	public void setAmphur(Amphur amphur) {
		this.amphur = amphur;
	}

	public Tumbol getTumbol() {
		return tumbol == null ? new Tumbol() : tumbol;
	}

	public void setTumbol(Tumbol tumbol) {
		this.tumbol = tumbol;
	}

	public Prefix getPrefix() {
		return prefix == null ? new Prefix() : prefix;
	}

	public void setPrefix(Prefix prefix) {
		this.prefix = prefix;
	}

	public HospitalType getHospitalType() {
		return hospitalType == null ? new HospitalType() : hospitalType;
	}

	public void setHospitalType(HospitalType hospitalType) {
		this.hospitalType = hospitalType;
	}

	

}
