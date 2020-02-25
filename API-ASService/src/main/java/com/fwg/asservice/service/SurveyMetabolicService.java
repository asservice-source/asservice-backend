package com.fwg.asservice.service;

import java.util.List;

import com.fwg.asservice.model.survey.MetabolicDetailInfo;

public interface SurveyMetabolicService {

	/*
	 * CREATE and UPDATE 
	 */
	public MetabolicDetailInfo metabolicInsOrUpd(MetabolicDetailInfo metabolicDetailInfo) throws Exception;

	/*
	 * READ
	 */
	public List<MetabolicDetailInfo> listMetabolic(String documentId, Integer villageId, String osmId, String name) throws Exception;
	public List<MetabolicDetailInfo> listMetabolicNotSurvey(String documentId, String osmId) throws Exception;
	public MetabolicDetailInfo metabolicDataByRowGUID(String rowGUID) throws Exception;
	/*
	 * DELETE
	 */
	//public void deleteProvince(int id)  throws Exception;

}
