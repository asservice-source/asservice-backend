package com.fwg.asservice.service;

import java.util.List;

import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.PregnantDetailInfo;

public interface SurveyPregnantService {

	public List<PregnantDetailInfo> listPregnantDetailInfo(Filter filterParams) throws Exception;
	public List<PregnantDetailInfo> listPregnantDetailInfoNotExistsSurveyBorn(PregnantDetailInfo pregnantDetailInfo) throws Exception;
	public List<PregnantDetailInfo> listPregnantDetailInfoForAddBorn(PregnantDetailInfo pregnantDetailInfo) throws Exception;
	public PregnantDetailInfo getByRowGUID(String rowGUID) throws Exception;
	public PregnantDetailInfo insertOrUpdatePregnantDetailInfo(PregnantDetailInfo pregnantDetailInfo) throws Exception;
	
}
