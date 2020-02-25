package com.fwg.asservice.dao;

import java.util.List;

import com.fwg.asservice.model.HomeMembers;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.PopulationDetail;
import com.fwg.asservice.model.survey.PopulationDetailInfo;

public interface SurveyPopulationDao {

	/*
	 * CREATE and UPDATE
	 */
	public Person addHomeMember(HomeMembers homeMembers) throws Exception;
	public PopulationDetailInfo insertOrUpdatePopulationDetail(PopulationDetail populationDetail, PopulationDetailInfo populationDetailInfo) throws Exception;
	public PopulationDetail populationDetailInsOrUpd(PopulationDetail populationDetail) throws Exception;
	public PopulationDetailInfo populationDetailInfoInsOrUpd(PopulationDetailInfo populationDetailInfo) throws Exception;
	public void deletePopulation(PopulationDetail populationDetail) throws Exception;

	/*
	 * READ
	 */
	
	public List<PopulationDetail> listPopulation(Filter filterParams) throws Exception;
	public List<PopulationDetailInfo> listPopulationDetailInfo(String documentId, Integer homeId) throws Exception;
	public List<PopulationDetail> listPopulationDetailNotSurvey(Filter filterParams) throws Exception;
	public List<HomeMembers> listHomeMemberByDocumentID(String documentId, Integer homeId) throws Exception;
	
	/*
	 * DELETE
	 */
	//public void deleteProvince(int id) throws Exception;
}
