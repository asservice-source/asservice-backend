package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwg.asservice.dao.HomeDao;
import com.fwg.asservice.model.FamilyStatus;
import com.fwg.asservice.model.Home;
import com.fwg.asservice.model.HomeType;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService { 
	
	@Autowired
	private HomeDao homeDao;

	@Transactional(readOnly = true)
	public List<Home> listHomes(Integer villageId, String osmId, Integer id) throws Exception {
		try{
			return homeDao.listHomes(villageId, osmId, id);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<Home> listHomeByVillageOrOSM(Integer villageId, String osmId, String headerTypeCode, boolean memberOnly) throws Exception {
		try{
			return homeDao.listHomeByVillageOrOSM(villageId, osmId, headerTypeCode, memberOnly);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<FamilyStatus> listFamilyStatuss() throws Exception {
		try{
			return homeDao.listFamilyStatuss();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public Home getHomeByPersonId(String personId) throws Exception {
		try{
			return homeDao.getHomeByPersonId(personId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public Home getHomeInfoWithOsm(Integer homeId) throws Exception {
		try{
			return homeDao.getHomeInfoWithOsm(homeId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public Home insertOrUpdateHome(Home home) throws Exception {
		try{
			return homeDao.insertOrUpdateHome(home);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public boolean deleteHome(Integer homeId) throws Exception {
		try{
			return homeDao.deleteHome(homeId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<HomeType> listHomeTypeMonitorHICI() throws Exception {
		try{
			return homeDao.listHomeTypeMonitorHICI();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<Home> listHomeByVillageHomeType(Integer id, String homeTypeCode, String documentId, String osmId) throws Exception {
		try{
			return homeDao.listHomeByVillageHomeType(id, homeTypeCode, documentId, osmId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<Home> find(Filter filter, Home home) throws Exception {
		try{
			return homeDao.find(filter, home);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<Home> listHomeWithoutOSM(Integer villageId) throws Exception {
		try{
			return homeDao.listHomeWithoutOSM(villageId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<Home> listHomeByHeaderTypeCode(String documentId, Integer villageId, String osmId,
			String headerTypeCode) throws Exception {
		try{
			return homeDao.listHomeByHeaderTypeCode(documentId, villageId, osmId, headerTypeCode);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public Home insertOrUpdateOSM(Home home, String osmId) throws Exception {
		try{
			return homeDao.insertOrUpdateOSM(home, osmId);
		}catch(Exception e){
			throw e;
		}
	}
}