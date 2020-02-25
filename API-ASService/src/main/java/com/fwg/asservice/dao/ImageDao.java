package com.fwg.asservice.dao;

import java.util.List;

import com.fwg.asservice.model.config.Image;

public interface ImageDao {

	/*
	 * READ
	 */
	public List<Image> listImages(Image image) throws Exception; 	

}
