package com.fwg.asservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fwg.asservice.builder.MenuBuilder;
import com.fwg.asservice.constants.RequestMappingConstants;
import com.fwg.asservice.constants.RestURIConstants;
import com.fwg.asservice.model.config.Menu;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.service.MenuService;

/**
 * Handles requests for the Employee service.
 */
@Controller
@RequestMapping(RequestMappingConstants.APP)
public class MenuController { 
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value = RestURIConstants.GET_MENU, method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getAllMenu(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Filter filter = mapper.convertValue(requestParams, Filter.class);
		
		List<Menu> menuList = null;
		MenuBuilder menuBuilder = new MenuBuilder();
		String jsonMenu = "";
		
		Integer userRoleId = filter.getRoleId();
		
		try {
			menuList = menuService.listMenus(userRoleId);
			jsonMenu = menuBuilder.buildJsonMenu(menuList); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonMenu;
	}
}
