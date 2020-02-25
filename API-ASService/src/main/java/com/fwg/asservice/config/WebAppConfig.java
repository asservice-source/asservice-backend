package com.fwg.asservice.config;

import java.util.Locale;
import java.util.Properties;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan("com.fwg.asservice.controller")
public class WebAppConfig extends WebMvcConfigurerAdapter{
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/web-resources/**")
		        .addResourceLocations("/WEB-INF/resources/");
	}
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

//	@Bean(name = "multipartResolver")
//    public StandardServletMultipartResolver resolver() {
//        return new StandardServletMultipartResolver();
//    }
	
//	 @Bean  
//	public ResourceBundleMessageSource messageSource() {
//		 ResourceBundleMessageSource source = new ResourceBundleMessageSource();
//	        source.setBasename("/Message/message");
//	        //source.setUseCodeAsDefaultMessage(true);
//	        source.setDefaultEncoding("UTF-8");
//	    return source; 
//	}
	 
	 @Bean
	 public MessageSource messageSource() {
	        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//	        messageSource.setBasename("/META-INF/i18/message");
	        messageSource.setBasename("classpath:message");
	        messageSource.setDefaultEncoding("UTF-8");
	        return messageSource;
	}
	 
	 @Bean
	 public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource){
	     return new MessageSourceAccessor(messageSource, Locale.ENGLISH );
	 }
	 
	@Bean
	public LocaleResolver localeResolver(){
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(new Locale("en"));
		resolver.setCookieName("localeCookie");
		resolver.setCookieMaxAge(4800);
		return resolver;
	}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("locale");
		registry.addInterceptor(interceptor);
    }
	
    @Bean
	public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
	  SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
      Properties errorMaps = new Properties();
      errorMaps.setProperty("ElectricityNotFoundException", "error");
      errorMaps.setProperty("NullPointerException", "error");
      resolver.setExceptionMappings(errorMaps);
      resolver.setDefaultErrorView("globalerror");
      resolver.setExceptionAttribute("exc");
      return resolver;
   }
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver resolver = new CommonsMultipartResolver();
	    resolver.setDefaultEncoding("utf-8");
	    return resolver;
	}
	
}
