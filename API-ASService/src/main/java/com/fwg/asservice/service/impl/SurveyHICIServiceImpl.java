package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwg.asservice.dao.SurveyHICIDao;
import com.fwg.asservice.model.survey.ContainerLocateType;
import com.fwg.asservice.model.survey.ContainerType;
import com.fwg.asservice.model.survey.MonitorHICIDetailInfo;
import com.fwg.asservice.service.SurveyHICIService;

@Service
public class SurveyHICIServiceImpl implements SurveyHICIService {

	@Autowired
	private SurveyHICIDao surveyHICIDao;

	@Override
	public List<MonitorHICIDetailInfo> listMonitorHICIDetailInfo(String documentId, Integer villageId, String homeTypeCode,
			String osmId, Integer homeId) throws Exception {
		try{
			return surveyHICIDao.listMonitorHICIDetailInfo(documentId, villageId, homeTypeCode, osmId, homeId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<ContainerType> listContainerTypeByHomeID(String documentId, Integer homeId) throws Exception {
		try{
			return surveyHICIDao.listContainerTypeByHomeID(documentId, homeId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<ContainerType> listContainerTypeAll() throws Exception {
		try{
			return surveyHICIDao.listContainerTypeAll();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public MonitorHICIDetailInfo monitorHICIInsOrUpd(MonitorHICIDetailInfo monitorHICIDetailInfo) throws Exception {
		try{
			return surveyHICIDao.monitorHICIInsOrUpd(monitorHICIDetailInfo);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<ContainerLocateType> listContainerLocatTypeAll() throws Exception {
		try{
			return surveyHICIDao.listContainerLocateTypeAll();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public MonitorHICIDetailInfo monitorHICIDelete(MonitorHICIDetailInfo monitorHICIDetailInfo) throws Exception {
		try{
			return surveyHICIDao.monitorHICIDelete(monitorHICIDetailInfo);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<MonitorHICIDetailInfo> listMonitorHICIDetailInfoNotSurvey(String documentId, String osmId) throws Exception {
		try{
			return surveyHICIDao.listMonitorHICIDetailInfoNotSurvey(documentId, osmId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public MonitorHICIDetailInfo monitorHICIDetailInfoByHomeID(String documentId, Integer homeId) throws Exception {
		try{
			return surveyHICIDao.monitorHICIDetailInfoByHomeID(documentId, homeId);
		}catch(Exception e){
			throw e;
		}
	}
}
