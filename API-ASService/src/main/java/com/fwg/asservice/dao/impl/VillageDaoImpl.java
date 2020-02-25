package com.fwg.asservice.dao.impl;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fwg.asservice.dao.VillageDao;
import com.fwg.asservice.model.Village;
import com.fwg.asservice.sql.CallableStatementUtil;

@Repository
public class VillageDaoImpl implements VillageDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int insOrUpdVillage(Village village) throws Exception {
		
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		int scopeIdentity = 0;
		
		try {

			String query = "call sp_village_InsOrUpd("
					+ ":id"
					+ ",:villageNo"
					+ ",:villageName"
					+ ",:hospitalCode"
					+ ",:createdBy"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session, query);
			
			/* register the OUT parameter before calling the stored procedure */
			callableStatementUtil.registerOutParameter("id", Types.INTEGER);
			
			callableStatementUtil.setInt("id", village.getId());
			callableStatementUtil.setString("villageNo", village.getVillageNo());
			callableStatementUtil.setString("villageName", village.getVillageName());
			callableStatementUtil.setString("hospitalCode", village.getHospitalCode());
			callableStatementUtil.setString("createdBy", village.getCreatedBy());

			callableStatementUtil.execute();
			
			/* read the OUT parameter now */
			scopeIdentity = callableStatementUtil.getInt("id");
			
			return scopeIdentity;

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
	public List<Village> listVillageNoByHospitalCode(String hospitalCode) throws Exception {
		
		Session session = sessionFactory.openSession();
		
		try{
		
			SQLQuery query = session.createSQLQuery("{CALL sp_village_getVillageNo_ByHospitalCode(:hospitalCode)}");
			query.setParameter("hospitalCode", hospitalCode);
			query.addEntity(Village.class);
			
			@SuppressWarnings("unchecked")
			List<Village> result = query.list();
			
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
	public boolean deleteVillage(Integer id) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		int scopeIdentity = 0;
		
		try {

			String query = "call sp_village_Del("
					+ ":id"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session, query);
			
			callableStatementUtil.setInt("id", id);

			callableStatementUtil.execute();
			
			return true;

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
