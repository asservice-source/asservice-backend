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
/****** Object:  StoredProcedure [Report].[sp_Population_GetYearlyReportData_ListOfHostAndRegisteredOccupants]    Script Date: 2/9/2018 10:07:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Weerapong Saipetch
-- Create date: 31/10/2017
-- Description:	Yearly Report Data for Population (SubReport Section#1, Host & Registered Occupants)
-- =============================================
ALTER PROCEDURE [Report].[sp_Population_GetYearlyReportData_ListOfHostAndRegisteredOccupants]
	-- Add the parameters for the stored procedure here
	@PopulationDetailRowGUID AS NVARCHAR(max) = NULL 
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT 
		[PopulationDetailRowGUID] = @PopulationDetailRowGUID
		, [PopulationDetailInfoRowGUID] = tbPopulationDetailInfo.RowGUID
		, [PersonName] = tbPersonPrefix.ShortName + tbPerson.FirstName + ' ' + tbPerson.LastName
		, [PersonCitizenID] = tbPerson.CitizenID
		, [PersonBirthDate] = CAST(DAY(tbPopulationDetailInfo.BirthDate) AS VARCHAR) 
		+ '-' + CASE MONTH(tbPopulationDetailInfo.BirthDate) 
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
		+ '-' + CAST(YEAR(tbPopulationDetailInfo.BirthDate)+543 AS VARCHAR)
		, [EducationName] = tbEducation.Name
		, [OccupationName] = tbOccupation.Name
		, [CongenitalDisease] = tbPopulationDetailInfo.CongenitalDisease
		, [FamilyStatusName] = tbFamilyStatus.Name
		, [IsGuest] = tbPopulationDetailInfo.IsGuest
		, [IsExists] = tbPopulationDetailInfo.isExists
		, [Remark] = tbPopulationDetailInfo.Remark
	FROM Survey.PopulationDetailInfo AS tbPopulationDetailInfo 
		LEFT JOIN dbo.Person AS tbPerson ON tbPopulationDetailInfo.PersonID = tbPerson.PersonID
			LEFT JOIN dbo.Prefix AS tbPersonPrefix ON tbPerson.PrefixCode = tbPersonPrefix.Code
		LEFT JOIN dbo.Education AS tbEducation ON tbPopulationDetailInfo.EducationCode = tbEducation.Code
		LEFT JOIN dbo.Occupation AS tbOccupation ON tbPopulationDetailinfo.OccupCode = tbOccupation.Code
		LEFT JOIN dbo.FamilyStatus AS tbFamilyStatus ON tbPopulationDetailInfo.FamilyStatusID = tbFamilyStatus.ID

	WHERE tbPopulationDetailInfo.ReferenceID = ISNULL(NULLIF(@PopulationDetailRowGUID,''), NULL/*tbPopulationDetailInfo.ReferenceID*/)
	AND (
		tbPopulationDetailInfo.FamilyStatusID = '1'  -- เจ้าบ้าน
		OR  (tbPopulationDetailInfo.FamilyStatusID = '2' AND tbPopulationDetailInfo.isGuest = '0') -- ผู้อาศัยที่ไม่ใช่แขก (ผู้อาศัยที่มีข้อมูลในทะเบียนบ้าน)
	)

	ORDER BY 
		tbPopulationDetailInfo.FamilyStatusID ASC

END
