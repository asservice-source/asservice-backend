package com.fwg.asservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fwg.asservice.constants.APIStatusMessage;
import com.fwg.asservice.constants.RequestMappingConstants;
import com.fwg.asservice.constants.RestURIConstants;
import com.fwg.asservice.model.APIResponse;
import com.fwg.asservice.model.Home;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.Child;
import com.fwg.asservice.model.survey.PregnantDetailInfo;
import com.fwg.asservice.service.SurveyPregnantService;
import com.fwg.asservice.service.SurveyService;
import com.fwg.asservice.sql.CallableStatementUtil;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
@RequestMapping(RequestMappingConstants.SURVEY_PREGNANT)
public class SurveyPregnantController {

	@Autowired
	private SurveyPregnantService surveyPregnantService;
	@Autowired
	private SurveyService surveyService;
	
	
	@RequestMapping(value = RestURIConstants.SEARCH_PREGNANT_INFO_LIST, method = RequestMethod.POST)
	public @ResponseBody APIResponse searchPregnantInfoList(@RequestBody ObjectNode requestParams) {
		final ObjectMapper mapper = new ObjectMapper();
		Filter filterParams = mapper.convertValue(requestParams, Filter.class);

		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Survey Pregnant data.";
		
		List<PregnantDetailInfo> pregnantDetailInfoList = null;
		try {
			pregnantDetailInfoList = surveyPregnantService.listPregnantDetailInfo(filterParams);
		} catch (Exception ex) {
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}
		
		JSONArray jsonArray = new JSONArray();
		
		for (PregnantDetailInfo pdi : pregnantDetailInfoList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("rowGUID", pdi.getRowGUID());
			jsonObj.put("documentID", pdi.getDocumentId()); 
			jsonObj.put("osmId", pdi.getOsmId());
			jsonObj.put("operationDate", pdi.getOperationDate());
			jsonObj.put("homeId", pdi.getHomeId());
			jsonObj.put("masterGUID", pdi.getMasterGUID());
			jsonObj.put("personId", pdi.getPersonId());
			jsonObj.put("citizenId", pdi.getPerson().getCitizenId());
			jsonObj.put("genderId", pdi.getPerson().getGenderId());
			jsonObj.put("fullName", GlobalFunction.generateFullName(pdi.getPerson()));
			jsonObj.put("address", GlobalFunction.generateAddress(pdi.getPerson().getAddress()));
			jsonObj.put("wombNo", pdi.getWombNo());
			jsonObj.put("pSurveyTypeCode", pdi.getpSurveyTypeCode());
			if (!GlobalFunction.isEmpty(pdi.getPerson().getBirthDate())) {
				jsonObj.put("age", GlobalFunction.getAgeFromBirthDate(pdi.getPerson().getBirthDate()));
			} else {
				jsonObj.put("age", "");
			}
			if (!GlobalFunction.isEmpty(pdi.getHomePerson())) {
				Home homePerson = pdi.getHomePerson();
				jsonObj.put("latitude", homePerson.getLatitude()!=null?homePerson.getLatitude():"");
				jsonObj.put("longitude", homePerson.getLongitude()!=null?homePerson.getLongitude():"");
			} else {
				jsonObj.put("latitude", "");
				jsonObj.put("longitude", "");
			}
			
			String bornDueDate = pdi.getBornDueDate();
			int bornTypeId = 0;
			String bornTypeName = "";
			boolean isEditOrDelete = true;
			try {
				List<Child> childList = surveyService.listChildByReferenceId(pdi.getRowGUID());
				if (childList!=null && childList.size()>0) {
					for (Child child : childList) {
						if("Born".equalsIgnoreCase(pdi.getpSurveyTypeCode())){
							bornDueDate = child.getBirthDate();
							bornTypeId = child.getBornTypeId();
							bornTypeName = child.getBornType().getName();
						}
						if (surveyService.checkCitizenIdIsSurvey(child.getBornCitizenId()) > 0) {
							isEditOrDelete = false;
							break;
						}
					}
				}
			} catch (Exception ex) {
			}
			jsonObj.put("bornDueDate", bornDueDate);
			jsonObj.put("bornTypeId", bornTypeId);
			jsonObj.put("bornTypeName", bornTypeName);
			jsonObj.put("isEditOrDelete", isEditOrDelete);
			jsonObj.put("picturePath", pdi.getPerson().getPicturePath());
			jsonArray.add(jsonObj);
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(jsonArray);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_PREGNANT_DETAIL_INFO, method = RequestMethod.POST)
	public @ResponseBody APIResponse getPregnantDetailInfo(@RequestBody ObjectNode requestParams) {
		final ObjectMapper mapper = new ObjectMapper();
		Filter filterParams = mapper.convertValue(requestParams, Filter.class);

		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Survey Pregnant Detail Info data.";
		
		PregnantDetailInfo pdi = null;
		try {
			pdi = surveyPregnantService.getByRowGUID(filterParams.getRowGUID());
		} catch (Exception ex) {
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}

		JSONObject jsonObj = new JSONObject();
		if (pdi != null) {
			jsonObj.put("rowGUID", pdi.getRowGUID());
			jsonObj.put("documentID", pdi.getDocumentId());
			jsonObj.put("osmId", pdi.getOsmId());
			jsonObj.put("operationDate", pdi.getOperationDate());
			jsonObj.put("homeId", pdi.getHomeId());
			jsonObj.put("masterGUID", pdi.getMasterGUID());
			jsonObj.put("personId", pdi.getPersonId());
			jsonObj.put("citizenId", pdi.getPerson().getCitizenId());
			jsonObj.put("genderId", pdi.getPerson().getGenderId());
			jsonObj.put("fullName", GlobalFunction.generateFullName(pdi.getPerson()));
			jsonObj.put("address", GlobalFunction.generateAddress(pdi.getPerson().getAddress()));
			jsonObj.put("wombNo", pdi.getWombNo());
			jsonObj.put("pSurveyTypeCode", pdi.getpSurveyTypeCode());
			if (!GlobalFunction.isEmpty(pdi.getPerson().getBirthDate())) {
				jsonObj.put("age", GlobalFunction.getAgeFromBirthDate(pdi.getPerson().getBirthDate()));
			} else {
				jsonObj.put("age", "");
			}
			if (!GlobalFunction.isEmpty(pdi.getHomePerson())) {
				Home homePerson = pdi.getHomePerson();
				jsonObj.put("latitude", homePerson.getLatitude()!=null?homePerson.getLatitude():"");
				jsonObj.put("longitude", homePerson.getLongitude()!=null?homePerson.getLongitude():"");
			} else {
				jsonObj.put("latitude", "");
				jsonObj.put("longitude", "");
			}
			//String bornDueDate = pdi.getBornDueDate();
			boolean isEditOrDelete = true;
			JSONArray childObjs = new JSONArray();
			try {
				List<Child> childList = surveyService.listChildByReferenceId(pdi.getRowGUID());
				if (childList!=null && childList.size()>0) {
					for (Child child : childList) {
						JSONObject childObj = new JSONObject();
						childObj.put("rowGUID", child.getRowGUID());
						childObj.put("referenceId", child.getReferenceId());
						childObj.put("childNo", child.getChildNo());
						childObj.put("bloodTypeId", child.getBloodTypeId());
						childObj.put("bornTypeId", child.getBornTypeId());
						childObj.put("birthDate",  child.getBirthDate());
						childObj.put("weight", child.getWeight());
						childObj.put("prefixCode", child.getPrefixCode());
						childObj.put("firstName", child.getFirstName());
						childObj.put("lastName", child.getLastName());
						childObj.put("childFullName", GlobalFunction.genFullName(child.getPrefix(), child.getFirstName(), child.getLastName()));
						childObj.put("genderId", child.getGenderId());
						childObj.put("bornLocationId", child.getBornLocationId());
						childObj.put("bornCitizenId", child.getBornCitizenId());
						childObj.put("abortionCause", child.getAbortionCause());
						childObjs.add(childObj);
						if (isEditOrDelete && surveyService.checkCitizenIdIsSurvey(child.getBornCitizenId()) > 0) {
							isEditOrDelete = false;
						}
					}
				}
			} catch (Exception ex) {
			}
			jsonObj.put("childs", childObjs);
			jsonObj.put("bornDueDate", pdi.getBornDueDate());
			jsonObj.put("isEditOrDelete", isEditOrDelete);
			jsonObj.put("picturePath", pdi.getPerson().getPicturePath());
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(jsonObj);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.SEARCH_PREGNANT_INFO_LIST_NOT_EXISTS_SURVEY_BORN, method = RequestMethod.POST)
	public @ResponseBody APIResponse getPregnantDetailListNotExistsSurveyBorn(@RequestBody ObjectNode requestParams) {
		final ObjectMapper mapper = new ObjectMapper();
		PregnantDetailInfo pregnantDetailInfo = mapper.convertValue(requestParams, PregnantDetailInfo.class);

		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Survey Pregnant list data [Not exists survey born].";
		
		List<PregnantDetailInfo> pregnantDetailInfoList = null;
		try {
			pregnantDetailInfoList = surveyPregnantService.listPregnantDetailInfoNotExistsSurveyBorn(pregnantDetailInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}
		
		JSONArray jsonArray = new JSONArray();
		
		for (PregnantDetailInfo pdi : pregnantDetailInfoList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("rowGUID", pdi.getRowGUID());
			jsonObj.put("documentID", pdi.getDocumentId());
			jsonObj.put("osmId", pdi.getOsmId());
			jsonObj.put("operationDate", pdi.getOperationDate());
			jsonObj.put("homeId", pdi.getHomeId());
			jsonObj.put("masterGUID", pdi.getMasterGUID());
			jsonObj.put("personId", pdi.getPersonId());
			jsonObj.put("citizenId", pdi.getPerson().getCitizenId());
			jsonObj.put("genderId", pdi.getPerson().getGenderId());
			jsonObj.put("fullName", GlobalFunction.generateFullName(pdi.getPerson()));
			jsonObj.put("address", GlobalFunction.generateAddress(pdi.getPerson().getAddress()));
			jsonObj.put("wombNo", pdi.getWombNo());
			jsonObj.put("bornDueDate", pdi.getBornDueDate());
			jsonObj.put("pSurveyTypeCode", pdi.getpSurveyTypeCode());
			if (!GlobalFunction.isEmpty(pdi.getPerson().getBirthDate())) {
				jsonObj.put("age", GlobalFunction.getAgeFromBirthDate(pdi.getPerson().getBirthDate()));
			} else {
				jsonObj.put("age", "");
			}
			jsonObj.put("picturePath", pdi.getPerson().getPicturePath());
			jsonArray.add(jsonObj);
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(jsonArray);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.SEARCH_PREGNANT_INFO_LIST_FOR_ADD_BORN, method = RequestMethod.POST)
	public @ResponseBody APIResponse getPregnantDetailListForAddBorn(@RequestBody ObjectNode requestParams) {
		final ObjectMapper mapper = new ObjectMapper();
		PregnantDetailInfo pregnantDetailInfo = mapper.convertValue(requestParams, PregnantDetailInfo.class);

		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Survey Pregnant list data [For add born].";
		
		List<PregnantDetailInfo> pregnantDetailInfoList = null;
		try {
			pregnantDetailInfoList = surveyPregnantService.listPregnantDetailInfoForAddBorn(pregnantDetailInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}
		
		JSONArray jsonArray = new JSONArray();
		
		for (PregnantDetailInfo pdi : pregnantDetailInfoList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("rowGUID", pdi.getRowGUID());
			jsonArray.add(jsonObj);
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(jsonArray);
		
		return resp;
	}

	@PostMapping(RestURIConstants.INS_UPD_PREGNANT_INFO)
	public @ResponseBody APIResponse insertOrUpdate(@RequestBody ObjectNode requestParams) {
		return callInsertOrUpdatePregnantDetailInfo(requestParams, false);
	}

	@PostMapping(RestURIConstants.DEL_PREGNANT_INFO)
	public @ResponseBody APIResponse delete(@RequestBody ObjectNode requestParams) {
		return callInsertOrUpdatePregnantDetailInfo(requestParams, true);
	}

	
	private APIResponse callInsertOrUpdatePregnantDetailInfo(@RequestBody ObjectNode requestParams, boolean isDeleted) {
		final ObjectMapper mapper = new ObjectMapper();
		PregnantDetailInfo pregnantDetailInfo = mapper.convertValue(requestParams, PregnantDetailInfo.class);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to Insert or Update Survey Pregnant data.";
		if (isDeleted) {
			message ="Success to Delete Survey Pregnant data.";
		}

		Object resObj = new Object();
		CallableStatementUtil callableStatementUtil = null;
		try {
			pregnantDetailInfo.setDeleted(isDeleted);
			pregnantDetailInfo = surveyPregnantService.insertOrUpdatePregnantDetailInfo(pregnantDetailInfo);
			callableStatementUtil = pregnantDetailInfo.getCallableStatementUtil();
			if (!isDeleted) {
				Child[] childs = pregnantDetailInfo.getChilds();
				if (childs!=null && childs.length>0) {
					int childNo = 1;
					for (Child child : childs) {
						child.setChildNo(childNo++);
						child.setReferenceId(pregnantDetailInfo.getRowGUID());
						child.setMotherCID(pregnantDetailInfo.getPersonId());
						child.setHomeID(pregnantDetailInfo.getHomeId());
						// Insert Child, Person, HomeMembers, Address
						child = surveyService.insertChildAndPersonAndHomeMembersAndAddress(child, callableStatementUtil);
					}
				}
			}
			
			pregnantDetailInfo.setCallableStatementUtil(null);
			resObj = pregnantDetailInfo;
			
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
				resObj = GlobalFunction.getErrorCode(exs);
			}
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

}
