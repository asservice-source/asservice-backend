package com.fwg.asservice.dao;

import java.util.List;

import com.fwg.asservice.model.BloodType;
import com.fwg.asservice.model.BornLocation;
import com.fwg.asservice.model.BornType;
import com.fwg.asservice.model.CancerType;
import com.fwg.asservice.model.DisabilityCauseType;
import com.fwg.asservice.model.DisabilityType;
import com.fwg.asservice.model.Discharge;
import com.fwg.asservice.model.DiseaseStatusType;
import com.fwg.asservice.model.Education;
import com.fwg.asservice.model.Gender;
import com.fwg.asservice.model.HealthInsuranceType;
import com.fwg.asservice.model.MedicalRight;
import com.fwg.asservice.model.Nationality;
import com.fwg.asservice.model.Occupation;
import com.fwg.asservice.model.PatientSurveyType;
import com.fwg.asservice.model.PatientType;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.Prefix;
import com.fwg.asservice.model.RHGroup;
import com.fwg.asservice.model.Race;
import com.fwg.asservice.model.Religion;
import com.fwg.asservice.model.TypeArea;

public interface PersonDao {

	/*
	 * CREATE and UPDATE
	 */
	//public void saveProvince(Province brand) throws Exception; // create and update

	/*
	 * READ
	 */
	
	public List<Prefix> listPrefixs(Integer genderId) throws Exception;
	public List<Gender> listGenders() throws Exception;
	public List<Race> listRaces() throws Exception;
	public List<Nationality> listNationalitys() throws Exception;
	public List<Religion> listReligions() throws Exception;
	public List<BloodType> listBloodTypes() throws Exception;
	public List<BornLocation> listBornLocations(BornLocation bornLocation) throws Exception;
	public List<BornType> listBornTypes() throws Exception;
	public List<RHGroup> listRHGroups() throws Exception;
	public List<Education> listEducations() throws Exception;
	public List<Occupation> listOccupations() throws Exception;
	public List<TypeArea> listTypeAreas() throws Exception;
	public List<Discharge> listDischarges() throws Exception;
	public List<HealthInsuranceType> listHealthInsuranceTypes() throws Exception;
	public List<CancerType> listCancerTypes() throws Exception;
	public List<DiseaseStatusType> listDiseaseStatusTypes() throws Exception;
	public List<PatientType> listPatientTypes() throws Exception;
	public List<PatientSurveyType> listPatientSurveyTypes() throws Exception;
	public List<DisabilityType> listDisabilityTypes() throws Exception;
	public List<DisabilityCauseType> listDisabilityCauseTypes() throws Exception;
	public List<Person> listPersonByCitizenId(String citizenId) throws Exception;
	public List<Person> listPersonByPersonId(String personId) throws Exception;
	public List<MedicalRight> listMedicalRights(MedicalRight medicalRight) throws Exception;

	/*
	 * DELETE
	 */
	//public void deleteProvince(int id) throws Exception;
}
