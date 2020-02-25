package com.fwg.asservice.dao.impl;

import java.sql.Types;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fwg.asservice.dao.SurveyDeathDao;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.DeathDetailInfo;
import com.fwg.asservice.sql.CallableStatementUtil;
import com.fwg.asservice.utility.GlobalFunction;

@Repository
public class SurveyDeathDaoImpl implements SurveyDeathDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<DeathDetailInfo> listDeathDetailInfo(Filter filterParams) throws Exception {
		Session session = sessionFactory.openSession();
		try{
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_Death_DeathDetailInfoList`(:documentId, :villageId, :osmId, :name)}");
			query.addEntity(DeathDetailInfo.class);
			query.setParameter("documentId", GlobalFunction.isEmpty(filterParams.getDocumentId())?null:filterParams.getDocumentId());
			query.setParameter("villageId", GlobalFunction.isEmpty(filterParams.getVillageId())?null:filterParams.getVillageId());
			query.setParameter("osmId", GlobalFunction.isEmpty(filterParams.getOsmId())?null:filterParams.getOsmId());
			query.setParameter("name", GlobalFunction.isEmpty(filterParams.getName())?null:filterParams.getName());
			
			List<DeathDetailInfo> result = query.list();
			
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
	public DeathDetailInfo getDeathDetailInfo(Filter filterParams) throws Exception {
		Session session = sessionFactory.openSession();
		DeathDetailInfo deathDetailInfo = null;
		try {
			Criteria cr = session.createCriteria(DeathDetailInfo.class);
			cr.add(Restrictions.eq("rowGUID", filterParams.getRowGUID()));
			if (cr.list()!=null && cr.list().size()>0) {
				deathDetailInfo = (DeathDetailInfo)cr.list().get(0);
			}
			return deathDetailInfo;
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public DeathDetailInfo insertOrUpdateDeathDetailInfo(DeathDetailInfo deathDetailInfo) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		try{
			
			String query = " CALL `Survey.sp_Death_DeathDetailInfoInsOrUpd`(" + 
				":ErrorMsg" +  
				",:RowGUID" +  
				",:DocumentID" +  
				",:OSMID" + 
				",:PersonID" + 
				",:DeathDate" + 
				",:HospDeath" + 
				",:Age" + 
				",:IsDiabetes" + 
				",:IsHypertension" + 
				",:IsAccident" + 
				",:IsCancer" + 
				",:CancerTypeID" + 
				",:CauseOther" + 
				",:IsNoDisease" + 
				",:DeathPlace" + 
				",:PlaceOther" + 
				",:IsDeleted" + 
			")";
			
			callableStatementUtil = new CallableStatementUtil(session, query);
			
			// Output Parameter
			callableStatementUtil.registerOutParameter("ErrorMsg", Types.NVARCHAR);
			callableStatementUtil.registerOutParameter("RowGUID", Types.NVARCHAR);
			
			// Input Parameter
			callableStatementUtil.setString("RowGUID", GlobalFunction.isEmpty(deathDetailInfo.getRowGUID())?null:deathDetailInfo.getRowGUID());
			callableStatementUtil.setString("DocumentID", deathDetailInfo.getDocumentId());
			callableStatementUtil.setString("OSMID", deathDetailInfo.getOsmId());
			callableStatementUtil.setString("PersonID", deathDetailInfo.getPersonId());
			callableStatementUtil.setTimestamp("DeathDate", GlobalFunction.convertStringToTimestamp(deathDetailInfo.getDeathDate()));
			callableStatementUtil.setString("HospDeath", deathDetailInfo.getHospDeath());
			callableStatementUtil.setInt("Age", deathDetailInfo.getAge());
			callableStatementUtil.setBoolean("IsDiabetes", deathDetailInfo.isDiabetes());
			callableStatementUtil.setBoolean("IsHypertension", deathDetailInfo.isHypertension());
			callableStatementUtil.setBoolean("IsAccident", deathDetailInfo.isAccident());
			callableStatementUtil.setBoolean("IsCancer", deathDetailInfo.isCancer());
			callableStatementUtil.setInt("CancerTypeID", deathDetailInfo.getCancerTypeId());
			callableStatementUtil.setString("CauseOther", deathDetailInfo.getCauseOther());
			callableStatementUtil.setBoolean("IsNoDisease", deathDetailInfo.isNoDisease());
			callableStatementUtil.setString("DeathPlace", deathDetailInfo.getDeathPlaceCode());
			callableStatementUtil.setString("PlaceOther", deathDetailInfo.getPlaceOther());
			callableStatementUtil.setBoolean("IsDeleted", deathDetailInfo.isDeleted());
			
			Log.error(callableStatementUtil);
			
			callableStatementUtil.execute();
			
			String errorMsg = callableStatementUtil.getString("ErrorMsg");
			if (GlobalFunction.isEmpty(errorMsg)) {
				deathDetailInfo.setRowGUID(callableStatementUtil.getString("RowGUID"));
			} else {
				throw new Exception(errorMsg);
			}
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (callableStatementUtil != null) {
				callableStatementUtil.close();
			}
			if (session != null) {
				session.close();
			}
		}
		return deathDetailInfo;
	}

	
}
