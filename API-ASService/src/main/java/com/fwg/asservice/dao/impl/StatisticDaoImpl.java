package com.fwg.asservice.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fwg.asservice.dao.OSMDao;
import com.fwg.asservice.dao.StatisticDao;
import com.fwg.asservice.model.FamilySummary;
import com.fwg.asservice.model.Hospital;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.SurveySummary;
import com.fwg.asservice.model.UserMapping;
import com.fwg.asservice.sql.CallableStatementUtil;
import com.fwg.asservice.utility.GlobalFunction;

@Repository
public class StatisticDaoImpl implements StatisticDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public FamilySummary familySummaryOfOSM(String osmId) throws Exception {
		
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		ResultSet rs = null;
		FamilySummary familySummary = new FamilySummary();
		try{
		
			String query = "CALL sp_Statistic_FamilySummary"
					+ "("
					+ ":osmId"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session,query);
			
			callableStatementUtil.setString("osmId",GlobalFunction.isEmpty(osmId) ? null : osmId);
			
			Log.error(callableStatementUtil);
			
			rs = callableStatementUtil.executeQuery();
			if(rs.next()) {
				familySummary.setFamily(rs.getInt("Family"));
				familySummary.setMale(rs.getInt("Male"));
				familySummary.setFemale(rs.getInt("Female"));
			}
			return familySummary;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
			if(rs != null){
				rs.close();
			}
			if(callableStatementUtil != null){
				callableStatementUtil.close();
			}
		}
	}

	@Override
	public List<SurveySummary> surveySummary(String osmId) throws Exception {
		
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		ResultSet rs = null;
		List<SurveySummary> surveySummary = new ArrayList<>();
		try{
		
			String query = "CALL sp_Statistic_SurveySummary"
					+ "("
					+ ":osmId"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session,query);
			
			callableStatementUtil.setString("osmId",GlobalFunction.isEmpty(osmId) ? null : osmId);
			
			Log.error(callableStatementUtil);
			
			rs = callableStatementUtil.executeQuery();
			while(rs.next()) {
				SurveySummary summary = new SurveySummary();
				summary.setTotal(rs.getInt("Total"));
				summary.setSurvey(rs.getInt("Survey"));
				summary.setHeaderTypeCode(rs.getString("HeaderTypeCode"));
				surveySummary.add(summary);
			}
			return surveySummary;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
			if(rs != null){
				rs.close();
			}
			if(callableStatementUtil != null){
				callableStatementUtil.close();
			}
		}
	}

	@Override
	public FamilySummary familySummaryOfHospital(String hospitalCode) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		ResultSet rs = null;
		FamilySummary familySummary = new FamilySummary();
		try{
		
			String query = "CALL sp_Statistic_FamilySummary_ByCode5"
					+ "("
					+ ":hospitalCode"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session,query);
			
			callableStatementUtil.setString("hospitalCode",GlobalFunction.isEmpty(hospitalCode) ? null : hospitalCode);
			
			Log.error(callableStatementUtil);
			
			rs = callableStatementUtil.executeQuery();
			if(rs.next()) {
				familySummary.setFamily(rs.getInt("Family"));
				familySummary.setMale(rs.getInt("Male"));
				familySummary.setFemale(rs.getInt("Female"));
			}
			return familySummary;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
			if(rs != null){
				rs.close();
			}
			if(callableStatementUtil != null){
				callableStatementUtil.close();
			}
		}
	}

	@Override
	public List<SurveySummary> surveySummaryOfHospital(String hospitalCode) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		ResultSet rs = null;
		List<SurveySummary> surveySummary = new ArrayList<>();
		try{
		
			String query = "CALL sp_Statistic_SurveySummary_ByCode5"
					+ "("
					+ ":hospitalCode"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session,query);
			
			callableStatementUtil.setString("hospitalCode",GlobalFunction.isEmpty(hospitalCode) ? null : hospitalCode);
			
			Log.error(callableStatementUtil);
			
			rs = callableStatementUtil.executeQuery();
			while(rs.next()) {
				SurveySummary summary = new SurveySummary();
				summary.setTotal(rs.getInt("Total"));
				summary.setSurvey(rs.getInt("Survey"));
				summary.setHeaderTypeCode(rs.getString("HeaderTypeCode"));
				surveySummary.add(summary);
			}
			return surveySummary;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
			if(rs != null){
				rs.close();
			}
			if(callableStatementUtil != null){
				callableStatementUtil.close();
			}
		}
	}
}
