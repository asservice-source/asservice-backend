package com.fwg.asservice.service;

import java.util.List;

import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.Session;
import com.fwg.asservice.model.UserLogin;
import com.fwg.asservice.model.UserMapping;
import com.fwg.asservice.model.filter.Filter;

import net.minidev.json.JSONObject;

public interface UserService {
	public String userInsOrUpd(Person person,Filter filter,Integer userRoleID) throws Exception;
	public List<UserMapping> userLists(Filter filter) throws Exception;
	public List<UserMapping> userLogin(String userName ,String password) throws Exception;
	public UserMapping userRoleAndHospitalCode5(String personId) throws Exception;
	public UserLogin changePassword(String userLoginId, String oldPassword, String newPassword) throws Exception;
	public boolean editProfile(Person person) throws Exception;
	public JSONObject forgotPasswordVerified(Filter filter) throws Exception;
	public JSONObject forgotPasswordVerifiedHospital(Filter filter) throws Exception;
	public JSONObject resetPassword(Filter filter) throws Exception;
	public com.fwg.asservice.model.Session insertSession(Session session) throws Exception;
	public boolean verifySession(Session session) throws Exception;
}