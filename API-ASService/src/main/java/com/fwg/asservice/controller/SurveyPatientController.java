package com.fwg.asservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.fwg.asservice.constants.RequestMappingConstants;
import com.fwg.asservice.constants.RestURIConstants;
import com.fwg.asservice.constants.APIStatusMessage;
import com.fwg.asservice.model.APIResponse;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.model.survey.PatientDetailInfo;
import com.fwg.asservice.service.SurveyPatientService;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
@RequestMapping(RequestMappingConstants.SURVEY_PATIENT)
public class SurveyPatientController {

	@Autowired
	private SurveyPatientService surveyPatientService;
	
	@PostMapping(RestURIConstants.SURVEY_PATIENT_PATIENTDETAILINFO_FILTER)
	public @ResponseBody APIResponse find(@RequestBody ObjectNode requestParams) {
		
		APIResponse resp = new APIResponse();
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Filter filter = mapper.convertValue(requestParams, Filter.class);

		List<PatientDetailInfo> patientDetailInfos = null;
		
		try {
			patientDetailInfos = surveyPatientService.patientDetailInfoList(filter);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(APIStatusMessage.FAILED);
			resp.setMessage(e.getMessage());
			resp.setParam(requestParams);
			resp.setDate(GlobalFunction.currentTimeStamp());
			resp.setResponse(new JSONObject());
			return resp;
		}
		

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonPatientDetailInfo = null;
		
		if(patientDetailInfos != null && patientDetailInfos.size()>0) {
			for (PatientDetailInfo info : patientDetailInfos) {
				jsonPatientDetailInfo =  new JSONObject();
				jsonPatientDetailInfo.put("rowGUID", info.getRowGUID());
				jsonPatientDetailInfo.put("documentId", info.getDocumentId());
				jsonPatientDetailInfo.put("osmId", info.getOsmId());
				jsonPatientDetailInfo.put("operationDate", info.getOperationDate());
				jsonPatientDetailInfo.put("patientDate", info.getPatientDate());
				jsonPatientDetailInfo.put("homeId", info.getHomeId());
				jsonPatientDetailInfo.put("homeName", info.getHome().getName());
				jsonPatientDetailInfo.put("personId", info.getPersonId());
				jsonPatientDetailInfo.put("genderName", GlobalFunction.isEmpty(info.getPerson().getGender())?"":info.getPerson().getGender().getName());
				jsonPatientDetailInfo.put("remark", info.getRemark());
				jsonPatientDetailInfo.put("cancerTypeId", info.getCancerTypeId());
				jsonPatientDetailInfo.put("cancerTypeName", info.getCancerType().getName());
				jsonPatientDetailInfo.put("treatmentPlace", info.getTreatmentPlace());
				jsonPatientDetailInfo.put("hInsuranceTypeId", info.gethInsuranceTypeId());
				jsonPatientDetailInfo.put("hInsuranceTypeName", info.getHealthInsuranceType().getName());
				jsonPatientDetailInfo.put("disabilityTypeId", info.getDisabilityTypeId());
				jsonPatientDetailInfo.put("disabilityTypeName", info.getDisabilityType().getName());
				jsonPatientDetailInfo.put("telephone", info.getTelephone());
				jsonPatientDetailInfo.put("isDeleted", info.isDeleted());
				jsonPatientDetailInfo.put("fullName",GlobalFunction.generateFullName(info.getPerson()));
				jsonPatientDetailInfo.put("citizenId",info.getPerson().getCitizenId());
				jsonPatientDetailInfo.put("disabilityCauseTypeId", info.getDisabilityCauseTypeId());
				jsonPatientDetailInfo.put("disabilityCauseTypeName", info.getDisabilityCauseType().getName());
				jsonPatientDetailInfo.put("prefixCode", info.getPerson().getPrefixCode());
				jsonPatientDetailInfo.put("prefixName", info.getPerson().getPrefix().getName());
				jsonPatientDetailInfo.put("age", GlobalFunction.getAgeFromBirthDate(info.getPerson().getBirthDate()));
				jsonPatientDetailInfo.put("patientTypeId", info.getPatientTypeId());
				jsonPatientDetailInfo.put("patientTypeName", info.getPatientType().getName());
				jsonPatientDetailInfo.put("patientSurveyTypeCode", info.getPatientSurveyTypeCode());
				jsonPatientDetailInfo.put("patientSurveyTypeName", info.getPatientSurveyType().getName());
				jsonPatientDetailInfo.put("diseaseStatusTypeId", info.getDiseaseStatusTypeId());
				jsonPatientDetailInfo.put("diseaseStatusTypeName", info.getDiseaseStatusType().getName());
				jsonPatientDetailInfo.put("address", GlobalFunction.generateAddress(info.getPerson()));
				if (GlobalFunction.isEmpty(info.getLatitude())) {
					jsonPatientDetailInfo.put("latitude", info.getHome().getLatitude());
				} else {
					jsonPatientDetailInfo.put("latitude", info.getLatitude());
				}
				if (GlobalFunction.isEmpty(info.getLongitude())) {
					jsonPatientDetailInfo.put("longitude", info.getHome().getLongitude());
				} else {
					jsonPatientDetailInfo.put("longitude", info.getLongitude());
				}
				jsonPatientDetailInfo.put("picturePath", info.getPerson().getPicturePath());

				jsonArray.add(jsonPatientDetailInfo);
			}
			
		}
		resp.setStatus(APIStatusMessage.SUCCESS);
		resp.setMessage("find Survey PatientDetailInfo data has Success...");
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(jsonArray);
		
		return resp;
	}
	
