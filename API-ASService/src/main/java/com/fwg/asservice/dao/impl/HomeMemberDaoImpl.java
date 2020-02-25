package com.fwg.asservice.dao.impl;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fwg.asservice.dao.HomeMemberDao;
import com.fwg.asservice.model.HomeMembers;
import com.fwg.asservice.sql.CallableStatementUtil;
import com.fwg.asservice.utility.GlobalFunction;

@Repository
public class HomeMemberDaoImpl implements HomeMemberDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<HomeMembers> listHomeMemberByHomeID(Integer homeId, boolean isManage) throws Exception {
		
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_homemember_getHomeMember_ByHomeID(:homeId, :isManage)}");
			
			query.setParameter("homeId", homeId);
			query.setParameter("isManage", isManage);
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
	public HomeMembers getHomeMemberByPersonId(String personId) throws Exception {
Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_homemember_getHomeMember_ByPersonId(:personId)}");
			
			query.setParameter("personId", personId);
			query.addEntity(HomeMembers.class);
			
			List<HomeMembers> result = query.list();
			if(result!=null && result.size()>0) {
				return result.get(0);
			}
			return null;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public HomeMembers deleteHomeMembers(String personId, int homeId) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		try {

			String query = "call sp_homemember_homeMember_Del(:personId,:homeId)";
			callableStatementUtil = new CallableStatementUtil(session, query);

			callableStatementUtil.setString("personId", GlobalFunction.isEmpty(personId) ? null : personId);
			callableStatementUtil.setInt("homeId", homeId);

			callableStatementUtil.execute();

			return null;

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
