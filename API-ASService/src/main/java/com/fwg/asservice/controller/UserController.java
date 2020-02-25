package com.fwg.asservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fwg.asservice.config.ShaHashConfig;
import com.fwg.asservice.constants.APIStatusMessage;
import com.fwg.asservice.constants.RequestMappingConstants;
import com.fwg.asservice.constants.RestURIConstants;
import com.fwg.asservice.constants.UserRoleConstants;
import com.fwg.asservice.model.APIResponse;
import com.fwg.asservice.model.Home;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.Session;
import com.fwg.asservice.model.UserLogin;
import com.fwg.asservice.model.UserMapping;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.service.HomeService;
import com.fwg.asservice.service.PersonService;
import com.fwg.asservice.service.UserService;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
@RequestMapping(RequestMappingConstants.USER)
public class UserController {
	
	private final String PARAM_USERROLE_ID = "userRoleId";
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private PersonService personService;
	
	@Autowired
	private HomeService homeService;
	
	@PostMapping(RestURIConstants.USER_LOGIN)
	public @ResponseBody APIResponse userLogin(HttpServletRequest request, @RequestBody ObjectNode requestParams) {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final UserLogin userLogin = mapper.convertValue(requestParams, UserLogin.class);
		
		Session session = new Session();
		
        String clientIP = request.getRemoteHost();
        int clientPort = request.getRemotePort();
        String uri = request.getRequestURI();
        String sid = request.getHeader("sid");
        String userAgent = request.getHeader("User-Agent");

        /*System.out.println("clientIP : "+clientIP);
        System.out.println("clientPort : "+clientPort);
        System.out.println("uri : "+uri);
        System.out.println("sid : "+sid);
        System.out.println("userAgent : "+userAgent);*/
        
		APIResponse resp = new APIResponse();
		JSONObject obj = new JSONObject();
		List<UserMapping> userMappings = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success Login...";
		
		try {
			userMappings = userService.userLogin(userLogin.getUserName(), userLogin.getPassword());
			
			if(userMappings != null && userMappings.size()>0) {
				
				session.setSid(sid);
				session.setUserId(userMappings.get(0).getUserLogin().getId());
				session.setIpAddress(clientIP);
				session.setUserAgent(userAgent);
				
				session = userService.insertSession(session);
				
				obj = setData(userMappings.get(0),session);
				obj.put("login", true);
				
				
			}else {
				status = APIStatusMessage.FAILED;
				message ="Failed Login...";
				obj.put("login", false);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
			obj.put("login", false);
		}
		
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(obj);
		return resp;
	}
	@PostMapping(RestURIConstants.USER_INFO)
	public @ResponseBody APIResponse info(@RequestBody ObjectNode requestParams) {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		APIResponse resp = new APIResponse();
		String status = APIStatusMessage.SUCCESS;
		String message ="Success Find Users";
		
		JSONObject obj = new JSONObject();
		List<UserMapping> userMappings = null;
		try {
			userMappings = userService.userLists(filter);
			if(userMappings!=null && userMappings.size()>0) {
				List<Home> homeList = null;

				obj = setData(userMappings.get(0), null);
				
				homeList = homeService.listHomeByVillageOrOSM(null, userMappings.get(0).getPersonID(), null,false);
				if(homeList!=null && homeList.size()>0) {
					obj.put("activateHome", true);
				}
			}
		} catch(Exception ex){
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}
		
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(obj);
		return resp;
	}
	@RequestMapping(value= RestURIConstants.USER_FIND,method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody APIResponse find(@RequestBody ObjectNode requestParams,@PathVariable(RestURIConstants.USER_PARAM_ROLESCOPE)String roleScope) {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		APIResponse resp = new APIResponse();
		String status = APIStatusMessage.SUCCESS;
		String message ="Success Find Users";
		
		JSONArray array = new JSONArray();
		JSONObject obj = null;
		List<UserMapping> userMappings = null;
		try{
			userMappings = userService.userLists(filter);
			if(userMappings != null && userMappings.size()>0) {
				for (UserMapping userMapping : userMappings) {
					obj = new JSONObject();
					if(RestURIConstants.USER_VALUE_ROLESCOPE_OSM.equalsIgnoreCase(roleScope)) {
						if(userMapping.getUserRoleID()==UserRoleConstants.ROLE_ID_HEADVOLUNTEER || userMapping.getUserRoleID()==UserRoleConstants.ROLE_ID_VOLUNTEER) {
							obj = setData(userMapping, null);
							array.add(obj);
						}
					}else if(RestURIConstants.USER_VALUE_ROLESCOPE_STAFF.equalsIgnoreCase(roleScope)) {
						if(userMapping.getUserRoleID()==UserRoleConstants.ROLE_ID_STAFF) {
							obj = setData(userMapping, null);
							
							array.add(obj);
						}
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}
		
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(array);
		
		return resp;
	}

	@PostMapping(RestURIConstants.USER_INSERT_UPDATE)
	public @ResponseBody APIResponse userInsOrUpdate(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Person person = mapper.convertValue(requestParams, Person.class);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		APIResponse resp = new APIResponse();
		
		/*check citizenId dupl*/
		List<Person> _ps = null;
		try {
			_ps = personService.listPersonByCitizenId(person.getCitizenId());
			if(GlobalFunction.isEmpty(person.getPersonId())) {
				if(_ps != null && _ps.size()>0) {
					person.setPersonId(_ps.get(0).getPersonId());
				}
			}
			
			if(!GlobalFunction.isEmpty(person.getPersonId())) {
				if(!GlobalFunction.isEmpty(_ps) && _ps.size()>0) {
					if(!_ps.get(0).getPersonId().equals(person.getPersonId())){
						resp.setStatus(APIStatusMessage.FAILED);
						resp.setMessage("DuplicateCitizenId");
						resp.setParam(requestParams);
						resp.setDate(GlobalFunction.currentTimeStamp());
						resp.setResponse(new JSONObject());
						return resp;
					}
				}
			}
			
		} catch (Exception e) {
			resp.setStatus(APIStatusMessage.FAILED);
			resp.setMessage(GlobalFunction.getExceptionMessage(e));
			resp.setParam(requestParams);
			resp.setDate(GlobalFunction.currentTimeStamp());
			resp.setResponse(new JSONObject());
			return resp;
		}
		
		Integer userRoleID = null;
		try {
			if(GlobalFunction.isEmpty(filter.getDeleteId())) {//DeleteId not null Do Insert or Update Else Delete.
				userRoleID = Integer.parseInt(requestParams.get(PARAM_USERROLE_ID).textValue());
			}
		} catch (Exception e) {
			resp.setStatus(APIStatusMessage.FAILED);
			resp.setMessage(GlobalFunction.getExceptionMessage(e));
			resp.setParam(requestParams);
			resp.setDate(GlobalFunction.currentTimeStamp());
			resp.setResponse(new JSONObject());
			return resp;
		}
		//check user has duplicate on other hospital 
		try {
			if(!GlobalFunction.isEmpty(person.getPersonId()) && filter.getDeleteId() == null) {
				
				boolean validate = true;
				List<UserMapping> userList = null;
				Filter ft = new Filter();
				
				ft.setOsmId(person.getPersonId());
				
				userList = userService.userLists(ft);
				if(userList!=null && userList.size()>0) {
					UserMapping um = userList.get(0);

					if(!um.getHospitalCode().equals(filter.getCode5()) 
							&& UserRoleConstants.ROLE_ID_SUPERVISOR != um.getUserRoleID() ) 
					{
						resp.setStatus(APIStatusMessage.FAILED);
						resp.setMessage("UserHasDuplicateOnOtherHospital");
						validate = false;
					}else if(UserRoleConstants.ROLE_ID_STAFF == um.getUserRoleID() 
							&& UserRoleConstants.ROLE_ID_STAFF != userRoleID) //check user charge role to OSM
					{
						resp.setStatus(APIStatusMessage.FAILED);
						resp.setMessage("UserCanNotChargeToOSMRole");
						validate = false;
					}
					
				}
				if(!validate) {
					resp.setParam(requestParams);
					resp.setDate(GlobalFunction.currentTimeStamp());
					resp.setResponse(new JSONObject());
					return resp;
				}
			}
		} catch (Exception e) {
			resp.setStatus(APIStatusMessage.FAILED);
			resp.setMessage(GlobalFunction.getExceptionMessage(e));
			resp.setParam(requestParams);
			resp.setDate(GlobalFunction.currentTimeStamp());
			resp.setResponse(new JSONObject());
			return resp;
		}
		
		//insert or update user 
		String status = APIStatusMessage.SUCCESS;
		String message ="Success Insert Or Update User";
		if (!GlobalFunction.isEmpty(filter.getDeleteId())) {
			message ="Success Delete User";
		}
		
		JSONObject object = null;

		String personId = null;
		try{
			object = new JSONObject();
			
			personId = userService.userInsOrUpd(person, filter, userRoleID);
			
			object.put("personId", personId);
			
		}catch(Exception ex){
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}
		
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(object);
		
		return resp;
	}
	
	@RequestMapping(value= RestURIConstants.CHANGE_PASSWORD,method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody APIResponse changePassword(@RequestBody ObjectNode requestParams) {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String id = requestParams.get("id").textValue();
		String username = requestParams.get("username").textValue();
		String oldPassword = requestParams.get("oldPassword").textValue();
		String newPassword = requestParams.get("newPassword").textValue();
		
		APIResponse resp = new APIResponse();
		String status = APIStatusMessage.SUCCESS;
		String message ="Success Change password";
		
		Object resObj = new Object();
		System.out.println("HASHHHH OLD: "+new ShaHashConfig().hashSHA1(username+oldPassword));
		System.out.println("HASHHHH NEW: "+new ShaHashConfig().hashSHA1(username+newPassword));
		
		try{
			
			userService.changePassword(id, new ShaHashConfig().hashSHA1(username+oldPassword) , new ShaHashConfig().hashSHA1(username+newPassword));
			resObj = message;
			
		}catch(Exception exception){
			exception.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(exception);
			resObj = GlobalFunction.getErrorCode(exception);
		}
		
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(resObj);
		
		return resp;
	}
	
	@RequestMapping(value= RestURIConstants.EDIT_PROFILE,method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody APIResponse editProfile(@RequestBody ObjectNode requestParams) {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Person person = mapper.convertValue(requestParams, Person.class);
		
		APIResponse resp = new APIResponse();
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to edit profile.";
		
		Object resObj = new Object();
		
		try{
			
			userService.editProfile(person);
			resObj = message;
			
		}catch(Exception exception){
			exception.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(exception);
			resObj = GlobalFunction.getErrorCode(exception);
		}
		
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(resObj);
		
		return resp;
	}
	
	@RequestMapping(value= RestURIConstants.FORGOT_PASSWORD_VERIFIED,method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody APIResponse forgotPasswordVerified(@RequestBody ObjectNode requestParams) {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		APIResponse resp = new APIResponse();
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to verified personal data.";
		
		JSONObject resObj = new JSONObject();
		
		try{
			
			resObj = userService.forgotPasswordVerified(filter);
			
		}catch(Exception exception){
			exception.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(exception);
		}
		
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(resObj);
		
		return resp;
	}
	
	@RequestMapping(value= RestURIConstants.FORGOT_PASSWORD_VERIFIED_HOSPITAL,method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody APIResponse forgotPasswordVerifiedHospital(@RequestBody ObjectNode requestParams) {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		APIResponse resp = new APIResponse();
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to verified hospital data.";
		
		JSONObject resObj = new JSONObject();
		
		try{
			
			resObj = userService.forgotPasswordVerifiedHospital(filter);
			
		}catch(Exception exception){
			exception.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(exception);
		}
		
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(resObj);
		
		return resp;
	}
	
	@RequestMapping(value= RestURIConstants.RESET_PASSWORD,method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody APIResponse resetPassword(@RequestBody ObjectNode requestParams) {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		APIResponse resp = new APIResponse();
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to reset password.";
		
		JSONObject resObj = new JSONObject();
		
		try{
			
			resObj = userService.resetPassword(filter);
			
		}catch(Exception exception){
			exception.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(exception);
		}
		
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(resObj);
		
		return resp;
	}
	
	
	private JSONObject setData(UserMapping userMapping, Session session) throws Exception {
		
		JSONObject object = new JSONObject();
		
		if(userMapping.getUserLogin().getResponsibility()!=null) {
			object.put("villageId", userMapping.getUserLogin().getResponsibility().getVillage().getId());
			object.put("villageNo", userMapping.getUserLogin().getResponsibility().getVillage().getVillageNo());
			object.put("villageName", userMapping.getUserLogin().getResponsibility().getVillage().getVillageName());
		}
		
		Home home = homeService.getHomeByPersonId(userMapping.getPersonID());
		
		object.put("homeId", home!=null?home.getId():null);
		object.put("roleId", userMapping.getUserRoles().getId());
		object.put("roleName", userMapping.getUserRoles().getCode());
		object.put("roleAcronym", userMapping.getUserRoles().getAcronym());
		
		if(session != null){
			object.put("sid", session.getSid());
		}
		
		if(userMapping.getUserLogin()!=null) {
			object.put("userId", userMapping.getUserLogin().getId());
			object.put("userName", userMapping.getUserLogin().getUserName());
			object.put("isActive", userMapping.getUserLogin().isActive());
		}
		if(userMapping.getHospital()!=null) {
			object.put("code5", userMapping.getHospital().getCode5());
			object.put("code9", userMapping.getHospital().getCode9());
			object.put("hospitalName", userMapping.getHospital().getName());
			object.put("hospitalTumbolCode", userMapping.getHospital().getTumbolCode());
			object.put("hospitalAmphurCode", userMapping.getHospital().getAmphurCode());
			object.put("hospitalProvinceCode", userMapping.getHospital().getProvinceCode());
			object.put("hospitalZipCode", userMapping.getHospital().getZipCode());
			if(userMapping.getHospital().getTumbol()!=null)
				object.put("hospitalTumbolName", userMapping.getHospital().getTumbol().getName());
			if(userMapping.getHospital().getAmphur()!=null)
				object.put("hospitalAmphurName", userMapping.getHospital().getAmphur().getName());
			if(userMapping.getHospital().getProvince()!=null)
				object.put("hospitalProvinceName", userMapping.getHospital().getProvince().getName());
			
	
			object.put("hospitalDisplayName",userMapping.getHospital().getName() 
					+GlobalFunction.addressControl(null,null,null,null
					, userMapping.getHospital().getTumbol()
							, userMapping.getHospital().getAmphur()
									, userMapping.getHospital().getProvince(), null));
		}
		return GlobalFunction.mergeJSONObject(object, GlobalFunction.generateProfile(userMapping.getPerson()));
	}
	
	@PostMapping("/password")
	public @ResponseBody APIResponse userPassword(HttpServletRequest request, @RequestBody ObjectNode requestParams) {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		System.out.println("filter : "+filter.getCitizenId());
		System.out.println("filter : "+filter.getBirthDate());

        
		APIResponse resp = new APIResponse();
		
		resp.setStatus("success");
		resp.setMessage("hashing password");
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(GlobalFunction.generatePassword(filter.getCitizenId(), filter.getBirthDate()));
		return resp;
	}
}