package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwg.asservice.dao.HospitalDao;
import com.fwg.asservice.model.Hospital;
import com.fwg.asservice.service.HospitalService;

@Service
public class HospitalServiceImpl implements HospitalService {  
	
	@Autowired
	private HospitalDao hospitalDao;

	@Transactional
	public void saveHospital(Hospital hospital) throws Exception {
		
	}
	
	@Override
	public String registerHospital(Hospital hospital) throws Exception {
		try{
			return hospitalDao.registerHospital(hospital);
		}catch(Exception e){
			throw e;
		}
		
	}
	
	@Override
	public String activeHospital(String tokenId ,String password) throws Exception {
		try{
			return hospitalDao.activeHospital(tokenId,password);
		}catch(Exception e){
			throw e;
		}
		
	}

	/*@Transactional(readOnly = true)
	public List<Hospital> listHospitals() throws Exception {
		try{
			return hospitalDao.listHospitals();
		}catch(Exception e){
			throw e;
		}
	}*/

	@Transactional(readOnly = true)
	public List<Hospital> listHospitals(String code5) throws Exception {
		try{
			return hospitalDao.listHospitals(code5);
		}catch(Exception e){
			throw e;
		}
	}

	public Hospital getHospital(String code5) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteHospital(String code5) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Hospital> getHospitalByTokenId(String tokenId) throws Exception {
		try{
			return hospitalDao.getHospitalByTokenId(tokenId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<Hospital> listHospitalsNameAll(String code5) throws Exception {
		try{
			return hospitalDao.listHospitalsNameAll(code5);
		}catch(Exception e){
			throw e;
		}
	}
}