package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwg.asservice.dao.SurveyPregnantDao;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.PregnantDetailInfo;
import com.fwg.asservice.service.SurveyPregnantService;

@Service
public class SurveyPregnantServiceImpl implements SurveyPregnantService {

	@Autowired
	private SurveyPregnantDao surveyPregnantDao;
	
	public List<PregnantDetailInfo> listPregnantDetailInfo(Filter filterParams) throws Exception {
		try{
			return surveyPregnantDao.listPregnantDetailInfo(filterParams);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<PregnantDetailInfo> listPregnantDetailInfoNotExistsSurveyBorn(PregnantDetailInfo pregnantDetailInfo) throws Exception {
		try{
			return surveyPregnantDao.listPregnantDetailInfoNotExistsSurveyBorn(pregnantDetailInfo);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<PregnantDetailInfo> listPregnantDetailInfoForAddBorn(PregnantDetailInfo pregnantDetailInfo) throws Exception {
		try{
			return surveyPregnantDao.listPregnantDetailInfoForAddBorn(pregnantDetailInfo);
		}catch(Exception e){
			throw e;
		}
	}
	
	public PregnantDetailInfo getByRowGUID(String rowGUID) throws Exception {
		try{
			return surveyPregnantDao.getByRowGUID(rowGUID);
		}catch(Exception e){
			throw e;
		}
	}
	
	public PregnantDetailInfo insertOrUpdatePregnantDetailInfo(PregnantDetailInfo pregnantDetailInfo) throws Exception {
		try{
			return surveyPregnantDao.insertOrUpdatePregnantDetailInfo(pregnantDetailInfo);
		}catch(Exception e){
			throw e;
		}
	}

	
}
