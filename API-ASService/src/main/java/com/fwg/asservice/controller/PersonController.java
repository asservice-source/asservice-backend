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
import com.fwg.asservice.constants.UserRoleConstants;
import com.fwg.asservice.model.APIResponse;
import com.fwg.asservice.model.Address;
import com.fwg.asservice.model.BloodType;
import com.fwg.asservice.model.BornLocation;
import com.fwg.asservice.model.BornType;
import com.fwg.asservice.model.CancerType;
import com.fwg.asservice.model.DisabilityCauseType;
import com.fwg.asservice.model.DisabilityType;
import com.fwg.asservice.model.Discharge;
import com.fwg.asservice.model.DiseaseStatusType;
import com.fwg.asservice.model.Education;
import com.fwg.asservice.model.Gender;
import com.fwg.asservice.model.HealthInsuranceType;
import com.fwg.asservice.model.Home;
import com.fwg.asservice.model.HomeMembers;
import com.fwg.asservice.model.Hospital;
import com.fwg.asservice.model.MedicalRight;
import com.fwg.asservice.model.Nationality;
import com.fwg.asservice.model.Occupation;
import com.fwg.asservice.model.PatientSurveyType;
import com.fwg.asservice.model.PatientType;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.Prefix;
import com.fwg.asservice.model.RHGroup;
import com.fwg.asservice.model.Race;
import com.fwg.asservice.model.Religion;
import com.fwg.asservice.model.TypeArea;
import com.fwg.asservice.model.UserMapping;
import com.fwg.asservice.model.Village;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.service.HomeMemberService;
import com.fwg.asservice.service.HomeService;
import com.fwg.asservice.service.PersonService;
import com.fwg.asservice.service.UserService;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * Handles requests for the Employee service.
 */
@Controller
@RequestMapping(RequestMappingConstants.PERSON)
public class PersonController {
	
	//private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private HomeMemberService homeMemberService;
	
