package com.fwg.asservice.service;

import java.util.List;

import com.fwg.asservice.model.Village;

public interface VillageService {
	/*
	 * CREATE and UPDATE 
	 */
	public int insOrUpdVillage(Village village) throws Exception;

	/*
	 * READ
	 */
	public List<Village> listVillageNoByHospitalCode(String hospitalCode) throws Exception;
	//public List<Hospital> listHospitals(boolean isRegister) throws Exception;
	//public Prefix getPrefix(String code) throws Exception;
	//public Hospital getHospital(int id, int status, boolean deleted) throws Exception;
	
	/*
	 * DELETE
	 */
	public boolean deleteVillage(Integer id) throws Exception;
}
