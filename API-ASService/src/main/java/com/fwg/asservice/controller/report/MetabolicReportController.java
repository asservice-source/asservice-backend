package com.fwg.asservice.controller.report;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fwg.asservice.config.ReportConfig;
import com.fwg.asservice.constants.ReportURIConstants;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Controller
public class MetabolicReportController {

	AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ReportConfig.class);
	DataSource asServiceDataSource = applicationContext.getBean(DataSource.class);
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = applicationContext.getBean(NamedParameterJdbcTemplate.class);

	
	@RequestMapping(value = "/test" + ReportURIConstants.METABOLIC_PERSON_INFORMATION)
	public void test_viewReportOfPersonInformation(HttpServletResponse response/* Writer responseWriter */) throws Exception {
//		viewReportOfPersonInformation("A380E4B2-77C3-E711-AB84-005056C00008", "07D03A92-6BC3-E711-AB84-005056C00008", "0848DE88-BAC2-E711-AB84-005056C00008", response);
//		viewReportOfPersonInformation("A380E4B2-77C3-E711-AB84-005056C00008", "07D03A92-6BC3-E711-AB84-005056C00008", null, response);
//		viewReportOfPersonInformation("A380E4B2-77C3-E711-AB84-005056C00008", null, null, response);
		//viewReportOfPersonInformation(null, null, null, response);
	}
	
	@RequestMapping(value = ReportURIConstants.METABOLIC_PERSON_INFORMATION)
	public void viewReportOfPersonInformation(
			@RequestParam(name="SurveyHeaderRowGUID") String surveyHeaderRowGUID, 
			@RequestParam(name="OSMPersonID") String osmPersonID,
			@RequestParam(name="VillageID") String villageID,
			@RequestParam(name="Name") String serviceProviderName,
			@RequestParam(name="StatusID") String statusID,
			@RequestParam(name="UserPersonID") String userPersonID,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> reportParams = new HashMap<>();
		reportParams.put("SurveyHeaderRowGUID", surveyHeaderRowGUID);
		reportParams.put("OSMPersonID", osmPersonID);
		reportParams.put("VillageID", villageID);
		reportParams.put("ServiceProviderName", serviceProviderName);
		//reportParams.put("StatusID", statusID);
		reportParams.put("UserPersonID", userPersonID);
		
		String reportName = ""; 
		switch(statusID) {
		   case "1" :
			   reportName =  "ReportForMetabolicSurvey_PersonInfo (Twice a Year)";
			   
		      break; // optional		   
		   case "2" :
			   reportName =  "ReportForMetabolicSurvey_PersonInfo_NotSurvey";
		      break; // optional
		   default : // Optional
			   System.out.println("Can not found data");
		}
		// Render to PDF Page
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_METABOLIC_SURVEY + reportName + ".jasper", reportParams, asServiceDataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}
	
	@RequestMapping(value = ReportURIConstants.METABOLIC_PERSON_INFORMATION_FORM)
	public void viewFormMetabolic(
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> reportParams = new HashMap<>();
	

		// Render to PDF Page
		String reportName = "ReportForMetabolicSurvey_PersonInfo (Twice a Year)_NewForm";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_METABOLIC_SURVEY + reportName + ".jasper", reportParams, asServiceDataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}
}
