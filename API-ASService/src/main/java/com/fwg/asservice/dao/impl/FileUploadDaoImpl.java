package com.fwg.asservice.dao.impl;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fwg.asservice.dao.FileUploadDao;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.Province;
import com.fwg.asservice.sql.CallableStatementUtil;
import com.fwg.asservice.utility.GlobalFunction;

@Repository
public class FileUploadDaoImpl implements FileUploadDao {

	@Autowired
	private SessionFactory sessionFactory;

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
	public Person uploadProfile(Person person) throws Exception {
		
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		try {

			String query = "call sp_Person_Upload_Profile(:personId,:picturePath)";
			callableStatementUtil = new CallableStatementUtil(session, query);

			callableStatementUtil.setString("personId", GlobalFunction.isEmpty(person.getPersonId()) ? null : person.getPersonId());
			callableStatementUtil.setString("picturePath", person.getPicturePath().replace(GlobalFunction.getBaseUrl(), ""));

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
