package com.fwg.asservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.MetabolicDetailInfo;
import com.fwg.asservice.service.SurveyMetabolicService;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
@RequestMapping(RequestMappingConstants.SURVEY_METABOLIC)
public class SurveyMetabolicController {
	@Autowired
	private SurveyMetabolicService surveyListService;
	
	@RequestMapping(value = RestURIConstants.SEARCH_METABOLIC_LIST, method = RequestMethod.POST) 
	public @ResponseBody APIResponse searchMetabolicList(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		//final MetabolicDetail metabolicDetail = mapper.convertValue(requestParams, MetabolicDetail.class);
		
		String name = requestParams.get("name").textValue();
		String documentId = requestParams.get("documentId").textValue();
		Integer villageId = GlobalFunction.isEmpty(requestParams.get("villageId").textValue()) ? null : Integer.valueOf(requestParams.get("villageId").textValue());
		String osmId = requestParams.get("osmId").textValue();
		
		List<MetabolicDetailInfo> list = null;		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting sp_Metabolic_MetabolicDetailInfoList data.";
		
		JSONArray array = new JSONArray();
		JSONObject object = null;
		
		try {
			list = surveyListService.listMetabolic(documentId, villageId, osmId, name);
			for(MetabolicDetailInfo listItem : list){
				
				object = new JSONObject();
				object.put("rowGUID", listItem.getRowGUID());
				object.put("documentId", listItem.getDocumentId());
				object.put("osmId", listItem.getOsmId());
				object.put("operationDate", listItem.getOperationDate());
				object.put("personId", listItem.getPersonId());
				object.put("fullName", GlobalFunction.generateFullName(listItem.getPerson()));
				object.put("citizenId", listItem.getPerson().getCitizenId());
				object.put("homeId", listItem.getHomeId());
				object.put("homeNo", listItem.getHome().getHomeNo());
				object.put("genderId", listItem.getPerson().getGenderId());
				object.put("genderName", listItem.getPerson().getGender().getName());
				object.put("age", GlobalFunction.getAgeFromBirthDate(listItem.getPerson().getBirthDate()));
				object.put("hInsuranceTypeId", listItem.getHealthInsuranceType().getId());
				object.put("healthInsuranceTypeName", listItem.getHealthInsuranceType().getName());
				object.put("address", GlobalFunction.generateAddress(listItem.getPerson().getAddress()));
				object.put("isHeredityMetabolic", listItem.isHeredityMetabolic());
				object.put("isWaistlineOver", listItem.isWaistlineOver());
				object.put("isBPOver", listItem.isBPOver());
				object.put("isFBS", listItem.isFBS());
				object.put("isCholesterol", listItem.isCholesterol());
				object.put("isNewborn4kg", listItem.isNewborn4kg());
				object.put("isHeredityHypertension", listItem.isHeredityHypertension());
				object.put("smokingStatusId", listItem.getSmokingStatusId());
				object.put("smokingStatus", listItem.getSmokingStatus().getName());
				object.put("rollPerDay", listItem.getRollPerDay());
				object.put("packPerYear", listItem.getPackPerYear());
				object.put("drinkingStatusId", listItem.getDrinkingStatusId());
				object.put("drinkingStatus", listItem.getDrinkingStatus().getName());
				object.put("oftenPerWeek", listItem.getOftenPerWeek());
				object.put("weight", listItem.getWeight());
				object.put("height", listItem.getHeight());
				object.put("bmi", listItem.getBmi());
				object.put("waistline", listItem.getWaistline());
				object.put("bp1", listItem.getBp1());
				object.put("bp2", listItem.getBp2());
				object.put("fbs", listItem.getFbs());
				object.put("isMetabolic", listItem.isMetabolic());
				object.put("isHypertension", listItem.isHypertension());
				object.put("isEyeComplication", listItem.isEyeComplication());
				object.put("isKidneyComplication", listItem.isKidneyComplication());
				object.put("isPeripheralNeuropathy", listItem.isPeripheralNeuropathy());
				object.put("peripheralName", listItem.getPeripheralName());
				object.put("isNeuropathy", listItem.isNeuropathy());
				object.put("isOther", listItem.isOther());
				object.put("otherComplication", listItem.getOtherComplication());
				object.put("isDeleted", listItem.isDeleted());
				object.put("latitude", listItem.getHome().getLatitude());
				object.put("longitude", listItem.getHome().getLongitude());
				object.put("picturePath", listItem.getPerson().getPicturePath());
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
	
	@RequestMapping(value = RestURIConstants.METABOLIC_BY_ROWGUID, method = RequestMethod.POST) 
	public @ResponseBody APIResponse metabolicDataByRowGUID(@RequestBody ObjectNode requestParams) {
		
		String rowGUID = requestParams.get("rowGUID").textValue();
		
		MetabolicDetailInfo metabolicDetailInfo = null;		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting METABOLIC_BY_ROWGUID data.";
		
		JSONArray array = new JSONArray();
		JSONObject object = null;
		
		try {
			metabolicDetailInfo = surveyListService.metabolicDataByRowGUID(rowGUID);

			object = new JSONObject();
			object.put("rowGUID", metabolicDetailInfo.getRowGUID());
			object.put("documentId", metabolicDetailInfo.getDocumentId());
			object.put("osmId", metabolicDetailInfo.getOsmId());
			object.put("operationDate", metabolicDetailInfo.getOperationDate());
			object.put("personId", metabolicDetailInfo.getPersonId());
			object.put("fullName", GlobalFunction.generateFullName(metabolicDetailInfo.getPerson()));
			object.put("citizenId", metabolicDetailInfo.getPerson().getCitizenId());
			object.put("homeId", metabolicDetailInfo.getHomeId());
			object.put("homeNo", metabolicDetailInfo.getHome().getHomeNo());
			object.put("genderId", metabolicDetailInfo.getPerson().getGenderId());
			object.put("genderName", metabolicDetailInfo.getPerson().getGender().getName());
			object.put("age", GlobalFunction.getAgeFromBirthDate(metabolicDetailInfo.getPerson().getBirthDate()));
			object.put("hInsuranceTypeId", metabolicDetailInfo.getHealthInsuranceType().getId());
			object.put("healthInsuranceTypeName", metabolicDetailInfo.getHealthInsuranceType().getName());
			object.put("address", GlobalFunction.generateAddress(metabolicDetailInfo.getPerson().getAddress()));
			object.put("isHeredityMetabolic", metabolicDetailInfo.isHeredityMetabolic());
			object.put("isWaistlineOver", metabolicDetailInfo.isWaistlineOver());
			object.put("isBPOver", metabolicDetailInfo.isBPOver());
			object.put("isFBS", metabolicDetailInfo.isFBS());
			object.put("isCholesterol", metabolicDetailInfo.isCholesterol());
			object.put("isNewborn4kg", metabolicDetailInfo.isNewborn4kg());
			object.put("isHeredityHypertension", metabolicDetailInfo.isHeredityHypertension());
			object.put("smokingStatusId", metabolicDetailInfo.getSmokingStatusId());
			object.put("smokingStatus", metabolicDetailInfo.getSmokingStatus().getName());
			object.put("rollPerDay", metabolicDetailInfo.getRollPerDay());
			object.put("packPerYear", metabolicDetailInfo.getPackPerYear());
			object.put("drinkingStatusId", metabolicDetailInfo.getDrinkingStatusId());
			object.put("drinkingStatus", metabolicDetailInfo.getDrinkingStatus().getName());
			object.put("oftenPerWeek", metabolicDetailInfo.getOftenPerWeek());
			object.put("weight", metabolicDetailInfo.getWeight());
			object.put("height", metabolicDetailInfo.getHeight());
			object.put("bmi", metabolicDetailInfo.getBmi());
			object.put("waistline", metabolicDetailInfo.getWaistline());
			object.put("bp1", metabolicDetailInfo.getBp1());
			object.put("bp2", metabolicDetailInfo.getBp2());
			object.put("fbs", metabolicDetailInfo.getFbs());
			object.put("isMetabolic", metabolicDetailInfo.isMetabolic());
			object.put("isHypertension", metabolicDetailInfo.isHypertension());
			object.put("isEyeComplication", metabolicDetailInfo.isEyeComplication());
			object.put("isKidneyComplication", metabolicDetailInfo.isKidneyComplication());
			object.put("isPeripheralNeuropathy", metabolicDetailInfo.isPeripheralNeuropathy());
			object.put("peripheralName", metabolicDetailInfo.getPeripheralName());
			object.put("isNeuropathy", metabolicDetailInfo.isNeuropathy());
			object.put("isOther", metabolicDetailInfo.isOther());
			object.put("otherComplication", metabolicDetailInfo.getOtherComplication());
			object.put("isDeleted", metabolicDetailInfo.isDeleted());
			object.put("latitude", metabolicDetailInfo.getHome().getLatitude());
			object.put("longitude", metabolicDetailInfo.getHome().getLongitude());
			object.put("picturePath", metabolicDetailInfo.getPerson().getPicturePath());

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
	
	@RequestMapping(value = RestURIConstants.SEARCH_METABOLIC_LIST_NOT_SURVEY, method = RequestMethod.POST) 
	public @ResponseBody APIResponse searchMetabolicListNotSurvey(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		List<MetabolicDetailInfo> list = null;		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting sp_Metabolic_MetabolicDetailInfoList not survey data."; 
		
		JSONArray array = new JSONArray();
		JSONObject object = null;
		
		try {
			list = surveyListService.listMetabolicNotSurvey(filter.getDocumentId(), filter.getOsmId());
			for(MetabolicDetailInfo listItem : list){
				
				object = new JSONObject();
				object.put("rowGUID", listItem.getRowGUID());
				object.put("documentId", listItem.getDocumentId());
				object.put("osmId", listItem.getOsmId());
				object.put("operationDate", listItem.getOperationDate());
				object.put("personId", listItem.getPersonId());
				object.put("fullName", GlobalFunction.generateFullName(listItem.getPerson()));
				object.put("citizenId", listItem.getPerson().getCitizenId());
				object.put("homeId", listItem.getHomeId());
				object.put("homeNo", listItem.getHome().getHomeNo());
				//object.put("genderId", listItem.getPerson().getGenderId());
				object.put("genderName", listItem.getPerson().getGender().getName());
				object.put("age", GlobalFunction.getAgeFromBirthDate(listItem.getPerson().getBirthDate()));
				//object.put("hInsuranceTypeId", listItem.getHealthInsuranceType().getId());
				//object.put("healthInsuranceTypeName", listItem.getHealthInsuranceType().getName());
				object.put("address", GlobalFunction.generateAddress(listItem.getPerson().getAddress()));
				//object.put("isHeredityMetabolic", listItem.isHeredityMetabolic());
				//object.put("isWaistlineOver", listItem.isWaistlineOver());
				//object.put("isBPOver", listItem.isBPOver());
				//object.put("isFBS", listItem.isFBS());
				//object.put("isCholesterol", listItem.isCholesterol());
				//object.put("isNewborn4kg", listItem.isNewborn4kg());
				//object.put("isHeredityHypertension", listItem.isHeredityHypertension());
				//object.put("smokingStatusId", listItem.getSmokingStatusId());
				//object.put("smokingStatus", listItem.getSmokingStatus().getName());
				//object.put("rollPerDay", listItem.getRollPerDay());
				//object.put("packPerYear", listItem.getPackPerYear());
				//object.put("drinkingStatusId", listItem.getDrinkingStatusId());
				//object.put("drinkingStatus", listItem.getDrinkingStatus().getName());
				//object.put("oftenPerWeek", listItem.getOftenPerWeek());
				//object.put("weight", listItem.getWeight());
				//object.put("height", listItem.getHeight());
				//object.put("bmi", listItem.getBmi());
				//object.put("waistline", listItem.getWaistline());
				//object.put("bp1", listItem.getBp1());
				//object.put("bp2", listItem.getBp2());
				//object.put("fbs", listItem.getFbs());
				//object.put("isMetabolic", listItem.isMetabolic());
				//object.put("isHypertension", listItem.isHypertension());
				//object.put("isEyeComplication", listItem.isEyeComplication());
				//object.put("isKidneyComplication", listItem.isKidneyComplication());
				//object.put("isPeripheralNeuropathy", listItem.isPeripheralNeuropathy());
				//object.put("peripheralName", listItem.getPeripheralName());
				//object.put("isNeuropathy", listItem.isNeuropathy());
				//object.put("isOther", listItem.isOther());
				//object.put("otherComplication", listItem.getOtherComplication());
				//object.put("isDeleted", listItem.isDeleted());
				object.put("picturePath", listItem.getPerson().getPicturePath());
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


	@PostMapping(RestURIConstants.INS_UPD_METABOLIC_INFO)
	public @ResponseBody APIResponse insertOrUpdate(@RequestBody ObjectNode requestParams) {
		return metabolicInsertOrUpdate(requestParams, false);
	}

	@PostMapping(RestURIConstants.DEL_METABOLIC_INFO)
	public @ResponseBody APIResponse delete(@RequestBody ObjectNode requestParams) {
		return metabolicInsertOrUpdate(requestParams, true);
	}

	private APIResponse metabolicInsertOrUpdate(@RequestBody ObjectNode requestParams, boolean isDeleted) {
		ObjectMapper mapper = new ObjectMapper();
		MetabolicDetailInfo metabolicDetailInfo = mapper.convertValue(requestParams, MetabolicDetailInfo.class);
			
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to Insert or Update Survey Metabolic data.";
		if (isDeleted) {
			message ="Success to Delete Survey Metabolic data.";
		}

		Object resObj = new Object();
		try {
			metabolicDetailInfo.setDeleted(isDeleted);
			resObj = surveyListService.metabolicInsOrUpd(metabolicDetailInfo).getRowGUID();
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
}
