package com.fwg.asservice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fwg.asservice.config.ShaHashConfig;
import com.fwg.asservice.dao.UserDao;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.UserLogin;
import com.fwg.asservice.model.UserMapping;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.sql.CallableStatementUtil;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONObject;

@Repository
public class UserDaoImpl implements UserDao { 
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public String userInsOrUpd(Person person,Filter filter ,Integer userRoleID) throws Exception {
		
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		
		try{
		
			String query = "CALL sp_User_UserInsOrUpd"
					+ "("
					+ ":DeleteId"
					+ ",:PersonId"
					+ ",:CitizenID"
					+ ",:GenderID"
					+ ",:PrefixCode"
					+ ",:FirstName"
					+ ",:LastName"
					+ ",:BirthDate"
					+ ",:VillageId"
					+ ",:Code5"
					+ ",:UserRoleID"
					+ ",:IsActive"
					+ ",:Password"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session,query);
			//set return Parameter
			callableStatementUtil.registerOutParameter("PersonId", Types.NVARCHAR);
			
			callableStatementUtil.setString("DeleteId", filter.getDeleteId());
			callableStatementUtil.setString("PersonId",GlobalFunction.isEmpty( person.getPersonId())?null: person.getPersonId());
			callableStatementUtil.setString("CitizenID", person.getCitizenId());
			callableStatementUtil.setInt("GenderID", person.getGenderId());
			callableStatementUtil.setString("PrefixCode", GlobalFunction.isEmpty(person.getPrefixCode())?null:person.getPrefixCode());
			callableStatementUtil.setString("FirstName", person.getFirstName());
			callableStatementUtil.setString("LastName", person.getLastName());
			callableStatementUtil.setDate("BirthDate",person.getBirthDate());
			callableStatementUtil.setInt("VillageId", filter.getVillageId());
			callableStatementUtil.setString("Code5",filter.getCode5());
			callableStatementUtil.setInt("UserRoleID",userRoleID);
			callableStatementUtil.setBoolean("IsActive",filter.isActive());
			callableStatementUtil.setString("Password",GlobalFunction.generatePassword(person.getCitizenId(), person.getBirthDate()));
			
			Log.error(callableStatementUtil);
			
			callableStatementUtil.execute();

			return callableStatementUtil.getString("PersonId");
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (callableStatementUtil != null) {
				callableStatementUtil.close();
			}
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<UserMapping> userLists(Filter filter) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			SQLQuery query = session.createSQLQuery("{CALL sp_User_UserList(:userId,:osm,:Name,:Code5,:VillageID)}");
			
			query.addEntity(UserMapping.class);
			
			query.setParameter("userId", GlobalFunction.isEmpty(filter.getUserId())?null:filter.getUserId());
			query.setParameter("osm", GlobalFunction.isEmpty(filter.getOsmId())?null:filter.getOsmId());
			query.setParameter("Name", filter.getName());
			query.setParameter("Code5", GlobalFunction.isEmpty(filter.getCode5())?null:filter.getCode5());
			query.setParameter("VillageID",filter.getVillageId());
			
			Log.error(query);
			
			List<UserMapping> list = query.list();
			
			return list;
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<UserMapping> userLogin(String userName, String password) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			SQLQuery query = session.createSQLQuery("{CALL sp_User_UserLogin(:userName,:password)}");

			query.addEntity(UserMapping.class);
			
			query.setParameter("userName", userName);
			query.setParameter("password", GlobalFunction.generatePassword(userName, password));
			
			Log.error(query);
			
			List<UserMapping> list = query.list();
			
			return list;
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public UserMapping userRoleAndHospitalCode5(String personId) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		ResultSet rs = null;
		UserMapping userMapping = new UserMapping();
		try{
		
			String query = "CALL sp_User_UserRoleAndHospitalCode_ByPersonID"
					+ "("
					+ ":PersonId"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session,query);
			
			callableStatementUtil.setString("PersonId",GlobalFunction.isEmpty(personId) ? null : personId);
			
			Log.error(callableStatementUtil);
			
			rs = callableStatementUtil.executeQuery();
			if(rs.next()) {
				userMapping.setHospitalCode(rs.getString("HospitalCode"));
				userMapping.setUserRoleID(rs.getInt("UserRoleID"));
				userMapping.setVillageId(rs.getInt("VillageID"));
			}
			return userMapping;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
			if(rs != null){
				rs.close();
			}
			if(callableStatementUtil != null){
				callableStatementUtil.close();
			}
		}
	}

	@Override
	public UserLogin changePassword(String userLoginId, String oldPassword, String newPassword) throws Exception {
		
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		ResultSet rs = null;
		UserLogin userLogin = new UserLogin();
		try{
		
			String query = "CALL sp_User_ChangePassword"
					+ "("
					+ ":userLoginId"
					+ ",:oldPassword"
					+ ",:newPassword"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session,query);
			
			callableStatementUtil.setString("userLoginId",GlobalFunction.isEmpty(userLoginId) ? null : userLoginId);
			callableStatementUtil.setString("oldPassword",oldPassword);
			callableStatementUtil.setString("newPassword",newPassword);
			
			
			callableStatementUtil.execute();
			
			return userLogin;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
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
	public boolean editProfile(Person person) throws Exception {
		
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;

		try{
		
			String query = "CALL sp_User_Edit_Profile"
					+ "("
					+ ":personId"
					+ ",:firstName"
					+ ",:lastName"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session,query);
			
			callableStatementUtil.setString("personId",GlobalFunction.isEmpty(person.getPersonId()) ? null : person.getPersonId());
			callableStatementUtil.setString("firstName",person.getFirstName());
			callableStatementUtil.setString("lastName",person.getLastName());
			
			
			callableStatementUtil.execute();
			
			return true;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
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
	public JSONObject forgotPasswordVerified(Filter filter) throws Exception {
		
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		ResultSet rs = null;
		JSONObject object = new JSONObject();
		try{
		
			String query = "CALL sp_User_ForgotPassword_Verified"
					+ "("
					+ ":Code5"
					+ ",:CitizenID"
					+ ",:FirstName"
					+ ",:LastName"
					+ ",:BirthDate"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session,query);
			
			callableStatementUtil.setString("Code5",filter.getCode5());
			callableStatementUtil.setString("CitizenID",filter.getCitizenId());
			callableStatementUtil.setString("FirstName",filter.getFirstName());
			callableStatementUtil.setString("LastName",filter.getLastName());
			callableStatementUtil.setDate("BirthDate",filter.getBirthDate());
			
			Log.error(callableStatementUtil);
			
			rs = callableStatementUtil.executeQuery();
			if(rs.next()) {
				object.put("isVerified", rs.getBoolean("IsVerified"));
				object.put("userName", rs.getString("UserName"));
				object.put("personId", rs.getString("PersonID"));
				object.put("citizenId", rs.getString("CitizenID"));
				object.put("userLoginId", rs.getString("ID"));
			}
			return object;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
			if(rs != null){
				rs.close();
			}
			if(callableStatementUtil != null){
				callableStatementUtil.close();
			}
		}
	}

	@Override
	public JSONObject resetPassword(Filter filter) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		ResultSet rs = null;
		JSONObject object = new JSONObject();
		try{
		
			String query = "CALL sp_User_ResetPassword"
					+ "("
					+ ":userLoginId"
					+ ",:newPassword"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session,query);
			
			callableStatementUtil.setString("userLoginId",filter.getUserLoginId());
			callableStatementUtil.setString("newPassword",new ShaHashConfig().hashSHA1(filter.getUserName()+filter.getPassword()));
			
			Log.error(callableStatementUtil);
			
			callableStatementUtil.executeQuery();
			object.put("status", "success");
			return object;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
			if(rs != null){
				rs.close();
			}
			if(callableStatementUtil != null){
				callableStatementUtil.close();
			}
		}
	}

	@Override
	public com.fwg.asservice.model.Session insertSession(com.fwg.asservice.model.Session sessionItems) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		ResultSet rs = null;
		com.fwg.asservice.model.Session sessions = new com.fwg.asservice.model.Session();
		
		try{
		
			String query = "CALL sp_Session_Ins"
					+ "("
					+ ":SID"
					+ ",:UserID"
					+ ",:LoginDate"
					+ ",:LastActive"
					+ ",:IsExpire"
					+ ",:IPAddress"
					+ ",:UserAgent"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session,query);
			
			//set return Parameter
			callableStatementUtil.registerOutParameter("SID", Types.NVARCHAR);
			
			callableStatementUtil.setString("SID",GlobalFunction.isEmpty(sessionItems.getSid()) ? null : sessionItems.getSid());
			callableStatementUtil.setString("UserID",GlobalFunction.isEmpty(sessionItems.getUserId()) ? null : sessionItems.getUserId());
			callableStatementUtil.setDate("LoginDate",sessionItems.getLoginDate());
			callableStatementUtil.setDate("LastActive",sessionItems.getLastActive());
			callableStatementUtil.setBoolean("IsExpire",sessionItems.isExpire());
			callableStatementUtil.setString("IPAddress",sessionItems.getIpAddress());
			callableStatementUtil.setString("UserAgent",sessionItems.getUserAgent());
			
			Log.error(callableStatementUtil);
			
			callableStatementUtil.execute();
			
			sessions.setSid(callableStatementUtil.getString("SID"));
			
			return sessions;
			
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
			if(rs != null){
				rs.close();
			}
			if(callableStatementUtil != null){
				callableStatementUtil.close();
			}
		}
	}

	@Override
	public boolean verifySession(com.fwg.asservice.model.Session sessionItem) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		ResultSet rs = null;
		boolean isVerify = false;
		
		try{
		
			String query = "CALL sp_Session_Verify"
					+ "("
					+ ":SID"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session,query);
			
			//set return Parameter
			//callableStatementUtil.registerOutParameter("SID", Types.NVARCHAR);
			
			callableStatementUtil.setString("SID",GlobalFunction.isEmpty(sessionItem.getSid()) ? null : sessionItem.getSid());
		
			Log.error(callableStatementUtil);
			
			callableStatementUtil.execute();
			isVerify = true;
			
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
			if(rs != null){
				rs.close();
			}
			if(callableStatementUtil != null){
				callableStatementUtil.close();
			}
		}
		
		return isVerify;
	}

	@Override
	public JSONObject forgotPasswordVerifiedHospital(Filter filter) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		ResultSet rs = null;
		JSONObject object = new JSONObject();
		try{
		
			String query = "CALL sp_User_ForgotPassword_Verified_Hospital"
					+ "("
					+ ":hospitalName"
					+ ",:provinceCode"
					+ ",:amphurCode"
					+ ",:tumbolCode"
					+ ",:code9"
					+ ",:code5"
					+ ",:citizenId"
					+ ")";
			
			callableStatementUtil = new CallableStatementUtil(session,query);
			
			callableStatementUtil.setString("hospitalName",filter.getHospitalName());
			callableStatementUtil.setString("provinceCode",filter.getProvinceCode());
			callableStatementUtil.setString("amphurCode",filter.getAmphurCode());
			callableStatementUtil.setString("tumbolCode",filter.getTumbolCode());
			callableStatementUtil.setString("code9",filter.getCode9());
			callableStatementUtil.setString("code5",filter.getCode5());
			callableStatementUtil.setString("citizenId",filter.getCitizenId());
			
			Log.error(callableStatementUtil);
			
			rs = callableStatementUtil.executeQuery();
			if(rs.next()) {
				object.put("isVerified", rs.getBoolean("IsVerified"));
				object.put("userLoginId", rs.getString("ID"));
				object.put("userName", rs.getString("UserName"));
			}
			return object;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
			if(rs != null){
				rs.close();
			}
			if(callableStatementUtil != null){
				callableStatementUtil.close();
			}
		}
	}
}