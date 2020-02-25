package com.fwg.asservice.service;

import java.util.List;

import com.fwg.asservice.model.Hospital;

public interface HospitalService {

	/*
	 * CREATE and UPDATE 
	 */
	public void saveHospital(Hospital hospital) throws Exception;
	public String registerHospital(Hospital hospital) throws Exception;
	public String activeHospital(String tokenId , String password) throws Exception;

	/*
	 * READ
	 */
	public List<Hospital> listHospitals(String code5) throws Exception;
	public List<Hospital> getHospitalByTokenId(String tokenId) throws Exception;
	//public List<Hospital> listHospitals(boolean isRegister) throws Exception;
	public Hospital getHospital(String code5) throws Exception;
	public List<Hospital> listHospitalsNameAll(String code5) throws Exception;
	//public Hospital getHospital(int id, int status, boolean deleted) throws Exception;
	
	/*
	 * DELETE
	 */
	public void deleteHospital(String code5)  throws Exception;

}
