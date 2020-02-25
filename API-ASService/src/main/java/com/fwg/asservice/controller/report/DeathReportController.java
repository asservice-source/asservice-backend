package com.fwg.asservice.controller.report;


import java.util.HashMap;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fwg.asservice.config.ReportConfig;
import com.fwg.asservice.constants.ReportURIConstants;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Controller
public class DeathReportController {

	AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ReportConfig.class);
	DataSource asServiceDataSource = applicationContext.getBean(DataSource.class);
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = applicationContext.getBean(NamedParameterJdbcTemplate.class);
	
	@RequestMapping(value = "/test" + ReportURIConstants.DEATH_MONTHLY_REPORT) 
	public void test_viewMonthlyReportForDeadPersons(HttpServletResponse response, HttpServletRequest request /* Writer responseWriter */) throws Exception {
		
		
//		viewMonthlyReportForDeadPersons("E9C87E85-91AD-E711-AB84-005056C00008", "1349900301500", response);
//		viewMonthlyReportForDeadPersons("E9C87E85-91AD-E711-AB84-005056C00008", null, response);
//		viewMonthlyReportForDeadPersons(null, "1349900301500", response);
	//viewMonthlyReportForDeadPersons(null, null, null,"à¸ªà¸¡à¸«à¸§à¸±à¸‡",null,response);
	}

	@RequestMapping(value = ReportURIConstants.DEATH_MONTHLY_REPORT, produces = "text/plain;charset=UTF-8")
	public void viewMonthlyReportForDeadPersons(
			@RequestParam(name="SurveyHeaderRowGUID") String surveyHeaderRowGUID,
			@RequestParam(name="OSMPersonID") String osmPersonID,
			@RequestParam(name="VillageID") String villageID,
			@RequestParam(name="Name") String deadName,
			@RequestParam(name="UserPersonID") String userPersonID,
			HttpServletResponse response ) throws Exception {
		
		//String deadN = deadName;
		//String deadN = request.getParameter("DeadName");
		//deadN = new String(deadN.getBytes(), "UTF-8");

		System.out.println("SurveyHeader: " + surveyHeaderRowGUID);
		System.out.println("OSMPersonID: " + osmPersonID);
		System.out.println("VillageID: " + villageID);
		System.out.println("Name: " + deadName);
		System.out.println("UserPersonID: " + userPersonID);
		
		
		Map<String, Object> reportParams = new HashMap<>();
		reportParams.put("SurveyHeaderRowGUID", surveyHeaderRowGUID);
		reportParams.put("OSMPersonID", osmPersonID);
		reportParams.put("VillageID", villageID);
		reportParams.put("Name", deadName);
		reportParams.put("UserPersonID", userPersonID);

		// Render to PDF Page
		String reportName = "MonthlyReportForDeadPersons";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_DEATH_SURVEY + reportName + ".jasper", reportParams, asServiceDataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();

//		JasperExportManager.exportReportToPdfFile(jasperPrint, ReportConfig.PATH_OF_DEATH_SURVEY + reportName + ".pdf");
//		JasperExportManager.exportReportToHtmlFile(jasperPrint, ReportConfig.PATH_OF_DEATH_SURVEY + reportName + ".html");

		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		// --- Deprecated ---
		// exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		// exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		exporter.exportReport();
	}
	
	@RequestMapping(value = ReportURIConstants.DEATH_MONTHLY_REPORT_FORM, produces = "text/plain;charset=UTF-8")
	public void viewFormDeadPersons(
			HttpServletResponse response ) throws Exception {

		Map<String, Object> reportParams = new HashMap<>();
	
		// Render to PDF Page
		String reportName = "MonthlyReportForDeadPersons_NewForm";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_DEATH_SURVEY + reportName + ".jasper", reportParams, asServiceDataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();

		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

		exporter.exportReport();
	}

	@RequestMapping(value = "/report/AngsanaUPCTest")
	public ModelAndView testAngsanaNewFont(HttpServletResponse response) throws Exception {
//		JRPropertiesUtil jrProps = JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance());
//		jrProps.setProperty(JRFont.DEFAULT_PDF_FONT_NAME, "Angsana New");
//		jrProps.setProperty(JRFont.DEFAULT_FONT_NAME, "Angsana New");
//		jrProps.setProperty(JRFont.DEFAULT_PDF_ENCODING, "Identity-H");
//		jrProps.setProperty(JRFont.DEFAULT_PDF_EMBEDDED, "TRUE");
		
		Map<String, Object> reportParams = new HashMap<String, Object>();
		reportParams.put("Message", "");

		String reportsPath = ReportConfig.REPORT_ROOT_PATH;
		String reportName = "AngsanaUPCReport";
		JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(null);
		JasperPrint jasperPrint = JasperFillManager.fillReport(reportsPath + reportName + ".jasper", null, dataSource);
		JRPdfExporter exporter = new JRPdfExporter();

		JasperExportManager.exportReportToPdfFile(jasperPrint, reportsPath + reportName + ".pdf");
		JasperExportManager.exportReportToHtmlFile(jasperPrint, reportsPath + reportName + ".html");

		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		// --- Deprecated ---
		// exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		// exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		exporter.exportReport();
		return null;
	}
	
	@RequestMapping(value = "/report/AngsanaUPCTest2")
	public ModelAndView testAngsanaNewFont2(HttpServletResponse response) throws Exception {
//		JRPropertiesUtil jrProps = JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance());
//		jrProps.setProperty(JRFont.DEFAULT_PDF_FONT_NAME, "Angsana New");
//		jrProps.setProperty(JRFont.DEFAULT_FONT_NAME, "Angsana New");
//		jrProps.setProperty(JRFont.DEFAULT_PDF_ENCODING, "Identity-H");
//		jrProps.setProperty(JRFont.DEFAULT_PDF_EMBEDDED, "TRUE");
		
		Map<String, Object> reportParams = new HashMap<String, Object>();
		reportParams.put("Message", "");

		String reportsPath = ReportConfig.REPORT_ROOT_PATH;
		String reportName = "AngsanaUPCReport2";
		JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(null);
		JasperPrint jasperPrint = JasperFillManager.fillReport(reportsPath + reportName + ".jasper", null, dataSource);
		JRPdfExporter exporter = new JRPdfExporter();

		JasperExportManager.exportReportToPdfFile(jasperPrint, reportsPath + reportName + ".pdf");
		JasperExportManager.exportReportToHtmlFile(jasperPrint, reportsPath + reportName + ".html");

		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		// --- Deprecated ---
		// exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		// exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		exporter.exportReport();
		return null;
	}
	
	@RequestMapping(value = "/test/report/posterForMonthlyDeathReport") 
	public ModelAndView posterForMonthlyDeathReport() throws Exception {
		ModelAndView modelAndView = new ModelAndView("test/Poster_MonthlyDeathReport");
		return modelAndView;
	}
	
	@RequestMapping(value = "/hello")
	public void helloWorld(HttpServletResponse response) throws Exception {
		response.getWriter().write("Hello....");
	}
	
//	@RequestMapping(value = "/hello")
//	public ModelAndView helloWorld() {
//		ModelAndView modelAndView = new ModelAndView("hello");
//		modelAndView.addObject("message", "Hello, World...123"); 
//		return modelAndView;
//	}
//	
//	@RequestMapping(value = "/hello2") 
//	public void helloWorld2(HttpServletResponse response) throws Exception {
//		response.getWriter().write("Hello...2");
//	}

	
}


