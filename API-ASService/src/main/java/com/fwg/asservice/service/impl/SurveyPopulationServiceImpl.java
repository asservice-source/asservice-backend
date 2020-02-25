package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwg.asservice.dao.SurveyPopulationDao;
import com.fwg.asservice.model.HomeMembers;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.PopulationDetail;
import com.fwg.asservice.model.survey.PopulationDetailInfo;
import com.fwg.asservice.service.SurveyPopulationService;

@Service
public class SurveyPopulationServiceImpl implements SurveyPopulationService {

	@Autowired
	private SurveyPopulationDao surveyPersonalDao;

	@Override
	public List<PopulationDetail> listPopulation(Filter filterParams) throws Exception {
		try{
			return surveyPersonalDao.listPopulation(filterParams);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public Person addHomeMember(HomeMembers homeMembers) throws Exception {
		try{
			return surveyPersonalDao.addHomeMember(homeMembers);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public PopulationDetailInfo insertOrUpdatePopulationDetail(PopulationDetail populationDetail, PopulationDetailInfo populationDetailInfo)
			throws Exception {
		try{
			return surveyPersonalDao.insertOrUpdatePopulationDetail(populationDetail, populationDetailInfo);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public void deletePopulation(PopulationDetail populationDetail)
			throws Exception {
		try{
			surveyPersonalDao.deletePopulation(populationDetail);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<PopulationDetailInfo> listPopulationDetailInfo(String documentId, Integer homeId) throws Exception {
		try{
			return surveyPersonalDao.listPopulationDetailInfo(documentId, homeId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<PopulationDetail> listPopulationDetailNotSurvey(Filter filterParams)
			throws Exception {
		try{
			return surveyPersonalDao.listPopulationDetailNotSurvey(filterParams);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<HomeMembers> listHomeMemberByDocumentID(String documentId, Integer homeId) throws Exception {
		try{
			return surveyPersonalDao.listHomeMemberByDocumentID(documentId, homeId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public PopulationDetail populationDetailInsOrUpd(PopulationDetail populationDetail) throws Exception {
		try{
			return surveyPersonalDao.populationDetailInsOrUpd(populationDetail);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public PopulationDetailInfo populationDetailInfoInsOrUpd(PopulationDetailInfo populationDetailInfo)
			throws Exception {
		try{
			return surveyPersonalDao.populationDetailInfoInsOrUpd(populationDetailInfo);
		}catch(Exception e){
			throw e;
		}
	}
}
