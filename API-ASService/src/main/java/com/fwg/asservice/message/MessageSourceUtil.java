package com.fwg.asservice.message;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class MessageSourceUtil {
	
	@Autowired
	private MessageSourceAccessor msgs;
	
	private static MessageSourceAccessor message;
	
	@PostConstruct     
	private void initStaticMessage () {
		message = this.msgs;
	}
	
	public static String getMessage(String code){
		
		return message.getMessage(code);
		
	}

}
