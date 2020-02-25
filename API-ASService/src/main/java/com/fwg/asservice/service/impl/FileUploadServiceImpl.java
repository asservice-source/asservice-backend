package com.fwg.asservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwg.asservice.dao.FileUploadDao;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	private FileUploadDao fileUploadDao;

	@Override
	public Person uploadProfile(Person person) throws Exception {
		try{
			return fileUploadDao.uploadProfile(person); 
		}catch(Exception e){
			throw e;
		}
		
	}

	
	
	

}
