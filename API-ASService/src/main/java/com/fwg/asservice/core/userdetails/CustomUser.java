package com.fwg.asservice.core.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User{
	private static final long serialVersionUID = 6224476254599546723L;
	private final int userId;

	public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, int userId){
	    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	    this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}
	
}
