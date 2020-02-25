package com.fwg.asservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fwg.asservice.dao.PersonDao;
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
import com.fwg.asservice.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService { 
	
	@Autowired
	private PersonDao personDao;

	@Transactional
	public void savePrefix(Prefix prefix) throws Exception {
		
	}

	@Transactional(readOnly = true)
	public List<Prefix> listPrefixs(Integer genderId) throws Exception {
		try{
			return personDao.listPrefixs(genderId);
		}catch(Exception e){
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public Prefix getPrefix(String code) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Gender> listGenders() throws Exception {
		try{
			return personDao.listGenders();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<Race> listRaces() throws Exception {
		try{
			return personDao.listRaces();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<Nationality> listNationalitys() throws Exception {
		try{
			return personDao.listNationalitys();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<Religion> listReligions() throws Exception {
		try{
			return personDao.listReligions();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<BloodType> listBloodTypes() throws Exception {
		try{
			return personDao.listBloodTypes();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<BornLocation> listBornLocations(BornLocation bornLocation) throws Exception {
		try{
			return personDao.listBornLocations(bornLocation);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<BornType> listBornTypes() throws Exception {
		try{
			return personDao.listBornTypes();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<RHGroup> listRHGroups() throws Exception {
		try{
			return personDao.listRHGroups();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<Education> listEducations() throws Exception {
		try{
			return personDao.listEducations();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<Occupation> listOccupations() throws Exception {
		try{
			return personDao.listOccupations();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<TypeArea> listTypeAreas() throws Exception {
		try{
			return personDao.listTypeAreas();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<Discharge> listDischarges() throws Exception {
		try{
			return personDao.listDischarges();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<HealthInsuranceType> listHealthInsuranceTypes() throws Exception {
		try{
			return personDao.listHealthInsuranceTypes();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<CancerType> listCancerTypes() throws Exception {
		try{
			return personDao.listCancerTypes();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<DiseaseStatusType> listDiseaseStatusTypes() throws Exception {
		try{
			return personDao.listDiseaseStatusTypes();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<PatientType> listPatientTypes() throws Exception {
		try{
			return personDao.listPatientTypes();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<PatientSurveyType> listPatientSurveyTypes() throws Exception {
		try{
			return personDao.listPatientSurveyTypes();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<DisabilityType> listDisabilityTypes() throws Exception {
		try{
			return personDao.listDisabilityTypes();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<DisabilityCauseType> listDisabilityCauseTypes() throws Exception {
		try{
			return personDao.listDisabilityCauseTypes();
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<Person> listPersonByCitizenId(String citizenId) throws Exception {
		try{
			return personDao.listPersonByCitizenId(citizenId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<Person> listPersonByPersonId(String personId) throws Exception {
		try{
			return personDao.listPersonByPersonId(personId);
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public List<MedicalRight> listMedicalRights(MedicalRight medicalRight) throws Exception {
		try{
			return personDao.listMedicalRights(medicalRight);
		}catch(Exception e){
			throw e;
		}
	}
}
