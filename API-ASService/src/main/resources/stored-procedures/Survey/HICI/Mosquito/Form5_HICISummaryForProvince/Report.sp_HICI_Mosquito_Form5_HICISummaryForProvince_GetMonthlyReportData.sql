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
-- Description:	Monthly Report Data for Mosquito HICI (House Index, Container Index) : Form #5 - Summary for Province
-- =============================================
ALTER PROCEDURE Report.sp_HICI_Mosquito_Form5_HICISummaryForProvince_GetMonthlyReportData
	-- Add the parameters for the stored procedure here
	@ProvinceCode AS NVARCHAR(max) = NULL,
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
	, [SurveyMonthYear] = DATENAME(MONTH, tbSurveyHeader.StartDate) + '-' + DATENAME(YEAR, tbSurveyHeader.StartDate)
	, [MonitorHICIDetailInfoRowGUID] = tbDetailInfo.RowGUID
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
	, [AmphurCodeOfDetailInfo] = tbAmphurOfDetailInfo.Code 
	, [AmphurNameOfDetailInfo] = tbAmphurOfDetailInfo.Name
	, [TumbolCode] = tbTumbol.Code 
	, [TumbolName] = tbTumbol.Name 
	, [TumbolCodeOfDetailInfo] = tbTumbolOfDetailInfo.Code 
	, [TumbolNameOfDetailInfo] = tbTumbolOfDetailInfo.Name
	, [VillageID] = tbVillageOfDetailInfo.ID
	, [VillageName] = tbVillageOfDetailInfo.VillageName
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
	, [TotalSurvey] = tbDetailInfo.TotalSurvey 
	, [TotalDetect] = tbDetailInfo.TotalDetect

	FROM Amphur AS tbAmphur 
		LEFT JOIN Province AS tbProvince ON tbAmphur.ProvinceCode = tbProvince.Code
			LEFT JOIN dbo.Hospital AS tbHospital ON tbProvince.Code = tbHospital.ProvinceCode
				LEFT JOIN  dbo.Tumbol AS tbTumbol ON tbHospital.TumbolCode = tbTumbol.Code
				LEFT JOIN Survey.SurveyHeader AS tbSurveyHeader ON tbHospital.Code5 = tbSurveyHeader.HospitalCode
					LEFT JOIN Survey.MonitorHICIDetailInfo AS tbDetailInfo ON tbSurveyHeader.RowGUID = tbDetailInfo.DocumentID
						LEFT JOIN dbo.Home AS tbHome ON tbDetailInfo.HomeID = tbHome.ID
							LEFT JOIN dbo.HomeType AS tbHomeType ON tbHome.HomeTypeCode = tbHomeType.Code
							LEFT JOIN dbo.Village AS tbVillageOfDetailInfo ON tbHome.VillageID = tbVillageOfDetailInfo.ID 
								LEFT JOIN dbo.Hospital AS tbHospitalOfDetailInfo ON tbVillageOfDetailInfo.HospitalCode = tbHospitalOfDetailInfo.Code5
									LEFT JOIN dbo.Tumbol AS tbTumbolOfDetailInfo ON tbHospitalOfDetailInfo.TumbolCode = tbTumbolOfDetailInfo.Code
									LEFT JOIN dbo.Amphur AS tbAmphurOfDetailInfo ON tbHospitalOfDetailInfo.AmphurCode = tbAmphurOfDetailInfo.Code
						LEFT JOIN Survey.ContainerType AS tbContainerType ON tbDetailInfo.ContainerID = tbContainerType.ID
						LEFT JOIN Survey.ContainerLocateType AS tbContainerLocateType ON tbDetailInfo.LocateTypeID = tbContainerLocateType.ID
						LEFT JOIN dbo.Person AS tbOSMPerson ON tbDetailInfo.OSMID = tbOSMPerson.PersonID
							LEFT JOIN dbo.Prefix AS tbOSMPersonPrefix ON tbOSMPerson.PrefixCode = tbOSMPersonPrefix.Code
							LEFT JOIN dbo.UserMapping AS tbUserMapping ON tbOSMPerson.PersonID = tbUserMapping.PersonID 
								LEFT JOIN dbo.UserRoles AS tbUserRoles ON tbUserMapping.UserRoleID = tbUserRoles.ID
	WHERE ( 
		tbSurveyHeader.HeaderTypeCode = 'MONITORHICI'
		OR tbSurveyHeader.RowGUID IS NULL
	)
	AND (
		tbProvince.Code = ISNULL(NULLIF(@ProvinceCode,''), tbProvince.Code)							-- Province
		OR (NULLIF(@ProvinceCode, '') IS NULL) AND tbProvince.Code IS NULL									
	)
	AND (																							-- Month
		(CASE
			WHEN (@Month = 0 OR @Month IS NULL) THEN 1  
			WHEN (@Month >= 1 AND (@Month = MONTH(tbSurveyHeader.StartDate))) THEN 1				-- ***Fixed to Monthly Survey
			WHEN (@Month >= 1 AND (@Month != MONTH(tbSurveyHeader.StartDate))) THEN 0
		END) = 1
	)
	AND (																							-- Year
		CASE
			WHEN (@Year = 0 OR @Year IS NULL) THEN 1  
			WHEN (@Year >= 1 AND (@Year = YEAR(tbSurveyHeader.StartDate))) THEN 1
			WHEN (@Year >= 1 AND (@Year != YEAR(tbSurveyHeader.StartDate))) THEN 0				
		END = 1
	)
	AND DATENAME(MONTH, tbSurveyHeader.StartDate) IS NOT NULL -- Clean some unnecessary records (Normalize report display)
	ORDER BY 
		tbProvince.Code ASC
		,YEAR(tbSurveyHeader.StartDate) ASC
		, MONTH(tbSurveyHeader.StartDate) ASC
		--, tbProvince.Code ASC
		, tbAmphur.Code ASC
		, tbTumbol.Code ASC
		, tbVillageOfDetailInfo.VillageNo
		, tbHome.HomeTypeCode ASC
		, tbHome.ID ASC
		
END
GO
