package com.fwg.asservice.model.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Menu")
public class Menu {
	
	@Id
	@Column(name = "MenuID")
	private int menuId;
	
	@Column(name = "ParentID")
	private int parentId;
	
	@Column(name = "SortOrder")
	private int sortOrder;
	
	@Column(name = "Title")
	private String title;
	
	@Column(name = "Icon")
	private String icon;
	
	@Column(name = "Link")
	private String link;
	
	@Column(name = "IsActive")
	private boolean isActive;
	
	private int childCount;

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public int getChildCount() {
		return childCount;
	}

	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}
	
}
