package com.fwg.asservice.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fwg.asservice.dao.ImageDao;
import com.fwg.asservice.model.config.Image;

@Repository
public class ImageDaoImpl implements ImageDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Image> listImages(Image image) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			SQLQuery query = session.createSQLQuery("CALL `config.sp_app_getImage`(:category)");
			query.addEntity(Image.class);
			query.setParameter("category", image.getCategory());
			
			List<Image> result = query.list();
			
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
