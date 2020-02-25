package com.fwg.asservice.utility;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fwg.asservice.config.ShaHashConfig;
import com.fwg.asservice.constants.APIStatusMessage;
import com.fwg.asservice.constants.SurveyConstants;
import com.fwg.asservice.message.MessageSourceUtil;
import com.fwg.asservice.model.Address;
import com.fwg.asservice.model.Amphur;
import com.fwg.asservice.model.Home;
import com.fwg.asservice.model.Person;
import com.fwg.asservice.model.Prefix;
import com.fwg.asservice.model.Province;
import com.fwg.asservice.model.Tumbol;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

@Component
public class GlobalFunction {
	
	@Autowired
    private HttpServletRequest requests;
	private static HttpServletRequest request;
	
	private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "EN"));
	private static SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy", new Locale("en", "EN"));
	private static SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", new Locale("en", "EN"));
	
	@PostConstruct     
	private void initStaticMessage () {
		request = this.requests;
	}
	
	public static SimpleDateFormat getSdfDate() {
		return sdfDate;
	}

	public static void setSdfDate(SimpleDateFormat sdfDate) {
		GlobalFunction.sdfDate = sdfDate;
	}

	public static SimpleDateFormat getSdfDateTime() {
		return sdfDateTime;
	}

	public static void setSdfDateTime(SimpleDateFormat sdfDateTime) {
		GlobalFunction.sdfDateTime = sdfDateTime;
	}

	public static String getExceptionMessage(final Throwable throwable) {
	     StringBuffer buffer = new StringBuffer();
	     buffer.append(throwable.getClass());
	     buffer.append(":");
	     buffer.append(throwable.getMessage());
	     return buffer.toString();
	}

	public static String getErrorCode(final Throwable throwable) {
		String errorMessage = "";
	    for (String err : APIStatusMessage.ERROR_CODE_STORED_PROCEDURE) {
		    String detailMessage = throwable.getMessage();
			if (detailMessage.contains(err)) {
				errorMessage = detailMessage.substring(0, detailMessage.indexOf("]")+1);
				break;
			}
	    }
		return errorMessage;
	}
	
	public static boolean isEmpty(String str){ 
		if(str == null)
			return true;
		else if(str.trim().length()<1)
			return true;
		else
			return false;
	}
	
	public static boolean isEmpty(Object object){
		if(object == null)
			return true;
		else 
			return isEmpty(object.toString());
	}
	
	public static String currentTimeStamp(){
		return new Timestamp(System.currentTimeMillis()).toString();
	}
	
	public static String getSimpleFormatDate(java.sql.Date date) {
		try {
			return date==null ? "" : sdf.format(new Date(date.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static int getAgeFromBirthDate(java.sql.Date birthDate){
		LocalDate birthdate = new LocalDate (birthDate);
		LocalDate now = new LocalDate();
		Years age = Years.yearsBetween(birthdate, now);
		
		return age.getYears();
	}
	
	public static Date convertStringToDate(String strDate){
		Date sqlDate = null;
		try {
			java.util.Date utilDate = sdfDate.parse(strDate);
			sqlDate = new java.sql.Date(utilDate.getTime());
		} catch (Exception e) {
		}
		return sqlDate;
	}
	
	public static Timestamp convertStringToTimestamp(String strDateTime){
		Timestamp sqlTimestamp = null;
		try {
			java.util.Date utilDate = sdfDateTime.parse(strDateTime);
			sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());
		} catch (Exception e) {
		}
		return sqlTimestamp;
	}
    
    public static String getBaseUrl(HttpServletRequest request) {
	    String scheme = request.getScheme() + "://";
	    String serverName = request.getServerName();
	    String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
	    String contextPath = request.getContextPath();
	    return scheme + serverName + serverPort + contextPath;
    }
    
    public static String getServerName(HttpServletRequest request) {
	    String scheme = request.getScheme() + "://";
	    String serverName = request.getServerName();
	    return scheme + serverName;
    }
    
    public static String getFullUrl(HttpServletRequest request) {
	    String requestURL = request.getRequestURL().toString();
	    return getBaseUrl(request) + requestURL;
    }
    
    public static String generateFullName(Person person) {
    	String fullName = "";
		if (person!=null && person.getPersonId()!=null) {
			fullName = genFullName(person.getPrefix(), person.getFirstName(), person.getLastName());
		}
	    return fullName;
	}
    
    public static String genFullName(Prefix prefix, String firstName, String lastName) {
    	StringBuilder fullName = new StringBuilder();
    	if (prefix != null) {
    		fullName.append(prefix.getName());
    	}
		fullName.append(firstName);
		fullName.append("  ");
		fullName.append(lastName);
	    return fullName.toString();
	}
    
    public static JSONObject mergeJSONObject(JSONObject j1,JSONObject... j2) {
    	JSONParser parser = new JSONParser();
    	JSONObject o1 = new JSONObject();
		try {
			o1 = (JSONObject) parser.parse(j1.toString());
			for (JSONObject jsonObject : j2) {
				if(!isEmpty(jsonObject)) {
					o1.merge(jsonObject); 
				}
			}
	    	return o1;
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static JSONObject generateProfile(Person person) {
    	JSONObject object = new JSONObject();
		if (person!=null && person.getPersonId()!=null) {
	    	object.put("personId", person.getPersonId());
			object.put("citizenId", person.getCitizenId());
			object.put("fullName", GlobalFunction.generateFullName(person));
			object.put("firstName",person.getFirstName());
			object.put("lastName", person.getLastName());
			object.put("nickName", person.getNickName());
			object.put("birthDate", person.getBirthDate());
			object.put("picturePath", person.getPicturePath());
			if(!GlobalFunction.isEmpty(person.getGender())) {
				object.put("genderId", person.getGenderId());
				object.put("genderName", person.getGender().getName());
			}
			if(!GlobalFunction.isEmpty(person.getPrefix())) {
				object.put("prefixCode", person.getPrefix().getCode());
				object.put("prefixName", person.getPrefix().getName());
			}
			if(!GlobalFunction.isEmpty(person.getMaritalStatus())) {
				object.put("mStatusCode", person.getMaritalStatus().getCode());
				object.put("mStatusName", person.getMaritalStatus().getName());
			}
			if(!GlobalFunction.isEmpty(person.getOccupation())) {
				object.put("occupCode", person.getOccupation().getCode());
				object.put("occupName", person.getOccupation().getName());
			}
			if(!GlobalFunction.isEmpty(person.getRace())) {
				object.put("raceCode", person.getRace().getCode());
				object.put("raceName", person.getRace().getName());
			}
			if(!GlobalFunction.isEmpty(person.getNationality())) {
				object.put("nationCode", person.getNationality().getCode());
				object.put("nationName", person.getNationality().getName());
			}
			if(!GlobalFunction.isEmpty(person.getReligion())) {
				object.put("religionCode", person.getReligion().getCode());
				object.put("religionName", person.getReligion().getName());
			}
			if(!GlobalFunction.isEmpty(person.getEducation())) {
				object.put("educationCode", person.getEducation().getCode());
				object.put("educationName", person.getEducation().getName());
			}
			if(!GlobalFunction.isEmpty(person.getVillageStatus())) {
				object.put("villageStatusCode", person.getVillageStatus().getCode());
				object.put("villageStatusName", person.getVillageStatus().getName());
			}
			if(!GlobalFunction.isEmpty(person.getBloodType())) {
				object.put("bloodTypeId", person.getBloodType().getId());
				object.put("bloodTypeName", person.getBloodType().getName());
			}
			if(!GlobalFunction.isEmpty(person.getRhGroup())) {
				object.put("rHGroupId", person.getRhGroup().getId());
				object.put("rHGroupName", person.getRhGroup().getName());
			}
			if(!GlobalFunction.isEmpty(person.getLabor())) {
				object.put("laborCode", person.getLabor().getCode());
				object.put("laborName", person.getLabor().getName());
			}
			object.put("passport", person.getPassport());
			object.put("coupleCId", person.getCoupleCid());
			object.put("address", generateAddress(person));
		}
    	return object;
    }
    
    public static String generateAddress(Address address) {
		if (address!=null && address.getId()!=null) {
			return addressControl(address.getHomeNo()
					, address.getHomeNo()
					, "01"
					, address.getMooNo()
					, address.getTumbol()
					, address.getAmphur()
					, address.getProvince()
					, address.getZipcode());
		}
		return "";
	}
    
    public static String generateAddress(Person person) {
		if (person !=null && person.getPersonId()!=null) {
			return generateAddress(person.getAddress());
		}
		return "";
	}
    
    public static String generateAddress(Home home) {
		if (home!=null && home.getId()!=null) {
			return addressControl(home.getHomeNo()
					, home.getName()
					, home.getHomeTypeCode()
					, home.getVillage().getVillageNo()
					, home.getVillage().getHospital().getTumbol()
					, home.getVillage().getHospital().getAmphur()
					, home.getVillage().getHospital().getProvince()
					, home.getVillage().getHospital().getZipCode());
		}
	    return "";
	}
    
    public static String addressControl(Object homeNo, Object name, Object homeTypeCode, Object mooNo,Tumbol tumbol,Amphur amphur,Province province,Object zipCode) {
		StringBuilder addressBuilder = new StringBuilder();
		
		if(SurveyConstants.HOME_TYPE_OF_HOME.contains(homeTypeCode)){
			addressBuilder.append(!GlobalFunction.isEmpty(homeNo) ? homeNo+" " : "");
		}else{
			addressBuilder.append(!GlobalFunction.isEmpty(name) ? name+" " : "");
		}
		
		if(!GlobalFunction.isEmpty(mooNo)) {
			addressBuilder.append(MessageSourceUtil.getMessage("mooNo"));
			addressBuilder.append(mooNo);
		}
		if(!GlobalFunction.isEmpty(tumbol.getName())) {
			addressBuilder.append(" "+MessageSourceUtil.getMessage("tumbol"));
			addressBuilder.append(tumbol.getName());
		}
		if(!GlobalFunction.isEmpty(amphur.getName())) {
			addressBuilder.append(" "+MessageSourceUtil.getMessage("amphur"));
			addressBuilder.append(amphur.getName());
		}
		if(!GlobalFunction.isEmpty(province.getName())) {
			addressBuilder.append(" "+MessageSourceUtil.getMessage("province"));
			addressBuilder.append(province.getName());
		}
		if(!GlobalFunction.isEmpty(zipCode)) {
			addressBuilder.append(" ");
			addressBuilder.append(zipCode);
		}
		return addressBuilder.toString();
	}
	
	public static String generatePassword(String username ,java.sql.Date date) {
		return new ShaHashConfig().hashSHA1((username==null?"":username) + getSimpleFormatDate(date));
	}
	
	public static String generatePassword(String username ,String password) {
		return new ShaHashConfig().hashSHA1((username==null?"":username) + password);
	}
	
	public static String getBaseUrl() {
		return request.getRequestURL().substring(0, request.getRequestURL().length() - request.getRequestURI().length()) + request.getContextPath();
	}
    
//    public static void main(String[] args) {
//    	JSONObject a = new JSONObject();
//    	a.put("a", "a");
//    	JSONObject b = new JSONObject();
//    	a.put("b", "b");
//    	JSONObject c = new JSONObject();
//    	a.put("c", "c");
//    	JSONObject d = new JSONObject();
//    	a.put("d", "d");
//    	System.out.println("A:"+mergeJSONObject(a,b,c,d,null,new JSONObject()));
//	}
}