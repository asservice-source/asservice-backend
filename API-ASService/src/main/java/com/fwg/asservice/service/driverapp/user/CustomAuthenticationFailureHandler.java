package com.fwg.asservice.service.driverapp.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.fwg.asservice.common.util.RequestUtil;

/**
 * @author audixo
 *
 */
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler  {
  
	
	public CustomAuthenticationFailureHandler() {}

	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		if(RequestUtil.isAjaxRequest(request)) {
			String errorMessage = getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION");
			RequestUtil.sendJsonResponse(response, "error", errorMessage);
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}
			
		
	}
	
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}

}
