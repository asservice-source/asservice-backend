package com.fwg.asservice.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fwg.asservice.dao.OSMDao;
import com.fwg.asservice.model.Hospital;
import com.fwg.asservice.model.Person;

@Repository
public class OSMDaoImpl implements OSMDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Person> listOSMByVillageID(int villageId) throws Exception {
		
		Session session = sessionFactory.openSession();
		
		try{
		
			SQLQuery query = session.createSQLQuery("{CALL sp_oms_getOMS_ByVillageID(:villageId)}");
			query.addEntity(Person.class);
			query.setParameter("villageId", villageId);
			
			@SuppressWarnings("unchecked")
			List<Person> result = query.list();
			
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
