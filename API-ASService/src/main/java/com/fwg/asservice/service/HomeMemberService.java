package com.fwg.asservice.service;

import java.util.List;

import com.fwg.asservice.model.HomeMembers;

public interface HomeMemberService {
	/*
	 * CREATE and UPDATE 
	 */
	//public void saveProvince(Province province) throws Exception;

	/*
	 * READ
	 */
	public List<HomeMembers> listHomeMemberByHomeID(Integer homeId, boolean isManage) throws Exception;
	public HomeMembers getHomeMemberByPersonId(String personId) throws Exception;
	
	/*
	 * DELETE
	 */
	public HomeMembers deleteHomeMembers(String personId, int homeId) throws Exception;
}
