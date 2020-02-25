package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fwg.asservice.dao.VillageDao;
import com.fwg.asservice.model.Village;
import com.fwg.asservice.service.VillageService;

@Service
public class VillageServiceImpl implements VillageService{

	@Autowired
	private VillageDao villageDao;

	@Override
	public int insOrUpdVillage(Village village) throws Exception {
		try{
			return villageDao.insOrUpdVillage(village);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<Village> listVillageNoByHospitalCode(String hospitalCode) throws Exception {
		try{
			return villageDao.listVillageNoByHospitalCode(hospitalCode);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public boolean deleteVillage(Integer id) throws Exception {
		try{
			return villageDao.deleteVillage(id);
		}catch(Exception e){
			throw e;
		}
	}
}
