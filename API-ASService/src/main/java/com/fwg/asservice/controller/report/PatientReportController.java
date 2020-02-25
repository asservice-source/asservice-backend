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
public class PatientReportController {

	AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ReportConfig.class);
	DataSource asServiceDataSource = applicationContext.getBean(DataSource.class);
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = applicationContext.getBean(NamedParameterJdbcTemplate.class);

	
	@RequestMapping(value = "/test" + ReportURIConstants.PATIENT_MONTHLY_REPORT)
	public void test_viewMonthlyReportForDisabledPersonsOrPatients(HttpServletResponse response/* Writer responseWriter */) throws Exception {
//		viewMonthlyReportForDisabledPersonsOrPatients("7EEEFF51-71B5-E711-AB84-005056C00008", "1349900301501", response);
//		viewMonthlyReportForDisabledPersonsOrPatients("7EEEFF51-71B5-E711-AB84-005056C00008", null, response);
		//viewMonthlyReportForDisabledPersonsOrPatients(null, null, response);
	}
	
	@RequestMapping(value = ReportURIConstants.PATIENT_MONTHLY_REPORT)
	public void viewMonthlyReportForDisabledPersonsOrPatients(
			@RequestParam(name="SurveyHeaderRowGUID") String surveyHeaderRowGUID, 
			@RequestParam(name="OSMPersonID") String osmCitizenID,
			@RequestParam(name="VillageID") String villageID,
			@RequestParam(name="Name") String patientName,
			@RequestParam(name="UserPersonID") String userPersonID,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> reportParams = new HashMap<>();
		reportParams.put("SurveyHeaderRowGUID", surveyHeaderRowGUID);
		reportParams.put("OSMPersonID", osmCitizenID);
		reportParams.put("VillageID", villageID);
		reportParams.put("PatientName", patientName);
		reportParams.put("UserPersonID", userPersonID);

		// Render to PDF Page
		String reportName = "MonthlyReportForPatient";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_PATIENT_SURVEY + reportName + ".jasper", reportParams, asServiceDataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}
	
	@RequestMapping(value = ReportURIConstants.PATIENT_MONTHLY_REPORT_FORM)
	public void viewFormMonthlyPatients(
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> reportParams = new HashMap<>();
		

		// Render to PDF Page
		String reportName = "MonthlyReportForPatient_NewForm";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_PATIENT_SURVEY + reportName + ".jasper", reportParams, asServiceDataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}
}
