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
-- Create date: 1/11/2017
-- Description:	Monthly Report Data for Death Persons
-- =============================================
ALTER PROCEDURE Report.sp_Death_GetMonthlyReportData
	-- Add the parameters for the stored procedure here
	@SurveyHeaderRowGUID AS NVARCHAR(max) = NULL, 
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
	, [SurveyHeaderRound] = tbSurveyHeader.Round
	, [SurveyMonthYear] = RIGHT(CONVERT(VARCHAR(10), tbSurveyHeader.StartDate, 105), 7)
	, [ThaiSurveyMonth] = DATENAME(MONTH, tbSurveyHeader.StartDate)
	, [SurveyYear] = DATENAME(YEAR, tbSurveyHeader.StartDate)
	, [OSMID] = tbDeathDetailInfo.OSMID
	, [OSMName] = (tbOSMPerson.FirstName+' '+tbOSMPerson.LastName)
	, [HospitalTelephone] = tbSurveyHospital.ContactTelephone
	, [SurveyMoo] = tbSurveyHospital.MooNo
	, [SurveyTumbol] = tbSurveyTumbol.Name
	, [SurveyAmphur] = tbSurveyAmphur.Name
	, [SurveyProvince] = tbSurveyProvince.Name
	, [SurveyMonth] = MONTH(tbSurveyHeader.StartDate)
	, [DeathDetailInfoRowGUID] = tbDeathDetailInfo.RowGUID
	, [DeadPersonName] = (tbDeadPerson.FirstName+' '+tbDeadPerson.LastName)
	, [DeadPersonCitizenID] = tbDeadPerson.CitizenID
	, [DeadPersonHomeNO] = tbDeadPersonAddress.HomeNO
	, [DeadPersonAge] = tbDeathDetailInfo.Age
	, [DeadPersonDeathDate] = DATEADD("yyyy", 543, tbDeathDetailInfo.DeathDate)
	, [IsDiabetes] = tbDeathDetailInfo.IsDiabetes
	, [IsHypertension] = tbDeathDetailInfo.IsHypertension
	, [IsAccident] = tbDeathDetailInfo.IsAccident
	, [IsCancer] = tbDeathDetailInfo.IsCancer
	, [CancerTypeName] = tbCancerType.Name
	, [CauseOther] = tbDeathDetailInfo.CauseOther
	, [IsNoDisease] = tbDeathDetailInfo.IsNoDisease
	, [DeathPlaceCode] = tbDeathPlace.Code
	, [DeathPlaceOther] = tbDeathDetailInfo.PlaceOther
	
	FROM Survey.SurveyHeader AS tbSurveyHeader
		LEFT JOIN Survey.DeathDetailInfo AS tbDeathDetailInfo ON tbSurveyHeader.RowGUID = tbDeathDetailInfo.DocumentID
			LEFT JOIN dbo.Person AS tbDeadPerson ON tbDeathDetailInfo.PersonID = tbDeadPerson.PersonID
				LEFT JOIN dbo.Address AS tbDeadPersonAddress ON tbDeadPerson.PersonID = tbDeadPersonAddress.PersonID
			LEFT JOIN Survey.DeathPlace AS tbDeathPlace ON tbDeathDetailInfo.DeathPlace = tbDeathPlace.Code
			LEFT JOIN dbo.Person AS tbOSMPerson ON tbDeathDetailInfo.OSMID = tbOSMPerson.PersonID 
			LEFT JOIN dbo.CancerType AS tbCancerType ON tbDeathDetailInfo.CancerTypeID = tbCancerType.ID
		LEFT JOIN dbo.Hospital AS tbSurveyHospital ON tbSurveyHeader.HospitalCode = tbSurveyHospital.Code5
			LEFT JOIN dbo.Tumbol AS tbSurveyTumbol ON tbSurveyHospital.TumbolCode = tbSurveyTumbol.Code 
			LEFT JOIN dbo.Amphur AS tbSurveyAmphur ON tbSurveyHospital.AmphurCode = tbSurveyAmphur.Code 
			LEFT JOIN dbo.Province AS tbSurveyProvince ON tbSurveyHospital.ProvinceCode = tbSurveyProvince.Code 
	WHERE tbSurveyHeader.HeaderTypeCode = 'DEATH' 
	AND tbSurveyHeader.RowGUID = ISNULL(NULLIF(@SurveyHeaderRowGUID, ''), tbSurveyHeader.RowGUID)
	AND (
	  	tbDeathDetailInfo.OSMID = ISNULL(NULLIF(@OSMPersonID, ''), tbDeathDetailInfo.OSMID)		-- OSM
		OR (NULLIF(@OSMPersonID, '') IS NULL AND tbDeathDetailInfo.RowGUID IS NULL)				-- AltCase : DetailInfo.OSMID(NULL) = DetailInfo.OSMID(NULL)
	)
	-- SurveyHeader : E9C87E85-91AD-E711-AB84-005056C00008
	-- OSM			: 0848DE88-BAC2-E711-AB84-005056C00008

	ORDER BY tbSurveyHeader.StartDate ASC
END
GO
