package com.fwg.asservice.service;

import java.util.List;

import com.fwg.asservice.model.config.Menu;

public interface MenuService {

	/*
	 * CREATE and UPDATE 
	 */
	public void saveMenu(Menu menu) throws Exception; 

	/*
	 * READ
	 */
	public List<Menu> listMenus(Integer userRoleId) throws Exception;
	public Menu getMenu(int id) throws Exception;

	/*
	 * DELETE
	 */
	public void deleteMenu(int id)  throws Exception;

}
