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
import com.fwg.asservice.message.MessageSourceUtil;
import com.fwg.asservice.model.APIResponse;
import com.fwg.asservice.model.Home;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.DeathDetailInfo;
import com.fwg.asservice.service.HomeService;
import com.fwg.asservice.service.SurveyDeathService;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
@RequestMapping(RequestMappingConstants.SURVEY_DEATH)
public class SurveyDeathController {

	@Autowired
	private SurveyDeathService surveyDeathService;
	@Autowired
	private HomeService homeService;
	
	@RequestMapping(value = RestURIConstants.SEARCH_DEATH_INFO_LIST, method = RequestMethod.POST)
	public @ResponseBody APIResponse searchDeathDetailList(@RequestBody ObjectNode requestParams) {
		final ObjectMapper mapper = new ObjectMapper();
		Filter filterParams = mapper.convertValue(requestParams, Filter.class);

		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Death Detail List data.";
		
		List<DeathDetailInfo> deathDetailInfoList = null;
		try {
			deathDetailInfoList = surveyDeathService.listDeathDetailInfo(filterParams);
		} catch (Exception ex) {
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}
		
		JSONArray jsonArray = new JSONArray();
		
		for (DeathDetailInfo ddi : deathDetailInfoList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("rowGUID", ddi.getRowGUID());
			jsonObj.put("personId", ddi.getPersonId());
			jsonObj.put("citizenId", ddi.getPerson().getCitizenId());
			jsonObj.put("fullName", GlobalFunction.generateFullName(ddi.getPerson()));
			jsonObj.put("address", GlobalFunction.generateAddress(ddi.getPerson().getAddress()));
			jsonObj.put("age", ddi.getAge()!=null?ddi.getAge():0);
			jsonObj.put("deathDate", ddi.getDeathDate());
			jsonObj.put("operationDate", ddi.getOperationDate());
			jsonObj.put("isDiabetes", ddi.isDiabetes());
			jsonObj.put("isHypertension", ddi.isHypertension());
			jsonObj.put("isAccident", ddi.isAccident());
			jsonObj.put("isNoDisease", ddi.isNoDisease());
			jsonObj.put("isCancer", ddi.isCancer());
			jsonObj.put("cancerTypeId", !GlobalFunction.isEmpty(ddi.getCancerType())?ddi.getCancerType().getId():"");
			jsonObj.put("deathPlaceCode", ddi.getDeathPlaceCode());
			jsonObj.put("deathPlaceName", ddi.getDeathPlace().getName());
			jsonObj.put("placeOther", !GlobalFunction.isEmpty(ddi.getPlaceOther())?ddi.getPlaceOther():"");
			
			Home home = null;
			try {
				home = homeService.getHomeByPersonId(ddi.getPersonId());
			} catch (Exception ex) {
			}
			if (home != null) {
				jsonObj.put("latitude", home.getLatitude()!=null?home.getLatitude():"");
				jsonObj.put("longitude", home.getLongitude()!=null?home.getLongitude():"");
			} else {
				jsonObj.put("latitude", "");
				jsonObj.put("longitude", "");
			}

			StringBuffer causeOfDeath = new StringBuffer();
			if (ddi.isDiabetes()) {
				causeOfDeath.append(MessageSourceUtil.getMessage("diabetes"));
				causeOfDeath.append(", ");
			}
			if (ddi.isHypertension()) {
				causeOfDeath.append(MessageSourceUtil.getMessage("hypertension"));
				causeOfDeath.append(", ");
			}
			if (ddi.isAccident()) {
				causeOfDeath.append(MessageSourceUtil.getMessage("accident"));
				causeOfDeath.append(", ");
			}
			if (ddi.isCancer()) {
				causeOfDeath.append(!GlobalFunction.isEmpty(ddi.getCancerType())?ddi.getCancerType().getName():"");
				causeOfDeath.append(", ");
			}
			
			String causeOther = "";
			if (!GlobalFunction.isEmpty(ddi.getCauseOther())) {
				causeOther = ddi.getCauseOther();
				causeOfDeath.append(causeOther);
				causeOfDeath.append(", ");
			}
			jsonObj.put("causeOther", causeOther);
			
			if (causeOfDeath.length() > 0) {
				jsonObj.put("causeOfDeath", causeOfDeath.substring(0, causeOfDeath.length()-2));
				
			} else {
				jsonObj.put("causeOfDeath", "");
			}
			
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
	
	@RequestMapping(value = RestURIConstants.GET_DEATH_DETAIL_INFO, method = RequestMethod.POST)
	public @ResponseBody APIResponse getDeathDetailInfo(@RequestBody ObjectNode requestParams) {
		final ObjectMapper mapper = new ObjectMapper();
		Filter filterParams = mapper.convertValue(requestParams, Filter.class);

		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Death Detail Info data.";
		
		DeathDetailInfo ddi = null;
		try {
			ddi = surveyDeathService.getDeathDetailInfo(filterParams);
		} catch (Exception ex) {
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}

		JSONObject jsonObj = new JSONObject();
		if (ddi != null) {
			jsonObj.put("rowGUID", ddi.getRowGUID());
			jsonObj.put("personId", ddi.getPersonId());
			jsonObj.put("citizenId", ddi.getPerson().getCitizenId());
			jsonObj.put("fullName", GlobalFunction.generateFullName(ddi.getPerson()));
			jsonObj.put("address", GlobalFunction.generateAddress(ddi.getPerson().getAddress()));
			jsonObj.put("age", ddi.getAge()!=null?ddi.getAge():0);
			jsonObj.put("deathDate", ddi.getDeathDate());
			jsonObj.put("isDiabetes", ddi.isDiabetes());
			jsonObj.put("isHypertension", ddi.isHypertension());
			jsonObj.put("isAccident", ddi.isAccident());
			jsonObj.put("isNoDisease", ddi.isNoDisease());
			jsonObj.put("isCancer", ddi.isCancer());
			jsonObj.put("cancerTypeId", !GlobalFunction.isEmpty(ddi.getCancerType())?ddi.getCancerType().getId():"");
			jsonObj.put("deathPlaceCode", ddi.getDeathPlaceCode());
			jsonObj.put("deathPlaceName", ddi.getDeathPlace().getName());
			jsonObj.put("placeOther", !GlobalFunction.isEmpty(ddi.getPlaceOther())?ddi.getPlaceOther():"");
			jsonObj.put("causeOther", !GlobalFunction.isEmpty(ddi.getCauseOther())?ddi.getCauseOther():"");
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(jsonObj);
		
		return resp;
	}

	@PostMapping(RestURIConstants.INS_UPD_DEATH_INFO)
	public @ResponseBody APIResponse insertOrUpdate(@RequestBody ObjectNode requestParams) {
		return callInsertOrUpdateDeathDetailInfo(requestParams, false);
	}

	@PostMapping(RestURIConstants.DEL_DEATH_INFO)
	public @ResponseBody APIResponse delete(@RequestBody ObjectNode requestParams) {
		return callInsertOrUpdateDeathDetailInfo(requestParams, true);
	}

	
	private APIResponse callInsertOrUpdateDeathDetailInfo(@RequestBody ObjectNode requestParams, boolean isDeleted) {
		final ObjectMapper mapper = new ObjectMapper();
		DeathDetailInfo deathDetailInfo = mapper.convertValue(requestParams, DeathDetailInfo.class);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to Insert or Update Survey Death data.";
		if (isDeleted) {
			message ="Success to Delete Survey Death data.";
		}

		Object resObj = new Object();
		try {
			deathDetailInfo.setDeleted(isDeleted);
			resObj = surveyDeathService.insertOrUpdateDeathDetailInfo(deathDetailInfo);
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
