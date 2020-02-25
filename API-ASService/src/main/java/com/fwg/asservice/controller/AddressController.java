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
import com.fwg.asservice.model.Amphur;
import com.fwg.asservice.model.Province;
import com.fwg.asservice.model.Tumbol;
import com.fwg.asservice.service.AddressService;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;


/**
 * Handles requests for the Employee service.
 */
@Controller
@RequestMapping(RequestMappingConstants.ADDRESS)
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@RequestMapping(value = RestURIConstants.GET_PROVINCE, method = RequestMethod.POST)
	public @ResponseBody APIResponse getAllProvinces(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Province province = mapper.convertValue(requestParams, Province.class);
		
		List<Province> provinceList = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Province data.";
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		try {
			provinceList = addressService.listProvinces(province.getCodeId());
			
			for (Province provinceValue : provinceList) {
				jsonObject = new JSONObject();
				jsonObject.put("codeId", provinceValue.getCodeId());
				jsonObject.put("name", provinceValue.getName());
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
	
	@RequestMapping(value = RestURIConstants.GET_AMPHUR, method = RequestMethod.POST)
	public @ResponseBody APIResponse getAmphur(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Amphur amphur = mapper.convertValue(requestParams, Amphur.class);
		
		List<Amphur> amphurList = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Amphur data.";
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		try {
			amphurList = addressService.listAmphurs(amphur.getProvinceCode());
			
			for (Amphur amphurValue : amphurList) {
				jsonObject = new JSONObject();
				jsonObject.put("code", amphurValue.getCode());
				jsonObject.put("name", amphurValue.getName());
				jsonObject.put("provinceCode", amphurValue.getProvinceCode());
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
	
	@RequestMapping(value = RestURIConstants.GET_TUMBOL,  method = RequestMethod.POST)
	public @ResponseBody APIResponse getTombol(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Tumbol tumbol = mapper.convertValue(requestParams, Tumbol.class);
		
		List<Tumbol> tumbolList = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Tumbol data.";
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		try {
			tumbolList = addressService.listTumbols(tumbol.getAmphurCode());
			
			for (Tumbol tumbolValue : tumbolList) {
				jsonObject = new JSONObject();
				jsonObject.put("codeId", tumbolValue.getCodeId());
				jsonObject.put("name", tumbolValue.getName());
				jsonObject.put("amphurCode", tumbolValue.getAmphurCode());
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
