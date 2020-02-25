package com.fwg.asservice.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

@Repository
public class PersonDaoImpl implements PersonDao { 

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Prefix> listPrefixs(Integer genderId) throws Exception {
		
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getPerfix(:genderId)}");
			query.addEntity(Prefix.class);
			query.setParameter("genderId", genderId);
			
			@SuppressWarnings("unchecked")
			List<Prefix> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Gender> listGenders() throws Exception {
		
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getGender()}");
			query.addEntity(Gender.class);
			
			@SuppressWarnings("unchecked")
			List<Gender> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Race> listRaces() throws Exception {
		
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getRace()}");
			query.addEntity(Race.class);
			
			@SuppressWarnings("unchecked")
			List<Race> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Nationality> listNationalitys() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getNationalityIsActive()}");
			query.addEntity(Nationality.class);
			
			@SuppressWarnings("unchecked")
			List<Nationality> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Religion> listReligions() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getReligion()}");
			query.addEntity(Religion.class);
			
			@SuppressWarnings("unchecked")
			List<Religion> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<BloodType> listBloodTypes() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getBloodType()}");
			query.addEntity(BloodType.class);
			
			@SuppressWarnings("unchecked")
			List<BloodType> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<BornLocation> listBornLocations(BornLocation bornLocation) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getBornLocation(:hospitalCode)}");
			query.addEntity(BornLocation.class);
			query.setParameter("hospitalCode", bornLocation.getHospitalCode());
			
			@SuppressWarnings("unchecked")
			List<BornLocation> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<BornType> listBornTypes() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getBornType()}");
			query.addEntity(BornType.class);
			
			@SuppressWarnings("unchecked")
			List<BornType> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<RHGroup> listRHGroups() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getRHGroup()}");
			query.addEntity(RHGroup.class);
			
			@SuppressWarnings("unchecked")
			List<RHGroup> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Education> listEducations() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getEducation()}");
			query.addEntity(Education.class);
			
			@SuppressWarnings("unchecked")
			List<Education> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Occupation> listOccupations() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getOccupation()}");
			query.addEntity(Occupation.class);
			
			@SuppressWarnings("unchecked")
			List<Occupation> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<TypeArea> listTypeAreas() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getTypeArea()}");
			query.addEntity(TypeArea.class);
			
			@SuppressWarnings("unchecked")
			List<TypeArea> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Discharge> listDischarges() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getDischarge()}");
			query.addEntity(Discharge.class);
			
			@SuppressWarnings("unchecked")
			List<Discharge> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	@Override
	public List<HealthInsuranceType> listHealthInsuranceTypes() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getHealthInsuranceType()}");
			query.addEntity(HealthInsuranceType.class);
			
			@SuppressWarnings("unchecked")
			List<HealthInsuranceType> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<CancerType> listCancerTypes() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getCancerType()}");
			query.addEntity(CancerType.class);
			
			@SuppressWarnings("unchecked")
			List<CancerType> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<DiseaseStatusType> listDiseaseStatusTypes() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getDiseaseStatusType()}");
			query.addEntity(DiseaseStatusType.class);
			
			@SuppressWarnings("unchecked")
			List<DiseaseStatusType> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<PatientType> listPatientTypes() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getPatientType()}");
			query.addEntity(PatientType.class);
			
			@SuppressWarnings("unchecked")
			List<PatientType> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<PatientSurveyType> listPatientSurveyTypes() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getPatientSurveyType()}");
			query.addEntity(PatientSurveyType.class);
			
			@SuppressWarnings("unchecked")
			List<PatientSurveyType> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<DisabilityType> listDisabilityTypes() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getDisabilityType()}");
			query.addEntity(DisabilityType.class);
			
			@SuppressWarnings("unchecked")
			List<DisabilityType> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<DisabilityCauseType> listDisabilityCauseTypes() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getDisabilityCauseType()}");
			query.addEntity(DisabilityCauseType.class);
			
			@SuppressWarnings("unchecked")
			List<DisabilityCauseType> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Person> listPersonByCitizenId(String citizenId) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getPerson_ByCitizenID(:citizenId)}");
			query.addEntity(Person.class);
			query.setParameter("citizenId", citizenId);
			@SuppressWarnings("unchecked")
			List<Person> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Person> listPersonByPersonId(String personId) throws Exception {
		Session session = sessionFactory.openSession();
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_person_getPerson_ByPersonId(:personId)}");
			query.addEntity(Person.class);
			query.setParameter("personId", personId);
			@SuppressWarnings("unchecked")
			List<Person> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<MedicalRight> listMedicalRights(MedicalRight medicalRight) throws Exception {
		Session session = sessionFactory.openSession();
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_Person_getMedicalRight()}");
			query.addEntity(MedicalRight.class);
			//query.setParameter("personId", personId);
			@SuppressWarnings("unchecked")
			List<MedicalRight> result = query.list();
			
			return result;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
}
