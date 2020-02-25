package com.fwg.asservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
import com.fwg.asservice.message.MessageSourceUtil;
import com.fwg.asservice.model.APIResponse;
import com.fwg.asservice.model.Hospital;
import com.fwg.asservice.model.Mail;
import com.fwg.asservice.service.HospitalService;
import com.fwg.asservice.service.MailService;
import com.fwg.asservice.service.SurveyService;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
@RequestMapping(RequestMappingConstants.HOSPITAL)
public class HospitalController {

	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private SurveyService surveyService ; 
	
	@RequestMapping(value = RestURIConstants.GET_HOSPITAL_LIST, method = RequestMethod.POST)
	public @ResponseBody APIResponse getAllHospitals(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final Hospital hospital = mapper.convertValue(requestParams, Hospital.class);
		
		List<Hospital> hospitalList = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting hospital data.";
		
		JSONArray jsonArray = new JSONArray();
		JSONObject object = null;
		
		try{
			hospitalList = hospitalService.listHospitals(hospital.getCode5());
			
			for(Hospital hospitalItem : hospitalList){
				object = new JSONObject();
				
				object.put("code5", hospitalItem.getCode5());
				object.put("code9", hospitalItem.getCode9());
				object.put("hospitalName", hospitalItem.getName());
				object.put("hospitalTypeCode", hospitalItem.getHospitalTypeCode());
				object.put("hospitalTypeName", hospitalItem.getHospitalType().getName());
				object.put("address", hospitalItem.getAddress());
				object.put("mooNo", hospitalItem.getMooNo());
				object.put("tumbolCode", hospitalItem.getTumbolCode());
				object.put("tumbolName", hospitalItem.getTumbol().getName());
				object.put("amphurCode", hospitalItem.getAmphurCode());
				object.put("amphurName", hospitalItem.getAmphur().getName());
				object.put("provinceCode", hospitalItem.getProvinceCode());
				object.put("provinceName", hospitalItem.getProvince().getName());
				object.put("telephone", hospitalItem.getTelephone());
				object.put("fax", hospitalItem.getFax());
				object.put("zipcode", hospitalItem.getZipCode());
				object.put("hospitalMasterCode", hospitalItem.getHospitalMasterCode());
				object.put("contactPrefix", hospitalItem.getContactPrefix());
				object.put("contactPrefixName", hospitalItem.getPrefix().getName());
				object.put("contactFirstName", hospitalItem.getContactFirstName());
				object.put("contactLastName", hospitalItem.getContactLastName());
				object.put("contactCitizenId", hospitalItem.getContactCitizenId());
				object.put("contactTelephone", hospitalItem.getContactTelephone());
				object.put("contactEmail", hospitalItem.getContactEmail());
				object.put("isActive", hospitalItem.isActive());
				object.put("isRegister", hospitalItem.isRegister());
				object.put("tokenId", hospitalItem.getTokenId());
				
				jsonArray.add(object);
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
		resp.setResponse(jsonArray);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.REGISTER_HOTPITAL, method = RequestMethod.POST)
	public @ResponseBody APIResponse getRegisterHospital(@RequestBody ObjectNode requestParams, HttpServletRequest request) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Hospital hospital = mapper.convertValue(requestParams, Hospital.class);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to register hospital.";
		
		JSONObject jsonObject = new JSONObject();
		String tokenId = null;
		String url = "";
		
		try {
			tokenId = hospitalService.registerHospital(hospital);
			jsonObject.put("tokenId", tokenId);
			
			try {
				url = GlobalFunction.getServerName(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(exception);
		}
		
		/* Send email*/
		try {
			List<Hospital> hospitals = null;
			hospitals = hospitalService.getHospitalByTokenId(tokenId);
			if(hospitals!=null && hospitals.size()>0) {
				Hospital hosp = new Hospital();
				hosp = hospitals.get(0);
			
				Mail mail = new Mail();
		        mail.setMailTo(hospital.getContactEmail());
		        mail.setMailSubject(MessageSourceUtil.getMessage("welcome"));
		 
		        Map < String, Object > model = new HashMap < String, Object > ();
		        model.put("hospitalName", hosp.getName());
		        model.put("firstName", hosp.getContactFirstName());
		        model.put("lastName", hosp.getContactLastName());
		        model.put("username", hosp.getCode5());
		        model.put("password", hosp.getCode5());
		        model.put("url", url);
		        model.put("tokenId", tokenId);
		        mail.setModel(model);
		 
		        MailService mailService = (MailService) appContext.getBean("mailService");
		        mailService.sendEmail(mail);
	        
			}
		} catch (Exception e) {
			e.printStackTrace();
//			message = GlobalFunction.getExceptionMessage(e);
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(jsonObject);
		
		return resp;
		
	}
	
	@RequestMapping(value = RestURIConstants.ACTIVATE_HOTPITAL, method = RequestMethod.POST)
	public @ResponseBody APIResponse getConfirmHospital(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Hospital hospital = mapper.convertValue(requestParams, Hospital.class);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to active hospital.";
		
		//JSONObject jsonObject = new JSONObject();
		List<Hospital> hospitals = null;
		try {
			hospitals = hospitalService.getHospitalByTokenId(hospital.getTokenId());
			if(hospitals!=null && hospitals.size()>0) {
				Hospital hosp = new Hospital();
				hosp = hospitals.get(0);
				String password = GlobalFunction.generatePassword(hosp.getCode5(), hosp.getCode5());
				hospitalService.activeHospital(hosp.getTokenId(),password);
				surveyService.autoGenSurveyHearderOfYear(hosp.getCode5());
			}else {
				status = APIStatusMessage.FAILED;
				message = "Failed Activate Hospital Or Hospital is Activated.";
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(exception);
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(hospitals);
		
		return resp;
	}
	
	@PostMapping(value = RestURIConstants.GET_HOSPITALNAME_LIST)
	public @ResponseBody APIResponse listHospitalsIsActiveAll(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Hospital hospital = mapper.convertValue(requestParams, Hospital.class);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to get hospitals name.";
		
		JSONArray arr = new JSONArray();
		JSONObject jsonObject = null;
		List<Hospital> hospitals = null;
		try {
			hospitals = hospitalService.listHospitalsNameAll(hospital.getCode5());
			if(hospitals!=null && hospitals.size()>0) {
				for (Hospital hosp : hospitals) {
					jsonObject = new JSONObject();
					jsonObject.put("hospitalName", hosp.getName());
					jsonObject.put("isRegister", hosp.isRegister());
					arr.add(jsonObject);
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(exception);
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(arr);
		
		return resp;
	}
}