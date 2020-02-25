package com.fwg.asservice.service;

import java.util.List;

import com.fwg.asservice.model.Hospital;
import com.fwg.asservice.model.Person;

public interface OSMService {
	/*
	 * CREATE and UPDATE 
	 */
	//public void saveHospital(Hospital hospital) throws Exception;
	//public String registerHospital(Hospital hospital) throws Exception;

	/*
	 * READ
	 */
	public List<Person> listOSMByVillageID(int villageId) throws Exception;
	//public List<Hospital> listHospitals(boolean isRegister) throws Exception;
	//public Hospital getHospital(String code5) throws Exception;
	//public Hospital getHospital(int id, int status, boolean deleted) throws Exception;
	
	/*
	 * DELETE
	 */
	//public void deleteHospital(String code5)  throws Exception;
}
