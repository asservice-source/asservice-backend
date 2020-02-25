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
import com.fwg.asservice.constants.SurveyConstants;
import com.fwg.asservice.message.MessageSourceUtil;
import com.fwg.asservice.model.APIResponse;
import com.fwg.asservice.model.survey.ContainerLocateType;
import com.fwg.asservice.model.survey.ContainerType;
import com.fwg.asservice.model.survey.MetabolicDetailInfo;
import com.fwg.asservice.model.survey.MonitorHICIDetailInfo;
import com.fwg.asservice.service.SurveyHICIService;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
@RequestMapping(RequestMappingConstants.SURVEY_HICI)
public class SurveyHICIController {
	@Autowired
	private SurveyHICIService surveyHICIService;
	
	@RequestMapping(value = RestURIConstants.SEARCH_HICI_INFO_LIST, method = RequestMethod.POST)  
	public @ResponseBody APIResponse searchHICIList(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		//final MetabolicDetail metabolicDetail = mapper.convertValue(requestParams, MetabolicDetail.class);
		
		String documentId = requestParams.get("documentId").textValue();
		Integer villageId = GlobalFunction.isEmpty(requestParams.get("villageId").textValue()) ? null : Integer.valueOf(requestParams.get("villageId").textValue());
		String homeTypeCode = requestParams.get("homeTypeCode").textValue();
		String osmId = requestParams.get("osmId").textValue();
		Integer homeId = GlobalFunction.isEmpty(requestParams.get("homeId").textValue()) ? null : Integer.valueOf(requestParams.get("homeId").textValue());
		
		List<MonitorHICIDetailInfo> list = null;		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting HICI list data.";
		
		List<ContainerType> listContainerType = null;
		
		JSONArray array = new JSONArray();
		JSONObject object = null;
		
		try {
			list = surveyHICIService.listMonitorHICIDetailInfo(documentId, villageId, homeTypeCode, osmId, homeId);
			
			for(MonitorHICIDetailInfo listItem : list){
				
				object = new JSONObject();
				//object.put("rowGUID", listItem.getRowGUID());
				object.put("documentId", listItem.getDocumentId());
				object.put("osmId", listItem.getOsmId());
				object.put("operationDate", listItem.getOperationDate());
				object.put("homeId", listItem.getHomeId());
				object.put("homeNo", listItem.getHome().getHomeNo());
				//object.put("containerId", listItem.getContainerId());
				object.put("totalSurvey", listItem.getTotalSurvey());
				object.put("totalDetect", listItem.getTotalDetect());
				object.put("locateTypeId", listItem.getLocateTypeId());
				object.put("isDeleted", listItem.isDeleted());
				object.put("address", GlobalFunction.generateAddress(listItem.getHome()));
				object.put("latitude", listItem.getHome().getLatitude());
				object.put("longitude", listItem.getHome().getLongitude());
				object.put("homeTypeCode", listItem.getHome().getHomeTypeCode());
				if(SurveyConstants.HOME_TYPE_OF_HOME.contains(listItem.getHome().getHomeTypeCode())){
					object.put("name", listItem.getHome().getHomeNo());
					object.put("homeTypeName", MessageSourceUtil.getMessage("home"));
				}else{
					object.put("name", listItem.getHome().getName());
					object.put("homeTypeName", listItem.getHome().getHomeType().getName());
				}
				
				/*listContainerType = surveyHICIService.listContainerTypeByHomeID(documentId, listItem.getHomeId());
				JSONArray typeList = new JSONArray();
				for(ContainerType type : listContainerType){
					JSONObject typeObject = new JSONObject();
					typeObject.put("containerTypeId", type.getId());
					typeObject.put("containerTypeName", type.getName());
					typeObject.put("documentId", listItem.getDocumentId());
					typeObject.put("homeId", listItem.getHomeId());
					typeObject.put("osmId", listItem.getOsmId());
					String totalSurvey = "0";
					String totalDetect = "0";
					String locateTypeId = null;
					
					if(!GlobalFunction.isEmpty(type.getDescription())){
						totalSurvey = type.getDescription().split("/")[0];
						totalDetect = type.getDescription().split("/")[1];
						locateTypeId = type.getDescription().split("/")[2];
					}
					typeObject.put("totalSurvey", totalSurvey);
					typeObject.put("totalDetect", totalDetect);
					typeObject.put("locateTypeId", locateTypeId);
					typeList.add(typeObject);
				}
				
				object.put("listContainerType", typeList);*/
				
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
	
	@RequestMapping(value = RestURIConstants.SEARCH_HICI_INFO_LIST_NOT_SURVEY, method = RequestMethod.POST)  
	public @ResponseBody APIResponse searchHICIListNotSurvey(@RequestBody ObjectNode requestParams) {

		String documentId = requestParams.get("documentId").textValue();
		String osmId = requestParams.get("osmId").textValue();
		
		List<MonitorHICIDetailInfo> list = null;		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting HICI SEARCH_HICI_INFO_LIST_NOT_SURVEY data.";
		
		List<ContainerType> listContainerType = null;
		
		JSONArray array = new JSONArray();
		JSONObject object = null;
		
		try {
			list = surveyHICIService.listMonitorHICIDetailInfoNotSurvey(documentId, osmId);
			
			for(MonitorHICIDetailInfo listItem : list){
				
				object = new JSONObject();
				//object.put("rowGUID", listItem.getRowGUID());
				object.put("documentId", listItem.getDocumentId());
				object.put("osmId", listItem.getOsmId());
				object.put("operationDate", listItem.getOperationDate());
				object.put("homeId", listItem.getHomeId());
				object.put("homeNo", listItem.getHome().getHomeNo());
				//object.put("containerId", listItem.getContainerId());
				object.put("totalSurvey", listItem.getTotalSurvey());
				object.put("totalDetect", listItem.getTotalDetect());
				object.put("locateTypeId", listItem.getLocateTypeId());
				object.put("isDeleted", listItem.isDeleted());
				object.put("address", GlobalFunction.generateAddress(listItem.getHome()));
				object.put("homeTypeCode", listItem.getHome().getHomeTypeCode());
				object.put("longitude", listItem.getHome().getLongitude());
				object.put("homeTypeCode", listItem.getHome().getHomeTypeCode());
				
				object.put("villageNo", listItem.getHome().getVillage().getVillageNo());
				String mooNo = "";
				if(!GlobalFunction.isEmpty(listItem.getHome().getVillage().getHospital().getMooNo())) {
					mooNo = listItem.getHome().getVillage().getHospital().getMooNo();
				}
				object.put("mooNo", mooNo);
				object.put("tumbolCode", listItem.getHome().getVillage().getHospital().getTumbol().getCodeId());
				object.put("tumbolName", listItem.getHome().getVillage().getHospital().getTumbol().getName());
				object.put("amphurCode", listItem.getHome().getVillage().getHospital().getAmphur().getCode());
				object.put("amphurName", listItem.getHome().getVillage().getHospital().getAmphur().getName());
				object.put("provinceCode", listItem.getHome().getVillage().getHospital().getProvince().getCodeId());
				object.put("provinceName", listItem.getHome().getVillage().getHospital().getProvince().getName());
				object.put("zipcode", listItem.getHome().getVillage().getHospital().getZipCode());
				
				if(SurveyConstants.HOME_TYPE_OF_HOME.contains(listItem.getHome().getHomeTypeCode())){
					object.put("name", listItem.getHome().getHomeNo());
					object.put("homeTypeName", MessageSourceUtil.getMessage("home"));
				}else{
					object.put("name", listItem.getHome().getName());
					object.put("homeTypeName", listItem.getHome().getHomeType().getName());
				}
				
				
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
	
	@RequestMapping(value = RestURIConstants.HICI_BY_HOMEID, method = RequestMethod.POST)  
	public @ResponseBody APIResponse HICIbyRowGUID(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final MetabolicDetailInfo metabolicDetail = mapper.convertValue(requestParams, MetabolicDetailInfo.class);
		
		//String rowGUID = requestParams.get("rowGUID").textValue();
		
		MonitorHICIDetailInfo list = null;		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting HICIbyRowGUID data.";
		
		List<ContainerType> listContainerType = null;
		
		JSONArray array = new JSONArray();
		JSONObject object = null;
		
		try {
			list = surveyHICIService.monitorHICIDetailInfoByHomeID(metabolicDetail.getDocumentId(), metabolicDetail.getHomeId());
			
			//for(MonitorHICIDetailInfo listItem : list){
				
				object = new JSONObject();
				object.put("rowGUID", list.getRowGUID());
				object.put("documentId", list.getDocumentId());
				object.put("osmId", list.getOsmId());
				object.put("operationDate", list.getOperationDate());
				object.put("homeId", list.getHomeId());
				object.put("homeNo", list.getHome().getHomeNo());
				//object.put("containerId", listItem.getContainerId());
				object.put("totalSurvey", list.getTotalSurvey());
				object.put("totalDetect", list.getTotalDetect());
				object.put("locateTypeId", list.getLocateTypeId());
				object.put("isDeleted", list.isDeleted());
				object.put("address", GlobalFunction.generateAddress(list.getHome()));
				object.put("latitude", list.getHome().getLatitude());
				object.put("longitude", list.getHome().getLongitude());
				object.put("homeTypeCode", list.getHome().getHomeTypeCode());
				
				object.put("villageNo", list.getHome().getVillage().getVillageNo());
				String mooNo = "";
				if(!GlobalFunction.isEmpty(list.getHome().getVillage().getHospital().getMooNo())) {
					mooNo = list.getHome().getVillage().getHospital().getMooNo();
				}
				object.put("mooNo", mooNo);
				object.put("tumbolCode", list.getHome().getVillage().getHospital().getTumbol().getCodeId());
				object.put("tumbolName", list.getHome().getVillage().getHospital().getTumbol().getName());
				object.put("amphurCode", list.getHome().getVillage().getHospital().getAmphur().getCode());
				object.put("amphurName", list.getHome().getVillage().getHospital().getAmphur().getName());
				object.put("provinceCode", list.getHome().getVillage().getHospital().getProvince().getCodeId());
				object.put("provinceName", list.getHome().getVillage().getHospital().getProvince().getName());
				object.put("zipcode", list.getHome().getVillage().getHospital().getZipCode());
				
				if(SurveyConstants.HOME_TYPE_OF_HOME.contains(list.getHome().getHomeTypeCode())){
					object.put("name", list.getHome().getHomeNo());
					object.put("homeTypeName", MessageSourceUtil.getMessage("home"));
				}else{
					object.put("name", list.getHome().getName());
					object.put("homeTypeName", list.getHome().getHomeType().getName());
				}
				
				listContainerType = surveyHICIService.listContainerTypeByHomeID(list.getDocumentId(), list.getHomeId());
				JSONArray typeList = new JSONArray();
				for(ContainerType type : listContainerType){
					JSONObject typeObject = new JSONObject();
					typeObject.put("containerTypeId", type.getId());
					typeObject.put("containerTypeName", type.getName());
					typeObject.put("documentId", list.getDocumentId());
					typeObject.put("homeId", list.getHomeId());
					typeObject.put("osmId", list.getOsmId());
					String totalSurvey = "0";
					String totalDetect = "0";
					String locateTypeId = null;
					
					if(!GlobalFunction.isEmpty(type.getDescription())){
						totalSurvey = type.getDescription().split("/")[0];
						totalDetect = type.getDescription().split("/")[1];
						locateTypeId = type.getDescription().split("/")[2];
					}
					typeObject.put("totalSurvey", totalSurvey);
					typeObject.put("totalDetect", totalDetect);
					typeObject.put("locateTypeId", locateTypeId);
					typeList.add(typeObject);
				}
				
				object.put("listContainerType", typeList);
			//}
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
	
	
	@RequestMapping(value = RestURIConstants.GET_CONTAINER_TYPE_LIST, method = RequestMethod.POST)  
	public @ResponseBody APIResponse ContainerTypeList(@RequestBody ObjectNode requestParams) {
			
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting ContainerTypeList data.";
		
		List<ContainerType> listContainerType = null;
		
		JSONArray array = new JSONArray();
		JSONObject object = null;
		
		try {
			listContainerType = surveyHICIService.listContainerTypeAll();
			
			for(ContainerType listItem : listContainerType){
				
				object = new JSONObject();
				
				object.put("containerTypeId", listItem.getId());
				object.put("containerTypeName", listItem.getName());
				object.put("description", listItem.getDescription());
				object.put("imagePath", listItem.getImagePath());
				object.put("isActive", listItem.isActive());
				
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
	
	@RequestMapping(value = RestURIConstants.GET_CONTAINER_LOCATE_TYPE_LIST, method = RequestMethod.POST)  
	public @ResponseBody APIResponse ContainerLocatTypeList(@RequestBody ObjectNode requestParams) {
			
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting ContainerLocateTypeList data.";
		
		List<ContainerLocateType> listContainerLocateType = null;
		
		JSONArray array = new JSONArray();
		JSONObject object = null;
		
		try {
			listContainerLocateType = surveyHICIService.listContainerLocatTypeAll();
			
			for(ContainerLocateType listItem : listContainerLocateType){
				
				object = new JSONObject();
				
				object.put("containerTypeId", listItem.getId());
				object.put("containerTypeName", listItem.getName());
				
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
	
	@RequestMapping(value = RestURIConstants.INS_UPD_HICI_INFO, method = RequestMethod.POST)
	public @ResponseBody APIResponse hiciInsertOrUpdate(@RequestBody ObjectNode requestParams) { 
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to insert or update HICI Info.";
		
		JSONArray arr = new JSONArray();
		
		try {
			
			List<MonitorHICIDetailInfo> monitorHICIDetailInfoList = mapper.readValue(requestParams.get("listContainerType").toString(), mapper.getTypeFactory().constructCollectionType(List.class, MonitorHICIDetailInfo.class));
			
			for(MonitorHICIDetailInfo items : monitorHICIDetailInfoList){
				System.out.println("DoccumentID : "+items.getDocumentId());
				System.out.println("HomeID : "+items.getHomeId());
				System.out.println("ContainerTypeID : "+items.getContainerId());
				System.out.println("LocateTypeID : "+items.getLocateTypeId());
				System.out.println("TotalSurvey : "+items.getTotalSurvey());
				System.out.println("TotalDetect : "+items.getTotalDetect());
				
				surveyHICIService.monitorHICIInsOrUpd(items);
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
		resp.setResponse(arr);

		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.DEL_HICI_INFO, method = RequestMethod.POST)
	public @ResponseBody APIResponse hiciDelete(@RequestBody ObjectNode requestParams) { 
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to delete HICI Info.";
		
		JSONArray arr = new JSONArray();
		
		try {
			
			MonitorHICIDetailInfo monitorHICIDetailInfo = mapper.convertValue(requestParams, MonitorHICIDetailInfo.class);
			
			surveyHICIService.monitorHICIDelete(monitorHICIDetailInfo);
			
			
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
		resp.setResponse(arr);

		return resp;
	}
}
