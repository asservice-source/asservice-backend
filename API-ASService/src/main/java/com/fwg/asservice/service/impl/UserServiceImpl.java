package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwg.asservice.dao.UserDao;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.Session;
import com.fwg.asservice.model.UserLogin;
import com.fwg.asservice.model.UserMapping;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.service.UserService;

import net.minidev.json.JSONObject;

@Service
public class UserServiceImpl implements UserService { 
	@Autowired
	private UserDao userDao;

	@Override
	public List<UserMapping> userLists(Filter filter) throws Exception {
		try{
			return userDao.userLists(filter);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public String userInsOrUpd(Person person, Filter filter, Integer userRoleID) throws Exception {
		try{
			return userDao.userInsOrUpd(person,filter, userRoleID);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<UserMapping> userLogin(String userName, String password) throws Exception {
		try{
			return userDao.userLogin(userName, password);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public UserMapping userRoleAndHospitalCode5(String personId) throws Exception {
		try{
			return userDao.userRoleAndHospitalCode5(personId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public UserLogin changePassword(String userLoginId, String oldPassword, String newPassword) throws Exception {
		try{
			return userDao.changePassword(userLoginId, oldPassword, newPassword);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public boolean editProfile(Person person) throws Exception {
		try{
			return userDao.editProfile(person);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public JSONObject forgotPasswordVerified(Filter filter) throws Exception {
		try{
			return userDao.forgotPasswordVerified(filter);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public JSONObject resetPassword(Filter filter) throws Exception {
		try{
			return userDao.resetPassword(filter);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public Session insertSession(Session session) throws Exception {
		try{
			return userDao.insertSession(session);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public boolean verifySession(Session session) throws Exception {
		try{
			return userDao.verifySession(session);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public JSONObject forgotPasswordVerifiedHospital(Filter filter) throws Exception {
		try{
			return userDao.forgotPasswordVerifiedHospital(filter);
		}catch(Exception e){
			throw e;
		}
	}
}