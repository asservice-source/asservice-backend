package com.fwg.asservice.dao.impl;

import java.sql.Types;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fwg.asservice.dao.SurveyPregnantDao;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.PregnantDetailInfo;
import com.fwg.asservice.sql.CallableStatementUtil;
import com.fwg.asservice.utility.GlobalFunction;

@Repository
public class SurveyPregnantDaoImpl implements SurveyPregnantDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<PregnantDetailInfo> listPregnantDetailInfo(Filter filterParams) throws Exception {
		Session session = sessionFactory.openSession();
		try {
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_Pregnant_PregnantDetailInfoList`(:documentId, :villageId, :osmId, :name)}");
			query.addEntity(PregnantDetailInfo.class);
			query.setParameter("documentId", GlobalFunction.isEmpty(filterParams.getDocumentId())?null:filterParams.getDocumentId());
			query.setParameter("villageId", GlobalFunction.isEmpty(filterParams.getVillageId())?null:filterParams.getVillageId());
			query.setParameter("osmId", GlobalFunction.isEmpty(filterParams.getOsmId())?null:filterParams.getOsmId());
			query.setParameter("name", GlobalFunction.isEmpty(filterParams.getName())?null:filterParams.getName());
			
			List<PregnantDetailInfo> result = query.list();
			
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
	public List<PregnantDetailInfo> listPregnantDetailInfoNotExistsSurveyBorn(PregnantDetailInfo pregnantDetailInfo) throws Exception {
		Session session = sessionFactory.openSession();
		try {
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_Pregnant_PregnantDetailInfoList_NotExistsSurveyBorn`(:osmId)}");
			query.addEntity(PregnantDetailInfo.class);
			query.setParameter("osmId", pregnantDetailInfo.getOsmId());
			
			List<PregnantDetailInfo> result = query.list();
			
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
	public List<PregnantDetailInfo> listPregnantDetailInfoForAddBorn(PregnantDetailInfo pregnantDetailInfo) throws Exception {
		Session session = sessionFactory.openSession();
		try {
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_Pregnant_PregnantDetailInfoList_ForAddBorn`(:personId)}");
			query.addEntity(PregnantDetailInfo.class);
			query.setParameter("personId", pregnantDetailInfo.getPersonId());
			
			List<PregnantDetailInfo> result = query.list();
			
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
	public PregnantDetailInfo getByRowGUID(String rowGUID) throws Exception {
		Session session = sessionFactory.openSession();
		PregnantDetailInfo pregnantDetailInfo = null;
		try {
			pregnantDetailInfo = (PregnantDetailInfo)session.get(PregnantDetailInfo.class, rowGUID);
			return pregnantDetailInfo;
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public PregnantDetailInfo insertOrUpdatePregnantDetailInfo(PregnantDetailInfo pregnantDetailInfo) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		try {
			
			String query = " CALL `Survey.sp_Pregnant_PregnantDetailInfoInsOrUpd`(" + 
				":RowGUID" +  
				",:DocumentID" +  
				",:OSMID" + 
				",:HomeID" + 
				",:MasterGUID" + 
				",:PersonID" + 
				",:WombNo" + 
				",:BornDueDate" + 
				",:PSurveyTypeCode" + 
				",:IsDeleted" + 
			")";
			
			callableStatementUtil = new CallableStatementUtil(session, query, false);
			
			// Output Parameter
			callableStatementUtil.registerOutParameter("RowGUID", Types.NVARCHAR);
			
			// Input Parameter
			callableStatementUtil.setString("RowGUID", GlobalFunction.isEmpty(pregnantDetailInfo.getRowGUID())?null:pregnantDetailInfo.getRowGUID());
			callableStatementUtil.setString("DocumentID", pregnantDetailInfo.getDocumentId());
			callableStatementUtil.setString("OSMID", pregnantDetailInfo.getOsmId());
			callableStatementUtil.setInt("HomeID", pregnantDetailInfo.getHomeId());
			callableStatementUtil.setString("MasterGUID", pregnantDetailInfo.getMasterGUID());
			callableStatementUtil.setString("PersonID", pregnantDetailInfo.getPersonId());
			callableStatementUtil.setInt("WombNo", pregnantDetailInfo.getWombNo());
			callableStatementUtil.setDate("BornDueDate", GlobalFunction.convertStringToDate(pregnantDetailInfo.getBornDueDate()));
			callableStatementUtil.setString("PSurveyTypeCode", pregnantDetailInfo.getpSurveyTypeCode());
			callableStatementUtil.setBoolean("IsDeleted", pregnantDetailInfo.isDeleted());
			
			Log.error(callableStatementUtil);
			
			callableStatementUtil.execute();

			pregnantDetailInfo.setRowGUID(callableStatementUtil.getString("RowGUID"));
			pregnantDetailInfo.setCallableStatementUtil(callableStatementUtil);
		
		} catch (Exception exception) {
			throw exception;
		}
		return pregnantDetailInfo;
	}

	
}
