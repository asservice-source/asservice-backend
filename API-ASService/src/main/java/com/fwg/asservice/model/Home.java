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
@Table(name = "Home")
public class Home {

	@Id
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "RegistrationID")
	private String registrationId;
	
	@Column(name = "HomeTypeCode")
	private String homeTypeCode;
	
	@Column(name = "HomeNO")
	private String homeNo;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "VillageID")
	private Integer villageId;
	
	@Column(name = "Road")
	private String road;
	
	@Column(name = "Soi")
	private String soi;
	
	@Column(name = "Telephone")
	private String telephone;
	
	@Column(name = "Latitude")
	private String latitude;
	
	@Column(name = "Longitude")
	private String longitude;
	
	@Column(name = "OSMID")
	private String osmId;
	
	@Column(name = "HolderID")
	private String holderId;
	
	@Transient
	@Column(name = "RoundGUID")
	private String roundGUID;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="HolderID", insertable = false, updatable = false)
	private Person holder;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="VillageID", insertable = false, updatable = false)
	private Village village;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="HomeTypeCode", insertable = false, updatable = false)
	private HomeType homeType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="OSMID", insertable = false, updatable = false)
	private Person osm;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getHomeTypeCode() {
		return homeTypeCode;
	}

	public void setHomeTypeCode(String homeTypeCode) {
		this.homeTypeCode = homeTypeCode;
	}

	public String getHomeNo() {
		return homeNo;
	}

	public void setHomeNo(String homeNo) {
		this.homeNo = homeNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVillageId() {
		return villageId;
	}

	public void setVillageId(Integer villageId) {
		this.villageId = villageId;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getSoi() {
		return soi;
	}

	public void setSoi(String soi) {
		this.soi = soi;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getOsmId() {
		return osmId;
	}

	public void setOsmId(String osmId) {
		this.osmId = osmId;
	}

	public String getHolderId() {
		return holderId;
	}

	public void setHolderId(String holderId) {
		this.holderId = holderId;
	}
	
	
	public String getRoundGUID() {
		return roundGUID;
	}

	public void setRoundGUID(String roundGUID) {
		this.roundGUID = roundGUID;
	}

	public Person getHolder() {
		return holder == null ? new Person() : holder;
	}

	public void setHolder(Person holder) {
		this.holder = holder;
	}

	public Village getVillage() {
		return village == null ? new Village() : village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	public HomeType getHomeType() {
		return homeType == null ? new HomeType() : homeType;
	}

	public void setHomeType(HomeType homeType) {
		this.homeType = homeType;
	}

	public Person getOsm() {
		return osm == null ? new Person() : osm;
	}

	public void setOsm(Person osm) {
		this.osm = osm;
	}
	
}

