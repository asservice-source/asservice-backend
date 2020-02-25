package com.fwg.asservice.dao;

import java.util.List;

import com.fwg.asservice.model.Village;

public interface VillageDao {

	/*
	 * CREATE and UPDATE
	 */
	public int insOrUpdVillage(Village village) throws Exception; // create and update

	/*
	 * READ
	 */
	public List<Village> listVillageNoByHospitalCode(String hospitalCode) throws Exception;
	
	/*public List<Province> listProvinces() throws Exception;
	public List<Province> listProvinces(int pageNum, int rowNum, int typeId, int status, boolean deleted) throws Exception;
	public Province getProvince(int id) throws Exception;
	public Province getProvince(int id, int status, boolean deleted) throws Exception;
	
	public List<Amphur> listAmphurs() throws Exception;
	public List<Amphur> listAmphurs(String provinceCode) throws Exception;
	public Amphur getAmphur(int id) throws Exception;
	public Amphur getAmphur(int id, int status, boolean deleted) throws Exception;
	
	public List<Tumbol> listTumbols() throws Exception;
	public List<Tumbol> listTumbols(String amphurCode) throws Exception;
	public Tumbol getTumbol(int id) throws Exception;
	public Tumbol getTumbol(int id, int status, boolean deleted) throws Exception;*/

	/*
	 * DELETE
	 */
	public boolean deleteVillage(Integer id) throws Exception;
}
