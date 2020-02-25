package com.fwg.asservice.dao.impl;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fwg.asservice.dao.SurveyDao;
import com.fwg.asservice.model.BornLocation;
import com.fwg.asservice.model.survey.Child;
import com.fwg.asservice.model.survey.DeathPlace;
import com.fwg.asservice.model.survey.SurveyHeader;
import com.fwg.asservice.sql.CallableStatementUtil;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONObject;

@Repository
public class SurveyDaoImpl implements SurveyDao{ 
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<SurveyHeader> listSurveyHeaders(String headerTypeCode, String hospitalCode) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			SQLQuery query = session.createSQLQuery("{CALL `survey.sp_Survey_SurveyHederList_ByHeaderTypeCode`(:headerTypeCode,:hospitalCode)}");
			
			query.addEntity(SurveyHeader.class);
			query.setParameter("headerTypeCode", headerTypeCode);
			query.setParameter("hospitalCode", hospitalCode);
			
			List<SurveyHeader> result = query.list();

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
	public List<DeathPlace> listDeathPlace() throws Exception {
		Session session = sessionFactory.openSession();
		try{
			Criteria cr = session.createCriteria(DeathPlace.class);
			List<DeathPlace> result = cr.list();
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
	public JSONObject surveyIsDuplucate(String headerTypeCode, String documentId, String personId) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		JSONObject isDuplicate = new JSONObject();
		
		try {

			String query = "call `Survey.sp_Survey_Survey_IsDuplicate`("
					+ ":headerTypeCode"
					+ ",:documentId"
					+ ",:personId"
					+ ",:isDuplicate"
					+ ",:rowGUID"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session, query);

			callableStatementUtil.registerOutParameter("isDuplicate", Types.BIT);
			callableStatementUtil.registerOutParameter("rowGUID", Types.NVARCHAR);
			
			callableStatementUtil.setString("headerTypeCode", headerTypeCode);
			callableStatementUtil.setString("documentId", documentId);
			callableStatementUtil.setString("personId", personId);
			callableStatementUtil.setBoolean("isDuplicate", false);
			callableStatementUtil.setString("rowGUID", null);

			callableStatementUtil.execute();
			
			isDuplicate.put("isDuplicate", callableStatementUtil.getInt("isDuplicate") > 0 ? true : false);
			isDuplicate.put("rowGUID", callableStatementUtil.getString("rowGUID"));
			
			return isDuplicate;

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
	public int checkCitizenIdIsSurvey(String citizenId) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		try {
			String query = "call `Survey.sp_Survey_CheckCitizenIdIsSurvey`(:CitizenId, :IsSurvey)";
			
			callableStatementUtil = new CallableStatementUtil(session, query);

			callableStatementUtil.registerOutParameter("IsSurvey", Types.INTEGER);

			callableStatementUtil.setString("CitizenId", citizenId);
			callableStatementUtil.setBoolean("IsSurvey", false);

			callableStatementUtil.execute();
			
			return callableStatementUtil.getInt("IsSurvey");

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
	public List<Child> listChildByReferenceId(String referenceId) throws Exception {
		
		Session session = sessionFactory.openSession();
		
		try{
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_Child_ChildList_ByReferenceID`(:referenceId)}");
			
			query.addEntity(Child.class);
			query.setParameter("referenceId", GlobalFunction.isEmpty(referenceId)?null:referenceId);
			
			List<Child> result = query.list();
			
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
	public Child insertChildAndPersonAndHomeMembersAndAddress(Child child, CallableStatementUtil callableStatementUtil) throws Exception {

		try{
			
			String query = " CALL `Survey.sp_Child_ChildAndPersonAndHomeMembersAndAddressIns`(" + 
				":PersonID" +  
				",:ReferenceID" +  
				",:ChildNo" + 
				",:BloodTypeID" + 
				",:BornTypeID" + 
				",:BirthDate" + 
				",:Weight" + 
				",:PrefixCode" + 
				",:FirstName" + 
				",:LastName" + 
				",:GenderID" + 
				",:BornLocationID" + 
				",:BornCitizenID" + 
				",:AbortionCause" + 
				",:MotherCID" + 
				",:HomeID" + 
			")";
			
			callableStatementUtil = new CallableStatementUtil(callableStatementUtil.getConnection(), query);
			
			// Output Parameter
			callableStatementUtil.registerOutParameter("PersonID", Types.NVARCHAR);
			
			// Input Parameter
			callableStatementUtil.setString("PersonID", null);
			callableStatementUtil.setString("ReferenceID", child.getReferenceId());
			callableStatementUtil.setInt("ChildNo", child.getChildNo());
			callableStatementUtil.setInt("BloodTypeID", child.getBloodTypeId());
			callableStatementUtil.setInt("BornTypeID", child.getBornTypeId());
			callableStatementUtil.setDate("BirthDate", GlobalFunction.convertStringToDate(child.getBirthDate()));
			callableStatementUtil.setInt("Weight", child.getWeight());
			callableStatementUtil.setString("PrefixCode", child.getPrefixCode());
			callableStatementUtil.setString("FirstName", child.getFirstName());
			callableStatementUtil.setString("LastName", child.getLastName());
			callableStatementUtil.setInt("GenderID", child.getGenderId());
			callableStatementUtil.setInt("BornLocationID", child.getBornLocationId());
			callableStatementUtil.setString("BornCitizenID", child.getBornCitizenId());
			callableStatementUtil.setString("AbortionCause", child.getAbortionCause());
			callableStatementUtil.setString("MotherCID", child.getMotherCID());
			callableStatementUtil.setInt("HomeID", child.getHomeID());
			
			Log.error(callableStatementUtil);
			
			callableStatementUtil.execute();

			child.setPersonIdChild(callableStatementUtil.getString("PersonID"));
		
		} catch (Exception exception) {
			throw exception;
		}
		return child;
	}

	@Override
	public List<com.fwg.asservice.model.survey.HomeMember> listHomeMemberNotSurvey(Integer homeId, String headerTypeCode, String documentId, boolean isAll)
			throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_Survey_getHomeMember_NotSurvey`(:homeId, :headerTypeCode, :documentId, :isAll)}");
			
			query.setParameter("homeId", homeId);
			query.setParameter("headerTypeCode", GlobalFunction.isEmpty(headerTypeCode) ? null : headerTypeCode);
			query.setParameter("documentId", GlobalFunction.isEmpty(documentId) ? null : documentId);
			query.setParameter("isAll", isAll);
			query.addEntity(com.fwg.asservice.model.survey.HomeMember.class);
			
			List<com.fwg.asservice.model.survey.HomeMember> result = query.list();
		
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
	public Integer autoGenSurveyHearderOfYear(String code5) throws Exception {
		Session session = sessionFactory.openSession();
		try{
			SQLQuery query = session.createSQLQuery("{CALL `config.sp_AutoGen_SurveyHearderOfYear`(:hospitalCode)}");
			
			query.setParameter("hospitalCode", code5);
			return query.executeUpdate();
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public BornLocation insertBornLocation(BornLocation bornLocation) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		BornLocation insUpdInfo = new BornLocation();
		try {

			String query = "call `Survey.sp_BornLocation_BornLocationInsOrUpd`("
					+ ":ID"
					+ ",:Name"
					+ ",:HospitalCode"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session, query);
			
			/*register the OUT parameter before calling the stored procedure*/
			callableStatementUtil.registerOutParameter("ID", Types.INTEGER);
			
			callableStatementUtil.setInt("ID", GlobalFunction.isEmpty(bornLocation) ? null : bornLocation.getId());
			callableStatementUtil.setString("Name", bornLocation.getName());
			callableStatementUtil.setString("HospitalCode", bornLocation.getHospitalCode());

			callableStatementUtil.execute();
			
			/*read the OUT parameter now*/ 
			insUpdInfo.setId(callableStatementUtil.getInt("ID"));
			
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
}
