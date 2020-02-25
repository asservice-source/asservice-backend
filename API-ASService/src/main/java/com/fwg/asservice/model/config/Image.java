package com.fwg.asservice.model.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fwg.asservice.utility.GlobalFunction;

@Entity
@Table(name = "Images")
public class Image {
	
	@Id
	@Column(name = "RowGUID")
	private String rowGUID;
	
	@Column(name = "Category")
	private String category;
	
	@Column(name = "Ordinal")
	private int ordinal;
	
	@Column(name = "Path")
	private String path;
	
	@Column(name = "Pathdata")
	private String pathdata;
	
	@Column(name = "Alt")
	private String alt;
	
	@Column(name = "IsActive")
	private boolean isActive;

	public String getRowGUID() {
		return rowGUID;
	}

	public void setRowGUID(String rowGUID) {
		this.rowGUID = rowGUID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public String getPath() {
		return GlobalFunction.isEmpty(path) ? "" : GlobalFunction.getBaseUrl()+path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPathdata() {
		return pathdata;
	}

	public void setPathdata(String pathdata) {
		this.pathdata = pathdata;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
