package com.fwg.asservice.constants;

public class RestURIConstants {

	/* APPLICATION */
	public static final String DUMMY_EMP = "/rest/emp/dummy";
	public static final String GET_EMP = "/rest/emp/{id}";
	public static final String GET_ALL_EMP = "/rest/emps";
	public static final String CREATE_EMP = "/rest/emp/create";
	public static final String DELETE_EMP = "/rest/emp/delete/{id}"; 
	public static final String GET_MENU = "/menu";
	public static final String GET_IMAGE = "/image";
	public static final String FIND = "/find";
	/* APPLICATION END */
	
	
	/* ADDRESS */
	public static final String GET_PROVINCE = "/province";
	public static final String GET_AMPHUR = "/amphur";
	public static final String GET_TUMBOL = "/tumbol";
	/* ADDRESS END */
	
	
	/* VILLAGE */
	public static final String GET_VILLAGE = "/village";
	public static final String INS_UPD_VILLAGE = "/ins_upd_village";
	public static final String DEL_VILLAGE = "/del_village";
	public static final String GET_VILLAGE_NO_LIST_BY_HOSPITAL = "/village_no_list_by_hospital";
	/* VILLAGE END */
	
	
	/* HOSPITAL */
	public static final String GET_HOSPITAL_LIST = "/hospital_list";
	public static final String GET_HOSPITALNAME_LIST = "/hospitalName_list";
	public static final String REGISTER_HOTPITAL = "/register_hospital";
	public static final String ACTIVATE_HOTPITAL = "/activate_hospital";
	/* HOSPITAL END */
	
	
	/* HOME */
	public static final String GET_HOME_NO_LIST_BY_VILLAGE_OR_OSM = "/home_no_list_by_village_or_osm";
	public static final String GET_HOME_NO_LIST_WITHOUT_OSM = "/home_no_list_without_osm";
	public static final String GET_HOME_LIST_BY_VILLAGE_OR_OSM = "/home_list_by_village_or_osm";
	public static final String GET_HOME_LIST_BY_VILLAGE_HOMETYPE = "/home_list_by_village_hometype";
	public static final String GET_HOME_LIST_BY_HEADERTYPE_CODE = "/home_list_by_headertype_code";
	public static final String GET_FAMILY_STATUS_LIST = "/family_status_list";
	public static final String GET_HOME_TYPE_LIST = "/home_type_list";
	public static final String GET_HOMEINFO_WITH_OSM_BY_HOMEID = "/home_info";
	public static final String INS_UPD_HOME = "/ins_upd_home";
	public static final String UPD_OSM = "/upd_osm";
	public static final String DEL_HOME = "/del_home";
	/* HOME END */
	
	
	/* HOME MEMBER */
	public static final String GET_HOME_MEMBER_BY_HOME = "/homemember_by_home";
	public static final String GET_HOME_MEMBER_BY_DOCUMENTID = "/homemember_by_documentid";
	public static final String GET_HOME_MEMBER_BY_CITIZENID = "/homemember_by_citizen";
	public static final String DEL_HOME_MEMBER = "/homemember_del";
	/* HOME MEMBER END */
	
	
	/* OSM */
	public static final String GET_OSM_BY_VILLAGE = "/osm_list_by_village";
	/* OSM END */
	
	/* USER */
	public static final String USER_PARAM_ROLESCOPE = "roleScope";
	
	public static final String USER_VALUE_ROLESCOPE_OSM = "osm";
	public static final String USER_VALUE_ROLESCOPE_STAFF = "staff";
	
	public static final String USER_INFO = "/info";
	public static final String USER_LOGIN = "/login";
	public static final String USER_INSERT_UPDATE = "/insert_update";
	public static final String USER_FIND = "/find/{"+USER_PARAM_ROLESCOPE+"}";
	public static final String CHANGE_PASSWORD = "/change_password";
	public static final String EDIT_PROFILE = "/edit_profile";
	public static final String FORGOT_PASSWORD_VERIFIED = "/forgot_password_verified";
	public static final String FORGOT_PASSWORD_VERIFIED_HOSPITAL = "/forgot_password_verified_hospital";
	public static final String RESET_PASSWORD = "/reset_password";
	/* USER END */
	
	/* PERSON */
	public static final String GET_PREFIX_LIST = "/prefix_list";
	public static final String GET_GENDER_LIST = "/gender_list";
	public static final String GET_RACE_LIST = "/race_list";
	public static final String GET_NATIONALITY_LIST = "/nationality_list";
	public static final String GET_RELIGION_LIST = "/religion_list";
	public static final String GET_BLOOD_TYPE_LIST = "/blood_type_list";
	public static final String GET_BORN_LOCATION_LIST = "/born_location_list";
	public static final String GET_BORN_TYPE_LIST = "/born_type_list";
	public static final String GET_RHGROUP_LIST = "/rhgroup_list";
	public static final String GET_EDUCATION_LIST = "/education_list";
	public static final String GET_OCCUPATION_LIST = "/occupation_list";
	public static final String GET_TYPE_AREA_LIST = "/type_area_list";
	public static final String GET_DISCHARGE_LIST = "/discharge_list";
	
