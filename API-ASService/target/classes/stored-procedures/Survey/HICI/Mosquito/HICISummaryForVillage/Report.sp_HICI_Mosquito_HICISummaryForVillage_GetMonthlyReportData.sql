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
-- Create date: 9/11/2017
-- Description:	Monthly Report Data for Mosquito HICI : Village Summary (DHF Form)
-- =============================================
ALTER PROCEDURE Report.sp_HICI_Mosquito_HICISummaryForVillage_GetMonthlyReportData 
	-- Add the parameters for the stored procedure here
	@SurveyHeaderRowGUID AS NVARCHAR(max) = NULL, 
	@VillageID AS int = NULL,
	@HomeID AS int = NULL,
	@OSMPersonID AS NVARCHAR(max) = NULL
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
	, [HospitalCode5] = tbHospital.Code5 
	, [HospitalName] = tbHospital.Name
	, [HospitalMooNo] = tbHospital.MooNo
	, [OSMPersonID] = tbOSMPerson.PersonID
	, [OSMName] = tbOSMPersonPrefix.ShortName + tbOSMPerson.FirstName + ' ' + tbOSMPerson.LastName
	, [ProvinceCode] = tbProvince.Code
	, [ProvinceName] = tbProvince.Name
	, [AmphurCode] = tbAmphur.Code
	, [AmphurName] = tbAmphur.Name
	, [TumbolCode] = tbTumbol.Code 
	, [TumbolName] = tbTumbol.Name 
	, [VillageID] = tbVillage.ID
	, [VillageName] = tbVillage.VillageName
	, [HomeID] = tbHome.ID 
	, [HomeNO] = tbHome.HomeNO
	, [HomeName] = tbHome.Name
	, [HomeRoad] = tbHome.Road
	, [HomeTypeCode] = tbHomeType.Code 
	, [HomeTypeName] = tbHomeType.Name
	, [HostName] = (
		SELECT TOP 1
			tbPrefixForHost.ShortName + tbPersonForHost.FirstName + ' ' + tbPersonForHost.LastName
		FROM dbo.HomeMembers tbHomeMemberForHost
			LEFT JOIN dbo.Person AS tbPersonForHost ON tbHomeMemberForHost.PersonID = tbPersonForHost.PersonID 
				LEFT JOIN dbo.Prefix AS tbPrefixForHost ON tbPersonForHost.PrefixCode = tbPrefixForHost.Code
		WHERE tbHomeMemberForHost.HomeID = tbHome.ID
		AND tbHomeMemberForHost.FamilyStatusID = '1'
	)		
	, [ContainerTypeID] = tbContainerType.ID 
	, [ContainerTypeName] = tbContainerType.Name 
	, [TotalSurvey] = tbMonitorHICIDetailInfo.TotalSurvey 
	, [TotalDetect] = tbMonitorHICIDetailInfo.TotalDetect
	

	FROM Survey.SurveyHeader AS tbSurveyHeader
		LEFT JOIN dbo.Hospital AS tbHospital ON tbSurveyHeader.HospitalCode = tbHospital.Code5 
			LEFT JOIN Tumbol AS tbTumbol ON tbHospital.TumbolCode = tbTumbol.Code 
			LEFT JOIN Amphur AS tbAmphur ON tbHospital.AmphurCode = tbAmphur.Code 
			LEFT JOIN Province AS tbProvince ON tbHospital.ProvinceCode = tbProvince.Code
		LEFT JOIN Survey.MonitorHICIDetailInfo AS tbMonitorHICIDetailInfo ON tbSurveyHeader.RowGUID = tbMonitorHICIDetailInfo.DocumentID
			LEFT JOIN dbo.Home AS tbHome ON tbMonitorHICIDetailInfo.HomeID = tbHome.ID
				LEFT JOIN dbo.HomeType AS tbHomeType ON tbHome.HomeTypeCode = tbHomeType.Code
				LEFT JOIN dbo.Village AS tbVillage ON tbHome.VillageID = tbVillage.ID
			LEFT JOIN Survey.ContainerType AS tbContainerType ON tbMonitorHICIDetailInfo.ContainerID = tbContainerType.ID
			LEFT JOIN Survey.ContainerLocateType AS tbContainerLocateType ON tbMonitorHICIDetailInfo.LocateTypeID = tbContainerLocateType.ID
			LEFT JOIN dbo.Person AS tbOSMPerson ON tbMonitorHICIDetailInfo.OSMID = tbOSMPerson.PersonID
				LEFT JOIN dbo.Prefix AS tbOSMPersonPrefix ON tbOSMPerson.PrefixCode = tbOSMPersonPrefix.Code

	WHERE tbSurveyHeader.HeaderTypeCode = 'MONITORHICI'
	AND (
		tbHome.HomeTypeCode IN ('01','02','03','04','05','06','10','11','15')    -- House(01-05),Academe(10),ReligiousPlace(06),OfficialPlace(15),ChildCenter(11)
		OR tbMonitorHICIDetailInfo.RowGUID IS NULL
	)
	AND tbSurveyHeader.RowGUID = ISNULL(NULLIF(@SurveyHeaderRowGUID,''), tbSurveyHeader.RowGUID)			-- SurveyHeader 
	AND (
		tbVillage.ID = ISNULL(NULLIF(@VillageID,''), tbVillage.ID)											-- Village
		OR (NULLIF(@VillageID, '') IS NULL AND tbMonitorHICIDetailInfo.RowGUID IS NULL)						-- AltCase for Village.VillageID(NULL) = Village.VillageID(NULL)
	)
	AND (
		tbMonitorHICIDetailInfo.HomeID = ISNULL(NULLIF(@HomeID,''), tbMonitorHICIDetailInfo.HomeID)			-- Home
		OR (NULLIF(@HomeID, '') IS NULL AND tbMonitorHICIDetailInfo.RowGUID IS NULL)						
	)
	AND (
		tbMonitorHICIDetailInfo.OSMID = ISNULL(NULLIF(@OSMPersonID,''), tbMonitorHICIDetailInfo.OSMID)			-- OSM
		OR (NULLIF(@OSMPersonID, '') IS NULL AND tbMonitorHICIDetailInfo.RowGUID IS NULL)								
	)
	-- NEXT : HomeType (May be...)

	ORDER BY 
		tbSurveyHeader.StartDate ASC
		, tbVillage.VillageNo
		, tbHome.HomeTypeCode ASC
		, tbHome.ID ASC
		
		--, tbHome.HomeTypeID ASC
		--, tbHome.HomeNO ASC
END
GO
