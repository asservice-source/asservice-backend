package com.fwg.asservice.dao;

import java.util.List;

import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.PatientDetailInfo;

public interface SurveyPatientDao {
	public PatientDetailInfo patientDetailInfoInsertOrUpdate(PatientDetailInfo patientDetailInfo) throws Exception;
	public List<PatientDetailInfo> patientDetailInfoList(Filter filter) throws Exception;
	public PatientDetailInfo patientDetailInfoByRowGUID(String rowGUID) throws Exception;
}