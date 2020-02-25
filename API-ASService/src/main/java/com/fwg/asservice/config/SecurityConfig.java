//package com.fwg.asservice.config;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import com.fwg.asservice.service.driverapp.user.CustomAuthenticationFailureHandler;
//import com.fwg.asservice.service.driverapp.user.CustomAuthenticationSuccessHandler;
//
////import com.audixo.service.driverapp.user.CustomAuthenticationFailureHandler;
////import com.audixo.service.driverapp.user.CustomAuthenticationSuccessHandler;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	private DataSource dataSource;
//	
//    @Autowired
//    private AuthenticationSuccessHandler authenticationSuccessHandler;
//    
//    @Autowired
//    private AuthenticationFailureHandler authenticationFailureHandler;
//	
//	@Autowired
//	@Qualifier("userDetailsService")
//	private UserDetailsService userDetailsServiceImpl;
//	
//    @Autowired
//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsServiceImpl);
//        auth.authenticationProvider(authenticationProvider());
//    }
//
//	@Autowired
//	public void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication().dataSource(dataSource)
//		.passwordEncoder(passwordEncoder());
//	}
//	
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }
//    
//	@Bean
//	public PasswordEncoder passwordEncoder(){
//		PasswordEncoder encoder = new BCryptPasswordEncoder();
//		return encoder;
//	}
//	
//    @Bean
//    public AuthenticationSuccessHandler authenticationSuccessHandler() {
//        return new CustomAuthenticationSuccessHandler();
//    }
//    
//    @Bean
//    public AuthenticationFailureHandler authenticationFailureHandler() {
//        return new CustomAuthenticationFailureHandler();
//    }
//	
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//	
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//    	
//    	http.authorizeRequests()
//			.antMatchers("/sec/moderation.html").hasRole("MODERATOR")
//			.antMatchers("/post-list").hasRole("ADMIN")
//			.antMatchers("/add-post").hasRole("ADMIN")
//			.antMatchers("/edit-post").hasRole("ADMIN")
//			.antMatchers("/product-list").hasAnyRole("ADMIN","USER")
//			.antMatchers("/add-product").hasAnyRole("ADMIN","USER")
//			.antMatchers("/edit-product").hasRole("ADMIN")
//			.antMatchers("/user-account").hasRole("USER")
//			.and()
//				.formLogin()
//				//.loginPage("/login")
//				.defaultSuccessUrl("/index")
//				.failureUrl("/login?error=1")
//				.loginProcessingUrl("/login/submit")
//				.successHandler(authenticationSuccessHandler)
//				.failureHandler(authenticationFailureHandler)
//				.usernameParameter("j_username")
//				.passwordParameter("j_password")
//				.permitAll()
//			.and()
//				.logout()
//				.logoutSuccessUrl("/index")
//			.and()
//				.exceptionHandling().accessDeniedPage("/403")
//			.and()
//				.csrf().disable();
//    }
//
//}