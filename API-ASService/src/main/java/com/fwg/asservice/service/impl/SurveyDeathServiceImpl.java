package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwg.asservice.dao.SurveyDeathDao;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.DeathDetailInfo;
import com.fwg.asservice.service.SurveyDeathService;

@Service
public class SurveyDeathServiceImpl implements SurveyDeathService {

	@Autowired
	private SurveyDeathDao surveyDeathDao;
	
	public List<DeathDetailInfo> listDeathDetailInfo(Filter filterParams) throws Exception {
		try{
			return surveyDeathDao.listDeathDetailInfo(filterParams);
		}catch(Exception e){
			throw e;
		}
	}
	
	public DeathDetailInfo getDeathDetailInfo(Filter filterParams) throws Exception {
		try{
			return surveyDeathDao.getDeathDetailInfo(filterParams);
		}catch(Exception e){
			throw e;
		}
	}
	
	public DeathDetailInfo insertOrUpdateDeathDetailInfo(DeathDetailInfo deathDetailInfo) throws Exception {
		try{
			return surveyDeathDao.insertOrUpdateDeathDetailInfo(deathDetailInfo);
		}catch(Exception e){
			throw e;
		}
	}

	
}
