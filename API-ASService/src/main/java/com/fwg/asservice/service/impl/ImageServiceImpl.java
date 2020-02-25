package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwg.asservice.dao.ImageDao;
import com.fwg.asservice.model.config.Image;
import com.fwg.asservice.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageDao imageDao;

	@Transactional(readOnly = true)
	public List<Image> listImages(Image image) throws Exception {
		try {
			return imageDao.listImages(image);
		} catch (Exception exception) {
			throw exception;
		}
	}
}
