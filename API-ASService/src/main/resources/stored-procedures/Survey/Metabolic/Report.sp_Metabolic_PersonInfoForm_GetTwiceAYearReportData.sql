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
USE ASService

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Weerapong Saipetch
-- Create date: 17/11/2017
-- Description:	Report Data of Metabolic Survey (Twice a Year) for Person Information
-- =============================================
ALTER PROCEDURE Report.sp_Metabolic_PersonInfoForm_GetTwiceAYearReportData
	-- Add the parameters for the stored procedure here
	@SurveyHeaderRowGUID AS NVARCHAR(max) = NULL,
	@PersonID AS NVARCHAR(max) = NULL,
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
	, [SurveyHeaderName] = tbSurveyHeader.Name
	, [SurveyMonth] = DATENAME(MONTH, tbSurveyHeader.StartDate)
	, [SurveyYear] = DATENAME(YEAR, tbSurveyHeader.StartDate)
	, [HospitalName] = tbHospital.Name
	, [OSMID] = tbDetailInfo.OSMID
	, [OSMName] = tbOSMPrefix.ShortName + tbOSMPerson.FirstName+' '+tbOSMPerson.LastName
	, [OperationDate] = (SELECT CAST(DAY(tbDetailInfo.OperationDate) AS VARCHAR(2)) + ' ' + DATENAME(MM, tbDetailInfo.OperationDate) + ' ' + CAST(YEAR(tbDetailInfo.OperationDate) AS VARCHAR(4)) AS [DD Month YYYY]) --tbDetailInfo.OperationDate
	, [PersonID] = tbPerson.PersonID
	, [PersonName] = tbPersonPrefix.ShortName + tbPerson.FirstName + ' ' + tbPerson.LastName 
	, [PersonAge] = DATEDIFF(hour, tbPerson.BirthDate, GETDATE())/8766
	, [PersonCitizenID] = tbPerson.CitizenID 
	, [PersonGenderID] = tbPersonGender.ID 
	, [PersonGenderName] = tbPersonGender.Name
	, [HealthInsuranceTypeName] = tbHealthInsuranceType.Name
	, [HomeID] = tbHome.ID 
	, [HomeNO] = tbHome.HomeNO 
	, [MooNO] = tbHospital.MooNo 
	, [TumbolID] = tbTumbol.Code
	, [TumbolName] = tbTumbol.Name 
	, [AmphurID] = tbAmphur.Code
	, [AmphurName] = tbAmphur.Name 
	, [ProvinceID] = tbProvince.Code
	, [ProvinceName] = tbProvince.Name
	, [IsHeredityMetabolic] = tbDetailInfo.IsHeredityMetabolic
	, [IsWaistlineOver] = tbDetailInfo.IsWaistlineOver
	, [IsBPOver] = tbDetailInfo.IsBPOver 
	, [IsFBS] = tbDetailInfo.IsFBS
	, [IsCholesterol] = tbDetailInfo.IsCholesterol 
	, [IsNewborn4kg] = tbDetailInfo.IsNewborn4kg 
	, [IsHeredityHypertension] = tbDetailInfo.IsHeredityHypertension
	, [SmokingStatusID] = tbDetailInfo.SmokingStatusID
	, [Smoking_RollPerDay] = tbDetailInfo.RollPerDay 
	, [Smoking_PackPerYear] = tbDetailInfo.PackPerYear 
	, [DrinkingStatusID] = tbDetailInfo.DrinkingStatusID 
	, [Drinking_OftenPerWeek] = tbDetailInfo.OftenPerWeek 
	, [Weight] = tbDetailInfo.Weight
	, [Height] = tbDetailInfo.Height 
	, [BMI] = tbDetailInfo.BMI 
	, [Waistline] = tbDetailInfo.Waistline 
	, [BP1] = tbDetailInfo.BP1 
	, [BP2] = tbDetailInfo.BP2 
	, [FBS] = tbDetailInfo.FBS 
	, [IsMetabolic] = tbDetailInfo.IsMetabolic 
	, [IsHypertension] = tbDetailInfo.IsHypertension 
	, [IsEyeComplication] = tbDetailInfo.IsEyeComplication 
	, [IsKidneyComplication] = tbDetailInfo.IsKidneyComplication 
	, [IsPeripheralNeuropathy] = tbDetailInfo.IsPeripheralNeuropathy 
	, [PeripheralName] = tbDetailInfo.PeripheralName
	, [IsNeuropathy] = tbDetailInfo.IsNeuropathy 
	, [IsOther] = tbDetailInfo.IsOther 
	, [OtherComplication] = tbDetailInfo.OtherComplication

	FROM Survey.SurveyHeader AS tbSurveyHeader 
		LEFT JOIN Survey.MetabolicDetailInfo AS tbDetailInfo ON tbSurveyHeader.RowGUID = tbDetailInfo.DocumentID
			LEFT JOIN dbo.Person AS tbPerson ON tbDetailInfo.PersonID = tbPerson.PersonID 
				LEFT JOIN dbo.Prefix AS tbPersonPrefix ON tbPerson.PrefixCode = tbPersonPrefix.Code 
				LEFT JOIN dbo.Gender AS tbPersonGender ON tbPerson.GenderID = tbPersonGender.ID
			LEFT JOIN dbo.Person AS tbOSMPerson ON tbDetailInfo.OSMID = tbOSMPerson.PersonID
				LEFT JOIN dbo.Prefix AS tbOSMPrefix ON tbOSMPerson.PrefixCode = tbOSMPrefix.Code 
			LEFT JOIN dbo.HealthInsuranceType AS tbHealthInsuranceType ON tbDetailInfo.HInsuranceTypeID = tbHealthInsuranceType.ID
			LEFT JOIN dbo.Home AS tbHome ON tbDetailInfo.HomeID = tbHome.ID 
				LEFT JOIN dbo.Village AS tbVillage ON tbHome.VillageID = tbVillage.ID
			LEFT JOIN dbo.SmokingStatus AS tbSmokingStatus ON tbDetailInfo.SmokingStatusID = tbSmokingStatus.ID
			LEFT JOIN dbo.DrinkingStatus AS tbDrinkingStatus ON tbDetailInfo.DrinkingStatusID = tbDrinkingStatus.ID
		LEFT JOIN dbo.Hospital AS tbHospital ON tbSurveyHeader.HospitalCode = tbHospital.Code5
			LEFT JOIN dbo.Province AS tbProvince ON tbHospital.ProvinceCode = tbProvince.Code 
			LEFT JOIN dbo.Amphur AS tbAmphur ON tbHospital.AmphurCode = tbAmphur.Code 
			LEFT JOIN dbo.Tumbol AS tbTumbol ON tbHospital.TumbolCode = tbTumbol.Code 

	WHERE tbSurveyHeader.HeaderTypeCode = 'METABOLIC'
	AND tbSurveyHeader.RowGUID = ISNULL(NULLIF(@SurveyHeaderRowGUID, ''), tbSurveyHeader.RowGUID)
	AND (
		tbDetailInfo.PersonID = ISNULL(NULLIF(@PersonID,''), tbDetailInfo.PersonID)
		OR NULLIF(@PersonID,'') IS NULL AND tbDetailInfo.RowGUID IS NULL 
	)
	AND (
		tbDetailInfo.OSMID = ISNULL(NULLIF(@OSMPersonID, ''), tbDetailInfo.OSMID)
		OR /*NULLIF(@OSMPersonID,'') IS NULL AND*/ tbDetailInfo.RowGUID IS NULL
	)
	ORDER BY 
		tbSurveyHeader.RowGUID ASC
		, tbHome.HomeNO
		, tbPerson.FirstName ASC
END
GO
