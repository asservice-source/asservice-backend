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
import com.fwg.asservice.model.BornLocation;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.DeathPlace;
import com.fwg.asservice.model.survey.HomeMember;
import com.fwg.asservice.model.survey.PopulationDetail;
import com.fwg.asservice.model.survey.PopulationDetailInfo;
import com.fwg.asservice.model.survey.SurveyHeader;
import com.fwg.asservice.service.SurveyService;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
@RequestMapping(RequestMappingConstants.SURVEY)
public class SurveyController {
	
	@Autowired
	private SurveyService surveyService;
	
	@RequestMapping(value = RestURIConstants.GET_SURVEY_HEADER_LIST, method = RequestMethod.POST)
	public @ResponseBody APIResponse getSurveyHeaderList(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final SurveyHeader surveyHeader = mapper.convertValue(requestParams, SurveyHeader.class);
		
		List<SurveyHeader> list = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting SurveyHeader data.";
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		try {
			list = surveyService.listSurveyHeaders(surveyHeader.getHeaderTypeCode(), surveyHeader.getHospitalCode());
			for (SurveyHeader value : list) {
				jsonObject = new JSONObject();
				jsonObject.put("rowGUID", value.getRowGUID());
				jsonObject.put("headerTypeCode", value.getHeaderTypeCode());
				jsonObject.put("hospitalCode", value.getHospitalCode());
				jsonObject.put("round", value.getRound());
				jsonObject.put("name", value.getName());
				jsonObject.put("description", value.getDescription());
				jsonObject.put("startDate", value.getStartDate());
				jsonObject.put("endDate", value.getEndDate());
				jsonObject.put("status", value.getStatus());
				jsonObject.put("statusName", value.getSurveyStatus().getName());
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
	
	@RequestMapping(value = RestURIConstants.GET_SURVEY_DEATH_PLACE_LIST, method = RequestMethod.POST)
	public @ResponseBody APIResponse getSurveyDeathPlaceList(@RequestBody ObjectNode requestParams) {
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting DeathPlace data.";
		
		JSONArray jsonArray = new JSONArray();
		
		try {
			List<DeathPlace> deathPlaceList = surveyService.listDeathPlace();
			for (DeathPlace deathPlace : deathPlaceList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("code", deathPlace.getCode());
				jsonObject.put("name", deathPlace.getName());
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
	
	@RequestMapping(value = RestURIConstants.GET_SURVEY_IS_DUPLICATE, method = RequestMethod.POST)
	public @ResponseBody APIResponse surveyIsDuplicate(@RequestBody ObjectNode requestParams) {
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to checking duplicate survey.";
		
		String headerTypeCode = requestParams.get("headerTypeCode").textValue();
		String documentId = requestParams.get("documentId").textValue();
		String personId = requestParams.get("personId").textValue();
		
		JSONObject jsonObject = new JSONObject();
		
		try {
			
			jsonObject = surveyService.surveyIsDuplucate(headerTypeCode, documentId, personId);
			
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
	
	@RequestMapping(value = RestURIConstants.GET_HOME_MEMBER_BY_HOME, method = RequestMethod.POST)
	public @ResponseBody APIResponse getHomeMemberNotSurvey(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		List<com.fwg.asservice.model.survey.HomeMember> resultList = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting HomeMembers not survey data.";
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		try {
			resultList = surveyService.listHomeMemberNotSurvey(filter.getHomeId(), filter.getHeaderTypeCode(), filter.getDocumentId(), filter.isAll());
			
			for (HomeMember homeMembers : resultList) {
				jsonObject = new JSONObject();
				
				jsonObject.put("personId", homeMembers.getPersonId());
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
				jsonObject.put("rowGUID", homeMembers.getRowGUID());
				jsonObject.put("latitude", homeMembers.getHome().getLatitude());
				jsonObject.put("longitude", homeMembers.getHome().getLongitude());
				jsonObject.put("medicalRightCode", homeMembers.getPerson().getMedicalRightCode());
				jsonObject.put("medicalRightDescription", homeMembers.getPerson().getMedicalRight().getDescription());
				jsonObject.put("congenitalDisease", homeMembers.getPerson().getCongenitalDisease());
				jsonObject.put("remark", homeMembers.getPerson().getRemark());
				jsonObject.put("isExists", homeMembers.isExists());
				
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
	
	@RequestMapping(value = RestURIConstants.INS_UPD_BORN_LOCATION, method = RequestMethod.POST)
	public @ResponseBody APIResponse populationInsertOrUpdate(@RequestBody ObjectNode requestParams) { 
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		BornLocation bornLocation = mapper.convertValue(requestParams, BornLocation.class);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to INS_UPD_BORN_LOCATION.";
		
		JSONObject object = new JSONObject();
		
		try {
			bornLocation = surveyService.insertBornLocation(bornLocation);
			object.put("id", bornLocation.getId());

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
		resp.setResponse(object);

		return resp;
	}
}
