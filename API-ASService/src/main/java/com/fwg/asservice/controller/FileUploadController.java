package com.fwg.asservice.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fwg.asservice.constants.APIStatusMessage;
import com.fwg.asservice.constants.RequestMappingConstants;
import com.fwg.asservice.constants.RestURIConstants;
import com.fwg.asservice.model.APIResponse;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.service.FileUploadService;
import com.fwg.asservice.utility.GlobalFunction;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;

@Controller
@RequestMapping(RequestMappingConstants.FILE_UPLOAD)
public class FileUploadController {
	
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
    private ServletContext context;
	
	@RequestMapping(value = RestURIConstants.UPLOAD_PROFILE, method = RequestMethod.POST, consumes = "multipart/form-data")
	public @ResponseBody APIResponse uploadProfile(@RequestParam("file") MultipartFile file, @RequestParam("personId") String personId) {
		
		String status = APIStatusMessage.SUCCESS;
		String message = "";
		String name = file.getOriginalFilename();
		Person person = new Person();
		if (!file.isEmpty()) {
			try {
				
				byte[] bytes = file.getBytes();
				
				String downloadFolder = context.getRealPath("/WEB-INF/resources/uploads/profile");
				String baseUrl = GlobalFunction.getBaseUrl();
				System.out.println("baseUrl : "+baseUrl);
				File dir = new File(downloadFolder + File.separator);
				if (!dir.exists())
					dir.mkdirs();
				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + personId + getFileExtension(file));
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				
				person.setPersonId(personId);
				person.setPicturePath("/web-resources/uploads/profile/"+ personId + getFileExtension(file));
				
				fileUploadService.uploadProfile(person);
				
				message = "You successfully uploaded file=" + name;
			} catch (Exception e) {
				e.printStackTrace();
				status = APIStatusMessage.FAILED;
				message = "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			status = APIStatusMessage.FAILED;
			message = "You failed to upload " + name
					+ " because the file was empty.";
		}
		
		APIResponse resp = new APIResponse();
		resp.setStatus(status);
		resp.setMessage(message);
		resp.setParam(name);
		resp.setDate(GlobalFunction.currentTimeStamp());
		resp.setResponse(person.getPicturePath());
		
		return resp;
	}
	
	private static String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf("."));
        else return "";
    }
}