	@PostMapping(RestURIConstants.SURVEY_PATIENT_PATIENTDETAILINFO_BY_ROWGUID)
	public @ResponseBody APIResponse patientDetailInfoByRowGUID(@RequestBody ObjectNode requestParams) {
		
		APIResponse resp = new APIResponse();
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Filter filter = mapper.convertValue(requestParams, Filter.class);

		PatientDetailInfo info = null;
		
		try {
			info = surveyPatientService.patientDetailInfoByRowGUID(filter.getRowGUID());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(APIStatusMessage.FAILED);
			resp.setMessage("find Survey PatientDetailInfo data has Failure...");
			resp.setParam(requestParams);
			resp.setDate(GlobalFunction.currentTimeStamp());
			resp.setResponse(new JSONObject());
			return resp;
		}
		

		JSONObject jsonPatientDetailInfo = null;
		
		if(info != null) {
			//for (PatientDetailInfo info : patientDetailInfos) {
				jsonPatientDetailInfo =  new JSONObject();
				jsonPatientDetailInfo.put("rowGUID", info.getRowGUID());
				jsonPatientDetailInfo.put("documentId", info.getDocumentId());
				jsonPatientDetailInfo.put("osmId", info.getOsmId());
				jsonPatientDetailInfo.put("operationDate", info.getOperationDate());
				jsonPatientDetailInfo.put("patientDate", info.getPatientDate());
				jsonPatientDetailInfo.put("homeId", info.getHomeId());
				jsonPatientDetailInfo.put("homeName", info.getHome().getName());
				jsonPatientDetailInfo.put("personId", info.getPersonId());
				jsonPatientDetailInfo.put("genderName", GlobalFunction.isEmpty(info.getPerson().getGender())?"":info.getPerson().getGender().getName());
				jsonPatientDetailInfo.put("remark", info.getRemark());
				jsonPatientDetailInfo.put("cancerTypeId", info.getCancerTypeId());
				jsonPatientDetailInfo.put("cancerTypeName", info.getCancerType().getName());
				jsonPatientDetailInfo.put("treatmentPlace", info.getTreatmentPlace());
				jsonPatientDetailInfo.put("hInsuranceTypeId", info.gethInsuranceTypeId());
				jsonPatientDetailInfo.put("hInsuranceTypeName", info.getHealthInsuranceType().getName());
				jsonPatientDetailInfo.put("disabilityTypeId", info.getDisabilityTypeId());
				jsonPatientDetailInfo.put("disabilityTypeName", info.getDisabilityType().getName());
				jsonPatientDetailInfo.put("telephone", info.getTelephone());
				jsonPatientDetailInfo.put("longitude", info.getLongitude());
				jsonPatientDetailInfo.put("latitude", info.getLatitude());
				jsonPatientDetailInfo.put("isDeleted", info.isDeleted());
				jsonPatientDetailInfo.put("fullName",GlobalFunction.generateFullName(info.getPerson()));
				jsonPatientDetailInfo.put("citizenId",info.getPerson().getCitizenId());
				jsonPatientDetailInfo.put("disabilityCauseTypeId", info.getDisabilityCauseTypeId());
				jsonPatientDetailInfo.put("disabilityCauseTypeName", info.getDisabilityCauseType().getName());
				jsonPatientDetailInfo.put("prefixCode", info.getPerson().getPrefixCode());
				jsonPatientDetailInfo.put("prefixName", info.getPerson().getPrefix().getName());
				jsonPatientDetailInfo.put("age", GlobalFunction.getAgeFromBirthDate(info.getPerson().getBirthDate()));
				jsonPatientDetailInfo.put("patientTypeId", info.getPatientTypeId());
				jsonPatientDetailInfo.put("patientTypeName", info.getPatientType().getName());
				jsonPatientDetailInfo.put("patientSurveyTypeCode", info.getPatientSurveyTypeCode());
				jsonPatientDetailInfo.put("patientSurveyTypeName", info.getPatientSurveyType().getName());
				jsonPatientDetailInfo.put("diseaseStatusTypeId", info.getDiseaseStatusTypeId());
				jsonPatientDetailInfo.put("diseaseStatusTypeName", info.getDiseaseStatusType().getName());
				jsonPatientDetailInfo.put("address", GlobalFunction.generateAddress(info.getPerson()));
				if (GlobalFunction.isEmpty(info.getLatitude())) {
					jsonPatientDetailInfo.put("latitude", info.getHome().getLatitude());
				} else {
					jsonPatientDetailInfo.put("latitude", info.getLatitude());
				}
				if (GlobalFunction.isEmpty(info.getLongitude())) {
					jsonPatientDetailInfo.put("longitude", info.getHome().getLongitude());
				} else {
					jsonPatientDetailInfo.put("longitude", info.getLongitude());
				}
				jsonPatientDetailInfo.put("picturePath", info.getPerson().getPicturePath());
			//}
			
		}
		resp.setStatus(APIStatusMessage.SUCCESS);
		resp.setMessage("find Survey PatientDetailInfo data has Success...");
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(jsonPatientDetailInfo);
		
		return resp;
	}
	
