package com.fwg.asservice.config;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.fwg.asservice")
public class RootConfig {
	
	@Resource
	private Environment env;
	
	public static final String DATABASE_DRIVER = "db.driverClassName";
	public static final String DATABASE_URL = "db.url";
	public static final String DATABASE_USERNAME = "db.username";
	public static final String DATABASE_PASSWORD = "db.password";
	
    public static final String HIBERNATE_DIALECT = "hibernate.dialect";
    public static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    public static final String HIBERNATE_CONNECTION_USE_UNICODE = "hibernate.connection.useUnicode";
    public static final String HIBERNATE_CONNECTION_CHARACTER_ENCODING = "hibernate.connection.characterEncoding";
    public static final String HIBERNATE_CONNECTION_CHAR_SET = "hibernate.connection.charSet";
    
    public static final String ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://192.168.1.203:3306/asservice?useEncoding=true&amp;characterEncoding=UTF-8;");
		dataSource.setUsername("root");
		dataSource.setPassword("password");
		
		return dataSource;
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan("com.fwg.asservice.model");
		sessionFactoryBean.setHibernateProperties(hibProperties());
		return sessionFactoryBean;
	}
	
	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put(HIBERNATE_CONNECTION_USE_UNICODE, "true");
		properties.put(HIBERNATE_CONNECTION_CHARACTER_ENCODING, "UTF-8");
		properties.put(HIBERNATE_CONNECTION_CHAR_SET, "UTF-8");
		properties.put(HIBERNATE_DIALECT, "org.hibernate.dialect.SQLServerDialect");
		properties.put(HIBERNATE_SHOW_SQL, "false");
		return properties;	
	}
	
//	@Bean
//	public DataSource dataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		
//		dataSource.setDriverClassName(env.getRequiredProperty(DATABASE_DRIVER));
//		dataSource.setUrl(env.getRequiredProperty(DATABASE_URL));
//		dataSource.setUsername(env.getRequiredProperty(DATABASE_USERNAME));
//		dataSource.setPassword(env.getRequiredProperty(DATABASE_PASSWORD));
//		
//		return dataSource;
//	}
	
//	@Bean
//	public LocalSessionFactoryBean sessionFactory() {
//		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//		sessionFactoryBean.setDataSource(dataSource());
//		sessionFactoryBean.setPackagesToScan(env.getRequiredProperty(ENTITYMANAGER_PACKAGES_TO_SCAN));
//		sessionFactoryBean.setHibernateProperties(hibProperties());
//		return sessionFactoryBean;
//	}
//	
//	private Properties hibProperties() {
//		Properties properties = new Properties();
//		properties.put(HIBERNATE_CONNECTION_USE_UNICODE, env.getRequiredProperty(HIBERNATE_CONNECTION_USE_UNICODE));
//		properties.put(HIBERNATE_CONNECTION_CHARACTER_ENCODING, env.getRequiredProperty(HIBERNATE_CONNECTION_CHARACTER_ENCODING));
//		properties.put(HIBERNATE_CONNECTION_CHAR_SET, env.getRequiredProperty(HIBERNATE_CONNECTION_CHAR_SET));
//		properties.put(HIBERNATE_DIALECT, env.getRequiredProperty(HIBERNATE_DIALECT));
//		properties.put(HIBERNATE_SHOW_SQL, env.getRequiredProperty(HIBERNATE_SHOW_SQL));
//		return properties;	
//	}
	
	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
	
    @Bean
    public static PropertyPlaceholderConfigurer placeHolderConfigurer(){
      PropertyPlaceholderConfigurer placeHolderConfigurer = new PropertyPlaceholderConfigurer();
      ClassPathResource[] cpResources = new ClassPathResource[] { new ClassPathResource("application.properties") };
      placeHolderConfigurer.setLocations(cpResources);
      placeHolderConfigurer.setIgnoreUnresolvablePlaceholders(true);
      return placeHolderConfigurer;
    }
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST")
                        .allowedHeaders("X-Requested-With", "Content-Type", "Authorization", "Origin", "Accept", "Access-Control-Request-Method", "Access-Control-Request-Headers")
                        .allowCredentials(true).maxAge(3600);
            }
        };
    }
    
    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
 
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("forceranking@gmail.com");
        mailSender.setPassword("F0R48#3we");
 
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");
 
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }
 
    @Bean
    public VelocityEngine getVelocityEngine() throws VelocityException, IOException {
        VelocityEngineFactory velocityEngineFactory = new VelocityEngineFactory();
        Properties props = new Properties();
        props.put("resource.loader", "class");
        props.put("input.encoding", "utf-8");
        props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
 
        velocityEngineFactory.setVelocityProperties(props);
        return velocityEngineFactory.createVelocityEngine();
    }
	
}
