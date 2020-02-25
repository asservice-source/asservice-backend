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
-- Description:	Additional information for Report - Monthly Report Data for Mosquito HICI (House Index, Container Index) : Form #3 - Summary for Tumbol
-- =============================================
ALTER PROCEDURE Report.sp_HICI_Mosquito_Form3_HICISummaryForTumbol_GetReportInformation
	-- Add the parameters for the stored procedure here
	@ReporterPersonID AS NVARCHAR(max) = NULL
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SET LANGUAGE Thai;

	SELECT 
		[ReporterPersonID] = tbUserMapping.PersonID 
		, [ReporterName] = tbPrefix.ShortName + tbPerson.FirstName + ' ' + tbPerson.LastName 
		, [ReporterRoleDescription] = tbUserRoles.Description
		, [HospitalCode5] = tbHospital.Code5 
		, [HospitalName] = tbHospital.Name 
		, [ProvinceName] = tbProvince.Name 
		, [AmphurName] = tbAmphur.Name 
		, [TumbolName] = tbTumbol.Name 

	/*FROM dbo.UserMapping AS tbUserMapping 
		LEFT JOIN dbo.Person AS tbPerson ON tbUserMapping.PersonID = tbPerson.PersonID 
			LEFT JOIN dbo.Prefix AS tbPrefix ON tbPerson.PrefixCode = tbPrefix.Code 
		LEFT JOIN dbo.UserRoles AS tbUserRoles ON tbUserMapping.UserRoleID = tbUserRoles.ID 
		LEFT JOIN dbo.Hospital AS tbHospital ON tbUserMapping.HospitalCode = tbHospital.Code5 
			LEFT JOIN dbo.Province AS tbProvince ON tbHospital.ProvinceCode = tbProvince.Code 
			LEFT JOIN dbo.Amphur AS tbAmphur ON tbHospital.AmphurCode = tbAmphur.Code 
			LEFT JOIN dbo.Tumbol AS tbTumbol ON tbHospital.TumbolCode = tbTumbol.Code */
	FROM dbo.Person AS tbPerson 
		LEFT JOIN dbo.Prefix AS tbPrefix ON tbPerson.PrefixCode = tbPrefix.Code 
		LEFT JOIN dbo.UserMapping AS tbUserMapping ON tbPerson.PersonID = tbUserMapping.PersonID
			LEFT JOIN dbo.UserRoles AS tbUserRoles ON tbUserMapping.UserRoleID = tbUserRoles.ID 
			LEFT JOIN dbo.Hospital AS tbHospital ON tbUserMapping.HospitalCode = tbHospital.Code5 
				LEFT JOIN dbo.Province AS tbProvince ON tbHospital.ProvinceCode = tbProvince.Code 
				LEFT JOIN dbo.Amphur AS tbAmphur ON tbHospital.AmphurCode = tbAmphur.Code 
				LEFT JOIN dbo.Tumbol AS tbTumbol ON tbHospital.TumbolCode = tbTumbol.Code 

	WHERE tbPerson.PersonID = NULLIF(@ReporterPersonID, '')
END
GO
