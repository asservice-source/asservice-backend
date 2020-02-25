package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwg.asservice.dao.SurveyMetabolicDao;
import com.fwg.asservice.model.survey.MetabolicDetailInfo;
import com.fwg.asservice.service.SurveyMetabolicService;

@Service
public class SurveyMetabolicServiceImpl implements SurveyMetabolicService {

	@Autowired
	private SurveyMetabolicDao surveyListDao;

	@Override
	public List<MetabolicDetailInfo> listMetabolic(String documentId, Integer villageId, String osmId, String name)
			throws Exception {
		try{
			return surveyListDao.listMetabolic(documentId, villageId, osmId, name);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public MetabolicDetailInfo metabolicInsOrUpd(MetabolicDetailInfo metabolicDetailInfo) throws Exception {
		try{
			return surveyListDao.metabolicInsOrUpd(metabolicDetailInfo);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public MetabolicDetailInfo metabolicDataByRowGUID(String rowGUID) throws Exception {
		try{
			return surveyListDao.metabolicDataByRowGUID(rowGUID);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<MetabolicDetailInfo> listMetabolicNotSurvey(String documentId, String osmId) throws Exception {
		try{
			return surveyListDao.listMetabolicNotSurvey(documentId, osmId);
		}catch(Exception e){
			throw e;
		}
	}
}
