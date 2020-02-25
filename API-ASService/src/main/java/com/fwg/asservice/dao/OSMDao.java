package com.fwg.asservice.dao;

import java.util.List;

import com.fwg.asservice.model.Person;

public interface OSMDao {
	/*
	 * CREATE and UPDATE
	 */
	//public void saveProvince(Province brand) throws Exception; // create and update

	/*
	 * READ
	 */
	
	public List<Person> listOSMByVillageID(int villageId) throws Exception;
	//public List<Hospital> listHospitals(boolean isRegister) throws Exception;

	/*
	 * DELETE
	 */
	//public void deleteProvince(int id) throws Exception;
}
