package com.fwg.asservice.service;

import java.util.List;

import com.fwg.asservice.model.config.Image;

public interface ImageService {


	/*
	 * READ
	 */
	public List<Image> listImages(Image image) throws Exception; 	
}
