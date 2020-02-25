package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fwg.asservice.dao.HomeMemberDao;
import com.fwg.asservice.model.HomeMembers;
import com.fwg.asservice.service.HomeMemberService;

@Service
public class HomeMemberServiceImpl implements HomeMemberService { 
	
	@Autowired
	private HomeMemberDao homeMemberDao;

	@Transactional(readOnly = true)
	public List<HomeMembers> listHomeMemberByHomeID(Integer homeId, boolean isManage) throws Exception {
		try {
			return homeMemberDao.listHomeMemberByHomeID(homeId, isManage);
		} catch (Exception exception) {
			throw exception;
		}
	}

	@Override
	public HomeMembers getHomeMemberByPersonId(String personId) throws Exception {
		try {
			return homeMemberDao.getHomeMemberByPersonId(personId);
		} catch (Exception exception) {
			throw exception;
		}
	}

	@Override
	public HomeMembers deleteHomeMembers(String personId, int homeId) throws Exception {
		try {
			return homeMemberDao.deleteHomeMembers(personId, homeId);
		} catch (Exception exception) {
			throw exception;
		}
	}
}
