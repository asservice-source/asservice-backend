package com.fwg.asservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Village")
public class Village {
	
	@Id
	@Column(name = "ID")
	private int id;
	
	@Column(name = "VillageNo")
	private String villageNo;
	
	@Column(name = "VillageName")
	private String villageName;
	
	@Column(name = "HospitalCode")
	private String hospitalCode;
	
	@Column(name = "CreatedBy")
	private String createdBy;
	
	@Column(name = "CreatedDate")
	private String createdDate;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="HospitalCode", insertable = false, updatable = false)
	private Hospital hospital;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVillageNo() {
		return villageNo;
	}

	public void setVillageNo(String villageNo) {
		this.villageNo = villageNo;
	}
	
	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
}