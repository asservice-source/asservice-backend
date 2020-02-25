package com.fwg.asservice.config.core;

import javax.servlet.Filter;

import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.fwg.asservice.config.CORSFilter;
import com.fwg.asservice.config.RootConfig;
//import com.fwg.asservice.config.SecurityConfig;
import com.fwg.asservice.config.WebAppConfig;

@Order(1)
public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		//return new Class[] { RootConfig.class, SecurityConfig.class}; 
		return new Class[] { RootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebAppConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
    @Override
    protected Filter[] getServletFilters() {
    	Filter [] singleton = { new CORSFilter()};
    	return singleton;
    }
	
//	@Override
//	protected Filter[] getServletFilters() {
//		return new Filter[]{new ApplicationHandleFilter()};
//	}

}
