package com.fwg.asservice.dao;

import java.util.List;

import com.fwg.asservice.model.config.Menu;

public interface MenuDao {

	/*
	 * CREATE and UPDATE
	 */
	public void saveMenu(Menu menu) throws Exception; // create and update

	/*
	 * READ
	 */
	public List<Menu> listMenus(Integer userRoleId) throws Exception; 
	public Menu getMenu(int id) throws Exception;

	/*
	 * DELETE
	 */
	public void deleteMenu(int id) throws Exception;
}