	@RequestMapping(value = RestURIConstants.GET_PREFIX_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllPrefixs(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final Prefix prefix = mapper.convertValue(requestParams, Prefix.class);
		
		List<Prefix> prefixsList = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Prefix data.";
		
		try {
			prefixsList = personService.listPrefixs(prefix.getGenderId());
			
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
		resp.setResponse(prefixsList);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_GENDER_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllGenders(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final Gender gender = mapper.convertValue(requestParams, Gender.class);
		
		List<Gender> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Gender data.";
		
		try {
			listResult = personService.listGenders();
			
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_RACE_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllRaces(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final Race race = mapper.convertValue(requestParams, Race.class);
		
		List<Race> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Race data.";
		
		try {
			listResult = personService.listRaces();
			
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_NATIONALITY_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllNationalitys(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final Nationality nationality = mapper.convertValue(requestParams, Nationality.class);
		
		List<Nationality> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Nationality data.";
		
		try {
			listResult = personService.listNationalitys();
			
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_RELIGION_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllReligions(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final Religion religion = mapper.convertValue(requestParams, Religion.class);
		
		List<Religion> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Religion data.";
		
		try {
			listResult = personService.listReligions();
			
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_BLOOD_TYPE_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllBloodTypes(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final BloodType bloodType = mapper.convertValue(requestParams, BloodType.class);
		
		List<BloodType> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting BloodType data.";
		
		try {
			listResult = personService.listBloodTypes();
			
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_BORN_LOCATION_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllBornLocations(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final BornLocation bornLocation = mapper.convertValue(requestParams, BornLocation.class);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting BornLocation data.";
		
		List<BornLocation> bornLocationList = null;
		try {
			bornLocationList = personService.listBornLocations(bornLocation);
		} catch (Exception ex) {
			ex.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(ex);
		}
		
		JSONArray jsonArray = new JSONArray();
		for (BornLocation item : bornLocationList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id", item.getId());
			jsonObj.put("name", item.getName());
			jsonObj.put("hospitalCode", item.getHospitalCode());
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
	
	@RequestMapping(value = RestURIConstants.GET_BORN_TYPE_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllBornTypes(@RequestBody ObjectNode requestParams) {
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting BornType data.";
		
		List<BornType> listResult = null;
		try {
			listResult = personService.listBornTypes();
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_RHGROUP_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllRHGroups(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final RHGroup rhGroup = mapper.convertValue(requestParams, RHGroup.class);
		
		List<RHGroup> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting RHGroup data.";
		
		try {
			listResult = personService.listRHGroups();
			
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_EDUCATION_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllEducations(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final Education education = mapper.convertValue(requestParams, Education.class);
		
		List<Education> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Education data.";
		
		try {
			listResult = personService.listEducations();
			
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_OCCUPATION_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllOccupations(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final Occupation occupation = mapper.convertValue(requestParams, Occupation.class);
		
		List<Occupation> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Occupation data.";
		
		try {
			listResult = personService.listOccupations();
			
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_TYPE_AREA_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllTypeAreas(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final TypeArea typeArea = mapper.convertValue(requestParams, TypeArea.class);
		
		List<TypeArea> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting TypeArea data.";
		
		try {
			listResult = personService.listTypeAreas();
			
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_DISCHARGE_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllDischarges(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final Discharge discharge = mapper.convertValue(requestParams, Discharge.class);
		
		List<Discharge> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Discharge data.";
		
		try {
			listResult = personService.listDischarges();
			
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_HEALTH_INSURANCE_TYPE_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllHealthInsuranceTypes(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final HealthInsuranceType healthInsuranceType = mapper.convertValue(requestParams, HealthInsuranceType.class);
		
		List<HealthInsuranceType> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting HealthInsuranceType data.";
		
		try {
			listResult = personService.listHealthInsuranceTypes();
			
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_CANCER_TYPE_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllCancerTypes(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final CancerType cancerType = mapper.convertValue(requestParams, CancerType.class);
		
		List<CancerType> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting CancerType data.";
		
		try {
			listResult = personService.listCancerTypes();
			
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_DISEASE_STATUS_TYPE_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllDiseaseStatusTypes(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final DiseaseStatusType diseaseStatusType = mapper.convertValue(requestParams, DiseaseStatusType.class);
		
		List<DiseaseStatusType> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting DiseaseStatusType data.";
		
		try {
			listResult = personService.listDiseaseStatusTypes();
			
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_PATIENT_TYPE_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllPatientTypes(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final PatientType patientType = mapper.convertValue(requestParams, PatientType.class);
		
		List<PatientType> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting PatientType data.";
		
		try {
			listResult = personService.listPatientTypes();
			
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_PATIENT_SURVEY_TYPE_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllPatientSurveyTypes(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final PatientSurveyType patientSurveyType = mapper.convertValue(requestParams, PatientSurveyType.class);
		
		List<PatientSurveyType> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting PatientSurveyType data.";
		
		try {
			listResult = personService.listPatientSurveyTypes();
			
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_DISABILITY_TYPE_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllDisabilityTypes(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final DisabilityType disabilityType = mapper.convertValue(requestParams, DisabilityType.class);
		
		List<DisabilityType> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting DisabilityType data.";
		
		try {
			listResult = personService.listDisabilityTypes();
			
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_DISABILITY_CAUSE_TYPE_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getAllDisabilityCauseTypes(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final DisabilityCauseType disabilityCauseType = mapper.convertValue(requestParams, DisabilityCauseType.class);
		
		List<DisabilityCauseType> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting DisabilityType data.";
		
		try {
			listResult = personService.listDisabilityCauseTypes();
			
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@RequestMapping(value = RestURIConstants.GET_PERSON_BY_CITIZENID, method=RequestMethod.POST)
	public @ResponseBody APIResponse getPersonByCitizenId(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final Person person = mapper.convertValue(requestParams, Person.class);
		
		List<Person> listResult = null;
//		UserMapping userMapping = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Person data.";
		
		//JSONArray array = new JSONArray();
		JSONObject object = null;
		
		try {
			listResult = personService.listPersonByCitizenId(person.getCitizenId());//return only one. ?Why to do 'for loop'.
			object = setPersonData(listResult);
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
	
	@RequestMapping(value = RestURIConstants.GET_MEDICAL_RIGHT_LIST, method=RequestMethod.POST)
	public @ResponseBody APIResponse getMedicalRightList(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final MedicalRight medicalRight = mapper.convertValue(requestParams, MedicalRight.class);
		
		List<MedicalRight> listResult = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting medicalRight data.";
		
		//JSONArray array = new JSONArray();
		//JSONObject object = null;
		
		try {
			listResult = personService.listMedicalRights(medicalRight);
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
		resp.setResponse(listResult);
		
		return resp;
	}
	
	@PostMapping(RestURIConstants.GET_PERSON_BY_PERSONID)
	public @ResponseBody APIResponse getPersonByPersonId(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final Person person = mapper.convertValue(requestParams, Person.class);
		
		List<Person> listResult = null;
//		UserMapping userMapping = null;
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to collecting Person data.";
		
		//JSONArray array = new JSONArray();
		JSONObject object =  new JSONObject();
		
		try {
			listResult = personService.listPersonByPersonId(person.getPersonId());//return only one. ?Why to do 'for loop'.
			object = setPersonData(listResult);
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

	private JSONObject setPersonData(List<Person> listResult) throws Exception {

		JSONObject object = new JSONObject();
		
		if(listResult!=null && listResult.size()>0) {
			
			for(Person personItem : listResult){
				
				List<UserMapping> userMappings = null;
				
				Filter filter = new Filter();
				filter.setOsmId(personItem.getPersonId());
				
				userMappings = userService.userLists(filter);
	//			userMapping = userService.userRoleAndHospitalCode5(personItem.getPersonId());
				String villageNo = null;
				String hospitalCode5 = null;
				Integer villageId = null;
				boolean activateHome = false;
				if(userMappings!= null && userMappings.size()>0) {
					for (UserMapping userMapping : userMappings) {
						if(userMapping.getUserRoleID() > UserRoleConstants.ROLE_ID_SUPERVISOR) {
							
							hospitalCode5 = userMapping.getHospitalCode();
							object.put("userId", userMapping.getUserLoginID());
							object.put("isActive", userMapping.getUserLogin().isActive());
							object.put("userRoleId", userMapping.getUserRoleID());
							
							Village userVillage = null;
							userVillage =  userMapping.getUserLogin().getResponsibility()!=null?userMapping.getUserLogin().getResponsibility().getVillage():null;
							if(userVillage!=null) {
								
								villageNo = userVillage.getVillageNo();
								villageId = userVillage.getId();
								
								List<Home>homeList = null; 
								
								homeList = homeService.listHomeByVillageOrOSM(null, userMappings.get(0).getPersonID(), null,false);
								if(homeList!=null && homeList.size()>0) {
									activateHome = true;
								}
							}
						}
					}
				}
				
				Home home = homeService.getHomeByPersonId(personItem.getPersonId());
				if(home!= null) {
					if(hospitalCode5 == null) {
						hospitalCode5 = home.getVillage()!=null?home.getVillage().getHospitalCode():null;
					}
					
					if(villageNo == null) {
						villageNo = home.getVillage()!=null?home.getVillage().getVillageNo():null;
						villageId = home.getVillage()!=null?home.getVillage().getId():null;		
					}
				}
				
				HomeMembers homeMembers = homeMemberService.getHomeMemberByPersonId(personItem.getPersonId());
				if(homeMembers!=null) {
					object.put("isGuest",homeMembers.isGuest());
					object.put("isExists",homeMembers.isExists());
					object.put("familyStatusId",homeMembers.getFamilyStatusId());
					object.put("familyStatusName",homeMembers.getFamilyStatus()!=null?homeMembers.getFamilyStatus().getName():null);
					object.put("homeId",homeMembers.getHomeId());
					object.put("dischargeId",homeMembers.getDischargeId());
					object.put("dischargeName",homeMembers.getDischarge()!=null?homeMembers.getDischarge().getName():null);
					object.put("dischargeDate",homeMembers.getDischargeDate());
				}
				Address address = personItem.getAddress();
				if(address!=null) {
					object.put("address",GlobalFunction.generateAddress(personItem));
					object.put("homeNo",address.getHomeNo());
					object.put("mooNo",address.getMooNo());
					object.put("road",address.getRoad());
					object.put("tumbolCode",address.getTumbolCode());
					object.put("amphurCode",address.getAmphurCode());
					object.put("provinceCode",address.getProvinceCode());
					object.put("zipcode",address.getZipcode());
				}

				object.put("hospitalCode5", hospitalCode5);
				object.put("homeId",home!=null?home.getId():null);
				object.put("activateHome", activateHome);
				object.put("villageId",villageId);
				object.put("villageNo",villageNo);
				object.put("personId", personItem.getPersonId());
				object.put("citizenId", personItem.getCitizenId());
				object.put("firstName", personItem.getFirstName());
				object.put("lastName", personItem.getLastName());
				object.put("nickName", personItem.getNickName());
				object.put("genderId", personItem.getGenderId());
				object.put("genderName", personItem.getGender().getName());
				object.put("prefixCode", personItem.getPrefixCode());
				object.put("prefixName", personItem.getPrefix().getName());
				object.put("birthDate", personItem.getBirthDate());
				object.put("mStatusCode", personItem.getmStatusCode());
				object.put("mStatusName", personItem.getMaritalStatus().getName());
				object.put("occupationCode", personItem.getOccupCode());
				object.put("occupationName", personItem.getOccupation().getName());
				object.put("raceCode", personItem.getRaceCode());
				object.put("raceName", personItem.getRace().getName());
				object.put("nationalityCode", personItem.getNationCode());
				object.put("nationalityName", personItem.getNationality().getName());
				object.put("religionCode", personItem.getReligionCode());
				object.put("religionName", personItem.getReligion().getName());
				object.put("educationCode", personItem.getEducationCode());
				object.put("educationName", personItem.getEducation().getName());
				object.put("fatherCid", personItem.getFatherCid());
				object.put("motherCid", personItem.getMotherCid());
				object.put("coupleCid", personItem.getCoupleCid());
				object.put("vStatusCode", personItem.getvStatusCode());
				object.put("vStatusName", personItem.getVillageStatus().getName());
				object.put("bloodTypeId", personItem.getBloodTypeId());
				object.put("bloodTypeName", personItem.getBloodType().getName());
				object.put("rhGroupId", personItem.getrhGroupId());
				object.put("rHGroupName", personItem.getRhGroup().getName());
				object.put("laborCode", personItem.getLaborCode());
				object.put("laborName", personItem.getLabor().getName());
				object.put("passport", personItem.getPassport());
				object.put("isDead", personItem.isDead());
				object.put("deadDate", personItem.getDeadDate());
				object.put("fullName", GlobalFunction.generateFullName(personItem));
				object.put("picturePath", personItem.getPicturePath());
				object.put("medicalRightCode", personItem.getMedicalRightCode());
				object.put("medicalRightDescription", personItem.getMedicalRight().getDescription());
				object.put("congenitalDisease", personItem.getCongenitalDisease());
				object.put("remark", personItem.getRemark());
			}
		}
		return object;
	}
}