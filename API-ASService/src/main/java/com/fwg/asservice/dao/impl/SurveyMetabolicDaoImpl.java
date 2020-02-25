package com.fwg.asservice.dao.impl;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fwg.asservice.dao.SurveyMetabolicDao;
import com.fwg.asservice.model.survey.MetabolicDetailInfo;
import com.fwg.asservice.sql.CallableStatementUtil;
import com.fwg.asservice.utility.GlobalFunction;

@Repository
public class SurveyMetabolicDaoImpl implements SurveyMetabolicDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<MetabolicDetailInfo> listMetabolic(String documentId, Integer villageId, String osmId, String name) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_Metabolic_MetabolicDetailInfoList`(:documentId, :villageId, :osmId, :name)}");
			
			query.addEntity(MetabolicDetailInfo.class);
			query.setParameter("documentId", GlobalFunction.isEmpty(documentId) ? null : documentId);
			query.setParameter("villageId", villageId);
			query.setParameter("osmId", GlobalFunction.isEmpty(osmId) ? null : osmId);
			query.setParameter("name", name);
			
			List<MetabolicDetailInfo> result = query.list();
		
			
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
	public MetabolicDetailInfo metabolicInsOrUpd(MetabolicDetailInfo metabolicDetailInfo) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		MetabolicDetailInfo insUpdInfo = new MetabolicDetailInfo();
		try {

			String query = "call `Survey.sp_Metabolic_MetabolicDetailInfoInsOrUpd`("
					+ ":RowGUID"
					+ ",:DocumentID"
					+ ",:OSMID"
					+ ",:PersonID"
					+ ",:HomeID"
					+ ",:HInsuranceTypeID"
					+ ",:IsHeredityMetabolic"
					+ ",:IsWaistlineOver"
					+ ",:IsBPOver"
					+ ",:IsFBS"
					+ ",:IsCholesterol"
					+ ",:IsNewborn4kg"
					+ ",:IsHeredityHypertension"
					+ ",:SmokingStatusID"
					+ ",:RollPerDay"
					+ ",:PackPerYear"
					+ ",:DrinkingStatusID"
					+ ",:OftenPerWeek"
					+ ",:Weight"
					+ ",:Height"
					+ ",:BMI"
					+ ",:Waistline"
					+ ",:BP1"
					+ ",:BP2"
					+ ",:FBS"
					+ ",:IsMetabolic"
					+ ",:IsHypertension"
					+ ",:IsEyeComplication"
					+ ",:IsKidneyComplication"
					+ ",:IsPeripheralNeuropathy"
					+ ",:PeripheralName"
					+ ",:IsNeuropathy"
					+ ",:IsOther"
					+ ",:OtherComplication"
					+ ",:IsDeleted"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session, query);
			
			/*register the OUT parameter before calling the stored procedure*/
			callableStatementUtil.registerOutParameter("RowGUID", Types.VARCHAR);
			
			callableStatementUtil.setString("RowGUID", metabolicDetailInfo.getRowGUID());
			callableStatementUtil.setString("DocumentID", metabolicDetailInfo.getDocumentId());
			callableStatementUtil.setString("OSMID", metabolicDetailInfo.getOsmId());
			callableStatementUtil.setString("PersonID", metabolicDetailInfo.getPersonId());
			callableStatementUtil.setInt("HomeID", metabolicDetailInfo.getHomeId());
			callableStatementUtil.setInt("HInsuranceTypeID", metabolicDetailInfo.gethInsuranceTypeId());
			callableStatementUtil.setBoolean("IsHeredityMetabolic", metabolicDetailInfo.isHeredityMetabolic());
			callableStatementUtil.setBoolean("IsWaistlineOver", metabolicDetailInfo.isWaistlineOver());
			callableStatementUtil.setBoolean("IsBPOver", metabolicDetailInfo.isBPOver());
			callableStatementUtil.setBoolean("IsFBS", metabolicDetailInfo.isFBS());
			callableStatementUtil.setBoolean("IsCholesterol", metabolicDetailInfo.isCholesterol());
			callableStatementUtil.setBoolean("IsNewborn4kg", metabolicDetailInfo.isNewborn4kg());
			callableStatementUtil.setBoolean("IsHeredityHypertension", metabolicDetailInfo.isHeredityHypertension());
			callableStatementUtil.setInt("SmokingStatusID", metabolicDetailInfo.getSmokingStatusId());
			callableStatementUtil.setInt("RollPerDay", metabolicDetailInfo.getRollPerDay());
			callableStatementUtil.setInt("PackPerYear", metabolicDetailInfo.getPackPerYear());
			callableStatementUtil.setInt("DrinkingStatusID", metabolicDetailInfo.getDrinkingStatusId());
			callableStatementUtil.setInt("OftenPerWeek", metabolicDetailInfo.getOftenPerWeek());
			callableStatementUtil.setInt("Weight", metabolicDetailInfo.getWeight());
			callableStatementUtil.setInt("Height", metabolicDetailInfo.getHeight());
			callableStatementUtil.setString("BMI", metabolicDetailInfo.getBmi());
			callableStatementUtil.setInt("Waistline", metabolicDetailInfo.getWaistline());
			callableStatementUtil.setString("BP1", metabolicDetailInfo.getBp1());
			callableStatementUtil.setString("BP2", metabolicDetailInfo.getBp2());
			callableStatementUtil.setInt("FBS", metabolicDetailInfo.getFbs());
			callableStatementUtil.setBoolean("IsMetabolic", metabolicDetailInfo.isMetabolic());
			callableStatementUtil.setBoolean("IsHypertension", metabolicDetailInfo.isHypertension());
			callableStatementUtil.setBoolean("IsEyeComplication", metabolicDetailInfo.isEyeComplication());
			callableStatementUtil.setBoolean("IsKidneyComplication", metabolicDetailInfo.isKidneyComplication());
			callableStatementUtil.setBoolean("IsPeripheralNeuropathy", metabolicDetailInfo.isPeripheralNeuropathy());
			callableStatementUtil.setString("PeripheralName", metabolicDetailInfo.getPeripheralName());
			callableStatementUtil.setBoolean("IsNeuropathy", metabolicDetailInfo.isNeuropathy());
			callableStatementUtil.setBoolean("IsOther", metabolicDetailInfo.isOther());
			callableStatementUtil.setString("OtherComplication", metabolicDetailInfo.getOtherComplication());
			callableStatementUtil.setBoolean("IsDeleted", metabolicDetailInfo.isDeleted());

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
	public MetabolicDetailInfo metabolicDataByRowGUID(String rowGUID) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_Metabolic_MetabolicDetailInfo_By_RowGUID`(:rowGUID)}");
			
			query.addEntity(MetabolicDetailInfo.class);
			query.setParameter("rowGUID", GlobalFunction.isEmpty(rowGUID) ? null : rowGUID);
			
			MetabolicDetailInfo result = (MetabolicDetailInfo) query.list().get(0);
		
			
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
	public List<MetabolicDetailInfo> listMetabolicNotSurvey(String documentId, String osmId) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_Metabolic_MetabolicDetailInfoList_NotSurvey`(:DocumentId, :OSMID)}");
			
			query.addEntity(MetabolicDetailInfo.class);
			query.setParameter("DocumentId", GlobalFunction.isEmpty(documentId) ? null : documentId);
			query.setParameter("OSMID", GlobalFunction.isEmpty(osmId) ? null : osmId);
			
			List<MetabolicDetailInfo> result = query.list();
		
			
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
