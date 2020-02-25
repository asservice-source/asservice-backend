package com.fwg.asservice.controller;

import java.sql.Date;
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
import com.fwg.asservice.model.Address;
import com.fwg.asservice.model.HomeMembers;
import com.fwg.asservice.model.MedicalRight;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.PopulationDetail;
import com.fwg.asservice.model.survey.PopulationDetailInfo;
import com.fwg.asservice.service.HomeMemberService;
import com.fwg.asservice.service.SurveyPopulationService;
import com.fwg.asservice.sql.CallableStatementUtil;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
@RequestMapping(RequestMappingConstants.SURVEY_POPULATION)
public class SurveyPopulationController {
	
	@Autowired
	private SurveyPopulationService surveyPopulationService; 
	@Autowired
	private HomeMemberService homeMemberService;
	
	@RequestMapping(value = RestURIConstants.SEARCH_POPULATION_LIST, method = RequestMethod.POST)
	public @ResponseBody APIResponse getHomeListSearchByVillageOSMHome(@RequestBody ObjectNode requestParams) { 
		final ObjectMapper mapper = new ObjectMapper();
		Filter filterParams = mapper.convertValue(requestParams, Filter.class);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Survey population list data."; 
		
		JSONArray array = new JSONArray();
		try {
			
			List<PopulationDetail> listPopulation = surveyPopulationService.listPopulation(filterParams);
			for (PopulationDetail item : listPopulation) {
				JSONObject object = new JSONObject();
				object.put("homeId", item.getHomeId());
				object.put("villageNo", item.getHome().getVillage().getVillageNo());
				object.put("homeNo", item.getHome().getHomeNo());
				object.put("holderName", GlobalFunction.generateFullName(item.getHome().getHolder()));
				object.put("memberAmount", item.getMemberAmount());
				object.put("isSurvey", item.isSurvey());
				object.put("longitude", item.getHome().getLongitude());
				object.put("latitude", item.getHome().getLatitude());
				object.put("isWithoutOSM", item.isWithoutOSM());
				object.put("isCurrent", item.isCurrent());
				array.add(object);
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
		resp.setResponse(array);

		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.SEARCH_POPULATION_LIST_NOT_SURVEY, method = RequestMethod.POST)
	public @ResponseBody APIResponse getPopulationListNotSurvey(@RequestBody ObjectNode requestParams) { 
		final ObjectMapper mapper = new ObjectMapper();
		Filter filterParams = mapper.convertValue(requestParams, Filter.class);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Survey population list data."; 
		
		JSONArray array = new JSONArray();
		try {
			
			List<PopulationDetail> listPopulation = surveyPopulationService.listPopulationDetailNotSurvey(filterParams);
			for (PopulationDetail item : listPopulation) {
				JSONObject object = new JSONObject();
				object.put("homeId", item.getHomeId());
				object.put("villageNo", item.getHome().getVillage().getVillageNo());
				object.put("homeNo", item.getHome().getHomeNo());
				object.put("holderName", GlobalFunction.generateFullName(item.getHome().getHolder()));
				object.put("memberAmount", item.getMemberAmount());
				object.put("isSurvey", item.isSurvey());
				object.put("longitude", item.getHome().getLongitude());
				object.put("latitude", item.getHome().getLatitude());
				array.add(object);
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
		resp.setResponse(array);

		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_HOME_MEMBER_BY_HOME, method = RequestMethod.POST)
	public @ResponseBody APIResponse getHomeMemberByHome(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		List<HomeMembers> resultList = null;
		List<PopulationDetailInfo> populationDetailInfo = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting HomeMembers By HomeID data.";
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		try {
			resultList = homeMemberService.listHomeMemberByHomeID(filter.getHomeId(), filter.isManage());
			populationDetailInfo = surveyPopulationService.listPopulationDetailInfo(filter.getDocumentId(), filter.getHomeId());
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
				jsonObject.put("dischargeDate", homeMembers.getDischargeDate());
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
				
				jsonObject.put("isGuest", homeMembers.isGuest());
				jsonObject.put("address", GlobalFunction.generateAddress(homeMembers.getPerson().getAddress()));
				jsonObject.put("homeNo", homeMembers.getPerson().getAddress().getHomeNo());
				jsonObject.put("mooNo", homeMembers.getPerson().getAddress().getMooNo());
				jsonObject.put("road", homeMembers.getPerson().getAddress().getRoad());
				jsonObject.put("provinceCode", homeMembers.getPerson().getAddress().getProvinceCode());
				jsonObject.put("amphurCode", homeMembers.getPerson().getAddress().getAmphurCode());
				jsonObject.put("tumbolCode", homeMembers.getPerson().getAddress().getTumbolCode());
				jsonObject.put("zipcode", homeMembers.getPerson().getAddress().getZipcode());
				
				jsonObject.put("longitude", homeMembers.getHome().getLongitude());
				jsonObject.put("latitude", homeMembers.getHome().getLatitude());
				
				jsonObject.put("picturePath", homeMembers.getPerson().getPicturePath());
				
				String congenitalDisease = "";
				String remark = "";
				String rowGUID = "";
				boolean isExists = true;
				
				for(PopulationDetailInfo infoItem : populationDetailInfo){
					if(infoItem.getPersonId().equalsIgnoreCase(homeMembers.getPersonId())){
						congenitalDisease = infoItem.getCongenitalDisease();
						remark = infoItem.getRemark();
						isExists = infoItem.isExists();
						rowGUID = infoItem.getRowGUID();
					}
				}
				
				jsonObject.put("congenitalDisease", congenitalDisease);
				jsonObject.put("remark", remark);
				jsonObject.put("isExists", isExists);
				jsonObject.put("rowGUID", rowGUID);
				jsonObject.put("medicalRightCode", homeMembers.getPerson().getMedicalRightCode());
				jsonObject.put("medicalRightDescription", homeMembers.getPerson().getMedicalRight().getDescription());
				
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
	
	@RequestMapping(value = RestURIConstants.POPULATION_ADD_HOME_MEMBER, method = RequestMethod.POST)
	public @ResponseBody APIResponse addHomeMember(@RequestBody ObjectNode requestParams) { 
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		HomeMembers homeMembers = mapper.convertValue(requestParams, HomeMembers.class);
		Person person = mapper.convertValue(requestParams, Person.class);
		Address address = mapper.convertValue(requestParams, Address.class);
		MedicalRight medicalRight = mapper.convertValue(requestParams, MedicalRight.class);
		
		System.out.println("address :: "+address.getHomeNo());
		
		person.setAddress(address);
		person.setMedicalRight(medicalRight);
		homeMembers.setPerson(person);
		//homeMembers.getPerson().setAddress(address);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to add home member [PopulationInfo] data.";
		
		JSONObject object = new JSONObject();
		
		try {
			
			person = surveyPopulationService.addHomeMember(homeMembers);
			object.put("personId", person.getPersonId());

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
	
	@RequestMapping(value = RestURIConstants.INS_UPD_POPULATION_INFO, method = RequestMethod.POST)
	public @ResponseBody APIResponse populationInsertOrUpdate(@RequestBody ObjectNode requestParams) { 
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to insert or update Population Info.";
		
		JSONArray arr = new JSONArray();
		CallableStatementUtil callableStatementUtil = null;
		try {
			PopulationDetail populationDetail = mapper.convertValue(requestParams, PopulationDetail.class);
			List<PopulationDetailInfo> populationDetailInfoList = mapper.readValue(requestParams.get("list").toString(), mapper.getTypeFactory().constructCollectionType(List.class, PopulationDetailInfo.class));
			List<Person> personList = mapper.readValue(requestParams.get("list").toString(), mapper.getTypeFactory().constructCollectionType(List.class, Person.class));
			List<Address> addresses = mapper.readValue(requestParams.get("list").toString(), mapper.getTypeFactory().constructCollectionType(List.class, Address.class));
			
			PopulationDetail populationDetailIns = surveyPopulationService.populationDetailInsOrUpd(populationDetail);
			callableStatementUtil = populationDetailIns.getCallableStatementUtil();
			
			populationDetail.setRowGUID(populationDetailIns.getRowGUID());
			populationDetail.setCallableStatementUtil(callableStatementUtil);

			if (populationDetailInfoList!=null && populationDetailInfoList.size()>0) {
				for(PopulationDetailInfo populationDetailInfo : populationDetailInfoList){
					for(Person personItem : personList){
						if(populationDetailInfo.getPersonId().equalsIgnoreCase(personItem.getPersonId())){
							for(Address addressItem : addresses){
								if(personItem.getPersonId().equalsIgnoreCase(addressItem.getPersonId())){
									personItem.setAddress(addressItem);
								}
							}
							populationDetailInfo.setPerson(personItem);
						}
					}
					PopulationDetailInfo insUpdInfo = surveyPopulationService.insertOrUpdatePopulationDetail(populationDetail, populationDetailInfo);
					JSONObject object = new JSONObject();
					object.put("RowGUID", insUpdInfo.getRowGUID());
					arr.add(object);
				}
			}

			if (callableStatementUtil != null) {
				callableStatementUtil.closeAndCommit(true);
			}
		} catch (Exception ex) {
			try {
				if (callableStatementUtil != null) {
					callableStatementUtil.closeAndCommit(false);
				}
			} catch (Exception exs) {
				exs.printStackTrace();
				status = APIStatusMessage.FAILED;
				message = GlobalFunction.getExceptionMessage(exs);
			}
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(arr);

		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.DEL_POPULATION, method = RequestMethod.POST)
	public @ResponseBody APIResponse deletePopulation(@RequestBody ObjectNode requestParams) {
		ObjectMapper mapper = new ObjectMapper();
		PopulationDetail populationDetail = mapper.convertValue(requestParams, PopulationDetail.class);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to delete survey population.";

		Object resObj = new Object();
		try {
			surveyPopulationService.deletePopulation(populationDetail);
			resObj = populationDetail;
		} catch (Exception ex) {
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
			resObj = GlobalFunction.getErrorCode(ex);
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(resObj);

		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_HOME_MEMBER_BY_DOCUMENTID, method = RequestMethod.POST)
	public @ResponseBody APIResponse getHomeMemberByDocumentID(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		List<HomeMembers> resultList = null;
		List<PopulationDetailInfo> populationDetailInfo = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting GET_HOME_MEMBER_BY_DOCUMENTID data.";
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		try {
			resultList = surveyPopulationService.listHomeMemberByDocumentID(filter.getDocumentId(), filter.getHomeId());
			populationDetailInfo = surveyPopulationService.listPopulationDetailInfo(filter.getDocumentId(), filter.getHomeId());
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
				
				jsonObject.put("dischargeId", homeMembers.getDischarge().getId());
				jsonObject.put("dischargeName", homeMembers.getDischarge().getName());
				jsonObject.put("dischargeDate", homeMembers.getDischargeDate());
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
				
				jsonObject.put("address", GlobalFunction.generateAddress(homeMembers.getPerson().getAddress()));
				jsonObject.put("homeNo", homeMembers.getPerson().getAddress().getHomeNo());
				jsonObject.put("mooNo", homeMembers.getPerson().getAddress().getMooNo());
				jsonObject.put("road", homeMembers.getPerson().getAddress().getRoad());
				jsonObject.put("provinceCode", homeMembers.getPerson().getAddress().getProvinceCode());
				jsonObject.put("amphurCode", homeMembers.getPerson().getAddress().getAmphurCode());
				jsonObject.put("tumbolCode", homeMembers.getPerson().getAddress().getTumbolCode());
				jsonObject.put("zipcode", homeMembers.getPerson().getAddress().getZipcode());
				
				jsonObject.put("longitude", homeMembers.getHome().getLongitude());
				jsonObject.put("latitude", homeMembers.getHome().getLatitude());
				
				jsonObject.put("picturePath", homeMembers.getPerson().getPicturePath());
				
				String congenitalDisease = "";
				Date birthDate = null;
				String remark = "";
				String rowGUID = "";
				String educationCode = "";
				String educationName = "";
				String occupationCode = "";
				String occupationName = "";
				int familyStatusId = 0;
				String familyStatusName = "";
				boolean isExists = true;
				boolean isGuest = true;
				
				for(PopulationDetailInfo infoItem : populationDetailInfo){
					if(infoItem.getPersonId().equalsIgnoreCase(homeMembers.getPersonId())){
						birthDate = infoItem.getBirthDate();
						congenitalDisease = infoItem.getCongenitalDisease();
						remark = infoItem.getRemark();
						isExists = infoItem.isExists();
						isGuest = infoItem.isGuest();
						rowGUID = infoItem.getRowGUID();
						educationCode = infoItem.getEducation().getCode();
						educationName = infoItem.getEducation().getName();
						occupationCode = infoItem.getOccupation().getCode();
						occupationName = infoItem.getOccupation().getName();
						familyStatusId = infoItem.getFamilyStatus().getId();
						familyStatusName = infoItem.getFamilyStatus().getName();
					}
				}
				
				jsonObject.put("birthDate", birthDate);
				jsonObject.put("age", GlobalFunction.getAgeFromBirthDate(birthDate));
				jsonObject.put("educationCode", educationCode);
				jsonObject.put("educationName", educationName);
				jsonObject.put("occupationCode", occupationCode);
				jsonObject.put("occupationName", occupationName);
				jsonObject.put("familyStatusId", familyStatusId);
				jsonObject.put("familyStatusName", familyStatusName);
				
				jsonObject.put("congenitalDisease", congenitalDisease);
				jsonObject.put("remark", remark);
				jsonObject.put("isExists", isExists);
				jsonObject.put("isGuest", isGuest);
				jsonObject.put("rowGUID", rowGUID);
				jsonObject.put("medicalRightCode", homeMembers.getPerson().getMedicalRightCode());
				jsonObject.put("medicalRightDescription", homeMembers.getPerson().getMedicalRight().getDescription());
				
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
	
}
