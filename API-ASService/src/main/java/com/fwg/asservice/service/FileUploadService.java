package com.fwg.asservice.service;

import com.fwg.asservice.model.Person;

public interface FileUploadService {

	/*
	 * CREATE and UPDATE 
	 */
	public Person uploadProfile(Person person) throws Exception;

	/*
	 * READ
	 */
}
