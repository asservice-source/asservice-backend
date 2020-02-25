package com.fwg.asservice.dao;

import java.util.List;

import com.fwg.asservice.model.FamilySummary;
import com.fwg.asservice.model.SurveySummary;

public interface StatisticDao {
	/*
	 * CREATE and UPDATE
	 */

	/*
	 * READ
	 */
	
	public FamilySummary familySummaryOfOSM(String osmId) throws Exception;
	public List<SurveySummary> surveySummary(String osmId) throws Exception;
	
	public FamilySummary familySummaryOfHospital(String hospitalCode) throws Exception;
	public List<SurveySummary> surveySummaryOfHospital(String hospitalCode) throws Exception;
	

	/*
	 * DELETE
	 */
	//public void deleteProvince(int id) throws Exception;
}
