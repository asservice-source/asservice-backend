package com.fwg.asservice.model.survey;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fwg.asservice.model.Home;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.sql.CallableStatementUtil;

@Entity
@Table(name = "PopulationDetail")
public class PopulationDetail {

	@Column(name = "RowGUID")
	private String rowGUID;
	
	@Column(name = "DocumentID")
	private String documentId;
	
	@Column(name = "OSMID")
	private String osmId;
	
	@Id
	@Column(name = "HomeID")
	private Integer homeId;
	
	@Column(name = "OperationDate")
	private String operationDate;
	
	@Column(name = "MemberAmount")
	private Integer memberAmount;
	
	@Column(name = "IsSurvey")
	private boolean isSurvey;
	
	@Column(name = "IsWithoutOSM")
	private boolean isWithoutOSM;
	
	@Column(name = "IsCurrent")
	private boolean isCurrent;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="OSMID", insertable = false, updatable = false)
	private Person osm;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="HomeID", insertable = false, updatable = false)
	private Home home;
	
	
	@Transient
	CallableStatementUtil callableStatementUtil;
	
	

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

	public Integer getHomeId() {
		return homeId;
	}

	public void setHomeId(Integer homeId) {
		this.homeId = homeId;
	}

	public String getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}

	public Integer getMemberAmount() {
		return memberAmount;
	}

	public void setMemberAmount(Integer memberAmount) {
		this.memberAmount = memberAmount;
	}

	public boolean isSurvey() {
		return isSurvey;
	}

	public void setSurvey(boolean isSurvey) {
		this.isSurvey = isSurvey;
	}
	
	public boolean isWithoutOSM() {
		return isWithoutOSM;
	}

	public void setWithoutOSM(boolean isWithoutOSM) {
		this.isWithoutOSM = isWithoutOSM;
	}
	
	public boolean isCurrent() {
		return isCurrent;
	}

	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
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

	public CallableStatementUtil getCallableStatementUtil() {
		return callableStatementUtil;
	}

	public void setCallableStatementUtil(CallableStatementUtil callableStatementUtil) {
		this.callableStatementUtil = callableStatementUtil;
	}

}
