USE ASService

-- ================================================
-- Template generated from Template Explorer using:
-- Create Procedure (New Menu).SQL
--
-- Use the Specify Values for Template Parameters 
-- command (Ctrl-Shift-M) to fill in the parameter 
-- values below.
--
-- This block of comments will not be included in
-- the definition of the procedure.
-- ================================================
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Weerapong Saipetch
-- Create date: 10/11/2017
-- Description:	Monthly Report Data for Mosquito HICI (House Index, Container Index) : Form #3 - Summary for Tumbol
-- =============================================
ALTER PROCEDURE Report.sp_HICI_Mosquito_Form3_HICISummaryForTumbol_GetMonthlyReportData
	-- Add the parameters for the stored procedure here
	@SurveyHeaderRowGUID AS NVARCHAR(max) = NULL, 
	@ProvinceCode AS NVARCHAR(max) = NULL,
	@AmphurCode AS NVARCHAR(max) = NULL,
	@TumbolCode AS NVARCHAR(max) = NULL,
	@OSMPersonID AS NVARCHAR(max) = NULL,
	@Month AS int = 0,
	@Year AS int = 0
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SET LANGUAGE Thai;

	SELECT 
	[SurveyHeaderRowGUID] = tbSurveyHeader.RowGUID 
	, [MonitorHICIDetailInfoRowGUID] = tbMonitorHICIDetailInfo.RowGUID
	, [HospitalCode5] = tbHospital.Code5
	, [HospitalName] = tbHospital.Name
	, [SurveyMonth] = DATENAME(MONTH, tbSurveyHeader.StartDate)
	, [SurveyYear] = DATENAME(YEAR, tbSurveyHeader.StartDate)
	, [OSMPersonID] = tbOSMPerson.PersonID
	, [OSMName] = tbOSMPersonPrefix.ShortName + tbOSMPerson.FirstName + ' ' + tbOSMPerson.LastName
	, [OSMPosition] = tbUserRoles.Description
	, [ProvinceCode] = tbProvince.Code
	, [ProvinceName] = tbProvince.Name
	, [AmphurCode] = tbAmphur.Code
	, [AmphurName] = tbAmphur.Name
	, [TumbolCode] = tbTumbol.Code 
	, [TumbolName] = tbTumbol.Name 
	, [VillageID] = tbVillage.ID
	, [VillageName] = tbVillage.VillageName
	, [VillageIDOfDetailInfo] = tbVillageOfDetailInfo.ID 
	, [VillageNameOfDetailInfo] = tbVillageOfDetailInfo.VillageName
	, [HomeID] = tbHome.ID 
	, [HomeNO] = tbHome.HomeNO
	, [HomeName] = tbHome.Name
	, [HomeRoad] = tbHome.Road
	, [HomeTypeCode] = tbHomeType.Code 
	, [HomeTypeName] = tbHomeType.Name
	, [ContainerTypeID] = tbContainerType.ID 
	, [ContainerTypeName] = tbContainerType.Name 
	, [TotalSurvey] = tbMonitorHICIDetailInfo.TotalSurvey 
	, [TotalDetect] = tbMonitorHICIDetailInfo.TotalDetect

	/*FROM Survey.SurveyHeader AS tbSurveyHeader 
		RIGHT JOIN dbo.Village AS tbVillage ON tbSurveyHeader.HospitalCode = tbSurveyHeader.HospitalCode*/
	
	FROM dbo.Village AS tbVillage
		LEFT OUTER JOIN Survey.SurveyHeader AS tbSurveyHeader ON tbVillage.HospitalCode = tbSurveyHeader.HospitalCode
			LEFT JOIN Survey.MonitorHICIDetailInfo AS tbMonitorHICIDetailInfo ON tbSurveyHeader.RowGUID = tbMonitorHICIDetailInfo.DocumentID
				LEFT JOIN dbo.Home AS tbHome ON tbMonitorHICIDetailInfo.HomeID = tbHome.ID
					LEFT JOIN dbo.HomeType AS tbHomeType ON tbHome.HomeTypeCode = tbHomeType.Code
					LEFT JOIN dbo.Village AS tbVillageOfDetailInfo ON tbHome.VillageID = tbVillageOfDetailInfo.ID 
				LEFT JOIN Survey.ContainerType AS tbContainerType ON tbMonitorHICIDetailInfo.ContainerID = tbContainerType.ID
				LEFT JOIN Survey.ContainerLocatType AS tbContainerLocatType ON tbMonitorHICIDetailInfo.LocatTypeID = tbContainerLocatType.ID
				LEFT JOIN dbo.Person AS tbOSMPerson ON tbMonitorHICIDetailInfo.OSMID = tbOSMPerson.PersonID
					LEFT JOIN dbo.Prefix AS tbOSMPersonPrefix ON tbOSMPerson.PrefixCode = tbOSMPersonPrefix.Code
					LEFT JOIN dbo.UserMapping AS tbUserMapping ON tbOSMPerson.PersonID = tbUserMapping.PersonID 
						LEFT JOIN dbo.UserRoles AS tbUserRoles ON tbUserMapping.UserRoleID = tbUserRoles.ID
		LEFT JOIN dbo.Hospital AS tbHospital ON tbVillage.HospitalCode = tbHospital.Code5 
			LEFT JOIN Tumbol AS tbTumbol ON tbHospital.TumbolCode = tbTumbol.Code 
			LEFT JOIN Amphur AS tbAmphur ON tbHospital.AmphurCode = tbAmphur.Code 
			LEFT JOIN Province AS tbProvince ON tbHospital.ProvinceCode = tbProvince.Code
	WHERE ( 
		tbSurveyHeader.HeaderTypeCode = 'MONITORHICI'
		OR tbSurveyHeader.RowGUID IS NULL
	)
	AND (
		tbSurveyHeader.RowGUID = ISNULL(NULLIF(@SurveyHeaderRowGUID,''), tbSurveyHeader.RowGUID)			-- SurveyHeader 
		OR NULLIF(@SurveyHeaderRowGUID,'') IS NULL AND tbSurveyHeader.RowGUID IS NULL
	)
	AND (
		tbHospital.ProvinceCode = ISNULL(NULLIF(@ProvinceCode,''), tbHospital.ProvinceCode)					-- Province
		OR NULLIF(@ProvinceCode,'') IS NULL AND tbHospital.Code5 IS NULL												
	)
	AND (
		tbHospital.AmphurCode = ISNULL(NULLIF(@AmphurCode,''), tbHospital.AmphurCode)						-- Amphur
		OR NULLIF(@AmphurCode,'') IS NULL AND tbHospital.Code5 IS NULL
	)
	AND (
		tbHospital.TumbolCode = ISNULL(NULLIF(@TumbolCode,''), tbHospital.TumbolCode)						-- Tumbol
		OR NULLIF(@TumbolCode,'') IS NULL AND tbHospital.Code5 IS NULL
	)
	AND (
		tbMonitorHICIDetailInfo.OSMID = ISNULL(NULLIF(@OSMPersonID,''), tbMonitorHICIDetailInfo.OSMID)		-- OSM
		OR NULLIF(@OSMPersonID,'') IS NULL AND tbMonitorHICIDetailInfo.RowGUID IS NULL					
	)
	AND (																									-- Month
		(CASE																								-- ***Fixed to Monthly Survey
			WHEN tbSurveyHeader.RowGUID IS NULL THEN 1
			WHEN (@Month = 0 OR @Month IS NULL) THEN 1  
			WHEN (@Month >= 1 AND (@Month = MONTH(tbSurveyHeader.StartDate))) THEN 1				
			WHEN (@Month >= 1 AND (@Month != MONTH(tbSurveyHeader.StartDate))) THEN 0
		END) = 1
	)
	AND (																									-- Year
		CASE
			WHEN tbSurveyHeader.RowGUID IS NULL THEN 1
			WHEN (@Year = 0 OR @Year IS NULL) THEN 1  
			WHEN (@Year >= 1 AND (@Year = YEAR(tbSurveyHeader.StartDate))) THEN 1
			WHEN (@Year >= 1 AND (@Year != YEAR(tbSurveyHeader.StartDate))) THEN 0
		END = 1
	)
	ORDER BY 
		/*tbSurveyHeader.HospitalCode ASC
		, tbSurveyHeader.StartDate ASC
		, tbProvince.Code ASC
		, tbAmphur.Code ASC
		, tbTumbol.Code ASC
		, tbVillage.VillageNo
		, tbHome.HomeTypeCode ASC
		, tbHome.ID ASC*/

		tbProvince.Name ASC
		, YEAR(tbSurveyHeader.StartDate) ASC
		, tbAmphur.Code ASC
		, tbTumbol.Code ASC
		, MONTH(tbSurveyHeader.StartDate) ASC
		, tbVillage.VillageNo
		, tbHome.HomeTypeCode ASC
		, tbHome.ID ASC
		
END
GO
