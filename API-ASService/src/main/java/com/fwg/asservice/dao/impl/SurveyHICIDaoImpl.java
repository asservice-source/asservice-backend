package com.fwg.asservice.dao.impl;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fwg.asservice.dao.SurveyHICIDao;
import com.fwg.asservice.model.survey.ContainerLocateType;
import com.fwg.asservice.model.survey.ContainerType;
import com.fwg.asservice.model.survey.MonitorHICIDetailInfo;
import com.fwg.asservice.model.survey.PopulationDetailInfo;
import com.fwg.asservice.sql.CallableStatementUtil;
import com.fwg.asservice.utility.GlobalFunction;

@Repository
public class SurveyHICIDaoImpl implements SurveyHICIDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<MonitorHICIDetailInfo> listMonitorHICIDetailInfo(String documentId, Integer villageId, String homeTypeCode, String osmId, Integer homeId) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_HICI_MonitorHICIDetailInfoList`(:documentId, :villageId, :homeTypeCode, :osmId, :homeId)}");
			
			query.addEntity(MonitorHICIDetailInfo.class);
			query.setParameter("documentId", GlobalFunction.isEmpty(documentId) ? null : documentId);
			query.setParameter("villageId", villageId);
			query.setParameter("homeTypeCode", homeTypeCode);
			query.setParameter("osmId", GlobalFunction.isEmpty(osmId) ? null : osmId);
			query.setParameter("homeId", homeId);
			
			List<MonitorHICIDetailInfo> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<ContainerType> listContainerTypeByHomeID(String documentId, Integer homeId) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_HICI_MonitorHICIDetailInfo_ByHomeID`(:documentId, :homeId)}");
			
			query.addEntity(ContainerType.class);
			query.setParameter("documentId", GlobalFunction.isEmpty(documentId) ? null : documentId);
			query.setParameter("homeId", homeId);
			
			List<ContainerType> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<ContainerType> listContainerTypeAll() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_ContainerType_GetContainerTypeList`()}");
			
			query.addEntity(ContainerType.class);
			
			List<ContainerType> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public MonitorHICIDetailInfo monitorHICIInsOrUpd(MonitorHICIDetailInfo monitorHICIDetailInfo) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		MonitorHICIDetailInfo insUpdInfo = new MonitorHICIDetailInfo();
		try {

			String query = "call `Survey.sp_HICI_MonitorHICIDetailInfoInsOrUpd`("
					+ ":RowGUID"
					+ ",:DocumentID"
					+ ",:OSMID"
					+ ",:HomeID"
					+ ",:ContainerID"
					+ ",:LocateTypeID"
					+ ",:TotalSurvey"
					+ ",:TotalDetect"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session, query);
			
			/*register the OUT parameter before calling the stored procedure*/
			callableStatementUtil.registerOutParameter("RowGUID", Types.VARCHAR);
			
			callableStatementUtil.setString("RowGUID", null);
			callableStatementUtil.setString("DocumentID", monitorHICIDetailInfo.getDocumentId());
			callableStatementUtil.setString("OSMID", monitorHICIDetailInfo.getOsmId());
			callableStatementUtil.setInt("HomeID", monitorHICIDetailInfo.getHomeId());
			callableStatementUtil.setInt("ContainerID", monitorHICIDetailInfo.getContainerId());
			callableStatementUtil.setInt("LocateTypeID", monitorHICIDetailInfo.getLocateTypeId());
			callableStatementUtil.setInt("TotalSurvey", monitorHICIDetailInfo.getTotalSurvey());
			callableStatementUtil.setInt("TotalDetect", monitorHICIDetailInfo.getTotalDetect());

			callableStatementUtil.execute();
			
			/*read the OUT parameter now*/ 
			insUpdInfo.setRowGUID(callableStatementUtil.getString("RowGUID"));
			
			return insUpdInfo;

		} catch (Exception exception) {
			throw exception;
		} finally {
			try {
				if(callableStatementUtil != null){
					callableStatementUtil.close();
				}
				if (session != null) {
					session.close();
				}
			} catch (SQLException sqlException) {
				throw sqlException;
			}
		}
	}

	@Override
	public List<ContainerLocateType> listContainerLocateTypeAll() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_ContainerLocateType_GetContainerLocateTypeList`()}");
			
			query.addEntity(ContainerLocateType.class);
			
			List<ContainerLocateType> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public MonitorHICIDetailInfo monitorHICIDelete(MonitorHICIDetailInfo monitorHICIDetailInfo) throws Exception {
		
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		MonitorHICIDetailInfo delInfo = new MonitorHICIDetailInfo();
		try {

			String query = "call `Survey.sp_HICI_MonitorHICIDetailInfoDel`("
					+ ":DocumentID"
					+ ",:HomeID"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session, query);
			
			callableStatementUtil.setString("DocumentID", monitorHICIDetailInfo.getDocumentId());
			callableStatementUtil.setInt("HomeID", monitorHICIDetailInfo.getHomeId());

			callableStatementUtil.execute();
			
			return delInfo;

		} catch (Exception exception) {
			throw exception;
		} finally {
			try {
				if(callableStatementUtil != null){
					callableStatementUtil.close();
				}
				if (session != null) {
					session.close();
				}
			} catch (SQLException sqlException) {
				throw sqlException;
			}
		}
	}

	@Override
	public List<MonitorHICIDetailInfo> listMonitorHICIDetailInfoNotSurvey(String documentId, String osmId) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_HICI_MonitorHICIDetailInfoList_NotSurvey`(:documentId, :osmId)}");
			
			query.addEntity(MonitorHICIDetailInfo.class);
			query.setParameter("documentId", GlobalFunction.isEmpty(documentId) ? null : documentId);
			query.setParameter("osmId", GlobalFunction.isEmpty(osmId) ? null : osmId);
			
			List<MonitorHICIDetailInfo> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public MonitorHICIDetailInfo monitorHICIDetailInfoByHomeID(String documentId, Integer homeId) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_HICI_MonitorHICIDetailInfoBy_HomeID`(:documentId, :homeId)}");
			
			query.addEntity(MonitorHICIDetailInfo.class);
			query.setParameter("documentId", GlobalFunction.isEmpty(documentId) ? null : documentId);
			query.setParameter("homeId", homeId);
			
			MonitorHICIDetailInfo result = (MonitorHICIDetailInfo) query.list().get(0);
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
}
