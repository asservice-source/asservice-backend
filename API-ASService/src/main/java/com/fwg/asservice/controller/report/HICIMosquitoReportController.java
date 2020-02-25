package com.fwg.asservice.controller.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
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
public class HICIMosquitoReportController {

	AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ReportConfig.class);
	DataSource dataSource = applicationContext.getBean(DataSource.class);
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = applicationContext.getBean(NamedParameterJdbcTemplate.class);

	@RequestMapping(value = "/test" + ReportURIConstants.HICI_MOSQUITO_FORM1_CONTAINER_INDEX_MONTHLY_REPORT)
	public void test_viewMonthlyReport_Form1_ContainerIndex(HttpServletResponse response/* Writer responseWriter */) throws Exception {
		// viewMonthlyReport_Form1_ContainerIndex("A580E4B2-77C3-E711-AB84-005056C00008", "3", response);
		// viewMonthlyReport_Form1_ContainerIndex("A580E4B2-77C3-E711-AB84-005056C00008", null, response);
		// viewMonthlyReport_Form1_ContainerIndex(null, "3", response);
		viewMonthlyReport_Form1_ContainerIndex(null, null, null, null, response);
	}

	@RequestMapping(value = ReportURIConstants.HICI_MOSQUITO_FORM1_CONTAINER_INDEX_MONTHLY_REPORT)
	public void viewMonthlyReport_Form1_ContainerIndex(
			
			@RequestParam(name = "SurveyHeaderRowGUID") String surveyHeaderRowGUID, 
			@RequestParam(name = "HomeID") String homeID,
			@RequestParam(name = "OSMPersonID") String osmPersonID,
			@RequestParam(name = "VillageID") String villageID,
			
			HttpServletResponse response) throws Exception {

		Map<String, Object> reportParams = new HashMap<>();
		reportParams.put("SurveyHeaderRowGUID", surveyHeaderRowGUID);
		reportParams.put("HomeID", homeID);
		

		// Render to PDF Page
		String reportName = "MonthlyReport_HICI_Mosquito_Form1_ContainerIndex";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_HICI_MOSQUITO_SURVEY + reportName + ".jasper", reportParams, dataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}

	@RequestMapping(value = "/test" + ReportURIConstants.HICI_MOSQUITO_FORM2_HOUSE_INDEX_MONTHLY_REPORT)
	public void test_viewMonthlyReport_Form2_HouseIndex(HttpServletResponse response/* Writer responseWriter */) throws Exception {
		// viewMonthlyReport_Form2_HouseIndex("A580E4B2-77C3-E711-AB84-005056C00008", "12", response);
		// viewMonthlyReport_Form2_HouseIndex("A580E4B2-77C3-E711-AB84-005056C00008", null, response);
		// viewMonthlyReport_Form2_HouseIndex(null, "12", response);
		viewMonthlyReport_Form2_HouseIndex(null, null, null, null, response);
	}

	@RequestMapping(value = ReportURIConstants.HICI_MOSQUITO_FORM2_HOUSE_INDEX_MONTHLY_REPORT)
	public void viewMonthlyReport_Form2_HouseIndex(
			
			@RequestParam(name = "SurveyHeaderRowGUID") String surveyHeaderRowGUID, 
			@RequestParam(name = "HomeID") String homeID,
			@RequestParam(name = "VillageID") String villageID,
			@RequestParam(name = "OSMPersonID") String osmPersonID,
			
			HttpServletResponse response) throws Exception {

		Map<String, Object> reportParams = new HashMap<>();
		reportParams.put("SurveyHeaderRowGUID", surveyHeaderRowGUID);
		reportParams.put("VillageID", villageID);

		// Render to PDF Page
		String reportName = "MonthlyReport_HICI_Mosquito_Form2_HouseIndex";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_HICI_MOSQUITO_SURVEY + reportName + ".jasper", reportParams, dataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}

	@RequestMapping(value = "/test" + ReportURIConstants.HICI_MOSQUITO_FORM3_HICI_SUMMARY_FOR_TUMBOL_MONTHLY_REPORT)
	public void test_viewMonthlyReport_Form3_HICISummaryForTumbol(HttpServletResponse response/* Writer responseWriter */) throws Exception {
		// viewMonthlyReport_Form3_HICISummaryForTumbol("A580E4B2-77C3-E711-AB84-005056C00008", "95", "9501", "950101", null, null, "0848DE88-BAC2-E711-AB84-005056C00008", "0848DE88-BAC2-E711-AB84-005056C00008", response);
		// viewMonthlyReport_Form3_HICISummaryForTumbol("A580E4B2-77C3-E711-AB84-005056C00008", "95", "9501", null, null, null, "0848DE88-BAC2-E711-AB84-005056C00008", "0848DE88-BAC2-E711-AB84-005056C00008", response);
		// viewMonthlyReport_Form3_HICISummaryForTumbol(null, "3", response);
		// viewMonthlyReport_Form3_HICISummaryForTumbol("A580E4B2-77C3-E711-AB84-005056C00008", null, null, null, null, null, null, "0848DE88-BAC2-E711-AB84-005056C00008", response);
		//viewMonthlyReport_Form3_HICISummaryForTumbol(null, "95", null, null, null, null, null, "0848DE88-BAC2-E711-AB84-005056C00008", response);
		// viewMonthlyReport_Form3_HICISummaryForTumbol(null, null, null, null, null, "0848DE88-BAC2-E711-AB84-005056C00008", response);
	}

	//This method is revise parameter 
	@RequestMapping(value = ReportURIConstants.HICI_MOSQUITO_FORM3_HICI_SUMMARY_FOR_TUMBOL_MONTHLY_REPORT)
	public void viewMonthlyReport_Form3_HICISummaryForTumbol(
			@RequestParam(name = "SurveyHeaderRowGUID") String surveyHeaderRowGUID, 
			//@RequestParam(name = "ProvinceCode") String provinceCode,
			//@RequestParam(name = "AmphurCode") String amphurCode, 
			@RequestParam(name = "OSMPersonID") String osmPersonID,
			@RequestParam(name = "VillageID") String villageID,
			@RequestParam(name = "HomeTypeCode") String homeTypeCode,
			//@RequestParam(name = "Month") String month,
			//@RequestParam(name = "Year") String year,
			//@RequestParam(name = "ReporterPersonID") String reporterPersonID, 
			@RequestParam(name = "UserPersonID") String userPersonID, 
			HttpServletResponse response) throws Exception {

		Map<String, Object> reportParams = new HashMap<>();
		reportParams.put("SurveyHeaderRowGUID", surveyHeaderRowGUID);
		reportParams.put("OSMPersonID", osmPersonID);
		reportParams.put("VillageID", villageID);
		reportParams.put("HomeTypeCode", homeTypeCode);
		//reportParams.put("TumbolCode", tumbolCode);
		reportParams.put("UserPersonID", userPersonID);
		//reportParams.put("ReporterName", "");
		//reportParams.put("ReporterRoleDescription", "");
		
		/*String month = "";
		String year = "";
		String provinceCode= "";
		String amphurCode= "";
		String reporterPersonID= "";

		Map<String, Object> qParams = new HashMap<>();
		qParams.put("ReporterPersonID", reporterPersonID);
		Map<String, Object> reportInfo = namedParameterJdbcTemplate.query(
			"EXEC Report.sp_HICI_Mosquito_Form3_HICISummaryForTumbol_GetReportInformation \r\n" 
			+ "    @ReporterPersonID = :ReporterPersonID", qParams, new ResultSetExtractor<Map<String, Object>>() {
				@Override
				public Map<String, Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, Object> resultMap = new HashMap<String, Object>();
					if (rs.next()) {
						resultMap.put("ReporterName", rs.getString("ReporterName"));
						resultMap.put("ReporterRoleDescription", rs.getString("ReporterRoleDescription"));
						return resultMap;
					} else {
						return null;
					}
				}
			});

		Map<String, Object> reportParams = new HashMap<>();
		reportParams.put("SurveyHeaderRowGUID", surveyHeaderRowGUID);
		reportParams.put("TumbolCode", tumbolCode);
		reportParams.put("VillageID", villageID);
		reportParams.put("OSMPersonID", osmPersonID);
		reportParams.put("UserPersonID", userPersonID);
		
		reportParams.put("ProvinceCode", provinceCode);
		reportParams.put("AmphurCode", amphurCode);
		reportParams.put("Month", month);
		reportParams.put("Year", year);
		reportParams.put("ReporterName", reportInfo != null ? reportInfo.get("ReporterName") : null);
		reportParams.put("ReporterRoleDescription", reportInfo != null ? reportInfo.get("ReporterRoleDescription") : null);
		*/
		
		
		// Render to PDF Page
		String reportName = "MonthlyReport_HICI_Mosquito_Form3_HICISummaryForTumbol";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_HICI_MOSQUITO_SURVEY + reportName + ".jasper", reportParams, dataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}
	
