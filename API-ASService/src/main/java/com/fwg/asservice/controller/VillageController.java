package com.fwg.asservice.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fwg.asservice.constants.APIStatusMessage;
import com.fwg.asservice.constants.RequestMappingConstants;
import com.fwg.asservice.constants.RestURIConstants;
import com.fwg.asservice.model.APIResponse;
import com.fwg.asservice.model.Village;
import com.fwg.asservice.service.VillageService;
import com.fwg.asservice.utility.GlobalFunction;

/**
 * Handles requests for the Employee service.
 */
@Controller
@RequestMapping(RequestMappingConstants.VILLAGE)
public class VillageController {
	
	@Autowired
	private VillageService villageService;
	
	@RequestMapping(value = RestURIConstants.DEL_VILLAGE, method = RequestMethod.POST)
	public @ResponseBody APIResponse deleteVillage(@RequestBody ObjectNode requestParams/*List<Map<String, String>> requestParams, ArrayList<Village> villageList*/) throws JsonParseException, JsonMappingException, IOException {
		
		final ObjectMapper mapper = new ObjectMapper();
	
		Village village = mapper.convertValue(requestParams, Village.class);
		String status = "success";
		String message ="Success to del village.";
		
		Object resObj = new Object();
		try {
			
			resObj = villageService.deleteVillage(village.getId());
			
		} catch (Exception exception) {
			exception.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(exception);
			resObj = GlobalFunction.getErrorCode(exception);
		}
	
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(resObj);
		
		return resp;
		
	}
	
	@RequestMapping(value = RestURIConstants.INS_UPD_VILLAGE, method = RequestMethod.POST)
	public @ResponseBody APIResponse insertOrUpdateVillage(@RequestBody ObjectNode requestParams/*List<Map<String, String>> requestParams, ArrayList<Village> villageList*/) throws JsonParseException, JsonMappingException, IOException {
		
		final ObjectMapper mapper = new ObjectMapper();
	
		//List<Village> villageList = mapper.readValue(requestParams.toString(), mapper.getTypeFactory().constructCollectionType(List.class, Village.class));
		Village village = mapper.convertValue(requestParams, Village.class);
		String status = "success";
		String message ="Success to insOrUpd village.";
		
		System.err.println("village : "+village.getId());
		
		Object resObj = new Object();
		try {
			
			resObj = villageService.insOrUpdVillage(village);
			
		} catch (Exception exception) {
			exception.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(exception);
			resObj = GlobalFunction.getErrorCode(exception);
		}
	
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(resObj);
		
		return resp;
		
	}
	
	@RequestMapping(value = RestURIConstants.GET_VILLAGE_NO_LIST_BY_HOSPITAL, method = RequestMethod.POST)
	public @ResponseBody APIResponse getVillageNoListByHospital(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final Village village = mapper.convertValue(requestParams, Village.class);
		
		List<Village> villageList = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting village No. by hospital code data.";
		
		try{
			villageList = villageService.listVillageNoByHospitalCode(village.getHospitalCode());
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
		resp.setResponse(villageList);
		
		return resp;
	}
}
