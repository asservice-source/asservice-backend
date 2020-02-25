package com.fwg.asservice.builder;

import java.util.List;

import com.fwg.asservice.model.config.Menu;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class MenuBuilder {
	
	public String buildJsonMenu(List<Menu> menuList){ 
		
		JsonArray jsonArray = new JsonArray();
		JsonObject jsonObject = null;
		
		for (Menu menu : menuList) {
			if(menu.getParentId() == 0){
				    jsonObject = new JsonObject();
				    
				    jsonObject.addProperty("menuId", menu.getMenuId());
				    jsonObject.addProperty("parentId", menu.getParentId());
				    jsonObject.addProperty("title", menu.getTitle());
				    jsonObject.addProperty("icon", menu.getIcon() == null? "" : menu.getIcon());
				    jsonObject.addProperty("link", menu.getLink() == null? "" : menu.getLink());
				    
					if(menu.getChildCount() > 0){
						jsonObject.add("sublinks", buildSubMenu(menuList, menu.getMenuId()));
					}
					
				jsonArray.add(jsonObject);
			}
			
			
		}
		
		return jsonArray.toString();
		
	}
	
	private JsonArray buildSubMenu(List<Menu> menuList, int menuId){
		
		JsonArray jsonArray = new JsonArray();
		JsonObject jsonObject = null;
		
		for (Menu menu : menuList) {
			if(menu.getParentId() == menuId){
				jsonObject = new JsonObject();
			    jsonObject.addProperty("menuId", menu.getMenuId());
			    jsonObject.addProperty("parentId", menu.getParentId());
			    jsonObject.addProperty("title", menu.getTitle());
			    jsonObject.addProperty("icon", menu.getIcon() == null? "" : menu.getIcon());
			    jsonObject.addProperty("link", menu.getLink() == null? "" : menu.getLink());
			    
			    jsonArray.add(jsonObject);
			}
		}
		
		return jsonArray;
	}

}
