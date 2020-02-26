package com.fwg.asservice.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fwg.asservice.model.Session;
import com.fwg.asservice.service.UserService;
import com.fwg.asservice.utility.GlobalFunction;

import net.minidev.json.JSONObject;

public class CORSFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "3600");
		res.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-Headers, sid");
        
		if (!"OPTIONS".equals(req.getMethod())) { 
            // CORS "pre-flight" request 
			//res.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE"); 
			//res.addHeader("Access-Control-Allow-Headers", "Authorization"); 
			//res.addHeader("Access-Control-Max-Age", "1728000");
			String sid = req.getHeader("sid");
			String uri = req.getRequestURI();
	        String[] uris = uri.split("/");
	        //System.out.println("servletPath : "+uris[2]);
	        //System.out.println("servletPath : "+uris[3]);

	        System.out.println("uri : "+uri);
	        System.out.println("sid : "+sid);
	        
	        List<String> bypassUrl = Arrays.asList(  "app"
	        		,"login"
	        		,"web-resources"
	        		,"register"
	        		,"prefix_list"
	        		,"address"
	        		,"hospital"
	        		,"person_by_citizenid"
	        		//,"statistic"
	        		//,"survey_header_list"
	        		//,"edit_profile"
	        		,"file_upload"
	        		
	        		);
	        boolean isBypass = false;
	        try {
	        	isBypass = (bypassUrl.contains(uris[2]) || bypassUrl.contains(uris[3]));
	        }catch(Exception e) {
	        	isBypass = true;
	        }
			
			System.out.println("isBypass : "+isBypass+"\n SID : "+sid);
			System.out.println("SID : "+sid);
			
			if(isBypass){
				
				chain.doFilter(req, res);
				
			}else{
				
				SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

		        Session session = new Session();
		        session.setSid(sid);
		        
				System.out.println("SID : "+session.getSid());
				String message = "session is alive.";
				String code = "";
				boolean isVerifySession = false;
				
				try {
					
					isVerifySession = userService.verifySession(session);
					code = "Success";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					message =  GlobalFunction.getExceptionMessage(e);
					code = GlobalFunction.getErrorCode(e);
				}
				
				if(isVerifySession){
					chain.doFilter(req, res);
				}else{
					JSONObject resp = new JSONObject();
		        	resp.put("sid", session.getSid());
		        	resp.put("status", isVerifySession);
		        	resp.put("code", code);
		        	resp.put("message", message);
		        	
		        	res.reset();
		        	res.setHeader("Content-Type", "application/json;charset=UTF-8");
		        	res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		        	res.getWriter().write(resp.toJSONString());
				}
			}
			
        } else { 
        	chain.doFilter(req, res); 
        } 
		
		
	}
	
	/*public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		System.out.println("Filtering on...........................................................");

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-Headers, sid");
        

        
        String sid = request.getHeader("sid");
        
        String uri = request.getRequestURI();
        String[] uris = uri.split("/");
        System.out.println("servletPath : "+uris[2]);
        System.out.println("servletPath : "+uris[3]);

        System.out.println("uri : "+uri);
        System.out.println(uri);
        System.out.println("sid : "+sid);
        
        List<String> bypassUrl = Arrays.asList(  "app"
								        		,"login"
								        		,"web-resources"
								        		,"register"
								        		,"prefix_list"
								        		,"address"
								        		,"hospital"
								        		,"statistic"
								        		,"survey_header_list"
								        		,"edit_profile"
								        		,"file_upload");
        

		boolean isBypass = (bypassUrl.contains(uris[2]) || bypassUrl.contains(uris[3]));
		
		System.out.println("isBypass : "+isBypass+"\n SID : "+sid);
		System.out.println("SID : "+sid);
		
		if(!"OPTIONS".equalsIgnoreCase(request.getMethod())){
			if(isBypass){
				
				chain.doFilter(req, res);
				
			}else{
				
				SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

		        Session session = new Session();
		        session.setSid(sid);
		        
				System.out.println("SID : "+session.getSid());
				String message = "session is alive.";
				String code = "";
				boolean isVerifySession = false;
				
				try {
					
					isVerifySession = userService.verifySession(session);
					code = "Success";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					message =  GlobalFunction.getExceptionMessage(e);
					code = GlobalFunction.getErrorCode(e);
				}
				
				if(isVerifySession){
					chain.doFilter(req, res);
				}else{
					JSONObject resp = new JSONObject();
		        	resp.put("sid", session.getSid());
		        	resp.put("status", isVerifySession);
		        	resp.put("code", code);
		        	resp.put("message", message);
		        	
		        	response.reset();
		        	response.setHeader("Content-Type", "application/json;charset=UTF-8");
		        	//httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		        	response.getWriter().write(resp.toJSONString());
				}
			}
		}else{
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}*/

}
