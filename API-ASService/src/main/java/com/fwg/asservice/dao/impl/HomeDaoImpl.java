package com.fwg.asservice.dao.impl;

import java.sql.Types;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fwg.asservice.dao.HomeDao;
import com.fwg.asservice.model.FamilyStatus;
import com.fwg.asservice.model.Home;
import com.fwg.asservice.model.HomeType;
import com.fwg.asservice.model.filter.Filter;
import com.fwg.asservice.sql.CallableStatementUtil;
import com.fwg.asservice.utility.GlobalFunction;

import javassist.bytecode.analysis.Type;

@Repository
public class HomeDaoImpl implements HomeDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Home> listHomes(Integer villageId, String osmId, Integer id) throws Exception { 
		Session session = sessionFactory.openSession();
		
		try{
			
		
			SQLQuery query = session.createSQLQuery("{CALL sp_home_getHomeList_SearchBy_Village_OSM_Home(:villageId, :osmId, :id)}");
			
			query.addEntity(Home.class);
			query.setParameter("villageId", villageId);
			query.setParameter("osmId", osmId);
			query.setParameter("id", id);
			
			List<Home> result = query.list();
		
			
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
	public List<Home> listHomeByVillageOrOSM(Integer villageId, String osmId, String headerTypeCode, boolean memberOnly) throws Exception {
		
		Session session = sessionFactory.openSession();
		
		try{
		
			SQLQuery query = session.createSQLQuery("{CALL sp_home_getHomeList_ByVillageID_Or_OSM(:villageId,:osmId,:headerTypeCode,:memberOnly)}");
			query.addEntity(Home.class);
			query.setParameter("villageId", villageId);
			query.setParameter("osmId", GlobalFunction.isEmpty(osmId) ? null : osmId);
			query.setParameter("headerTypeCode", headerTypeCode);
			query.setParameter("memberOnly", memberOnly);
			
			List<Home> result = query.list();
			
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
	public List<FamilyStatus> listFamilyStatuss() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
		
			SQLQuery query = session.createSQLQuery("{CALL sp_home_getFamilyStatus()}");
			query.addEntity(FamilyStatus.class);
			
			List<FamilyStatus> result = query.list();
			
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
	public Home getHomeByPersonId(String personId) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			SQLQuery query = session.createSQLQuery("{CALL sp_home_getHomeByPersonId(:personId)}");
			query.addEntity(Home.class);
			query.setParameter("personId", personId);
			List<Home> result = query.list();
			if(result != null && result.size()>0) {
				return result.get(0);
			}
			return null;
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Home getHomeInfoWithOsm(Integer homeId) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			SQLQuery query = session.createSQLQuery("{CALL sp_home_getHomeInfo_By_HomeId(:homeId)}");
			query.addEntity(Home.class);
			
			query.setParameter("homeId", homeId);
			
			List<Home> result = query.list();
			if(result != null && result.size()>0) {
				return result.get(0);
			}
			return null;
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Home insertOrUpdateHome(Home home) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		try{
			
			String query = " CALL sp_home_InsOrUpd(" + 
				":ID" +  
				",:RegistrationID" +  
				",:HomeTypeCode" + 
				",:HomeNO" + 
				",:VillageID" + 
				",:Name" + 
				",:Road" + 
				",:Soi" + 
				",:Telephone" + 
				",:Latitude" + 
				",:Longitude" + 
				",:OSMID" + 
			")";
			
			callableStatementUtil = new CallableStatementUtil(session, query);
			
			// Output Parameter
			callableStatementUtil.registerOutParameter("ID", Types.INTEGER);
			
			// Input Parameter
			if(GlobalFunction.isEmpty(home.getId())){
				callableStatementUtil.setInt("ID", 0);
			}else{
				callableStatementUtil.setInt("ID", home.getId());
			}
			
			callableStatementUtil.setString("RegistrationID", home.getRegistrationId());
			callableStatementUtil.setString("HomeTypeCode", home.getHomeTypeCode());
			callableStatementUtil.setString("HomeNO", home.getHomeNo());
			callableStatementUtil.setInt("VillageID", home.getVillageId());
			callableStatementUtil.setString("Name", home.getName());
			callableStatementUtil.setString("Road", home.getRoad());
			callableStatementUtil.setString("Soi", home.getSoi());
			callableStatementUtil.setString("Telephone", home.getTelephone());
			callableStatementUtil.setString("Latitude", home.getLatitude());
			callableStatementUtil.setString("Longitude", home.getLongitude());
			callableStatementUtil.setString("OSMID", home.getOsmId());
			
			Log.error(callableStatementUtil);
			
			callableStatementUtil.execute();

			home.setId(callableStatementUtil.getInt("ID"));
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (callableStatementUtil != null) {
				callableStatementUtil.close();
			}
			if (session != null) {
				session.close();
			}
		}
		return home;
	}

	@Override
	public boolean deleteHome(Integer homeId) throws Exception {
		boolean result = false;
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		try{
			String query = " CALL sp_home_Del(:ID)";
			
			callableStatementUtil = new CallableStatementUtil(session, query);
			callableStatementUtil.setInt("ID", homeId);
			
			Log.error(callableStatementUtil);
			
			callableStatementUtil.execute();
			
			result = true;
			
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (callableStatementUtil != null) {
				callableStatementUtil.close();
			}
			if (session != null) {
				session.close();
			}
		}
		return result;
	}

	@Override
	public List<HomeType> listHomeTypeMonitorHICI() throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
		
			SQLQuery query = session.createSQLQuery("{CALL sp_home_getHomeType_MonitorHICI()}");
			
			query.addEntity(HomeType.class);
			
			List<HomeType> result = query.list();
		
			
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
	public List<Home> listHomeByVillageHomeType(Integer id, String homeTypeCode, String documentId, String osmId) throws Exception {
		Session session = sessionFactory.openSession();
		
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_home_getHomeList_ByVillageID_HomeTypeCode(:id,:homeTypeCode,:documentId, :osmId)}");
			
			query.addEntity(Home.class);
			query.setParameter("id", id);
			query.setParameter("homeTypeCode", homeTypeCode);
			query.setParameter("documentId", GlobalFunction.isEmpty(documentId) ? null : documentId);
			query.setParameter("osmId", GlobalFunction.isEmpty(osmId) ? null : osmId);
			List<Home> result = query.list();
		
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
	public List<Home> find(Filter filter, Home home) throws Exception {
		Session session = sessionFactory.openSession();
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_home_HomeList(:homeId,:villageId,:code5,:osmId,:homeTypeCode,:name)}");
			
			query.addEntity(Home.class);
			
			query.setParameter("homeId", filter.getHomeId());
			query.setParameter("villageId", filter.getVillageId());
			query.setParameter("code5", GlobalFunction.isEmpty(filter.getCode5())?null:filter.getCode5());
			query.setParameter("osmId", GlobalFunction.isEmpty(filter.getOsmId())?null:filter.getOsmId());
			query.setParameter("homeTypeCode", GlobalFunction.isEmpty(home.getHomeTypeCode())?null:home.getHomeTypeCode());
			query.setParameter("name", GlobalFunction.isEmpty(filter.getName())?null:filter.getName());
			
			List<Home> result = query.list();
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
	public List<Home> listHomeWithoutOSM(Integer villageId) throws Exception {
		Session session = sessionFactory.openSession();
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_home_getHomeList_WithoutOSM(:villageId)}");
			
			query.addEntity(Home.class);
			
			query.setParameter("villageId", villageId);
			
			List<Home> result = query.list();
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
	public List<Home> listHomeByHeaderTypeCode(String documentId, Integer villageId, String osmId,
			String headerTypeCode) throws Exception {
		Session session = sessionFactory.openSession();
		try{
			
			SQLQuery query = session.createSQLQuery("{CALL sp_home_getHomeList_ByHeaderTypeCode(:documentId, :villageId, :osmId, :headerTypeCode)}");
			
			query.addEntity(Home.class);
			query.setParameter("documentId", GlobalFunction.isEmpty(documentId) ? null : documentId);
			query.setParameter("villageId", villageId);
			query.setParameter("osmId", GlobalFunction.isEmpty(osmId) ? null : osmId);
			query.setParameter("headerTypeCode", headerTypeCode);
			
			List<Home> result = query.list();
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
	public Home insertOrUpdateOSM(Home home, String osmId) throws Exception {
		Session session = sessionFactory.openSession();
		CallableStatementUtil callableStatementUtil = null;
		try{
			
			String query = " CALL sp_home_Upd_OSM(" +  
				":ID" +  
				",:OSMID" + 
			")";
			
			callableStatementUtil = new CallableStatementUtil(session, query);
			
			// Output Parameter
			callableStatementUtil.registerOutParameter("ID", Types.INTEGER);
			
			// Input Parameter
			callableStatementUtil.setInt("ID", GlobalFunction.isEmpty(home.getId())?null:home.getId());
			callableStatementUtil.setString("OSMID", GlobalFunction.isEmpty(osmId)?null:osmId);
			
			Log.error(callableStatementUtil);
			
			callableStatementUtil.execute();

			home.setId(callableStatementUtil.getInt("ID"));
		
		} catch (Exception exception) {
			throw exception;
		}finally{
			if (callableStatementUtil != null) {
				callableStatementUtil.close();
			}
			if (session != null) {
				session.close();
			}
		}
		return home;
	}
}
