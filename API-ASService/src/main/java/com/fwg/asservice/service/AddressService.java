package com.fwg.asservice.service;

import java.util.List;

import com.fwg.asservice.model.Amphur;
import com.fwg.asservice.model.Province;
import com.fwg.asservice.model.Tumbol;

public interface AddressService {

	/*
	 * CREATE and UPDATE 
	 */
	public void saveProvince(Province province) throws Exception;

	/*
	 * READ
	 */
	public List<Province> listProvinces(String provinceCode) throws Exception;
	//public List<Province> listProvinces(int pageNum, int rowNum, int typeId, int status, boolean deleted) throws Exception;
	//public Province getProvince(int id) throws Exception;
	//public Province getProvince(int id, int status, boolean deleted) throws Exception;
	
	//public List<Amphur> listAmphurs() throws Exception;
	public List<Amphur> listAmphurs(String provinceCode) throws Exception;
	//public Amphur getAmphur(int id) throws Exception;
	//public Amphur getAmphur(int id, int status, boolean deleted) throws Exception;
	
	//public List<Tumbol> listTumbols() throws Exception;
	public List<Tumbol> listTumbols(String amphurCode) throws Exception;
	//public Tumbol getTumbol(int id) throws Exception;
	//public Tumbol getTumbol(int id, int status, boolean deleted) throws Exception;

	/*
	 * DELETE
	 */
	public void deleteProvince(int id)  throws Exception;

}
