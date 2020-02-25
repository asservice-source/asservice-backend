package com.fwg.asservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.Village;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.MetabolicDetailInfo;
import com.fwg.asservice.service.OSMService;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
@RequestMapping(RequestMappingConstants.OSM)
public class OSMController {
	
	@Autowired
	private OSMService osmService;
	
	@RequestMapping(value = RestURIConstants.GET_OSM_BY_VILLAGE, method = RequestMethod.POST)
	public @ResponseBody APIResponse getOSMByVillage(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		
		
		List<Person> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting OSM. by Village data.";
		
		JSONArray array = new JSONArray();
		JSONObject object = null;
		
		try{
			listResult = osmService.listOSMByVillageID(filter.getVillageId());
			
			for(Person listItem : listResult){
				object = new JSONObject();
				object.put("personId", listItem.getPersonId());
				object.put("fullName", GlobalFunction.generateFullName(listItem));
				object.put("citizenId", listItem.getCitizenId());
				array.add(object);
			}
			
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
		resp.setResponse(array);
		
		return resp;
	}
}
