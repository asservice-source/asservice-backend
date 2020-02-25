package com.fwg.asservice.model.filter;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Filter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2079562733194007221L;
	private Integer villageId;
	private Integer homeId;
	private String documentId;
	private String osmId;
	private String name;
	private String rowGUID;
	private String code5;
	private String code9;
	private String deleteId;
	private String userId;
	private String homeTypeCode;
	@JsonProperty(value="isManage")
	private boolean isManage;
	private String headerTypeCode;
	@JsonProperty(value="isAll")
	private boolean isAll;
	boolean active = true;
	private String citizenId;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private String userLoginId;
	private String userName;
	private String password;
	private String sessionId;
	@JsonProperty(value="memberOnly")
	private boolean memberOnly;
	private int roleId;
	private String hospitalName;
	private String provinceCode;
	private String amphurCode;
	private String tumbolCode;
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	/**
	 * user/info  module
	 * @return the user info
	 */
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the homeId
	 */
	public Integer getHomeId() {
		return homeId;
	}
	/**
	 * @param homeId the homeId to set
	 */
	public void setHomeId(Integer homeId) {
		this.homeId = homeId;
	}
	/**
	 * @return the village.Id
	 */
	public Integer getVillageId() {
		return villageId;
	}
	/**
	 * @param villageId the village.Id to set
	 */
	public void setVillageId(Integer villageId) {
		this.villageId = villageId;
	}
	/**
	 * @return the SurveyHeader.RowGUID
	 */
	public String getDocumentId() {
		return documentId;
	}
	/**
	 * @param documentId the SurveyHeader.RowGUID to set
	 */
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	/**
	 * @return the person.personId  (*is OSM)
	 */
	public String getOsmId() {
		return osmId;
	}
	/**
	 * @param osmId the person.personId to set
	 */
	public void setOsmId(String osmId) {
		this.osmId = osmId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the input name value to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the rowGUID
	 */
	public String getRowGUID() {
		return rowGUID;
	}
	/**
	 * @param rowGUID the rowGUID to set
	 */
	public void setRowGUID(String rowGUID) {
		this.rowGUID = rowGUID;
	}
	/**
	 * @return the code5
	 */
	public String getCode5() {
		return code5;
	}
	/**
	 * @param code5 the code5 to set
	 */
	public void setCode5(String code5) {
		this.code5 = code5;
	}
	/**
	 * @return the code9
	 */
	public String getCode9() {
		return code9;
	}
	/**
	 * @param code9 the code9 to set
	 */
	public void setCode9(String code9) {
		this.code9 = code9;
	}
	
	public String getDeleteId() {
		return deleteId;
	}
	public void setDeleteId(String deleteId) {
		this.deleteId = deleteId;
	}
	public String getHomeTypeCode() {
		return homeTypeCode;
	}
	public void setHomeTypeCode(String homeTypeCode) {
		this.homeTypeCode = homeTypeCode;
	}
	public boolean isManage() {
		return isManage;
	}
	public void setManage(boolean isManage) {
		this.isManage = isManage;
	}
	public String getHeaderTypeCode() {
		return headerTypeCode;
	}
	public void setHeaderTypeCode(String headerTypeCode) {
		this.headerTypeCode = headerTypeCode;
	}
	public boolean isAll() {
		return isAll;
	}
	public void setAll(boolean isAll) {
		this.isAll = isAll;
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
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getUserLoginId() {
		return userLoginId;
	}
	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public boolean isMemberOnly() {
		return memberOnly;
	}
	public void setMemberOnly(boolean memberOnly) {
		this.memberOnly = memberOnly;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getAmphurCode() {
		return amphurCode;
	}
	public void setAmphurCode(String amphurCode) {
		this.amphurCode = amphurCode;
	}
	public String getTumbolCode() {
		return tumbolCode;
	}
	public void setTumbolCode(String tumbolCode) {
		this.tumbolCode = tumbolCode;
	}
	
	
}