package com.fwg.asservice.dao.impl;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fwg.asservice.dao.SurveyPopulationDao;
import com.fwg.asservice.model.HomeMembers;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.PopulationDetail;
import com.fwg.asservice.model.survey.PopulationDetailInfo;
import com.fwg.asservice.sql.CallableStatementUtil;
import com.fwg.asservice.utility.GlobalFunction;

@Repository
public class SurveyPopulationDaoImpl implements SurveyPopulationDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<PopulationDetail> listPopulation(Filter filterParams) throws Exception {
		
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_Population_PopulationDetailInfoList`(:documentId, :villageId, :osmId, :homeId)}");
			
			query.addEntity(PopulationDetail.class);
			query.setParameter("documentId", GlobalFunction.isEmpty(filterParams.getDocumentId())?null:filterParams.getDocumentId());
			query.setParameter("villageId", GlobalFunction.isEmpty(filterParams.getVillageId())?null:filterParams.getVillageId());
			query.setParameter("osmId", GlobalFunction.isEmpty(filterParams.getOsmId())?null:filterParams.getOsmId());
			query.setParameter("homeId", GlobalFunction.isEmpty(filterParams.getHomeId())?null:filterParams.getHomeId());
			
			List<PopulationDetail> result = query.list();
		
			
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
	public Person addHomeMember(HomeMembers homeMembers) throws Exception {
		
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		Person insUpdInfo = new Person();
		try {

			String query = "call `Survey.sp_Population_AddHomeMember`("
					+ ":PersonID"
					+ ",:CitizenID"
					+ ",:FirstName"
					+ ",:LastName"
					+ ",:NickName"
					+ ",:GenderID"
					+ ",:PrefixCode"
					+ ",:BirthDate"
					+ ",:MStatusCode"
					+ ",:OccupCode"
					+ ",:RaceCode"
					+ ",:NationCode"
					+ ",:ReligionCode"
					+ ",:EducationCode"
					+ ",:FatherCID"
					+ ",:MotherCID"
					+ ",:CoupleCID"
					+ ",:VStatusCode"
					+ ",:BloodTypeID"
					+ ",:RHGroupID"
					+ ",:LaborCode"
					+ ",:Passport"
					+ ",:IsDead"
					+ ",:DeadDate"
					+ ",:HomeID"
					+ ",:FamilyStatusID"
					+ ",:DischargeID"
					+ ",:DischargeDate"
					+ ",:IsGuest"
					+ ",:HomeNO"
					+ ",:MooNO"
					+ ",:Road"
					+ ",:TumbolCode"
					+ ",:AmphurCode"
					+ ",:ProvinceCode"
					+ ",:ZipCode"
					+ ",:Password"
					+ ",:MedicalRightCode"
					+ ",:CongenitalDisease"
					+ ",:Remark"
					+ ",:IsExists"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session, query);
			
			/*register the OUT parameter before calling the stored procedure*/
			callableStatementUtil.registerOutParameter("PersonID", Types.VARCHAR);
			
			callableStatementUtil.setString("PersonID", homeMembers.getPerson().getPersonId());
			callableStatementUtil.setString("CitizenID", homeMembers.getPerson().getCitizenId());
			callableStatementUtil.setString("FirstName", homeMembers.getPerson().getFirstName());
			callableStatementUtil.setString("LastName", homeMembers.getPerson().getLastName());
			callableStatementUtil.setString("NickName", homeMembers.getPerson().getNickName());
			callableStatementUtil.setInt("GenderID", homeMembers.getPerson().getGenderId());
			callableStatementUtil.setString("PrefixCode", homeMembers.getPerson().getPrefixCode());
			callableStatementUtil.setDate("BirthDate", homeMembers.getPerson().getBirthDate());
			callableStatementUtil.setString("MStatusCode", homeMembers.getPerson().getmStatusCode());
			callableStatementUtil.setString("OccupCode", homeMembers.getPerson().getOccupCode());
			callableStatementUtil.setString("RaceCode", homeMembers.getPerson().getRaceCode());
			callableStatementUtil.setString("NationCode", homeMembers.getPerson().getNationCode());
			callableStatementUtil.setString("ReligionCode", homeMembers.getPerson().getReligionCode());
			callableStatementUtil.setString("EducationCode", homeMembers.getPerson().getEducationCode());
			callableStatementUtil.setString("FatherCID", homeMembers.getPerson().getFatherCid());
			callableStatementUtil.setString("MotherCID", homeMembers.getPerson().getMotherCid());
			callableStatementUtil.setString("CoupleCID", homeMembers.getPerson().getCoupleCid());
			callableStatementUtil.setString("VStatusCode", homeMembers.getPerson().getvStatusCode());
			callableStatementUtil.setInt("BloodTypeID", homeMembers.getPerson().getBloodTypeId());
			callableStatementUtil.setInt("RHGroupID", homeMembers.getPerson().getrhGroupId());
			callableStatementUtil.setString("LaborCode", homeMembers.getPerson().getLaborCode());
			callableStatementUtil.setString("Passport", homeMembers.getPerson().getPassport());
			callableStatementUtil.setBoolean("IsDead", homeMembers.getPerson().isDead());
			callableStatementUtil.setDate("DeadDate", homeMembers.getPerson().getDeadDate());
			callableStatementUtil.setInt("HomeID", homeMembers.getHomeId());
			callableStatementUtil.setInt("FamilyStatusID", homeMembers.getFamilyStatusId());
			callableStatementUtil.setInt("DischargeID", homeMembers.getDischargeId());
			callableStatementUtil.setDate("DischargeDate", homeMembers.getDischargeDate());
			callableStatementUtil.setBoolean("IsGuest", homeMembers.isGuest());
			
			callableStatementUtil.setString("HomeNO", homeMembers.getPerson().getAddress().getHomeNo());
			callableStatementUtil.setString("MooNO", homeMembers.getPerson().getAddress().getMooNo());
			callableStatementUtil.setString("Road", homeMembers.getPerson().getAddress().getRoad());
			callableStatementUtil.setString("TumbolCode", homeMembers.getPerson().getAddress().getTumbolCode());
			callableStatementUtil.setString("AmphurCode", homeMembers.getPerson().getAddress().getAmphurCode());
			callableStatementUtil.setString("ProvinceCode", homeMembers.getPerson().getAddress().getProvinceCode());
			callableStatementUtil.setString("ZipCode", homeMembers.getPerson().getAddress().getZipcode());
			callableStatementUtil.setString("Password", GlobalFunction.generatePassword(homeMembers.getPerson().getCitizenId(),homeMembers.getPerson().getBirthDate()));
			callableStatementUtil.setString("MedicalRightCode", homeMembers.getPerson().getMedicalRightCode());
			callableStatementUtil.setString("CongenitalDisease", homeMembers.getPerson().getCongenitalDisease());
			callableStatementUtil.setString("Remark", homeMembers.getPerson().getRemark());
			callableStatementUtil.setBoolean("IsExists", homeMembers.isExists());
			
			callableStatementUtil.execute();
			
			/*read the OUT parameter now*/ 
			insUpdInfo.setPersonId(callableStatementUtil.getString("PersonID"));
			
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
	public PopulationDetailInfo insertOrUpdatePopulationDetail(PopulationDetail populationDetail, PopulationDetailInfo populationDetailInfo) throws Exception {
		PopulationDetailInfo insUpdInfo = new PopulationDetailInfo();
		try {
			String query = "call `Survey.sp_Population_PopulationInsOrUpd`("
					+ ":RowGUID"
					+ ",:DocumentID"
					+ ",:ReferenceID"
					+ ",:OSMID"
					+ ",:CongenitalDisease"
					+ ",:IsExists"
					+ ",:Remark"
					+ ",:PersonID"
					+ ",:CitizenID"
					+ ",:FirstName"
					+ ",:LastName"
					+ ",:NickName"
					+ ",:GenderID"
					+ ",:PrefixCode"
					+ ",:BirthDate"
					+ ",:MStatusCode"
					+ ",:OccupCode"
					+ ",:RaceCode"
					+ ",:NationCode"
					+ ",:ReligionCode"
					+ ",:EducationCode"
					+ ",:FatherCID"
					+ ",:MotherCID"
					+ ",:CoupleCID"
					+ ",:VStatusCode"
					+ ",:BloodTypeID"
					+ ",:RHGroupID"
					+ ",:LaborCode"
					+ ",:Passport"
					+ ",:IsDead"
					+ ",:DeadDate"
					+ ",:HomeID"
					+ ",:FamilyStatusID"
					+ ",:DischargeID"
					+ ",:DischargeDate"
					+ ",:IsGuest"
					+ ",:HomeNo"
					+ ",:MooNo"
					+ ",:Road"
					+ ",:TumbolCode"
					+ ",:AmphurCode"
					+ ",:ProvinceCode"
					+ ",:Zipcode"
					+ ",:Password"
					+ ",:MedicalRightCode"
/*					+ ",:CongenitalDisease"
					+ ",:Remark"
					+ ",:IsExists"*/
					+ ")";

			CallableStatementUtil callableStatementUtil = populationDetail.getCallableStatementUtil();
			callableStatementUtil = new CallableStatementUtil(callableStatementUtil.getConnection(), query);
			
			/*register the OUT parameter before calling the stored procedure*/
			callableStatementUtil.registerOutParameter("RowGUID", Types.VARCHAR);
			
			callableStatementUtil.setString("RowGUID", null);
			callableStatementUtil.setString("DocumentID", populationDetail.getDocumentId());
			callableStatementUtil.setString("ReferenceID", populationDetail.getRowGUID());
			callableStatementUtil.setString("OSMID", populationDetail.getOsmId());
			callableStatementUtil.setString("CongenitalDisease", populationDetailInfo.getCongenitalDisease());
			callableStatementUtil.setBoolean("IsExists", populationDetailInfo.isExists());
			callableStatementUtil.setString("Remark", populationDetailInfo.getRemark());
			callableStatementUtil.setString("PersonID", populationDetailInfo.getPerson().getPersonId());
			callableStatementUtil.setString("CitizenID", populationDetailInfo.getPerson().getCitizenId());
			callableStatementUtil.setString("FirstName", populationDetailInfo.getPerson().getFirstName());
			callableStatementUtil.setString("LastName", populationDetailInfo.getPerson().getLastName());
			callableStatementUtil.setString("NickName", populationDetailInfo.getPerson().getNickName());
			callableStatementUtil.setInt("GenderID", populationDetailInfo.getPerson().getGenderId());
			callableStatementUtil.setString("PrefixCode", populationDetailInfo.getPerson().getPrefixCode());
			callableStatementUtil.setDate("BirthDate", populationDetailInfo.getPerson().getBirthDate());
			callableStatementUtil.setString("MStatusCode", populationDetailInfo.getPerson().getmStatusCode());
			callableStatementUtil.setString("OccupCode", populationDetailInfo.getPerson().getOccupCode());
			callableStatementUtil.setString("RaceCode", populationDetailInfo.getPerson().getRaceCode());
			callableStatementUtil.setString("NationCode", populationDetailInfo.getPerson().getNationCode());
			callableStatementUtil.setString("ReligionCode", populationDetailInfo.getPerson().getReligionCode());
			callableStatementUtil.setString("EducationCode", populationDetailInfo.getPerson().getEducationCode());
			callableStatementUtil.setString("FatherCID", populationDetailInfo.getPerson().getFatherCid());
			callableStatementUtil.setString("MotherCID", populationDetailInfo.getPerson().getMotherCid());
			callableStatementUtil.setString("CoupleCID", populationDetailInfo.getPerson().getCoupleCid());
			callableStatementUtil.setString("VStatusCode", populationDetailInfo.getPerson().getvStatusCode());
			callableStatementUtil.setInt("BloodTypeID", populationDetailInfo.getPerson().getBloodTypeId());
			callableStatementUtil.setInt("RHGroupID", populationDetailInfo.getPerson().getrhGroupId());
			callableStatementUtil.setString("LaborCode", populationDetailInfo.getPerson().getLaborCode());
			callableStatementUtil.setString("Passport", populationDetailInfo.getPerson().getPassport());
			callableStatementUtil.setBoolean("IsDead", populationDetailInfo.getPerson().isDead());
			callableStatementUtil.setDate("DeadDate", populationDetailInfo.getPerson().getDeadDate());
			callableStatementUtil.setInt("HomeID", populationDetail.getHomeId());
			callableStatementUtil.setInt("FamilyStatusID", populationDetailInfo.getFamilyStatusId());
			callableStatementUtil.setInt("DischargeID", populationDetailInfo.getDischargeId());
			callableStatementUtil.setDate("DischargeDate", populationDetailInfo.getDischargeDate());
			callableStatementUtil.setBoolean("IsGuest", populationDetailInfo.isGuest());
			callableStatementUtil.setString("HomeNo", populationDetailInfo.getPerson().getAddress().getHomeNo());
			callableStatementUtil.setString("MooNo", populationDetailInfo.getPerson().getAddress().getMooNo());
			callableStatementUtil.setString("Road", populationDetailInfo.getPerson().getAddress().getRoad());
			callableStatementUtil.setString("TumbolCode", populationDetailInfo.getPerson().getAddress().getTumbolCode());
			callableStatementUtil.setString("AmphurCode", populationDetailInfo.getPerson().getAddress().getAmphurCode());
			callableStatementUtil.setString("ProvinceCode", populationDetailInfo.getPerson().getAddress().getProvinceCode());
			callableStatementUtil.setString("Zipcode", populationDetailInfo.getPerson().getAddress().getZipcode());
			callableStatementUtil.setString("Password", GlobalFunction.generatePassword(populationDetailInfo.getPerson().getCitizenId(), populationDetailInfo.getPerson().getBirthDate()));
			callableStatementUtil.setString("MedicalRightCode", populationDetailInfo.getPerson().getMedicalRightCode());
			/*callableStatementUtil.setString("CongenitalDisease", populationDetailInfo.getPerson().getCongenitalDisease());
			callableStatementUtil.setString("Remark", populationDetailInfo.getPerson().getRemark());
			callableStatementUtil.setBoolean("IsExists", populationDetailInfo.isExists());*/
			
			callableStatementUtil.execute();
			
			/*read the OUT parameter now*/ 
			insUpdInfo.setRowGUID(callableStatementUtil.getString("RowGUID"));
			
			return insUpdInfo;

		} catch (Exception exception) {
			throw exception;
		}
	}

	@Override
	public void deletePopulation(PopulationDetail populationDetail) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		try {
			String query = "call `Survey.sp_Population_PopulationDel`(:RowGUID)";
			callableStatementUtil = new CallableStatementUtil(session, query);
			callableStatementUtil.setString("RowGUID", populationDetail.getRowGUID());
			callableStatementUtil.execute();
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
	public List<PopulationDetailInfo> listPopulationDetailInfo(String documentId, Integer homeId) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_Population_PopulationDetailInfo_DetailList`(:documentId, :homeId)}");
			
			query.addEntity(PopulationDetailInfo.class);
			
			query.setParameter("documentId", GlobalFunction.isEmpty(documentId) ? null : documentId);
			query.setParameter("homeId", homeId);
			
			List<PopulationDetailInfo> result = query.list();
		
			
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
	public List<PopulationDetail> listPopulationDetailNotSurvey(Filter filterParams) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL `Survey.sp_Population_PopulationDetailInfoList_NotSurvey`(:documentId, :osmId)}");
			
			query.addEntity(PopulationDetail.class);
			query.setParameter("documentId", GlobalFunction.isEmpty(filterParams.getDocumentId())?null:filterParams.getDocumentId());
			query.setParameter("osmId", GlobalFunction.isEmpty(filterParams.getOsmId())?null:filterParams.getOsmId());
			
			List<PopulationDetail> result = query.list();
		
			
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
	public List<HomeMembers> listHomeMemberByDocumentID(String documentId, Integer homeId) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_homemember_getHomeMember_ByDocumentID(:documentId, :homeId)}");
			
			query.setParameter("documentId", GlobalFunction.isEmpty(documentId) ? null : documentId);
			query.setParameter("homeId", homeId);
			query.addEntity(HomeMembers.class);
			
			List<HomeMembers> result = query.list();
		
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
	public PopulationDetail populationDetailInsOrUpd(PopulationDetail populationDetail) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		PopulationDetail insUpdInfo = new PopulationDetail();
		try {

			String query = "call `Survey.sp_Population_PopulationDetailInsOrUpd`("
					+ ":RowGUID"
					+ ",:DocumentID"
					+ ",:OSMID"
					+ ",:HomeID"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session, query, false);
			
			/*register the OUT parameter before calling the stored procedure*/
			callableStatementUtil.registerOutParameter("RowGUID", Types.VARCHAR);
			
			callableStatementUtil.setString("RowGUID", GlobalFunction.isEmpty(populationDetail.getRowGUID()) ? null : populationDetail.getRowGUID());
			callableStatementUtil.setString("DocumentID", populationDetail.getDocumentId());
			callableStatementUtil.setString("OSMID", populationDetail.getOsmId());
			callableStatementUtil.setInt("HomeID", populationDetail.getHomeId());

			callableStatementUtil.execute();
			
			/*read the OUT parameter now*/ 
			insUpdInfo.setRowGUID(callableStatementUtil.getString("RowGUID"));
			insUpdInfo.setCallableStatementUtil(callableStatementUtil);
			
			return insUpdInfo;

		} catch (Exception exception) {
			throw exception;
		}
	}

	@Override
	public PopulationDetailInfo populationDetailInfoInsOrUpd(PopulationDetailInfo populationDetailInfo)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