	public static final String GET_HEALTH_INSURANCE_TYPE_LIST = "/health_insurance_type_list";
	public static final String GET_CANCER_TYPE_LIST = "/cancer_type_list";
	public static final String GET_DISEASE_STATUS_TYPE_LIST = "/disease_status_type_list";
	public static final String GET_PATIENT_TYPE_LIST = "/patient_type_list";
	public static final String GET_PATIENT_SURVEY_TYPE_LIST = "/patient_survey_type_list";
	public static final String GET_DISABILITY_TYPE_LIST = "/disability_type_list";
	public static final String GET_DISABILITY_CAUSE_TYPE_LIST = "/disability_cause_type_list";
	public static final String GET_PERSON_BY_CITIZENID = "/person_by_citizenid";
	public static final String GET_PERSON_BY_PERSONID = "/person_by_personid";
	public static final String GET_MEDICAL_RIGHT_LIST = "/medical_right_list";
	
	/* PERSON END */
	
	/* PERSON */
	public static final String GET_TEMPLATE_TYPE_DETAIL_LIST = "/template_type_detail_list";
	/* PERSON END */
	
	/* SURVEY */
	public static final String GET_SURVEY_HEADER_LIST = "/survey_header_list";
	public static final String GET_SURVEY_DEATH_PLACE_LIST = "/survey_death_place_list"; 
	public static final String GET_SURVEY_IS_DUPLICATE = "/survey_is_duplicate";
	public static final String GET_CONTAINER_TYPE_LIST = "/container_type_list"; 
	public static final String GET_CONTAINER_LOCATE_TYPE_LIST = "/container_locate_type_list"; 
	public static final String INS_UPD_BORN_LOCATION = "/ins_upd_born_location"; 
	/* SURVEY END */
	
	/* SURVEY POPULATION */
	public static final String SEARCH_POPULATION_LIST = "/search_population_list";
	public static final String SEARCH_POPULATION_LIST_NOT_SURVEY = "/search_population_list_not_survey"; 
	public static final String POPULATION_ADD_HOME_MEMBER = "/population_add_home_member"; 
	public static final String INS_UPD_POPULATION_INFO = "/ins_upd_population_info";
	public static final String DEL_POPULATION = "/del_population";
	/* SURVEY POPULATION END */
	
	/* SURVEY DEATH */
	public static final String SEARCH_DEATH_INFO_LIST = "/search_death_info_list";
	public static final String GET_DEATH_DETAIL_INFO = "/get_death_detail_info";
	public static final String INS_UPD_DEATH_INFO = "/ins_upd_death_info";
	public static final String DEL_DEATH_INFO = "/del_death_info";
	/* SURVEY DEATH END */
	
	/* SURVEY METABOLIC */
	public static final String SEARCH_METABOLIC_LIST = "/search_metabolic_list"; 
	public static final String SEARCH_METABOLIC_LIST_NOT_SURVEY = "/search_metabolic_list_not_survey"; 
	public static final String INS_UPD_METABOLIC_INFO = "/ins_upd_metabolic_info";
	public static final String DEL_METABOLIC_INFO = "/del_metabolic_info";
	public static final String METABOLIC_BY_ROWGUID = "/metabolic_by_rowguid";
	/* SURVEY METABOLIC END*/
	
	/* SURVEY PATIENT */
	public static final String SURVEY_PATIENT_PATIENTDETAILINFO_INS_UPD = "/ins_upd";
	public static final String SURVEY_PATIENT_PATIENTDETAILINFO_DELETE = "/del";
	public static final String SURVEY_PATIENT_PATIENTDETAILINFO_FILTER = "/filter";
	public static final String SURVEY_PATIENT_PATIENTDETAILINFO_BY_ROWGUID = "/patient_by_rowguid";
	/* SURVEY PATIENT END */
	
	/* SURVEY PREGNANT */
	public static final String SEARCH_PREGNANT_INFO_LIST_NOT_EXISTS_SURVEY_BORN = "/search_pregnant_info_list_not_exists_survey_born";
	public static final String SEARCH_PREGNANT_INFO_LIST_FOR_ADD_BORN = "/search_pregnant_info_list_for_add_born";
	public static final String SEARCH_PREGNANT_INFO_LIST = "/search_pregnant_info_list";
	public static final String GET_PREGNANT_DETAIL_INFO = "/get_pregnant_detail_info";
	public static final String INS_UPD_PREGNANT_INFO = "/ins_upd_pregnant_info";
	public static final String DEL_PREGNANT_INFO = "/del_pregnant_info";
	/* SURVEY PREGNANT END */
	
	/* SURVEY MonitorHICI */
	public static final String SEARCH_HICI_INFO_LIST = "/search_hici_info_list";
	public static final String SEARCH_HICI_INFO_LIST_NOT_SURVEY = "/search_hici_info_list_not_survey";
	public static final String INS_UPD_HICI_INFO = "/ins_upd_hici_info";
	public static final String DEL_HICI_INFO = "/del_hici_info";
	public static final String HICI_BY_HOMEID = "/hici_by_homeid";
	/* SURVEY MonitorHICI END*/
	
	public static final String FAMILY_SUMMARY = "/family_summary";
	public static final String SURVEY_SUMMARY = "/survey_summary";
	
	public static final String FAMILY_SUMMARY_HOSPITAL = "/family_summary_hospital";
	public static final String SURVEY_SUMMARY_HOSPITAL = "/survey_summary_hospital";
	
	public static final String UPLOAD_PROFILE = "/upload_profile";
}