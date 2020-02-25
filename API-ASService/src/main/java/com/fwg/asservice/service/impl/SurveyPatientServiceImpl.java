package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwg.asservice.dao.SurveyPatientDao;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.PatientDetailInfo;
import com.fwg.asservice.service.SurveyPatientService;

@Service
public class SurveyPatientServiceImpl implements SurveyPatientService {

	@Autowired
	private SurveyPatientDao surveyPatientDao;

	@Override
	public PatientDetailInfo patientDetailInfoInsertOrUpdate(PatientDetailInfo patientDetailInfo) throws Exception {
		try {
			return surveyPatientDao.patientDetailInfoInsertOrUpdate(patientDetailInfo);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<PatientDetailInfo> patientDetailInfoList(Filter filter)
			throws Exception {
		try {
			return surveyPatientDao.patientDetailInfoList(filter);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public PatientDetailInfo patientDetailInfoByRowGUID(String rowGUID) throws Exception {
		try {
			return surveyPatientDao.patientDetailInfoByRowGUID(rowGUID);
		} catch (Exception e) {
			throw e;
		}
	}
}