package com.fwg.asservice.service;

import java.util.List;

import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.PatientDetailInfo;

public interface SurveyPatientService {
	public PatientDetailInfo patientDetailInfoInsertOrUpdate(PatientDetailInfo patientDetailInfo) throws Exception;
	public List<PatientDetailInfo> patientDetailInfoList(Filter filter) throws Exception;
	public PatientDetailInfo patientDetailInfoByRowGUID(String rowGUID) throws Exception;
}