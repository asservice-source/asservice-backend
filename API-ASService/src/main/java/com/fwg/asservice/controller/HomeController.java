package com.fwg.asservice.controller;

import java.util.Arrays;
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
import com.fwg.asservice.constants.SurveyConstants;
import com.fwg.asservice.message.MessageSourceUtil;
import com.fwg.asservice.model.APIResponse;
import com.fwg.asservice.model.FamilyStatus;
import com.fwg.asservice.model.Home;
import com.fwg.asservice.model.HomeType;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.service.HomeService;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
@RequestMapping(RequestMappingConstants.HOME)
public class HomeController {
	
	private final String HOME_TYPE_CODE_01 = "01";
	
	@Autowired
	private HomeService homeService;
	
	@PostMapping(RestURIConstants.GET_HOMEINFO_WITH_OSM_BY_HOMEID)
	public @ResponseBody APIResponse getHomeInfoWithOsm(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to get Home info.";
		
		JSONObject jsonObject = new JSONObject();
		
		APIResponse resp = new APIResponse();
		
		Home home = null;
	
		try {
			home = homeService.getHomeInfoWithOsm(filter.getHomeId());
			if(home!=null) {
				jsonObject.put("homeTypeCode", GlobalFunction.isEmpty(home.getHomeType())?null:home.getHomeType().getCode());
				jsonObject.put("homeTypeName", GlobalFunction.isEmpty(home.getHomeType())?null:home.getHomeType().getName());
				jsonObject.put("registrationId", home.getRegistrationId());
				jsonObject.put("homeId", home.getId());
				jsonObject.put("homeNo", home.getHomeNo());
				jsonObject.put("name", home.getName());
				jsonObject.put("road", home.getRoad());
				jsonObject.put("soi", home.getSoi());
				jsonObject.put("telephone", home.getTelephone());
				jsonObject.put("latitude", home.getLatitude());
				jsonObject.put("longitude", home.getLongitude());
				jsonObject.put("villageNo", GlobalFunction.isEmpty(home.getVillage())?null:home.getVillage().getVillageNo());
				jsonObject.put("villageName", GlobalFunction.isEmpty(home.getVillage())?null:home.getVillage().getVillageName());
				jsonObject.put("address", GlobalFunction.generateAddress(home));
				jsonObject.put("hospitalName",home.getVillage().getHospital().getName());
				jsonObject.put("provinceCode",home.getVillage().getHospital().getProvinceCode());
				jsonObject.put("amphurCode",home.getVillage().getHospital().getAmphurCode());
				jsonObject.put("tumbolCode",home.getVillage().getHospital().getTumbolCode());
				jsonObject.put("zipcode",home.getVillage().getHospital().getZipCode());
				if(home.getHolder()!=null) {
					jsonObject.put("holderFullName", GlobalFunction.generateFullName(home.getHolder()));
					jsonObject.put("holderId", home.getHolder().getPersonId());
				}
				if(!GlobalFunction.isEmpty(home.getOsm())) {
					jsonObject.put("osmDeadDate", home.getOsm().getDeadDate());
					jsonObject.put("osmIsDead", home.getOsm().isDead());
					jsonObject.put("osmPassport", home.getOsm().getPassport());
					jsonObject.put("osmVStatusName", GlobalFunction.isEmpty(home.getOsm().getVillageStatus())?null:home.getOsm().getVillageStatus().getName());
					jsonObject.put("osmId", home.getOsm().getPersonId());
					jsonObject.put("osmCitizenId", home.getOsm().getCitizenId());
					jsonObject.put("osmFullName", GlobalFunction.generateFullName(home.getOsm()));
					jsonObject.put("osmNickName", home.getOsm().getNickName());
					jsonObject.put("osmGender",GlobalFunction.isEmpty(home.getOsm().getGender())?null: home.getOsm().getGender().getName());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}
		
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(jsonObject);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_HOME_NO_LIST_BY_VILLAGE_OR_OSM, method = RequestMethod.POST)
	public @ResponseBody APIResponse getHomeNoListByVillageOrOSM(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		List<Home> homeList = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Home data.";
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		try {
			homeList = homeService.listHomeByVillageOrOSM(filter.getVillageId(), filter.getOsmId(), filter.getHeaderTypeCode(),filter.isMemberOnly());
			
			for (Home homeValue : homeList) {
				jsonObject = new JSONObject();
				jsonObject.put("homeId", homeValue.getId());
				jsonObject.put("homeNo", homeValue.getHomeNo());
				jsonObject.put("latitude", homeValue.getLatitude());
				jsonObject.put("longitude", homeValue.getLongitude());
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
	
	@RequestMapping(value = RestURIConstants.GET_HOME_NO_LIST_WITHOUT_OSM, method = RequestMethod.POST)
	public @ResponseBody APIResponse getHomeNoListWithoutOSM(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		List<Home> homeList = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Home data.";
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		try {
			homeList = homeService.listHomeWithoutOSM(filter.getVillageId());
			
			for (Home homeValue : homeList) {
				jsonObject = new JSONObject();
				String holderId = "";
				String holderFullName = "";
				if (!GlobalFunction.isEmpty(homeValue.getHolderId())) {
					holderId = homeValue.getHolderId();
					holderFullName = GlobalFunction.generateFullName(homeValue.getHolder());
				}
				jsonObject.put("homeId", homeValue.getId());
				jsonObject.put("registrationId", homeValue.getRegistrationId());
				jsonObject.put("homeTypeCode", homeValue.getHomeTypeCode());
				jsonObject.put("homeTypeName", homeValue.getHomeType().getName());
				jsonObject.put("homeNo", homeValue.getHomeNo());
				jsonObject.put("name", homeValue.getName());
				jsonObject.put("villageId", homeValue.getVillageId());
				jsonObject.put("villageName", homeValue.getVillage().getVillageName());
				jsonObject.put("road", homeValue.getRoad());
				jsonObject.put("soi", homeValue.getSoi());
				jsonObject.put("telephone", homeValue.getTelephone());
				jsonObject.put("latitude", homeValue.getLatitude());
				jsonObject.put("longitude", homeValue.getLongitude());
				jsonObject.put("osmId", homeValue.getOsmId());
				jsonObject.put("osmFullName", GlobalFunction.generateFullName(homeValue.getOsm()));
				jsonObject.put("holderId", holderId);
				jsonObject.put("holderFullName", holderFullName);
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
	
	@RequestMapping(value = RestURIConstants.GET_HOME_LIST_BY_VILLAGE_OR_OSM, method = RequestMethod.POST)
	public @ResponseBody APIResponse getHomeListByVillageOrOSM(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Home data.";
		
		JSONArray jsonArray = new JSONArray();
		try {
			List<Home> homeList = homeService.listHomeByVillageOrOSM(filter.getVillageId(), filter.getOsmId(), filter.getHeaderTypeCode(),filter.isMemberOnly());
			for (Home homeValue : homeList) {
				JSONObject jsonObject = new JSONObject();
				String holderId = "";
				String holderFullName = "";
				if (!GlobalFunction.isEmpty(homeValue.getHolderId())) {
					holderId = homeValue.getHolderId();
					holderFullName = GlobalFunction.generateFullName(homeValue.getHolder());
				}
				jsonObject.put("homeId", homeValue.getId());
				jsonObject.put("registrationId", homeValue.getRegistrationId());
				jsonObject.put("homeTypeCode", homeValue.getHomeTypeCode());
				jsonObject.put("homeTypeName", homeValue.getHomeType().getName());
				jsonObject.put("homeNo", homeValue.getHomeNo());
				jsonObject.put("name", homeValue.getName());
				jsonObject.put("villageId", homeValue.getVillageId());
				jsonObject.put("villageName", homeValue.getVillage().getVillageName());
				jsonObject.put("road", homeValue.getRoad());
				jsonObject.put("soi", homeValue.getSoi());
				jsonObject.put("telephone", homeValue.getTelephone());
				jsonObject.put("latitude", homeValue.getLatitude());
				jsonObject.put("longitude", homeValue.getLongitude());
				jsonObject.put("osmId", homeValue.getOsmId());
				jsonObject.put("osmFullName", GlobalFunction.generateFullName(homeValue.getOsm()));
				jsonObject.put("holderId", holderId);
				jsonObject.put("holderFullName", holderFullName);
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
	
	@RequestMapping(value = RestURIConstants.GET_FAMILY_STATUS_LIST, method = RequestMethod.POST)
	public @ResponseBody APIResponse getAllFamilyStatuss(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final FamilyStatus familyStatus = mapper.convertValue(requestParams, FamilyStatus.class);
		
		List<FamilyStatus> resultList = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting FamilyStatus data.";
		
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		try {
			resultList = homeService.listFamilyStatuss();
			for (FamilyStatus familyStatusValue : resultList) {
				jsonObject = new JSONObject();
				jsonObject.put("id", familyStatusValue.getId());
				jsonObject.put("name", familyStatusValue.getName());
				
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

	@PostMapping(RestURIConstants.INS_UPD_HOME)
	public @ResponseBody APIResponse insertOrUpdate(@RequestBody ObjectNode requestParams) {
		final ObjectMapper mapper = new ObjectMapper();
		Home home = mapper.convertValue(requestParams, Home.class);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to Insert or Update Home data.";

		Object resObj = new Object();
		try {
			resObj = homeService.insertOrUpdateHome(home);
			
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

	@PostMapping(RestURIConstants.DEL_HOME)
	public @ResponseBody APIResponse delete(@RequestBody ObjectNode requestParams) {
		Integer homeId = Integer.valueOf(requestParams.get("homeId").toString());
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to Delete Home data.";

		Object resObj = new Object();
		try {
			resObj = homeService.deleteHome(homeId);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
			resObj = GlobalFunction.getErrorCode(ex);
		}
		
		String errorCode = "conflicted";
		if (message.contains(errorCode)) {
			resObj = errorCode;
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(resObj);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_HOME_TYPE_LIST, method = RequestMethod.POST)
	public @ResponseBody APIResponse listHomeTypeMonitorHICI(@RequestBody ObjectNode requestParams) {
		
		//final ObjectMapper mapper = new ObjectMapper();
		//final FamilyStatus familyStatus = mapper.convertValue(requestParams, FamilyStatus.class);
		
		List<HomeType> resultList = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting listHomeType data.";
		
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		try {
			resultList = homeService.listHomeTypeMonitorHICI();
			for (HomeType item : resultList) {
				jsonObject = new JSONObject();
				jsonObject.put("code", item.getCode());
				jsonObject.put("name", item.getName());
				
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
	
	@RequestMapping(value = RestURIConstants.GET_HOME_LIST_BY_VILLAGE_HOMETYPE, method = RequestMethod.POST)
	public @ResponseBody APIResponse listHomeByVillageIDHomeTypeCode(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		List<Home> resultList = null;
		
		/*String documentId = requestParams.get("documentId").textValue();
		String osmId = requestParams.get("osmId").textValue();
		Integer villageId = GlobalFunction.isEmpty(requestParams.get("villageId").textValue()) ? null : Integer.valueOf(requestParams.get("villageId").textValue());
		String homeTypeCode = requestParams.get("homeTypeCode").textValue();*/
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting listHomeByVillageIDHomeTypeCode data.";	
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		try {
			
			resultList = homeService.listHomeByVillageHomeType(filter.getVillageId(), filter.getHomeTypeCode(), filter.getDocumentId(), filter.getOsmId());
			for (Home item : resultList) {
				jsonObject = new JSONObject();
				jsonObject.put("homeId", item.getId());
				jsonObject.put("name", item.getName());
				jsonObject.put("address", GlobalFunction.generateAddress(item));
				jsonObject.put("holderName", GlobalFunction.generateFullName(item.getHolder()));
				jsonObject.put("registrationId", item.getRegistrationId());
				jsonObject.put("homeTypeCode", item.getHomeTypeCode());
				
				if(SurveyConstants.HOME_TYPE_OF_HOME.contains(item.getHomeTypeCode())){
					jsonObject.put("homeTypeName", MessageSourceUtil.getMessage("home"));
				}else{
					jsonObject.put("homeTypeName", item.getHomeType().getName());
				}
				
				jsonObject.put("homeNo", item.getHomeNo());
				jsonObject.put("villageId", item.getVillageId());
				jsonObject.put("road", item.getRoad());
				jsonObject.put("soi", item.getSoi());
				jsonObject.put("telephone", item.getTelephone());
				jsonObject.put("latitude", item.getLatitude());
				jsonObject.put("longitude", item.getLongitude());
				jsonObject.put("osmId", item.getOsmId());
				jsonObject.put("osmName", GlobalFunction.generateFullName(item.getOsm()));
				jsonObject.put("mooNo", item.getVillage().getVillageNo());
				jsonObject.put("provinceCode",item.getVillage().getHospital().getProvinceCode());
				jsonObject.put("provinceName",item.getVillage().getHospital().getProvince().getName());
				jsonObject.put("amphurCode",item.getVillage().getHospital().getAmphurCode());
				jsonObject.put("amphurName",item.getVillage().getHospital().getAmphur().getName());
				jsonObject.put("tumbolCode",item.getVillage().getHospital().getTumbolCode());
				jsonObject.put("tumbolName",item.getVillage().getHospital().getTumbol().getName());
				jsonObject.put("zipcode",item.getVillage().getHospital().getZipCode());
				
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
	
	@RequestMapping(value = RestURIConstants.GET_HOME_LIST_BY_HEADERTYPE_CODE, method = RequestMethod.POST)
	public @ResponseBody APIResponse listHomeByHeaderTypeCode(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		List<Home> resultList = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting GET_HOME_LIST_BY_HEADERTYPE_CODE data.";	
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		try {
			
			resultList = homeService.listHomeByHeaderTypeCode(filter.getDocumentId(), filter.getVillageId(), filter.getOsmId(), filter.getHeaderTypeCode());
			for (Home item : resultList) {
				jsonObject = new JSONObject();
				jsonObject.put("homeId", item.getId());
				jsonObject.put("name", item.getName());
				jsonObject.put("address", GlobalFunction.generateAddress(item));
				jsonObject.put("holderName", GlobalFunction.generateFullName(item.getHolder()));
				jsonObject.put("registrationId", item.getRegistrationId());
				jsonObject.put("homeTypeCode", item.getHomeTypeCode());
				
				if(SurveyConstants.HOME_TYPE_OF_HOME.contains(item.getHomeTypeCode())){
					jsonObject.put("homeTypeName", MessageSourceUtil.getMessage("home"));
				}else{
					jsonObject.put("homeTypeName", item.getHomeType().getName());
				}
				
				jsonObject.put("homeNo", item.getHomeNo());
				jsonObject.put("villageId", item.getVillageId());
				jsonObject.put("road", item.getRoad());
				jsonObject.put("soi", item.getSoi());
				jsonObject.put("telephone", item.getTelephone());
				jsonObject.put("latitude", item.getLatitude());
				jsonObject.put("longitude", item.getLongitude());
				jsonObject.put("osmId", item.getOsmId());
				jsonObject.put("osmName", GlobalFunction.generateFullName(item.getOsm()));
				jsonObject.put("mooNo", item.getVillage().getVillageNo());
				jsonObject.put("provinceCode",item.getVillage().getHospital().getProvinceCode());
				jsonObject.put("provinceName",item.getVillage().getHospital().getProvince().getName());
				jsonObject.put("amphurCode",item.getVillage().getHospital().getAmphurCode());
				jsonObject.put("amphurName",item.getVillage().getHospital().getAmphur().getName());
				jsonObject.put("tumbolCode",item.getVillage().getHospital().getTumbolCode());
				jsonObject.put("tumbolName",item.getVillage().getHospital().getTumbol().getName());
				jsonObject.put("zipcode",item.getVillage().getHospital().getZipCode());
				
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
	
	@PostMapping(RestURIConstants.FIND)
	public @ResponseBody APIResponse find(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		final Home home = mapper.convertValue(requestParams, Home.class);
		
		List<Home> homeList = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to find data.";
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		try {
			homeList = homeService.find(filter, home);
			if(homeList != null && homeList.size()>0) {
				for (Home h : homeList) {
					jsonObject = new JSONObject();
					if(!GlobalFunction.isEmpty(home.getHomeTypeCode())) {
						if( HOME_TYPE_CODE_01.equals(home.getHomeTypeCode())
							&& SurveyConstants.HOME_TYPE_OF_HOME.contains(h.getHomeTypeCode())){
							jsonObject = genJSONObjectData(h);
							jsonArray.add(jsonObject);
						}else if(home.getHomeTypeCode().equals(h.getHomeTypeCode())) {
							jsonObject = genJSONObjectData(h);
							jsonArray.add(jsonObject);
						}
					}else{
						jsonObject = genJSONObjectData(h);
						jsonArray.add(jsonObject);
					}
				}
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

	private JSONObject genJSONObjectData(Home h) {
		JSONObject jsonObject = new  JSONObject();
		jsonObject.put("holderFullName", GlobalFunction.generateFullName(h.getHolder()));
		jsonObject.put("holderId", h.getHolderId());
		jsonObject.put("homeId", h.getId());
		jsonObject.put("homeNo", h.getHomeNo());
		jsonObject.put("homeTypeCode", h.getHomeTypeCode());
		jsonObject.put("homeTypeName", h.getHomeType()!=null?h.getHomeType().getName():null);
		jsonObject.put("latitude", h.getLatitude());
		jsonObject.put("longitude", h.getLongitude());
		jsonObject.put("name", h.getName());
		jsonObject.put("osmFullName", GlobalFunction.generateFullName(h.getOsm()));
		jsonObject.put("osmId", h.getOsm()!=null?h.getOsm().getPersonId():null);
		jsonObject.put("registrationId", h.getRegistrationId()); 
		jsonObject.put("road", h.getRoad());
		jsonObject.put("soi", h.getSoi());
		jsonObject.put("telephone", h.getTelephone());
		jsonObject.put("villageId", h.getVillageId());
		jsonObject.put("villageName", h.getVillage()!=null?h.getVillage().getVillageName():null);
		return jsonObject;
	}
	
	@PostMapping(RestURIConstants.UPD_OSM)
	public @ResponseBody APIResponse UpdateOSM(@RequestBody ObjectNode requestParams) {
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to UpdateOSM data.";

		JSONArray resArr = new JSONArray();
		JSONObject resObj = new JSONObject();
		try {
			
			final ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Home home = mapper.convertValue(requestParams, Home.class);
			List<Home> homeList = mapper.readValue(requestParams.get("list").toString(), mapper.getTypeFactory().constructCollectionType(List.class, Home.class));
			
			for(Home items : homeList){
				resObj.put("id", homeService.insertOrUpdateOSM(items, home.getOsmId()).getId());
			}
			resArr.add(resObj);
		} catch (Exception ex) {
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
			resObj.put("", GlobalFunction.getErrorCode(ex));
			resArr.add(resObj);
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(resArr);
		
		return resp;
	}
	
}