package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwg.asservice.dao.MenuDao;
import com.fwg.asservice.model.config.Menu;
import com.fwg.asservice.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Transactional
	public void saveMenu(Menu menu) throws Exception { 
		try {
			menuDao.saveMenu(menu);
		} catch (Exception exception) {
			throw exception;
		}
	}

	@Transactional(readOnly = true)
	public List<Menu> listMenus(Integer userRoleId) throws Exception {
		try {
			return menuDao.listMenus(userRoleId);
		} catch (Exception exception) {
			throw exception;
		}
	}
	
	@Transactional(readOnly = true)
	public Menu getMenu(int id) throws Exception {
		try {
			return menuDao.getMenu(id);
		} catch (Exception exception) {
			throw exception;
		}
	}

	@Transactional
	public void deleteMenu(int id) throws Exception {
		try {
			menuDao.deleteMenu(id);
		} catch (Exception exception) {
			throw exception;
		}

	}
}
