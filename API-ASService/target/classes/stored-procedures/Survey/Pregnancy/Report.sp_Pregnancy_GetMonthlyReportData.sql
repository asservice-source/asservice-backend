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
-- Create date: 1/11/2017
-- Description:	Monthly Report Data for Pregnancy
-- =============================================
ALTER PROCEDURE Report.sp_Pregnancy_GetMonthlyReportData
	-- Add the parameters for the stored procedure here
	@SurveyHeaderRowGUID AS NVARCHAR(max) = NULL, 
	@OSMPersonID AS NVARCHAR(max) = NULL,		-- OPTIONAL
	@Month AS int = 0,							-- OPTIONAL 
	@Year AS int = 0,							-- OPTIONAL
	@HospitalCode5 AS NVARCHAR(max) = NULL		-- OPTIONAL
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SET LANGUAGE Thai;

	SELECT 
	[SurveyHeaderRowGUID] = tbSurveyHeader.RowGUID
	, [SurveyHeaderName] = tbSurveyHeader.Name
	, [HospitalName] = tbHospital.Name
	, [HospitalMooNo] = tbHospital.MooNo
	, [SurveyMonth] = DATENAME(MONTH, tbSurveyHeader.StartDate)
	--, [SurveyYear] = DATENAME(YEAR, DATEADD("yyyy", 543,tbSurveyHeader.StartDate))
	, [SurveyYear] = DATENAME(YEAR, tbSurveyHeader.StartDate)
	, [OSMPersonID] = tbPregnantDetailInfo.OSMID
	, [PregnantDetailInfoRowGUID] = tbPregnantDetailInfo.RowGUID
	, [PregnantPersonCitizenID] = tbPregnantPerson.CitizenID
	, [PregnantPersonName] = tbPregnantPerson.FirstName + ' ' + tbPregnantPerson.LastName
	, [PregnantPersonAge] = DATEDIFF(hour, tbPregnantPerson.BirthDate, GETDATE())/8766
	, [PregnantPersonAddress] = tbPregnantPersonAddress.HomeNO + ' ม.' + tbPregnantPersonAddress.MooNO
	, [PregnantPersonWombNo] = tbPregnantDetailInfo.WombNo
	, [PregnantPersonBornDueDate] = CAST(DAY(tbPregnantDetailInfo.BornDueDate) AS VARCHAR) 
		+ '-' + CASE MONTH(tbPregnantDetailInfo.BornDueDate) 
					WHEN 1 THEN 'ม.ค.'
					WHEN 2 THEN 'ก.พ.'
					WHEN 3 THEN 'มี.ค.'
					WHEN 4 THEN 'เม.ย'
					WHEN 5 THEN 'พ.ค'
					WHEN 6 THEN 'มิ.ย'
					WHEN 7 THEN 'ก.ค.'
					WHEN 8 THEN 'ส.ค.'
					WHEN 9 THEN 'ก.ย.'
					WHEN 10 THEN 'ต.ค.'
					WHEN 11 THEN 'พ.ย.'
					WHEN 12 THEN 'ธ.ค.'
				END
		+ '-' + CAST(YEAR(tbPregnantDetailInfo.BornDueDate)+543 AS VARCHAR)
	, [PregnantPersonBloodTypeName] = tbPregnantPersonBloodType.Name
	, [BornTypeName] = tbBornType.Name
	, [PregnantSurveyTypeName] = tbPregnantSurveyType.Name
	, [ChildBirthDate] = CAST(DAY(tbChild.BirthDate) AS VARCHAR) 
		+ '-' + CASE MONTH(tbChild.BirthDate) 
					WHEN 1 THEN 'ม.ค.'
					WHEN 2 THEN 'ก.พ.'
					WHEN 3 THEN 'มี.ค.'
					WHEN 4 THEN 'เม.ย'
					WHEN 5 THEN 'พ.ค'
					WHEN 6 THEN 'มิ.ย'
					WHEN 7 THEN 'ก.ค.'
					WHEN 8 THEN 'ส.ค.'
					WHEN 9 THEN 'ก.ย.'
					WHEN 10 THEN 'ต.ค.'
					WHEN 11 THEN 'พ.ย.'
					WHEN 12 THEN 'ธ.ค.'
				END
		+ '-' + CAST(YEAR(tbChild.BirthDate)+543 AS VARCHAR)
	, [BornHospitalName] = tbBornHospital.Name
	, [ChildWeight] = tbChild.Weight
	, [ChildName] = CASE 
						WHEN LEN(tbChildPerson.FirstName) > 0 THEN tbChildPrefix.ShortName + tbChildPerson.FirstName + ' ' + tbChildPerson.LastName
						ELSE tbChild.BornCitizenID 
					END

	FROM Survey.SurveyHeader AS tbSurveyHeader
		LEFT JOIN dbo.Hospital AS tbHospital ON tbSurveyHeader.HospitalCode = tbHospital.Code5 
		LEFT JOIN Survey.PregnantDetailInfo AS tbPregnantDetailInfo ON tbSurveyHeader.RowGUID = tbPregnantDetailInfo.DocumentID
			LEFT JOIN dbo.Person AS tbPregnantPerson ON tbPregnantDetailInfo.PersonID = tbPregnantPerson.PersonID
				LEFT JOIN dbo.BloodType AS tbPregnantPersonBloodType ON tbPregnantPerson.BloodTypeID = tbPregnantPersonBloodType.ID 
				LEFT JOIN dbo.Address AS tbPregnantPersonAddress ON tbPregnantPerson.PersonID = tbPregnantPersonAddress.PersonID
			LEFT JOIN dbo.PregnantSurveyType AS tbPregnantSurveyType ON tbPregnantDetailInfo.PSurveyTypeCode = tbPregnantSurveyType.Code
			LEFT JOIN dbo.Person AS tbOSMPerson ON tbPregnantDetailInfo.OSMID = tbOSMPerson.PersonID
			LEFT JOIN Survey.Child AS tbChild ON tbPregnantDetailInfo.RowGUID = tbChild.ReferenceID
				LEFT JOIN dbo.Person AS tbChildPerson ON tbChild.BornCitizenID = tbChildPerson.CitizenID
					LEFT JOIN dbo.Prefix AS tbChildPrefix ON tbChildPerson.PrefixCode = tbChildPrefix.Code
				LEFT JOIN dbo.BornType AS tbBornType ON tbChild.BornTypeID = tbBornType.ID
				LEFT JOIN dbo.BornLocation AS tbBornLocation ON tbChild.BornLocationID = tbBornLocation.ID
					LEFT JOIN dbo.Hospital AS tbBornHospital ON tbBornLocation.HospitalCode = tbBornHospital.Code5 
	
	WHERE (
		tbSurveyHeader.HeaderTypeCode = 'PREGNANT'
		--OR tbSurveyHeader.RowGUID IS NULL
	)
	AND (
		tbSurveyHeader.RowGUID = ISNULL(NULLIF(@SurveyHeaderRowGUID, ''), tbSurveyHeader.RowGUID)						-- SurveyHeader
		OR (NULLIF(@SurveyHeaderRowGUID, '') IS NULL AND tbSurveyHeader.RowGUID IS NULL)
	)
	AND (
	  	tbPregnantDetailInfo.OSMID = ISNULL(NULLIF(@OSMPersonID, ''), tbPregnantDetailInfo.OSMID)						-- OSM
		OR (NULLIF(@OSMPersonID, '') IS NULL AND tbPregnantDetailInfo.RowGUID IS NULL)									-- ***AltCase for OSMID(NULL) = OSMID(NULL)
	)
	AND (																												-- Month
		(CASE																											-- ***Fixed to Monthly Survey
			WHEN tbSurveyHeader.RowGUID IS NULL THEN 1
			WHEN (@Month = 0 OR @Month IS NULL) THEN 1  
			WHEN (@Month >= 1 AND (@Month = MONTH(tbSurveyHeader.StartDate))) THEN 1				
			WHEN (@Month >= 1 AND (@Month != MONTH(tbSurveyHeader.StartDate))) THEN 0
		END) = 1
	)
	AND (																												-- Year
		CASE
			WHEN tbSurveyHeader.RowGUID IS NULL THEN 1
			WHEN (@Year = 0 OR @Year IS NULL) THEN 1  
			WHEN (@Year >= 1 AND (@Year = YEAR(tbSurveyHeader.StartDate))) THEN 1
			WHEN (@Year >= 1 AND (@Year != YEAR(tbSurveyHeader.StartDate))) THEN 0
		END = 1
	)
	AND (
		tbSurveyHeader.HospitalCode = ISNULL(NULLIF(@HospitalCode5, ''), tbSurveyHeader.HospitalCode)					-- Hospital 
	)
	AND (
		tbPregnantDetailInfo.RowGUID IS NOT NULL AND tbPregnantDetailInfo.PSurveyTypeCode IN ('Born'/*,'Pregnant'*/)	-- Without DetailInfo or With SurveyHeader&DetailInfo
		OR tbPregnantDetailInfo.RowGUID IS NULL
	)

	ORDER BY 
		tbHospital.Code5 ASC
		, YEAR(tbSurveyHeader.StartDate) ASC
		, MONTH(tbSurveyHeader.StartDate) ASC
		, tbSurveyHeader.RowGUID ASC

	-- SurveyHeader : 8ECAE013-B2AC-E711-AB84-005056C00008
END
GO
