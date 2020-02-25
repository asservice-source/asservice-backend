package com.fwg.asservice.dao;

import java.util.List;

import com.fwg.asservice.model.BornLocation;
import com.fwg.asservice.model.survey.Child;
import com.fwg.asservice.model.survey.DeathPlace;
import com.fwg.asservice.model.survey.SurveyHeader;
import com.fwg.asservice.sql.CallableStatementUtil;

import net.minidev.json.JSONObject;

public interface SurveyDao {
	/*
	 * CREATE and UPDATE 
	 */
	//public void saveProvince(Province province) throws Exception;

	/*
	 * READ
	 */
	public List<SurveyHeader> listSurveyHeaders(String headerTypeCode, String hospitalCode) throws Exception;
	public List<DeathPlace> listDeathPlace() throws Exception;
	public JSONObject surveyIsDuplucate(String headerTypeCode, String documentId, String personId) throws Exception;
	public int checkCitizenIdIsSurvey(String citizenId) throws Exception;
	public List<Child> listChildByReferenceId(String referenceId) throws Exception;
	public Child insertChildAndPersonAndHomeMembersAndAddress(Child child, CallableStatementUtil callableStatementUtil) throws Exception;
	public List<com.fwg.asservice.model.survey.HomeMember> listHomeMemberNotSurvey(Integer homeId, String headerTypeCode, String documentId, boolean isAll) throws Exception;
	public Integer autoGenSurveyHearderOfYear(String code5)  throws Exception;
	public BornLocation insertBornLocation(BornLocation bornLocation) throws Exception;
	/*
	 * DELETE
	 */
	//public void deleteProvince(int id)  throws Exception;
}
