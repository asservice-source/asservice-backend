package com.fwg.asservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fwg.asservice.constants.APIStatusMessage;
import com.fwg.asservice.constants.RequestMappingConstants;
import com.fwg.asservice.constants.RestURIConstants;
import com.fwg.asservice.model.APIResponse;
import com.fwg.asservice.model.config.Image;
import com.fwg.asservice.service.ImageService;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
@RequestMapping(RequestMappingConstants.APP)
public class ImageController { 
	
	@Autowired
	private ImageService imageService;
	
	@PostMapping(value = RestURIConstants.GET_IMAGE)
	public @ResponseBody APIResponse listImage(@RequestBody ObjectNode requestParams) {
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final Image image = mapper.convertValue(requestParams, Image.class);
		
		String status = APIStatusMessage.SUCCESS;
		String message ="Success to get Image.";
		
		JSONArray arr = new JSONArray();
		JSONObject jsonObject = null;
		List<Image> images = null;
		try {
			images = imageService.listImages(image);
			for (Image image2 : images) {
				jsonObject = new JSONObject();
				jsonObject.put("rowGUID", image2.getRowGUID());
				jsonObject.put("category", image2.getCategory());
				jsonObject.put("ordinal", image2.getOrdinal());
				jsonObject.put("path", image2.getPath());
				jsonObject.put("pathdata", image2.getPathdata());
				jsonObject.put("alt", image2.getAlt());
				jsonObject.put("isActive", image2.isActive());
				arr.add(jsonObject);
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
			status = APIStatusMessage.FAILED;
			message = GlobalFunction.getExceptionMessage(exception);
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(requestParams);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(arr);
		
		return resp;
	}
}
