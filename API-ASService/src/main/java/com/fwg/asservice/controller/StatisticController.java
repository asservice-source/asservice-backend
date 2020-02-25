package com.fwg.asservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fwg.asservice.constants.APIStatusMessage;
import com.fwg.asservice.constants.RequestMappingConstants;
import com.fwg.asservice.constants.RestURIConstants;
import com.fwg.asservice.model.APIResponse;
import com.fwg.asservice.model.FamilySummary;
import com.fwg.asservice.model.SurveySummary;
import com.fwg.asservice.service.StatisticService;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONObject;

@Controller
@RequestMapping(RequestMappingConstants.STATISTIC)
public class StatisticController {
	
	@Autowired
	private StatisticService statisticService;
	
	@RequestMapping(value = RestURIConstants.FAMILY_SUMMARY, method = RequestMethod.POST)
	public @ResponseBody APIResponse getFamilySummary(@RequestBody ObjectNode requestParams) {
		
		String osmId = requestParams.get("osmId").textValue();
		
		FamilySummary familySummary = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting OSM. FamilySummary data.";
		
		JSONObject object = new JSONObject();
		
		try{
			
			familySummary = statisticService.familySummaryOfOSM(osmId);
			object.put("family", familySummary.getFamily());
			object.put("male", familySummary.getMale());
			object.put("female", familySummary.getFemale());
			
		}catch(Exception ex){
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
	
	@RequestMapping(value = RestURIConstants.SURVEY_SUMMARY, method = RequestMethod.POST)
	public @ResponseBody APIResponse getSurveySummary(@RequestBody ObjectNode requestParams) {
		
		String osmId = requestParams.get("osmId").textValue();
		
		List<SurveySummary> surveySummary = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting OSM. SurveySummary data.";
		
		//JSONObject object = new JSONObject();
		
		try{
			surveySummary = statisticService.surveySummary(osmId);
			
		}catch(Exception ex){
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(surveySummary);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.FAMILY_SUMMARY_HOSPITAL, method = RequestMethod.POST)
	public @ResponseBody APIResponse getFamilySummaryOfHospital(@RequestBody ObjectNode requestParams) {
		
		String code5 = requestParams.get("code5").textValue();
		
		FamilySummary familySummary = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting FAMILY_SUMMARY_HOSPITAL data.";
		
		JSONObject object = new JSONObject();
		
		try{
			
			familySummary = statisticService.familySummaryOfHospital(code5);
			object.put("family", familySummary.getFamily());
			object.put("male", familySummary.getMale());
			object.put("female", familySummary.getFemale());
			
		}catch(Exception ex){
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
	
	@RequestMapping(value = RestURIConstants.SURVEY_SUMMARY_HOSPITAL, method = RequestMethod.POST)
	public @ResponseBody APIResponse getSurveySummaryOfHospital(@RequestBody ObjectNode requestParams) {
		
		String code5 = requestParams.get("code5").textValue();
		
		List<SurveySummary> surveySummary = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting SURVEY_SUMMARY_HOSPITAL data.";
		
		//JSONObject object = new JSONObject();
		
		try{
			surveySummary = statisticService.surveySummaryOfHospital(code5);
			
		}catch(Exception ex){
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(surveySummary);
		
		return resp;
	}
	
}
