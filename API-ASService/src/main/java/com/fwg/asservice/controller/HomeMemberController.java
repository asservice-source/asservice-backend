package com.fwg.asservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fwg.asservice.constants.APIStatusMessage;
import com.fwg.asservice.constants.RequestMappingConstants;
import com.fwg.asservice.constants.RestURIConstants;
import com.fwg.asservice.model.APIResponse;
import com.fwg.asservice.model.HomeMembers;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.service.HomeMemberService;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
@RequestMapping(RequestMappingConstants.HOME_MEMBER)
public class HomeMemberController {
	
	@Autowired
	private HomeMemberService homeMemberService;
	
	@RequestMapping(value = RestURIConstants.GET_HOME_MEMBER_BY_HOME, method = RequestMethod.POST) 
	public @ResponseBody APIResponse getHomeMemberByHome(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		List<HomeMembers> resultList = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting HomeMembers By HomeID data.";
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		try {
			resultList = homeMemberService.listHomeMemberByHomeID(filter.getHomeId(), filter.isManage());
			
			for (HomeMembers homeMembers : resultList) {
				jsonObject = new JSONObject();
				
				jsonObject.put("personId", homeMembers.getPerson().getPersonId());
				jsonObject.put("citizenId", homeMembers.getPerson().getCitizenId());
				jsonObject.put("osmId", homeMembers.getHome().getOsmId());
				jsonObject.put("prefixCode", homeMembers.getPerson().getPrefix().getCode());
				jsonObject.put("prefixName", homeMembers.getPerson().getPrefix().getName());
				jsonObject.put("prefixShortName", homeMembers.getPerson().getPrefix().getShortName());
				jsonObject.put("firstName", homeMembers.getPerson().getFirstName());
				jsonObject.put("lastName", homeMembers.getPerson().getLastName());
				jsonObject.put("nickName", homeMembers.getPerson().getNickName());
				jsonObject.put("genderId", homeMembers.getPerson().getGender().getId());
				jsonObject.put("genderName", homeMembers.getPerson().getGender().getName());
				jsonObject.put("raceCode", homeMembers.getPerson().getRace().getCode());
				jsonObject.put("raceName", homeMembers.getPerson().getRace().getName());
				jsonObject.put("nationalityCode", homeMembers.getPerson().getNationality().getCode());
				jsonObject.put("nationalityName", homeMembers.getPerson().getNationality().getName());
				jsonObject.put("religionCode", homeMembers.getPerson().getReligion().getCode());
				jsonObject.put("religionName", homeMembers.getPerson().getReligion().getName());
				jsonObject.put("bloodTypeId", homeMembers.getPerson().getBloodType().getId());
				jsonObject.put("bloodTypeName", homeMembers.getPerson().getBloodType().getName());
				jsonObject.put("rhGroupId", homeMembers.getPerson().getRhGroup().getId());
				jsonObject.put("rhGroupName", homeMembers.getPerson().getRhGroup().getName());
				jsonObject.put("birthDate", homeMembers.getPerson().getBirthDate());
				jsonObject.put("age", GlobalFunction.getAgeFromBirthDate(homeMembers.getPerson().getBirthDate()));
				jsonObject.put("educationCode", homeMembers.getPerson().getEducation().getCode());
				jsonObject.put("educationName", homeMembers.getPerson().getEducation().getName());
				jsonObject.put("occupationCode", homeMembers.getPerson().getOccupation().getCode());
				jsonObject.put("occupationName", homeMembers.getPerson().getOccupation().getName());
				jsonObject.put("familyStatusId", homeMembers.getFamilyStatus().getId());
				jsonObject.put("familyStatusName", homeMembers.getFamilyStatus().getName());
				jsonObject.put("dischargeId", homeMembers.getDischarge().getId());
				jsonObject.put("dischargeName", homeMembers.getDischarge().getName());
				jsonObject.put("isGuest", homeMembers.isGuest());
				jsonObject.put("address", GlobalFunction.generateAddress(homeMembers.getPerson().getAddress()));
				jsonObject.put("mooNo", homeMembers.getPerson().getAddress().getMooNo());
				jsonObject.put("road", homeMembers.getPerson().getAddress().getRoad());
				jsonObject.put("homeNo", homeMembers.getPerson().getAddress().getHomeNo());
				jsonObject.put("provinceCode", homeMembers.getPerson().getAddress().getProvinceCode());
				jsonObject.put("amphurCode", homeMembers.getPerson().getAddress().getAmphurCode());
				jsonObject.put("tumbolCode", homeMembers.getPerson().getAddress().getTumbolCode());
				jsonObject.put("fullName", GlobalFunction.generateFullName(homeMembers.getPerson()));
				jsonObject.put("homeId", homeMembers.getHomeId());
				jsonObject.put("vStatusCode", homeMembers.getPerson().getvStatusCode());
				jsonObject.put("vStatusName", homeMembers.getPerson().getVillageStatus().getName());
				jsonObject.put("mStatusCode", homeMembers.getPerson().getmStatusCode());
				jsonObject.put("mStatusName", homeMembers.getPerson().getMaritalStatus().getName());
				jsonObject.put("laborCode", homeMembers.getPerson().getLaborCode());
				jsonObject.put("laborName", homeMembers.getPerson().getLabor().getName());
				jsonObject.put("passport", homeMembers.getPerson().getPassport());
				jsonObject.put("isDead", homeMembers.getPerson().isDead());
				jsonObject.put("deadDate", homeMembers.getPerson().getDeadDate());
				jsonObject.put("picturePath", homeMembers.getPerson().getPicturePath());
				
				jsonArray.add(jsonObject);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(jsonArray);
		
		return resp;
	}
	
	
	
	@RequestMapping(value = RestURIConstants.GET_HOME_MEMBER_BY_CITIZENID, method = RequestMethod.POST)
	public @ResponseBody APIResponse getHomeMemberByCitizenID(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final HomeMembers memberByHomeID = mapper.convertValue(requestParams, HomeMembers.class);
		
		List<HomeMembers> resultList = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting HomeMembers By CitizenID data.";
		
		JSONObject jsonObject = new JSONObject();
		
		try {
			HomeMembers homeMembers = null;
			
				jsonObject.put("CitizenId", homeMembers.getPerson().getCitizenId());
				jsonObject.put("PrefixCode", homeMembers.getPerson().getPrefix().getCode());
				jsonObject.put("PrefixName", homeMembers.getPerson().getPrefix().getName());
				jsonObject.put("PrefixShortName", homeMembers.getPerson().getPrefix().getShortName());
				jsonObject.put("FirstName", homeMembers.getPerson().getFirstName());
				jsonObject.put("LastName", homeMembers.getPerson().getLastName());
				jsonObject.put("GenderId", homeMembers.getPerson().getGender().getId());
				jsonObject.put("GenderName", homeMembers.getPerson().getGender().getName());
				jsonObject.put("RaceCode", homeMembers.getPerson().getRace().getCode());
				jsonObject.put("RaceName", homeMembers.getPerson().getRace().getName());
				jsonObject.put("NationalityCode", homeMembers.getPerson().getNationality().getCode());
				jsonObject.put("NationalityName", homeMembers.getPerson().getNationality().getName());
				jsonObject.put("ReligionCode", homeMembers.getPerson().getReligion().getCode());
				jsonObject.put("ReligionName", homeMembers.getPerson().getReligion().getName());
				jsonObject.put("BloodTypeId", homeMembers.getPerson().getBloodType().getId());
				jsonObject.put("BloodTypeName", homeMembers.getPerson().getBloodType().getName());
				jsonObject.put("RhGroupId", homeMembers.getPerson().getRhGroup().getId());
				jsonObject.put("RhGroupName", homeMembers.getPerson().getRhGroup().getName());
				jsonObject.put("BirthDate", homeMembers.getPerson().getBirthDate());
				jsonObject.put("EducationCode", homeMembers.getPerson().getEducation().getCode());
				jsonObject.put("EducationName", homeMembers.getPerson().getEducation().getName());
				jsonObject.put("OccupationCode", homeMembers.getPerson().getOccupation().getCode());
				jsonObject.put("OccupationName", homeMembers.getPerson().getOccupation().getName());
				jsonObject.put("picturePath", homeMembers.getPerson().getPicturePath());
			
		} catch (Exception ex) {
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(jsonObject);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.DEL_HOME_MEMBER, method = RequestMethod.POST)
	public @ResponseBody APIResponse deleteHomeMembers(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final HomeMembers homeMembers = mapper.convertValue(requestParams, HomeMembers.class);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to delete HomeMembers.";
		
		try {
			
			homeMemberService.deleteHomeMembers(homeMembers.getPersonId(), homeMembers.getHomeId());
			
		} catch (Exception ex) {
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(message);
		
		return resp;
	}
}
