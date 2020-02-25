package com.fwg.asservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "Address")
public class Address implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7052949139795288961L;

	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "PersonID")
	private String personId;
	
	@Column(name = "HomeNO")
	private String homeNo;
	
	@Column(name = "MooNO")
	private String mooNo;
	
	@Column(name = "Road")
	private String road;

	@Column(name = "TumbolCode")
	private String tumbolCode;

	@Column(name = "AmphurCode")
	private String amphurCode;

	@Column(name = "ProvinceCode")
	private String provinceCode;
	
	@Column(name = "ZipCode")
	private String zipcode;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ProvinceCode", insertable = false, updatable = false)
	private Province province;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="AmphurCode", insertable = false, updatable = false)
	private Amphur amphur;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="TumbolCode", insertable = false, updatable = false)
	private Tumbol tumbol;


	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getHomeNo() {
		return homeNo;
	}

	public void setHomeNo(String homeNo) {
		this.homeNo = homeNo;
	}

	public String getMooNo() {
		return mooNo;
	}

	public void setMooNo(String mooNo) {
		this.mooNo = mooNo;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
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

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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
	
}
