package com.fwg.asservice.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fwg.asservice.dao.MenuDao;
import com.fwg.asservice.model.config.Menu;

@Repository
public class MenuDaoImpl implements MenuDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void saveMenu(Menu menu) {
		getSession().merge(menu);

	}
	
	public List<Menu> listMenus(Integer userRoleId) throws Exception { 
		Session session = sessionFactory.openSession();
		
		try{
			SQLQuery query = session.createSQLQuery("{call `config.sp_app_getMenu`(:userRoleId)}");
			query.addEntity(Menu.class);
			query.setParameter("userRoleId", userRoleId);
			
			List<Menu> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	public Menu getMenu(int id) {
		return (Menu) getSession().get(Menu.class, id);
	}
	
	public void deleteMenu(int id) {

		Menu brand = getMenu(id);

		if (null != brand) {
			getSession().delete(brand);
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

}
