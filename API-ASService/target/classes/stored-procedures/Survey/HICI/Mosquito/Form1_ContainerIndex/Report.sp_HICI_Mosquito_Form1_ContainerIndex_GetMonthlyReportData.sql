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
-- Create date: 7/11/2017
-- Description:	Monthly Report Data for Mosquito HICI (House Index, Container Index) : Container Index
-- =============================================
ALTER PROCEDURE Report.sp_HICI_Mosquito_Form1_ContainerIndex_GetMonthlyReportData
	-- Add the parameters for the stored procedure here
	@SurveyHeaderRowGUID AS NVARCHAR(max) = NULL, 
	@HomeID AS int = NULL
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
	, [SurveyMonth] = DATENAME(MONTH, tbSurveyHeader.StartDate)
	, [SurveyYear] = DATENAME(YEAR, tbSurveyHeader.StartDate)
	, [OSMPersonID] = tbOSMPerson.PersonID
	, [OSMName] = tbOSMPersonPrefix.ShortName + tbOSMPerson.FirstName + ' ' + tbOSMPerson.LastName
	, [ProvinceCode] = tbProvince.Code
	, [ProvinceName] = tbProvince.Name
	, [AmphurCode] = tbAmphur.Code
	, [AmphurName] = tbAmphur.Name
	, [TumbolCode] = tbTumbol.Code 
	, [TumbolName] = tbTumbol.Name 
	, [MooNo] = tbHospital.MooNo
	, [HomeID] = tbHome.ID 
	, [HomeName] = tbHome.Name
	, [HomeTypeCode] = tbHomeType.Code 
	, [HomeTypeName] = tbHomeType.Name
	, [HomeNO] = tbHome.HomeNO 
	, [HomeRoad] = tbHome.Road 
	, [HomeSoi] = tbHome.Soi 
	, [ContainerTypeID] = tbContainerType.ID 
	, [ContainerTypeName] = tbContainerType.Name 
	, [TotalSurvey] = tbMonitorHICIDetailInfo.TotalSurvey 
	, [TotalDetect] = tbMonitorHICIDetailInfo.TotalDetect
	

	FROM Survey.SurveyHeader AS tbSurveyHeader
		LEFT JOIN Survey.MonitorHICIDetailInfo AS tbMonitorHICIDetailInfo ON tbSurveyHeader.RowGUID = tbMonitorHICIDetailInfo.DocumentID
			LEFT JOIN dbo.Home AS tbHome ON tbMonitorHICIDetailInfo.HomeID = tbHome.ID
				LEFT JOIN dbo.HomeType AS tbHomeType ON tbHome.HomeTypeCode = tbHomeType.Code
				LEFT JOIN dbo.Village AS tbVillage ON tbHome.VillageID = tbVillage.ID
			LEFT JOIN dbo.Person AS tbOSMPerson ON tbMonitorHICIDetailInfo.OSMID = tbOSMPerson.PersonID
				LEFT JOIN dbo.Prefix AS tbOSMPersonPrefix ON tbOSMPerson.PrefixCode = tbOSMPersonPrefix.Code
			LEFT JOIN Survey.ContainerType AS tbContainerType ON tbMonitorHICIDetailInfo.ContainerID = tbContainerType.ID
			LEFT JOIN Survey.ContainerLocatType AS tbContainerLocatType ON tbMonitorHICIDetailInfo.LocatTypeID = tbContainerLocatType.ID
		LEFT JOIN dbo.Hospital AS tbHospital ON tbSurveyHeader.HospitalCode = tbHospital.Code5 
			LEFT JOIN Tumbol AS tbTumbol ON tbHospital.TumbolCode = tbTumbol.Code 
			LEFT JOIN Amphur AS tbAmphur ON tbHospital.AmphurCode = tbAmphur.Code 
			LEFT JOIN Province AS tbProvince ON tbHospital.ProvinceCode = tbProvince.Code

	WHERE tbSurveyHeader.HeaderTypeCode = 'MONITORHICI'
	AND tbSurveyHeader.RowGUID = ISNULL(NULLIF(@SurveyHeaderRowGUID,''), tbSurveyHeader.RowGUID)			-- SurveyHeader 
	AND (
		tbMonitorHICIDetailInfo.HomeID = ISNULL(NULLIF(@HomeID,''), tbMonitorHICIDetailInfo.HomeID)			-- Home
		OR (NULLIF(@HomeID, '') IS NULL AND tbMonitorHICIDetailInfo.RowGUID IS NULL)						-- AltCase for DetailInfo.HomeID(NULL) = DetailInfo.HomeID(NULL)
	)
	ORDER BY 
		tbSurveyHeader.StartDate ASC
		,tbHome.HomeNO ASC
			
END
GO
