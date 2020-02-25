package com.fwg.asservice.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fwg.asservice.dao.AddressDao;
import com.fwg.asservice.model.Amphur;
import com.fwg.asservice.model.Province;
import com.fwg.asservice.model.Tumbol;

@Repository
public class AddressDaoImpl implements AddressDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void saveProvince(Province province) {
		getSession().merge(province);

	}

	
	public List<Province> listProvinces(String provinceCode) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
		
			SQLQuery query = session.createSQLQuery("{CALL sp_address_getProvince(:provinceCode)}");
			query.addEntity(Province.class);
			query.setParameter("provinceCode", provinceCode);
			
			List<Province> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	public List<Tumbol> listTumbols(String amphurCode) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
		
			SQLQuery query = session.createSQLQuery("{CALL sp_address_getTumbol(:amphurCode)}");
			query.addEntity(Tumbol.class);
			query.setParameter("amphurCode", amphurCode);
			
			List<Tumbol> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	public List<Amphur> listAmphurs(String provinceCode) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
		
			SQLQuery query = session.createSQLQuery("{CALL sp_address_getAmphur(:provinceCode)}");
			query.addEntity(Amphur.class);
			query.setParameter("provinceCode", provinceCode);
			
			List<Amphur> result = query.list();
			
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
	public void deleteProvince(int id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
