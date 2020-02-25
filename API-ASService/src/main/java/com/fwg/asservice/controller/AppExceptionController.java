package com.fwg.asservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
class AppExceptionController {

	@RequestMapping("error")
	public ModelAndView initView(HttpServletRequest request, HttpServletResponse response) {
		// retrieve some useful information from the request
		ModelAndView modelAndView = new ModelAndView();
		
		int httpErrorCode = getErrorCode(request);
		Throwable throwable = getThrowable(request);
		String requestUri = getRequestUri(request);
		
		String message = "";
		String exceptionName = "";
		String exceptionMessage = "";
		
		System.out.println("httpErrorCode : " + httpErrorCode);
		
		switch (httpErrorCode) {
			case 400:{
				message = "Sorry, Bad Request.";
				exceptionName = "Bad Request";
				exceptionMessage = "Bad Request !!!!";
				break;
			}
			case 404:{
				message = "Sorry, Page you're looking for is not found";
				exceptionName = "";
				exceptionMessage = "";
				 break;
			}
			case 500:{
				message = "";
				exceptionName = throwable.getClass().getName();
				exceptionMessage = throwable.getMessage();
				 break;
			}
			default:{
				message = "Sorry, Page you're looking for is not found";
				exceptionName = "";
				exceptionMessage = "";
				break;
			}
		}
		
		modelAndView.addObject("httpErrorCode", httpErrorCode);
		modelAndView.addObject("message", message);
		modelAndView.addObject("exceptionName", exceptionName);
		modelAndView.addObject("exceptionMessage", exceptionMessage);
        modelAndView.addObject("requestUri", requestUri);
        modelAndView.setViewName("pages/error-page/error-page");
		
		return modelAndView;
	}
	
	private int getErrorCode(HttpServletRequest request) {
		return (Integer) request.getAttribute("javax.servlet.error.status_code");
	}
	
	private Throwable getThrowable(HttpServletRequest request) {
		return (Throwable) request.getAttribute("javax.servlet.error.exception");
	}
	
	private String getRequestUri(HttpServletRequest request) {
		
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		
		if (requestUri == null) {
			requestUri = "Unknown";
		}
		
		  return requestUri;
	}

}
