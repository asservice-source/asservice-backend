package com.fwg.asservice.controller.report;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fwg.asservice.config.ReportConfig;
import com.fwg.asservice.constants.APIStatusMessage;
import com.fwg.asservice.constants.ReportURIConstants;
import com.fwg.asservice.constants.RestURIConstants;
import com.fwg.asservice.model.APIResponse;
import com.fwg.asservice.model.Province;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Controller
public class ReportTesterController {

	AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ReportConfig.class);
	DataSource asServiceDataSource = applicationContext.getBean(DataSource.class);
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = applicationContext.getBean(NamedParameterJdbcTemplate.class);
	
	@RequestMapping(value = "/report/hello")
	public ModelAndView helloWorld() {
		ModelAndView modelAndView = new ModelAndView("test/report/hello");
		//modelAndView.addObject("message", "Hello, World...123"); 
		return modelAndView;
	}
	
	@RequestMapping(value="/report/tester")
	public ModelAndView viewReportTester() {
		ModelAndView modelAndView = new ModelAndView("test/report/ReportTester");
		return modelAndView;
	}
	
	@RequestMapping(value = "/report/path", method = RequestMethod.GET)
	public @ResponseBody APIResponse getReportpath() {
		
		//String classPath = ReportConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath()  ;
		//String jasperPath = classPath.substring(0, classPath.indexOf("classes")) + "classes\\reports\\" ;
		//System.out.println(jasperPath.replace("/","\\"));
		
		//jasperPath = jasperPath.replace("/","\\");
		//URI u = new URI(jasperPath.trim().replaceAll("\\u0020", "%20"));
		//File file = new File(jasperPath);
		//System.out.println(file.getPath());
		
		APIResponse resp = new APIResponse();
		//resp.setStatus(status);
		//resp.setMessage(message);
		//resp.setParam(requestParams);
		//resp.setDate(GlobalFunction.currentTimeStamp());
		String rootpath = "";
		try{
			rootpath = ReportConfig.REPORT_ROOT_PATH;
		}catch(Exception e){
			e.printStackTrace();
			rootpath = e.getMessage();
		}
		
		resp.setResponse(rootpath);
		
		return resp;
	}
	
}
