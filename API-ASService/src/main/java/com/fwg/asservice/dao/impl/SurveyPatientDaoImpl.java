package com.fwg.asservice.dao.impl;

import java.sql.Types;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fwg.asservice.dao.SurveyPatientDao;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.PatientDetailInfo;
import com.fwg.asservice.sql.CallableStatementUtil;
import com.fwg.asservice.utility.GlobalFunction;

/**
 * @author Phitak_Jha
 *
 */
@Repository
public class SurveyPatientDaoImpl implements SurveyPatientDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * @return RowGUID type String
	 *
	 */
	@Override
	public PatientDetailInfo patientDetailInfoInsertOrUpdate(PatientDetailInfo patientDetailInfo) throws Exception {
		System.out.println("insertOrUpdate->CallableStatementUtil");
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		try{
			
			String query = 
					" CALL `Survey.sp_Patient_PatientDetailInfoInsOrUpd`"
					+ " ("+
					":RowGUID"+ 
					",:DocumentID" + 
					",:OSMID" +  
					",:PersonID" + 
					",:HomeID" +
					",:CancerTypeID" + 
					",:DiseaseStatusTypeID" + 
					",:PatientDate" + 
					",:PatientTypeID" + 
					",:HInsuranceTypeID" + 
					",:PatientSurveyTypeCode" + 
					",:DisabilityTypeID" + 
					",:DisabilityCauseTypeID" + 
					",:TreatmentPlace" + 
					",:Remark" + 
					",:Telephone"+
					",:Latitude" + 
					",:Longitude"+
					",:IsDeleted"
					+ ")";
			
			System.out.println("StringSQL ->"+query);
			callableStatementUtil = new CallableStatementUtil(session,query);
			
			//set return Parameter
			callableStatementUtil.registerOutParameter("RowGUID", Types.NVARCHAR);
			
			//set input Parameter
			callableStatementUtil.setString("RowGUID", patientDetailInfo.getRowGUID());
			callableStatementUtil.setString("DocumentID", patientDetailInfo.getDocumentId());
			callableStatementUtil.setString("OSMID", patientDetailInfo.getOsmId());
			callableStatementUtil.setString("PersonID", patientDetailInfo.getPersonId());
			callableStatementUtil.setInt("HomeID", patientDetailInfo.getHomeId());
			callableStatementUtil.setInt("CancerTypeID",patientDetailInfo.getCancerTypeId());
			callableStatementUtil.setInt("DiseaseStatusTypeID",patientDetailInfo.getDiseaseStatusTypeId());
			callableStatementUtil.setDate("PatientDate",patientDetailInfo.getPatientDate());
			callableStatementUtil.setInt("PatientTypeID",patientDetailInfo.getPatientTypeId());
			callableStatementUtil.setInt("HInsuranceTypeID",patientDetailInfo.gethInsuranceTypeId());
			callableStatementUtil.setString("PatientSurveyTypeCode",patientDetailInfo.getPatientSurveyTypeCode());
			callableStatementUtil.setInt("DisabilityTypeID",patientDetailInfo.getDisabilityTypeId());
			callableStatementUtil.setInt("DisabilityCauseTypeID",patientDetailInfo.getDisabilityCauseTypeId());
			callableStatementUtil.setString("TreatmentPlace",patientDetailInfo.getTreatmentPlace());
			callableStatementUtil.setString("Remark",patientDetailInfo.getRemark());
			callableStatementUtil.setString("Telephone",patientDetailInfo.getTelephone());
			callableStatementUtil.setString("Latitude",patientDetailInfo.getLatitude());
			callableStatementUtil.setString("Longitude",patientDetailInfo.getLongitude());
			callableStatementUtil.setBoolean("IsDeleted",patientDetailInfo.isDeleted());
			
			Log.error(callableStatementUtil);
			
			callableStatementUtil.execute();

			patientDetailInfo.setRowGUID(callableStatementUtil.getString("RowGUID"));
		
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
		return patientDetailInfo;
	}

	@Override
	public List<PatientDetailInfo> patientDetailInfoList(Filter filter)
			throws Exception {
		Session session = sessionFactory.openSession();
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_Patient_PatientDetailInfoList`(:documentId, :villageId, :osmId, :name)}");
			
			query.addEntity(PatientDetailInfo.class);
			
			query.setParameter("documentId", GlobalFunction.isEmpty(filter.getDocumentId())?null:filter.getDocumentId());
			query.setParameter("villageId", GlobalFunction.isEmpty(filter.getVillageId())?null:filter.getVillageId());
			query.setParameter("osmId", GlobalFunction.isEmpty(filter.getOsmId())?null:filter.getOsmId());
			query.setParameter("name", GlobalFunction.isEmpty(filter.getName())?null:filter.getName());
			
			List<PatientDetailInfo> result = query.list();

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
	public PatientDetailInfo patientDetailInfoByRowGUID(String rowGUID) throws Exception {
		
		Session session = sessionFactory.openSession();
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_Patient_PatientDetailInfo_By_RowGUID`(:rowGUID)}");
			
			query.addEntity(PatientDetailInfo.class);
			
			query.setParameter("rowGUID", rowGUID);
			
			PatientDetailInfo result = (PatientDetailInfo) query.list().get(0);

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