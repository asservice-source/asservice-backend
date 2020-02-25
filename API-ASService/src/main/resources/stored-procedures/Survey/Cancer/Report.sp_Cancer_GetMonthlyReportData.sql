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
-- Description: Monthly Report Data for Cancer Patients
-- =============================================
ALTER PROCEDURE Report.sp_Cancer_GetMonthlyReportData
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
	, [HospitalName] = tbHospital.Name
	, [HospitalMooNo] = tbHospital.MooNo
	, [OSMPersonID] = tbPatientDetailInfo.OSMID
	, [ThaiSurveyMonth] = DATENAME(MONTH, tbSurveyHeader.StartDate)
	, [SurveyYear] = DATENAME(YEAR, tbSurveyHeader.StartDate)
	, [PatientDetailInfoRowGUID] = tbPatientDetailInfo.RowGUID
	, [PatientCitizenID] = tbPatientPerson.CitizenID
	, [PatientName] = tbPatientPerson.FirstName + ' ' + tbPatientPerson.LastName
	, [PatientAge] = DATEDIFF(hour, tbPatientPerson.BirthDate, GETDATE())/8766
	, [PatientAddress] = tbPatientAddress.HomeNO + ' ม.' + tbPatientAddress.MooNO
	, [CancerTypeName] = tbCancerType.Name
	, [HospitalName] = tbHospital.Name
	, [PatientDate] = CAST(DAY(tbPatientDetailInfo.PatientDate) AS VARCHAR) 
		+ '-' + CASE MONTH(tbPatientDetailInfo.PatientDate) 
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
		+ '-' + CAST(YEAR(tbPatientDetailInfo.PatientDate)+543 AS VARCHAR)
	, [DiseaseStatusTypeID] = tbPatientDetailInfo.DiseaseStatusTypeID
	, [DiseaseStatusTypeName] = tbDiseaseStatusType.Name
	, [PatientTelephone] = tbPatientDetailInfo.Telephone
	
	FROM Survey.SurveyHeader as tbSurveyHeader
		LEFT JOIN Survey.PatientDetailInfo AS tbPatientDetailInfo ON tbSurveyHeader.RowGUID = tbPatientDetailInfo.DocumentID
			LEFT JOIN dbo.Person AS tbPatientPerson ON tbPatientDetailInfo.PersonID = tbPatientPerson.PersonID
				LEFT JOIN dbo.Address AS tbPatientAddress ON tbPatientPerson.PersonID = tbPatientAddress.PersonID 
			LEFT JOIN dbo.CancerType AS tbCancerType ON tbPatientDetailInfo.CancerTypeID = tbCancerType.ID
			LEFT JOIN dbo.DiseaseStatusType AS tbDiseaseStatusType ON tbPatientDetailInfo.DiseaseStatusTypeID = tbDiseaseStatusType.ID
			LEFT JOIN dbo.Person AS tbOSMPerson ON tbPatientDetailInfo.OSMID = tbOSMPerson.PersonID
		LEFT JOIN dbo.Hospital AS tbHospital ON tbSurveyHeader.HospitalCode = tbHospital.Code5
	WHERE tbSurveyHeader.HeaderTypeCode ='Cancer' 
	AND tbSurveyHeader.RowGUID = ISNULL(NULLIF(@SurveyHeaderRowGUID, ''), tbSurveyHeader.RowGUID)			-- SurveyHeader
	AND (
	  	tbPatientDetailInfo.OSMID = ISNULL(NULLIF(@OSMPersonID, ''), tbPatientDetailInfo.OSMID)				-- OSM
		OR (NULLIF(@OSMPersonID, '') IS NULL AND tbPatientDetailInfo.RowGUID IS NULL)						-- AltCase : DetailInfo.OSMID(NULL) = DetailInfo.OSMID(NULL)
	)
	AND (
		tbPatientDetailInfo.RowGUID IS NOT NULL AND tbPatientDetailInfo.PatientSurveyTypeCode = 'Cancer'	-- Without Detail or With Detail&DetailInfo
		OR tbPatientDetailInfo.RowGUID IS NULL
	)
	-- SurveyHeader : 2D8F03F8-B6B4-E711-AB84-005056C00008
	-- OSM			: 2A13A59B-BAC2-E711-AB84-005056C00008
	
END
GO
