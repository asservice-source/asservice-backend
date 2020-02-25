package com.fwg.asservice.service;

import java.util.List;

import com.fwg.asservice.model.FamilyStatus;
import com.fwg.asservice.model.Home;
import com.fwg.asservice.model.HomeType;
import com.fwg.asservice.model.filter.Filter;

public interface HomeService {
	/*
	 * CREATE and UPDATE 
	 */
	//public void saveProvince(Province province) throws Exception;
	public Home insertOrUpdateOSM(Home home, String osmId) throws Exception;
	/*
	 * READ
	 */
	public List<Home> listHomeByVillageHomeType(Integer id, String homeTypeCode, String documentId, String osmId) throws Exception;
	public List<Home> listHomeWithoutOSM(Integer villageId) throws Exception;
	public List<HomeType> listHomeTypeMonitorHICI() throws Exception;
	public List<Home> listHomes(Integer villageId, String osmId, Integer id) throws Exception;
	public List<Home> listHomeByVillageOrOSM(Integer id, String osmId, String headerTypeCode, boolean memberOnly) throws Exception;
	public List<Home> listHomeByHeaderTypeCode(String documentId, Integer villageId, String osmId, String headerTypeCode) throws Exception;
	public List<FamilyStatus> listFamilyStatuss() throws Exception;
	public Home getHomeByPersonId(String personId) throws Exception;
	public Home getHomeInfoWithOsm(Integer homeId) throws Exception;
	public Home insertOrUpdateHome(Home home) throws Exception;
	public boolean deleteHome(Integer homeId) throws Exception;
	public List<Home> find(Filter filter,Home home) throws Exception;
	/*
	 * DELETE
	 */
	//public void deleteProvince(int id)  throws Exception;
}
