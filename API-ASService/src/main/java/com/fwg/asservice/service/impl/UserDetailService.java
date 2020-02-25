//package com.fwg.asservice.service.impl;
//
//import java.util.Collection;
//import java.util.Comparator;
//import java.util.HashSet;
//import java.util.Set;
//import java.util.TreeSet;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Role;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.audixo.dao.UserAuthenDao;
//import com.audixo.model.UserAuthen;
//import com.audixo.model.UserPermission;
//import com.audixo.model.UserRole;
//import com.fwg.asservice.core.userdetails.CustomUserDetails;
//
//
//@Service("userDetailsService")
//@Transactional(readOnly = true)
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//	@Autowired
//	private UserAuthenDao userAuthenDao;
//
//    @Override
//    public UserDetails loadUserByUsername(String username)
//            throws UsernameNotFoundException {
//    	
//    	UserAuthen userAuthen = null;
//
//        try {
//        	
//        	System.out.println("usernameza : " + username);
//        	
//        	userAuthen = userAuthenDao.getUserAuthenByUserName(username);
//
//            boolean enabled = true;
//            boolean accountNonExpired = true;
//            boolean credentialsNonExpired = true;
//            boolean accountNonLocked = true;
//
//            // adapt as needed
//            return new CustomUserDetails(userAuthen.getUsername(),
//            							 userAuthen.getPassword(),
//            							 enabled,
//            							 accountNonExpired,
//            							 credentialsNonExpired,
//            							 accountNonLocked,
//            							 getAuthorities(userAuthen.getUserRole()));
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static class SimpleGrantedAuthorityComparator implements
//            Comparator<SimpleGrantedAuthority> {
//
//        @Override
//        public int compare(SimpleGrantedAuthority o1, SimpleGrantedAuthority o2) {
//            return o1.equals(o2) ? 0 : -1;
//        }
//    }
//
//    /**
//     * Retrieves a collection of {@link GrantedAuthority} based on a list of
//     * roles
//     * 
//     * @param roles
//     *            the assigned roles of the user
//     * @return a collection of {@link GrantedAuthority}
//     */
//    public Collection<? extends GrantedAuthority> getAuthorities(UserRole userRole) {
//
//        Set<SimpleGrantedAuthority> authList = new TreeSet<SimpleGrantedAuthority>(
//                new SimpleGrantedAuthorityComparator());
//
//        //for (UserRole userRole : userRoles) {
//        	authList.addAll(getGrantedAuthorities(userRole));
//        //}
//
//        return authList;
//    }
//
//    /**
//     * Wraps a {@link Role} role to {@link SimpleGrantedAuthority} objects
//     * 
//     * @param roles
//     *            {@link String} of roles
//     * @return list of granted authorities
//     */
//    public static Set<SimpleGrantedAuthority> getGrantedAuthorities(UserRole role) {
//
//        Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
//       
//        authorities.add(new SimpleGrantedAuthority(role.getRoleCode()));
//        System.out.println("role : " +role.getRoleCode());
//        Set<UserPermission> userPermissions = role.getUserPermissions();
//        for (UserPermission userPermission : userPermissions) {
//            authorities.add(new SimpleGrantedAuthority("" + userPermission.getPermissionCode()));
//            System.out.println("getPermissionCode : " + userPermission.getPermissionCode());
//        }
//
//        return authorities;
//    }
//    
//}
//
////@Service("userDetailsService")
////public class UserDetailsServiceImpl implements UserDetailsService {
////	
////	@Autowired
////	private UserAuthenDao userAuthenDao;
////	
////	@Autowired
////	private UserRoleDao userRoleDao;
////
////	@Transactional(readOnly=true)
////	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
////		
////		System.out.println("username : " + username);
////		
////		UserAuthen domainUser = null;
////		
////		try {
////			domainUser = userAuthenDao.getUserAuthenByUserName(username);
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////		
////		boolean enabled = true;
////		boolean accountNonExpired = true;
////		boolean credentialsNonExpired = true;
////		boolean accountNonLocked = true;
////
////		return new CustomUser(domainUser.getUsername(), 
////						 domainUser.getPassword(),
////						 enabled, 
////						 accountNonExpired, 
////						 credentialsNonExpired, 
////						 accountNonLocked,
////						 getAuthorities(domainUser),
////						 domainUser.getUserAccountId()
////		);
////	}
////	
////	public Collection<? extends GrantedAuthority> getAuthorities(UserAuthen userAuthen) {
////		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(1));
////		return authList;
////	}
////	
////	public List<String> getRoles(int roleTypeId) {
////		
////		List<UserRole> userRoleList = null;
////		try {
////			userRoleList = userRoleDao.listUserRoleByRoleType(roleTypeId);
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////
////		List<String> roles = new ArrayList<String>();
////		
////		for (UserRole userRole : userRoleList) {
////			System.out.println("roleName : " + userRole.getRoleCode());
////			roles.add(userRole.getRoleName());
////		}
////		
////		return roles;
////	}
////	
////	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
////		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
////		
////		for (String role : roles) {
////			authorities.add(new SimpleGrantedAuthority(role));
////		}
////		return authorities;
////	}
////
////}