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
public class PopulationReportController {

	AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ReportConfig.class);
	DataSource dataSource = applicationContext.getBean(DataSource.class);
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = applicationContext.getBean(NamedParameterJdbcTemplate.class);

	
	@RequestMapping(value = "/test" + ReportURIConstants.POPULATION_YEARLY_REPORT)
	public void test_viewYearlyReportForPopulation(HttpServletResponse response/* Writer responseWriter */) throws Exception {
//		viewMonthlyReportForPregnantWomen("71A6F169-1CBD-E711-AB84-005056C00008", "1349900301501", response);
//		viewYearlyReportForPopulation("6AE8531A-0BBE-E711-AB84-005056C00008", "21", response);
//		viewYearlyReportForPopulation("6AE8531A-0BBE-E711-AB84-005056C00008", null, response);
//		viewYearlyReportForPopulation("6AE8531A-FFFF-FFFF-FFFF-005056C00008", null, response);
		//viewYearlyReportForPopulation(null, null, null, null,response);
	}
	
	@RequestMapping(value = ReportURIConstants.POPULATION_YEARLY_REPORT)
	public void viewYearlyReportForPopulation(
		@RequestParam(name="SurveyHeaderRowGUID") String surveyHeaderRowGUID, 
		@RequestParam(name="HomeID") String homeID,
		@RequestParam(name="OSMPersonID") String osmPersonID,
		@RequestParam(name="VillageID") String villageID,
		@RequestParam(name="StatusID") String statusID,
		@RequestParam(name="UserPersonID") String userPersonID,
		HttpServletResponse response) throws Exception {
		
		System.out.println("SurveyHeader: " + surveyHeaderRowGUID);
		System.out.println("HomeID: " + homeID);
		System.out.println("OSMPersonID: " + osmPersonID);
		System.out.println("VillageID: " + villageID);
		//System.out.println("StatusID: " + statusID);
		System.out.println("UserPersonID: " + userPersonID);
		
		Map<String, Object> reportParams = new HashMap<>();
		reportParams.put("SurveyHeaderRowGUID", surveyHeaderRowGUID);
		reportParams.put("HomeID", homeID);
		reportParams.put("OSMPersonID", osmPersonID);
		reportParams.put("VillageID", villageID);
		//reportParams.put("StatusID", statusID);
		reportParams.put("UserPersonID", userPersonID);
		reportParams.put("SUBREPORT_DIR", ReportConfig.PATH_OF_POPULATION_SURVEY);
		
		String reportName = ""; 
		switch(statusID) {
		   case "1" :
			   reportName =  "YearlyReportForPopulation";
		      break; // optional		   
		   case "2" :
			   reportName =  "YearlyReportForPopulation_NotSurvey";
		      break; // optional
		   default : // Optional
			   System.out.println("Can not found data");
		}
		
		// Render to PDF Page
		//String reportName = "YearlyReportForPopulation";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_POPULATION_SURVEY + reportName + ".jasper", reportParams, dataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}
	
	@RequestMapping(value = ReportURIConstants.POPULATION_YEARLY_REPORT_FORM)
	public void viewFormForPopulation(
	
		HttpServletResponse response) throws Exception {
		

		
		Map<String, Object> reportParams = new HashMap<>();

		
		// Render to PDF Page
		String reportName = "YearlyReportForPopulation_NewForm";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_POPULATION_SURVEY + reportName + ".jasper", reportParams, dataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}
	
}
