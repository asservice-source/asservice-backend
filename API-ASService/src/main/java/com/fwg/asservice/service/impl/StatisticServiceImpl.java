package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwg.asservice.dao.StatisticDao;
import com.fwg.asservice.model.FamilySummary;
import com.fwg.asservice.model.SurveySummary;
import com.fwg.asservice.service.StatisticService;

@Service
public class StatisticServiceImpl implements StatisticService {
	
	@Autowired
	private StatisticDao statisticDao;

	@Override
	public FamilySummary familySummaryOfOSM(String osmId) throws Exception {
		try{
			return statisticDao.familySummaryOfOSM(osmId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<SurveySummary> surveySummary(String osmId) throws Exception {
		try{
			return statisticDao.surveySummary(osmId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public FamilySummary familySummaryOfHospital(String hospitalCode) throws Exception {
		try{
			return statisticDao.familySummaryOfHospital(hospitalCode);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<SurveySummary> surveySummaryOfHospital(String hospitalCode) throws Exception {
		try{
			return statisticDao.surveySummaryOfHospital(hospitalCode);
		}catch(Exception e){
			throw e;
		}
	}

}