/*	@RequestMapping(value = ReportURIConstants.HICI_MOSQUITO_FORM3_HICI_SUMMARY_FOR_TUMBOL_MONTHLY_REPORT)
	public void viewMonthlyReport_Form3_HICISummaryForTumbol(
			@RequestParam(name = "SurveyHeaderRowGUID") String surveyHeaderRowGUID, 
			@RequestParam(name = "ProvinceCode") String provinceCode,
			@RequestParam(name = "AmphurCode") String amphurCode, 
			@RequestParam(name = "TumbolCode") String tumbolCode, 
			@RequestParam(name = "OSMPersonID") String osmPersonID,
			@RequestParam(name = "Month") String month,
			@RequestParam(name = "Year") String year,
			@RequestParam(name = "ReporterPersonID") String reporterPersonID, 
			HttpServletResponse response) throws Exception {

		// Map<String, Object> qParams = new HashMap<>();
		// qParams.put("ReporterPersonID", reporterPersonID);
		// Map<String, Object> reportInfo = namedParameterJdbcTemplate.queryForMap(
		// "EXEC Report.sp_HICI_Mosquito_Form3_HICISummaryForTumbol_GetReportInformation \r\n"
		// + " @ReporterPersonID = :ReporterPersonID", qParams);

		Map<String, Object> qParams = new HashMap<>();
		qParams.put("ReporterPersonID", reporterPersonID);
		Map<String, Object> reportInfo = namedParameterJdbcTemplate.query(
			"EXEC Report.sp_HICI_Mosquito_Form3_HICISummaryForTumbol_GetReportInformation \r\n" 
			+ "    @ReporterPersonID = :ReporterPersonID", qParams, new ResultSetExtractor<Map<String, Object>>() {
				@Override
				public Map<String, Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, Object> resultMap = new HashMap<String, Object>();
					if (rs.next()) {
						resultMap.put("ReporterName", rs.getString("ReporterName"));
						resultMap.put("ReporterRoleDescription", rs.getString("ReporterRoleDescription"));
						return resultMap;
					} else {
						return null;
					}
				}
			});

		Map<String, Object> reportParams = new HashMap<>();
		reportParams.put("SurveyHeaderRowGUID", surveyHeaderRowGUID);
		reportParams.put("ProvinceCode", provinceCode);
		reportParams.put("AmphurCode", amphurCode);
		reportParams.put("TumbolCode", tumbolCode);
		reportParams.put("OSMPersonID", osmPersonID);
		reportParams.put("Month", month);
		reportParams.put("Year", year);
		reportParams.put("ReporterName", reportInfo != null ? reportInfo.get("ReporterName") : null);
		reportParams.put("ReporterRoleDescription", reportInfo != null ? reportInfo.get("ReporterRoleDescription") : null);
		// Render to PDF Page
		String reportName = "MonthlyReport_HICI_Mosquito_Form3_HICISummaryForTumbol";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_HICI_MOSQUITO_SURVEY + reportName + ".jasper", reportParams, dataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}*/

	@RequestMapping(value = "/test" + ReportURIConstants.HICI_MOSQUITO_HICI_SUMMARY_FOR_VILLAGE_MONTHLY_REPORT)
	public void test_viewMonthlyReport_HICISummaryForVillage(HttpServletResponse response/* Writer responseWriter */) throws Exception {
		// viewMonthlyReport_HICISummaryForVillage("A580E4B2-77C3-E711-AB84-005056C00008", "12", "19", "0848DE88-BAC2-E711-AB84-005056C00008", response);
		// viewMonthlyReport_HICISummaryForVillage("A580E4B2-77C3-E711-AB84-005056C00008", "12", "19", null, response);
		//viewMonthlyReport_HICISummaryForVillage("A580E4B2-77C3-E711-AB84-005056C00008", "12", null, null, response);
		// viewMonthlyReport_HICISummaryForVillage("A580E4B2-77C3-E711-AB84-005056C00008", null, null, null, response);
		// viewMonthlyReport_HICISummaryForVillage(null, null, null, null, response);
	}

	@RequestMapping(value = ReportURIConstants.HICI_MOSQUITO_HICI_SUMMARY_FOR_VILLAGE_MONTHLY_REPORT)
	public void viewMonthlyReport_HICISummaryForVillage(
			@RequestParam(name = "SurveyHeaderRowGUID") String surveyHeaderRowGUID, 
			@RequestParam(name = "VillageID") String villageID,
			@RequestParam(name = "HomeID") String homeID, 
			@RequestParam(name = "OSMPersonID") String osmID,
			@RequestParam(name="StatusID") String statusID,
			@RequestParam(name = "UserPersonID") String userID,
			HttpServletResponse response) throws Exception {

		Map<String, Object> reportParams = new HashMap<>();
		reportParams.put("SurveyHeaderRowGUID", surveyHeaderRowGUID);
		reportParams.put("VillageID", villageID);
		reportParams.put("HomeID", homeID);
		reportParams.put("OSMPersonID", osmID);
		//reportParams.put("StatusID", statusID);
		reportParams.put("UserPersonID", userID);
		
		String reportName = ""; 
		switch(statusID) {
		   case "1" :
			   reportName = "MonthlyReport_HICI_Mosquito_HICISummaryForVillage";
			   
		      break; // optional		   
		   case "2" :
			   reportName = "MonthlyReport_HICI_Mosquito_HICISummaryForVillage_NotSurvey";
		      break; // optional
		   default : // Optional
			   System.out.println("Can not found data");
		}

		// Render to PDF Page
		//String reportName = "MonthlyReport_HICI_Mosquito_HICISummaryForVillage";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_HICI_MOSQUITO_SURVEY + reportName + ".jasper", reportParams, dataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}
	
	@RequestMapping(value = ReportURIConstants.HICI_MOSQUITO_HICI_SUMMARY_FOR_VILLAGE_MONTHLY_REPORT_FORM)
	public void viewForm_HICISummaryForVillage(
			HttpServletResponse response) throws Exception {

		Map<String, Object> reportParams = new HashMap<>();
	
		// Render to PDF Page
		String reportName = "MonthlyReport_HICI_Mosquito_HICISummaryForVillage_NewForm";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_HICI_MOSQUITO_SURVEY + reportName + ".jasper", reportParams, dataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}

	@RequestMapping(value = "/test" + ReportURIConstants.HICI_MOSQUITO_FORM4_HICI_SUMMARY_FOR_AMPHUR_MONTHLY_REPORT)
	public void test_viewMonthlyReport_Form4_HICISummaryForAmphur(HttpServletResponse response/* Writer responseWriter */) throws Exception {
		// viewMonthlyReport_Form4_HICISummaryForAmphur("95", "9501", "1", "2017", response);
		// viewMonthlyReport_Form4_HICISummaryForAmphur("95", "9501", null, "2017", response);
		// viewMonthlyReport_Form4_HICISummaryForAmphur("95", null, "1", null, response);
		// viewMonthlyReport_Form4_HICISummaryForAmphur(null, null, null, null, response);
		viewMonthlyReport_Form4_HICISummaryForAmphur("95", null, null, null, "0848DE88-BAC2-E711-AB84-005056C00008", response);
	}

	@RequestMapping(value = ReportURIConstants.HICI_MOSQUITO_FORM4_HICI_SUMMARY_FOR_AMPHUR_MONTHLY_REPORT)
	public void viewMonthlyReport_Form4_HICISummaryForAmphur(@RequestParam(name = "ProvinceCode") String provinceCode, @RequestParam(name = "AmphurCode") String amphurCode,
			@RequestParam(name = "Month") String month, @RequestParam(name = "Year") String year, @RequestParam(name = "ReporterPersonID") String reporterPersonID, HttpServletResponse response)
			throws Exception {

		Map<String, Object> qParams = new HashMap<>();
		qParams.put("ReporterPersonID", reporterPersonID);
		Map<String, Object> reportInfo = namedParameterJdbcTemplate.query(
			"EXEC Report.sp_HICI_Mosquito_Form4_HICISummaryForAmphur_GetReportInformation \r\n" + "    @ReporterPersonID = :ReporterPersonID", qParams, new ResultSetExtractor<Map<String, Object>>() {
				@Override
				public Map<String, Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, Object> resultMap = new HashMap<String, Object>();
					if (rs.next()) {
						resultMap.put("ReporterName", rs.getString("ReporterName"));
						resultMap.put("ReporterRoleDescription", rs.getString("ReporterRoleDescription"));
						return resultMap;
					} else {
						return null;
					}
				}
			});

		Map<String, Object> reportParams = new HashMap<>();
		reportParams.put("ProvinceCode", provinceCode);
		reportParams.put("AmphurCode", amphurCode);
		reportParams.put("Month", month);
		reportParams.put("Year", year);
		// --- Report Information ---
		// reportParams.put("ReportInfo_HospitalCode5", reportInfo.get("HospitalCode5"));
		// reportParams.put("ReportInfo_HospitalName", reportInfo.get("HospitalName"));
		// reportParams.put("ReportInfo_ProvinceName", reportInfo.get("ProvinceName"));
		// reportParams.put("ReportInfo_AmphurName", reportInfo.get("AmphurName"));
		// reportParams.put("ReportInfo_TumbolName", reportInfo.get("TumbolName"));
		reportParams.put("ReportInfo_ReporterName", reportInfo != null ? reportInfo.get("ReporterName") : null);
		reportParams.put("ReportInfo_ReporterRoleDescription", reportInfo != null ? reportInfo.get("ReporterRoleDescription") : null);

		// Render to PDF Page
		String reportName = "MonthlyReport_HICI_Mosquito_Form4_HICISummaryForAmphur";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_HICI_MOSQUITO_SURVEY + reportName + ".jasper", reportParams, dataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}

	@RequestMapping(value = "/test" + ReportURIConstants.HICI_MOSQUITO_FORM5_HICI_SUMMARY_FOR_PROVINCE_MONTHLY_REPORT)
	public void test_viewMonthlyReport_Form5_HICISummaryForProvince(HttpServletResponse response/* Writer responseWriter */) throws Exception {
		// viewMonthlyReport_Form5_HICISummaryForProvince("95", "1", "2017", "0848DE88-BAC2-E711-AB84-005056C00008", response);
		// viewMonthlyReport_Form5_HICISummaryForProvince("95", null, "2017", "0848DE88-BAC2-E711-AB84-005056C00008", response);
		viewMonthlyReport_Form5_HICISummaryForProvince(null, null, "2017", "0848DE88-BAC2-E711-AB84-005056C00008", response);
	}

	@RequestMapping(value = ReportURIConstants.HICI_MOSQUITO_FORM5_HICI_SUMMARY_FOR_PROVINCE_MONTHLY_REPORT)
	public void viewMonthlyReport_Form5_HICISummaryForProvince(@RequestParam(name = "ProvinceCode") String provinceCode, @RequestParam(name = "Month") String month,
			@RequestParam(name = "Year") String year, @RequestParam(name = "ReporterPersonID") String reporterPersonID, HttpServletResponse response) throws Exception {

		Map<String, Object> qParams = new HashMap<>();
		qParams.put("ReporterPersonID", reporterPersonID);
		Map<String, Object> reportInfo = namedParameterJdbcTemplate.query(
			"EXEC Report.sp_HICI_Mosquito_Form5_HICISummaryForProvince_GetReportInformation \r\n" + "    @ReporterPersonID = :ReporterPersonID", qParams,
			new ResultSetExtractor<Map<String, Object>>() {
				@Override
				public Map<String, Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, Object> resultMap = new HashMap<String, Object>();
					if (rs.next()) {
						resultMap.put("ReporterName", rs.getString("ReporterName"));
						resultMap.put("ReporterRoleDescription", rs.getString("ReporterRoleDescription"));
						return resultMap;
					} else {
						return null;
					}
				}
			});

		Map<String, Object> reportParams = new HashMap<>();
		reportParams.put("ProvinceCode", provinceCode);
		reportParams.put("Month", month);
		reportParams.put("Year", year);
		// --- Report Information ---
		// reportParams.put("ReportInfo_HospitalCode5", reportInfo.get("HospitalCode5"));
		// reportParams.put("ReportInfo_HospitalName", reportInfo.get("HospitalName"));
		// reportParams.put("ReportInfo_ProvinceName", reportInfo.get("ProvinceName"));
		// reportParams.put("ReportInfo_AmphurName", reportInfo.get("AmphurName"));
		// reportParams.put("ReportInfo_TumbolName", reportInfo.get("TumbolName"));
		reportParams.put("ReportInfo_ReporterName", reportInfo != null ? reportInfo.get("ReporterName") : null);
		reportParams.put("ReportInfo_ReporterRoleDescription", reportInfo != null ? reportInfo.get("ReporterRoleDescription") : null);

		// Render to PDF Page
		String reportName = "MonthlyReport_HICI_Mosquito_Form5_HICISummaryForProvince";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_HICI_MOSQUITO_SURVEY + reportName + ".jasper", reportParams, dataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}

	@RequestMapping(value = "/test" + ReportURIConstants.HICI_MOSQUITO_FORM6_HICI_SUMMARY_FOR_COUNTRY_MONTHLY_REPORT)
	public void test_viewMonthlyReport_Form6_HICISummaryForCountry(HttpServletResponse response/* Writer responseWriter */) throws Exception {
		viewMonthlyReport_Form6_HICISummaryForCountry("1", "2017", response);
		// viewMonthlyReport_Form6_HICISummaryForCountry("95", null, "2017", "0848DE88-BAC2-E711-AB84-005056C00008", response);
		// viewMonthlyReport_Form6_HICISummaryForCountry(null, null, "2017", "0848DE88-BAC2-E711-AB84-005056C00008", response);
	}

	@RequestMapping(value = ReportURIConstants.HICI_MOSQUITO_FORM6_HICI_SUMMARY_FOR_COUNTRY_MONTHLY_REPORT)
	public void viewMonthlyReport_Form6_HICISummaryForCountry(@RequestParam(name = "Month") String month, @RequestParam(name = "Year") String year, HttpServletResponse response) throws Exception {

		Map<String, Object> reportParams = new HashMap<>();
		reportParams.put("Month", month);
		reportParams.put("Year", year);

		// Render to PDF Page
		String reportName = "MonthlyReport_HICI_Mosquito_Form6_HICISummaryForCountry";
		JasperPrint jasperPrint = JasperFillManager.fillReport(ReportConfig.PATH_OF_HICI_MOSQUITO_SURVEY + reportName + ".jasper", reportParams, dataSource.getConnection());
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}
}
