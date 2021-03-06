/*    ==Scripting Parameters==

    Source Server Version : SQL Server 2008 R2 (10.50.1600)
    Source Database Engine Edition : Microsoft SQL Server Standard Edition
    Source Database Engine Type : Standalone SQL Server

    Target Server Version : SQL Server 2017
    Target Database Engine Edition : Microsoft SQL Server Standard Edition
    Target Database Engine Type : Standalone SQL Server
*/

USE [ASService]
GO
/****** Object:  StoredProcedure [Report].[sp_Population_GetYearlyReportData]    Script Date: 2/9/2018 12:41:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Weerapong Saipetch
-- Create date: 31/10/2017
-- Description:	Yearly Report Data for Population
-- =============================================
ALTER PROCEDURE [Report].[sp_Population_GetYearlyReportData]
	-- Add the parameters for the stored procedure here
	@SurveyHeaderRowGUID AS NVARCHAR(max) = NULL
	,@HomeID AS int = NULL
	,@OSMPersonID AS NVARCHAR(max) = NULL
	,@VillageID AS NVARCHAR(max) = NULL
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT
		[SurveyHeaderRowGUID] = tbSurveyHeader.RowGUID
		, [SurveyHeaderName] = tbSurveyHeader.Name
		, [PopulationDetailRowGUID] = tbPopulationDetail.RowGUID 
		, [SurveyYear] = DATENAME(YEAR, tbSurveyHeader.StartDate)
		, [HomeID] = tbHome.ID
		, [HomeNo] = tbHome.HomeNo 
		, [MooNo] = tbHospital.MooNo 
		, [TumbolName] = tbTumbol.Name 
		, [AmphurName] = tbAmphur.Name 
		, [ProvinceName] = tbProvince.Name
		, [HomeRegistrationID] = tbHome.RegistrationID
		, [HomeTelephone] = tbHome.Telephone
		, [OSMPersonID] = tbOSMPerson.PersonID
		, [OSMName] = tbOSMPrefix.ShortName + tbOSMPerson.FirstName + ' ' + tbOSMPerson.LastName 
        , [VillageID] = tbHome.VillageID
	FROM Survey.SurveyHeader AS tbSurveyHeader
		LEFT JOIN Survey.PopulationDetail AS tbPopulationDetail ON tbSurveyHeader.RowGUID = tbPopulationDetail.DocumentID 
			LEFT JOIN dbo.Home AS tbHome ON tbPopulationDetail.HomeID = tbHome.ID
				LEFT JOIN dbo.Village AS tbVillage ON tbHome.VillageID = tbVillage.ID 
			LEFT JOIN dbo.Person AS tbOSMPerson ON tbPopulationDetail.OSMID = tbOSMPerson.PersonID
				LEFT JOIN dbo.Prefix AS tbOSMPrefix ON tbOSMPerson.PrefixCode = tbOSMPrefix.Code
		LEFT JOIN dbo.Hospital AS tbHospital ON tbSurveyHeader.HospitalCode = tbHospital.Code5
			LEFT JOIN dbo.Tumbol AS tbTumbol ON tbHospital.TumbolCode = tbTumbol.Code 
			LEFT JOIN dbo.Amphur AS tbAmphur ON tbHospital.AmphurCode = tbAmphur.Code
			LEFT JOIN dbo.Province AS tbProvince ON tbHospital.Provincecode = tbProvince.Code 

	WHERE tbSurveyHeader.HeaderTypeCode = 'POPULATION'
	AND tbSurveyHeader.RowGUID = ISNULL(NULLIF(@SurveyHeaderRowGUID,''), tbSurveyHeader.RowGUID)	-- SurveyHeader 
	AND (
		tbPopulationDetail.HomeID = ISNULL(NULLIF(@HomeID,''), tbPopulationDetail.HomeID)			-- Home
		OR (NULLIF(@HomeID, '') IS NULL AND tbPopulationDetail.RowGUID IS NULL)						-- AltCase for Detail.HomeID(NULL) = Detail.HomeID(NULL)
	)
	AND (
	  	tbPopulationDetail.OSMID = ISNULL(NULLIF(@OSMPersonID, ''), tbPopulationDetail.OSMID)		-- OSM
		OR (NULLIF(@OSMPersonID, '') IS NULL AND tbPopulationDetail.RowGUID IS NULL)				-- AltCase : DetailInfo.OSMID(NULL) = DetailInfo.OSMID(NULL)
	)
	AND (
	  	tbHome.VillageID = ISNULL(NULLIF(@VillageID, ''), tbHome.VillageID)		-- Village
		OR (NULLIF(@VillageID, '') IS NULL AND tbPopulationDetail.RowGUID IS NULL)				
	)
	ORDER BY 
		--tbSurveyHeader.StartDate ASC
		tbSurveyHeader.StartDate ASC
		, tbSurveyHeader.RowGUID ASC 
		, tbProvince.Code ASC 
		, tbAmphur.Code ASC 
		, tbTumbol.Code ASC 
		, tbVillage.ID ASC 
		, tbHome.HomeTypeCode ASC
		, tbHome.HomeNO
END
