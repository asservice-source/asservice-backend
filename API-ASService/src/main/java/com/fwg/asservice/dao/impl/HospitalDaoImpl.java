package com.fwg.asservice.dao.impl;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fwg.asservice.dao.HospitalDao;
import com.fwg.asservice.model.Hospital;
import com.fwg.asservice.sql.CallableStatementUtil;
import com.fwg.asservice.utility.GlobalFunction;

@Repository
public class HospitalDaoImpl implements HospitalDao {  
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public String registerHospital(Hospital hospital) throws Exception {
		
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		try {

			String query = "call sp_hospital_registerHospital(:code5,:contactPrefix,:contactFirstName,:contactLastName,:contactCitizenId,:contactTelephone,:contactEmail,:tokenId)";
			callableStatementUtil = new CallableStatementUtil(session, query);
			
			/* register the OUT parameter before calling the stored procedure */
			callableStatementUtil.registerOutParameter("tokenId", Types.VARCHAR);

			callableStatementUtil.setString("code5", hospital.getCode5());
			callableStatementUtil.setString("contactPrefix", hospital.getContactPrefix());
			callableStatementUtil.setString("contactFirstName", hospital.getContactFirstName());
			callableStatementUtil.setString("contactLastName", hospital.getContactLastName());
			callableStatementUtil.setString("contactCitizenId", hospital.getContactCitizenId());
			callableStatementUtil.setString("contactTelephone", hospital.getContactTelephone());
			callableStatementUtil.setString("contactEmail", hospital.getContactEmail());
			callableStatementUtil.setString("tokenId", null);

			callableStatementUtil.execute();
			
			/* read the OUT parameter now */
			String tokenId = callableStatementUtil.getString("tokenId");

			return tokenId;

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
	public String activeHospital(String tokenId, String password) throws Exception {
		
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		try {
			
			
			//Hospital ssss = new Hospital();
			//ssss.setTokenId("54D8A1E3-8B3C-422C-9AC7-9CC89FBE7372");
			
			
/*			final ObjectMapper mapper = new ObjectMapper();
			  ObjectNode requestParam = mapper.createObjectNode();
			  requestParam.put("tokenId", "862B6436-99C3-E711-AB84-005056C00008");
			  Hospital model = mapper.convertValue(requestParam, Hospital.class);*/
			  

			String query = "call sp_hospital_activateHospital(:tokenID,:password)";
			callableStatementUtil = new CallableStatementUtil(session, query);
			
			callableStatementUtil.setString("tokenID", tokenId);
			callableStatementUtil.setString("password", password);

			callableStatementUtil.execute();

			return tokenId;

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
	public List<Hospital> listHospitals(String code5) throws Exception {
		
		Session session = sessionFactory.openSession();
		
		try{
		
			SQLQuery query = session.createSQLQuery("{CALL sp_hospital_getHospital(:code5)}");
			query.addEntity(Hospital.class);
			query.setParameter("code5", code5);
			
			@SuppressWarnings("unchecked")
			List<Hospital> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	private Session getSession() {
		Session sess = getSessionFactory().getCurrentSession();
		if (sess == null) {
			sess = getSessionFactory().openSession();
		}
		return sess;
	}

	private SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public List<Hospital> getHospitalByTokenId(String tokenId) throws Exception {
		Session session = sessionFactory.openSession();
		try{
		
			SQLQuery query = session.createSQLQuery("{CALL sp_hospital_getHospitalByTokenId(:tokenId)}");
			query.addEntity(Hospital.class);
			query.setParameter("tokenId", tokenId);
			
			@SuppressWarnings("unchecked")
			List<Hospital> result = query.list();
			
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
	public List<Hospital> listHospitalsNameAll(String code5) throws Exception {
		Session session = sessionFactory.openSession();
		try{
		
			SQLQuery query = session.createSQLQuery("{CALL sp_hospital_getHospitalNameByCode5(:code5)}");
			query.addEntity(Hospital.class);
			query.setParameter("code5", GlobalFunction.isEmpty(code5)?null:code5);
			
			@SuppressWarnings("unchecked")
			List<Hospital> result = query.list();
			
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