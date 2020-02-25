package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwg.asservice.dao.AddressDao;
import com.fwg.asservice.model.Amphur;
import com.fwg.asservice.model.Province;
import com.fwg.asservice.model.Tumbol;
import com.fwg.asservice.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressDao addressDao;

	@Transactional
	public void saveProvince(Province brand) throws Exception {
		try {
			addressDao.saveProvince(brand);
		} catch (Exception exception) {
			throw exception;
		}
	}

	@Transactional(readOnly = true)
	public List<Tumbol> listTumbols(String amphurCode) throws Exception {
		try {
			return addressDao.listTumbols(amphurCode);
		} catch (Exception exception) {
			throw exception;
		}

	}
	


	@Transactional
	public void deleteProvince(int id) throws Exception {
		try {
			addressDao.deleteProvince(id);
		} catch (Exception exception) {
			throw exception;
		}

	}

	@Transactional(readOnly = true)
	public List<Amphur> listAmphurs(String provinceCode) throws Exception {
		try {
			return addressDao.listAmphurs(provinceCode);
		} catch (Exception exception) {
			throw exception;
		}
	}

	@Override
	public List<Province> listProvinces(String provinceCode) throws Exception {
		try {
			return addressDao.listProvinces(provinceCode);
		} catch (Exception exception) {
			throw exception;
		}
	}

}
