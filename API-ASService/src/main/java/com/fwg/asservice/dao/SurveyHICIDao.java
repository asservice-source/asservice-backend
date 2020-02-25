package com.fwg.asservice.dao;

import java.util.List;

import com.fwg.asservice.model.survey.ContainerLocateType;
import com.fwg.asservice.model.survey.ContainerType;
import com.fwg.asservice.model.survey.MetabolicDetailInfo;
import com.fwg.asservice.model.survey.MonitorHICIDetailInfo;

public interface SurveyHICIDao {

	/*
	 * CREATE and UPDATE
	 */
	//public MetabolicDetailInfo metabolicInsOrUpd(MetabolicDetailInfo metabolicDetailInfo) throws Exception; // create and update
	public MonitorHICIDetailInfo monitorHICIInsOrUpd(MonitorHICIDetailInfo monitorHICIDetailInfo) throws Exception;

	/*
	 * READ
	 */
	public List<ContainerType> listContainerTypeByHomeID(String documentId, Integer homeId) throws Exception;
	public List<ContainerType> listContainerTypeAll() throws Exception;
	public List<ContainerLocateType> listContainerLocateTypeAll() throws Exception;
	public List<MonitorHICIDetailInfo> listMonitorHICIDetailInfo(String documentId, Integer villageId, String homeTypeCode, String osmId, Integer homeId) throws Exception;
	public List<MonitorHICIDetailInfo> listMonitorHICIDetailInfoNotSurvey(String documentId, String osmId) throws Exception;
	public MonitorHICIDetailInfo monitorHICIDetailInfoByHomeID(String documentId, Integer homeId) throws Exception;
	/*
	 * DELETE
	 */
	public MonitorHICIDetailInfo monitorHICIDelete(MonitorHICIDetailInfo monitorHICIDetailInfo) throws Exception;
}
