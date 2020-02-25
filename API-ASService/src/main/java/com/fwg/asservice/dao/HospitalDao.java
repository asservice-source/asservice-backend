package com.fwg.asservice.dao;

import java.util.List;

import com.fwg.asservice.model.Hospital;

public interface HospitalDao {

	/*
	 * CREATE and UPDATE
	 */
	public String registerHospital(Hospital hospital) throws Exception;
	public String activeHospital(String tokenId,String password) throws Exception;

	/*
	 * READ
	 */
	
	public List<Hospital> listHospitals(String code5) throws Exception;
	public List<Hospital> getHospitalByTokenId(String tokenId) throws Exception;
	public List<Hospital> listHospitalsNameAll(String code5) throws Exception;
	//public List<Hospital> listHospitals(boolean isRegister) throws Exception;

	/*
	 * DELETE
	 */
	//public void deleteProvince(int id) throws Exception;
}
