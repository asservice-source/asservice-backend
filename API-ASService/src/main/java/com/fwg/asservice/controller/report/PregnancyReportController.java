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
public class PregnancyReportController {

	AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ReportConfig.class);
	DataSource asServiceDataSource = applicationContext.getBean(DataSource.class);
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = applicationContext.getBean(NamedParameterJdbcTemplate.class);

	
	@RequestMapping(value = "/test" + ReportURIConstants.PREGNANCY_MONTHLY_REPORT)
	public void test_viewMonthlyReportForPregnantWomen(HttpServletResponse response/* Writer responseWriter */) throws Exception {
//		viewMonthlyReportForPregnantWomen("040BA5E9-86C3-E711-AB84-005056C00008", "0848DE88-BAC2-E711-AB84-005056C00008", response);
//		viewMonthlyReportForPregnantWomen("040BA5E9-86C3-E711-AB84-005056C00008", null, response);
		//viewMonthlyReportForPregnantWomen(null, null, response);
	}
	
	@RequestMapping(value = ReportURIConstants.PREGNANCY_MONTHLY_REPORT)
	public void viewMonthlyReportForPregnantWomen(
			@RequestParam(name="SurveyHeaderRowGUID") String surveyHeaderRowGUID, 
			@RequestParam(name="OSMPersonID") String osmPersonID,
			@RequestParam(name="VillageID") String villageID,
			@RequestParam(name="Name") String pregnantName,
			@RequestParam(name="UserPersonID") String userPersonID,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> reportParams = new HashMap<>();
		reportParams.put("SurveyHeaderRowGUID", surveyHeaderRowGUID);
		reportParams.put("OSMPersonID", osmPersonID);
		reportParams.put("VillageID", villageID);
		reportParams.put("PregnantName", pregnantName);
		reportParams.put("UserPersonID", userPersonID);

		// Render to PDF Page
		String reportName = "MonthlyReportForPregnantWomen";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_PREGNANT_SURVEY + reportName + ".jasper", reportParams, asServiceDataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}
	
	@RequestMapping(value = ReportURIConstants.PREGNANCY_MONTHLY_REPORT_FORM)
	public void viewFormPregnantWomen(
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> reportParams = new HashMap<>();
		

		// Render to PDF Page
		String reportName = "MonthlyReportForPregnantWomen_NewForm";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_PREGNANT_SURVEY + reportName + ".jasper", reportParams, asServiceDataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}
}
