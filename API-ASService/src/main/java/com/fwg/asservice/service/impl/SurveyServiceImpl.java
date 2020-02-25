package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwg.asservice.dao.SurveyDao;
import com.fwg.asservice.model.BornLocation;
import com.fwg.asservice.model.survey.Child;
import com.fwg.asservice.model.survey.DeathPlace;
import com.fwg.asservice.model.survey.SurveyHeader;
import com.fwg.asservice.service.SurveyService;
import com.fwg.asservice.sql.CallableStatementUtil;

import net.minidev.json.JSONObject;

@Service
public class SurveyServiceImpl implements SurveyService { 
	
	@Autowired
	private SurveyDao surveyDao;

	@Override
	public List<SurveyHeader> listSurveyHeaders(String headerTypeCode, String hospitalCode) throws Exception {
		try{
			return surveyDao.listSurveyHeaders(headerTypeCode, hospitalCode);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<DeathPlace> listDeathPlace() throws Exception {
		try{
			return surveyDao.listDeathPlace();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public JSONObject surveyIsDuplucate(String headerTypeCode, String documentId, String personId) throws Exception {
		try{
			return surveyDao.surveyIsDuplucate(headerTypeCode, documentId, personId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public int checkCitizenIdIsSurvey(String citizenId) throws Exception {
		try{
			return surveyDao.checkCitizenIdIsSurvey(citizenId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<Child> listChildByReferenceId(String referenceId) throws Exception {
		try{
			return surveyDao.listChildByReferenceId(referenceId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public Child insertChildAndPersonAndHomeMembersAndAddress(Child child, CallableStatementUtil callableStatementUtil) throws Exception {
		try{
			return surveyDao.insertChildAndPersonAndHomeMembersAndAddress(child, callableStatementUtil);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<com.fwg.asservice.model.survey.HomeMember> listHomeMemberNotSurvey(Integer homeId, String headerTypeCode, String documentId, boolean isAll)
			throws Exception {
		try{
			return surveyDao.listHomeMemberNotSurvey(homeId, headerTypeCode, documentId, isAll);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public Integer autoGenSurveyHearderOfYear(String code5) throws Exception {
		try{
			return surveyDao.autoGenSurveyHearderOfYear(code5);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public BornLocation insertBornLocation(BornLocation bornLocation) throws Exception {
		try{
			return surveyDao.insertBornLocation(bornLocation);
		}catch(Exception e){
			throw e;
		}
	}
}
