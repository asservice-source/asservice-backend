package com.fwg.asservice.config;

import java.io.File;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class ReportConfig {
	// C:\Program Files\Apache Software Foundation\Tomcat 8.0\resources\
	public static String FILE_SEPARATOR = File.separator;
//	public static String REPORT_ROOT_PATH = "D:\\WORKSPACE\\JAVAEE\\Eclipse\\API-ASService\\src\\main\\resources\\reports\\";
	public static String classPath =  ReportConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath().replaceAll("%20", " ");
	public static String REPORT_ROOT_PATH = classPath.substring(0, classPath.indexOf("classes")) + "classes/reports/"; //ReportConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"\\reports\\";//"C:\\Program Files\\Apache Software Foundation\\Tomcat 8.0\\webapps\\api-asservice-front\\WEB-INF\\resources\\reports\\";
	public static String PATH_OF_DEATH_SURVEY = REPORT_ROOT_PATH + "Survey"+ FILE_SEPARATOR + "Death" + FILE_SEPARATOR;
	public static String PATH_OF_CANCER_SURVEY = REPORT_ROOT_PATH + "Survey" + FILE_SEPARATOR + "Cancer" + FILE_SEPARATOR;
	public static String PATH_OF_PATIENT_SURVEY = REPORT_ROOT_PATH + "Survey" + FILE_SEPARATOR + "Patient" + FILE_SEPARATOR;
	public static String PATH_OF_PREGNANT_SURVEY = REPORT_ROOT_PATH + "Survey" + FILE_SEPARATOR + "Pregnancy" + FILE_SEPARATOR;
	public static String PATH_OF_POPULATION_SURVEY = REPORT_ROOT_PATH + "Survey" + FILE_SEPARATOR + "Population" + FILE_SEPARATOR;
	public static String PATH_OF_HICI_MOSQUITO_SURVEY = REPORT_ROOT_PATH + "Survey" + FILE_SEPARATOR + "HICI" + FILE_SEPARATOR + "Mosquito" + FILE_SEPARATOR;
	public static String PATH_OF_METABOLIC_SURVEY = REPORT_ROOT_PATH + "Survey" + FILE_SEPARATOR + "Metabolic" + FILE_SEPARATOR;

	@Bean
	@Autowired
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}

	@Bean
	@Autowired
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate(dataSource));
		return namedParameterJdbcTemplate;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://192.168.1.203:3306/asservice?useEncoding=true&amp;characterEncoding=UTF-8;");
		dataSource.setUsername("root");
		dataSource.setPassword("password");
		return dataSource;
	}
	
	public static void main(String[] args) {
		String classPath = ReportConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath()  ;
		String jasperPath = classPath.substring(0, classPath.length()-8) + "reports\\" ;
		System.out.println(jasperPath.replace("/","\\"));
	}
}