	@PostMapping(RestURIConstants.SURVEY_PATIENT_PATIENTDETAILINFO_DELETE)
	public @ResponseBody APIResponse delete(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		PatientDetailInfo patientDetailInfo = mapper.convertValue(requestParams, PatientDetailInfo.class);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Delete Survey PatientDetailInfo data has Success...";
		boolean delete = false;

		Map<String, Object> map = new HashMap<>();
		APIResponse resp = new APIResponse();
		
		if(GlobalFunction.isEmpty(patientDetailInfo.getRowGUID())) {
			
			map.put("rowGUID", patientDetailInfo.getRowGUID());
			
			resp.setStatus(APIStatusMessage.FAILED);
			resp.setMessage("Param \"rowGUID\" not found");
			resp.setParam(map);
			resp.setDate(GlobalFunction.currentTimeStamp());
			resp.setResponse(new JSONObject());
			
			return resp;
		}
		
		try {
			patientDetailInfo.setDeleted(true);
			surveyPatientService.patientDetailInfoInsertOrUpdate(patientDetailInfo);
			delete = true;
			
		} catch (Exception ex) {
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}
		
		map.put("rowGUID", patientDetailInfo.getRowGUID());
		map.put("delete", delete);
		
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(map);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(delete);
		
		return resp;
	}
	
	@PostMapping(RestURIConstants.SURVEY_PATIENT_PATIENTDETAILINFO_INS_UPD)
	public @ResponseBody APIResponse InsertOrUpdate(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		PatientDetailInfo patientDetailInfo = mapper.convertValue(requestParams, PatientDetailInfo.class);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to Insert or Update Survey Patient data.";

		APIResponse resp = new APIResponse();
		
		if(GlobalFunction.isEmpty(patientDetailInfo.getPatientSurveyTypeCode())) {
			resp.setStatus(APIStatusMessage.FAILED);
			resp.setMessage("Param \"PatientSurveyTypeCode\" not found");
			resp.setParam(requestParams);
			resp.setDate(GlobalFunction.currentTimeStamp());
			resp.setResponse(new JSONObject());
			
			return resp;
		}
		
		try {
			patientDetailInfo.setDeleted(false);
			patientDetailInfo = surveyPatientService.patientDetailInfoInsertOrUpdate(patientDetailInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}
		
		JSONObject response = new JSONObject();
		response.put("rowGUID",patientDetailInfo.getRowGUID());
		response.put("personId",patientDetailInfo.getPersonId());
		response.put("documentId",patientDetailInfo.getDocumentId());
		response.put("osmId",patientDetailInfo.getOsmId());
		response.put("operationDate",patientDetailInfo.getOperationDate());
		response.put("homeId",patientDetailInfo.getHomeId());
		response.put("cancerTypeId",patientDetailInfo.getCancerTypeId());
		response.put("diseaseStatusTypeId",patientDetailInfo.getDiseaseStatusTypeId());
		response.put("patientDate",patientDetailInfo.getPatientDate());
		response.put("patientTypeId",patientDetailInfo.getPatientTypeId());
		response.put("hInsuranceTypeId",patientDetailInfo.gethInsuranceTypeId());
		response.put("patientSurveyTypeCode",patientDetailInfo.getPatientSurveyTypeCode());
		response.put("disabilityTypeId",patientDetailInfo.getDisabilityTypeId());
		response.put("disabilityCauseTypeId",patientDetailInfo.getDisabilityCauseTypeId());
		response.put("treatmentPlace",patientDetailInfo.getTreatmentPlace());
		response.put("remark",patientDetailInfo.getRemark());
		response.put("telephone",patientDetailInfo.getTelephone());
		response.put("latitude",patientDetailInfo.getLatitude());
		response.put("longitude",patientDetailInfo.getLongitude());
	
		
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(response);
		
		return resp;
	} 
}