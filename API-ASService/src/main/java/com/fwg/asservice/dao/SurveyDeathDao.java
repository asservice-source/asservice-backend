package com.fwg.asservice.dao;

import java.util.List;

import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.DeathDetailInfo;

public interface SurveyDeathDao {
	
	public List<DeathDetailInfo> listDeathDetailInfo(Filter filterParams) throws Exception;
	public DeathDetailInfo getDeathDetailInfo(Filter filterParams) throws Exception;
	public DeathDetailInfo insertOrUpdateDeathDetailInfo(DeathDetailInfo deathDetailInfo) throws Exception;
	
}
