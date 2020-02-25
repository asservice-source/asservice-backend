package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwg.asservice.dao.OSMDao;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.service.OSMService;

@Service
public class OSMServiceImpl implements OSMService {
	
	@Autowired
	private OSMDao osmDao;

	@Override
	public List<Person> listOSMByVillageID(int villageId) throws Exception {
		try{
			return osmDao.listOSMByVillageID(villageId);
		}catch(Exception e){
			throw e;
		}
	}

}
