package com.fwg.asservice.common.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.web.util.matcher.ELRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Utilities related to server requests and responses
 * 
 * @author audixo
 *
 */
public class RequestUtil {

  private static final Logger LOGGER = Logger.getLogger(RequestUtil.class.getName());
	
	private static final RequestMatcher REQUEST_MATCHER = new ELRequestMatcher("hasHeader('X-Requested-With','XMLHttpRequest')");
	
	public static final String JSON_VALUE = "{\"%s\": %s}";
	
	
	public static Boolean isAjaxRequest(HttpServletRequest request) {
		return REQUEST_MATCHER.matches(request);
	}
	
	public static void sendJsonResponse(HttpServletResponse response, String key, String message) {
		response.setContentType("application/json;charset=UTF-8");           
        response.setHeader("Cache-Control", "no-cache");
        try {
			response.getWriter().write(String.format(JSON_VALUE, key, message));
		} catch (IOException e) {
			LOGGER.error("error writing json to response", e);
		}
	}

}