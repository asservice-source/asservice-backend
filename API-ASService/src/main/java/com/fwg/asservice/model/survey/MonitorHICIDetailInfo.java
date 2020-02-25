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
import com.fwg.asservice.model.Home;
import com.fwg.asservice.model.Person;

@Entity
@Table(name = "MonitorHICIDetailInfo")
public class MonitorHICIDetailInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6384248795989891743L;

	@Column(name = "RowGUID")
	private String rowGUID;
	
	@Column(name = "DocumentID")
	private String documentId;
	
	@Column(name = "OSMID")
	private String osmId;
	
	@Column(name = "OperationDate")
	private String operationDate;
	
	@Id
	@Column(name = "HomeID")
	private Integer homeId;
	
	@JsonProperty(value="containerTypeId")
	@Column(name = "ContainerID")
	private Integer containerId;
	
	@Column(name = "TotalSurvey")
	private Integer totalSurvey;
	
	@Column(name = "TotalDetect")
	private Integer totalDetect;
	
	@Column(name = "LocateTypeID")
	private Integer locateTypeId;
	
	@JsonProperty(value="isDeleted")
	@Column(name = "IsDeleted")
	private boolean isDeleted;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="OSMID", insertable = false, updatable = false)
	private Person osm;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="HomeID", insertable = false, updatable = false)
	private Home home;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="LocateTypeID", insertable = false, updatable = false)
	private ContainerLocateType containerLocateType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ContainerID", insertable = false, updatable = false)
	private ContainerType containerType;

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

	public Integer getHomeId() {
		return homeId;
	}

	public void setHomeId(Integer homeId) {
		this.homeId = homeId;
	}

	public Integer getContainerId() {
		return containerId;
	}

	public void setContainerId(Integer containerId) {
		this.containerId = containerId;
	}

	public Integer getTotalSurvey() {
		return totalSurvey;
	}

	public void setTotalSurvey(Integer totalSurvey) {
		this.totalSurvey = totalSurvey;
	}

	public Integer getTotalDetect() {
		return totalDetect;
	}

	public void setTotalDetect(Integer totalDetect) {
		this.totalDetect = totalDetect;
	}

	public Integer getLocateTypeId() {
		return locateTypeId;
	}

	public void setLocateTypeId(Integer locateTypeId) {
		this.locateTypeId = locateTypeId;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Person getOsm() {
		return osm == null ? new Person() : osm;
	}

	public void setOsm(Person osm) {
		this.osm = osm;
	}

	public Home getHome() {
		return home == null ? new Home() : home;
	}

	public void setHome(Home home) {
		this.home = home;
	}

	public ContainerLocateType getContainerLocatType() {
		return containerLocateType == null ? new ContainerLocateType() : containerLocateType;
	}

	public void setContainerLocatType(ContainerLocateType containerLocateType) {
		this.containerLocateType = containerLocateType;
	}

	public ContainerType getContainerType() {
		return containerType == null ? new ContainerType() : containerType;
	}

	public void setContainerType(ContainerType containerType) {
		this.containerType = containerType;
	}
	
}
