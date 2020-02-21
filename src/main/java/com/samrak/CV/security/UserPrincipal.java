package com.samrak.CV.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.samrak.CV.entities.Role;
import com.samrak.CV.entities.Users;


public class UserPrincipal implements UserDetails {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6353442721667898479L;
	
	private Users users;
	
	public UserPrincipal(Users users) {
		super();
		this.users = users;
	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		
//		return Collections.singleton(new SimpleGrantedAuthority("USER"));
//	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
			    
		Collection<Role> roles=users.getRoles();
		
		List<GrantedAuthority> authorities= new ArrayList<>();
			    
//			    for (Role role: roles) {
//			        authorities.add(new SimpleGrantedAuthority(role.getRolename()));
//			        
////			        role.getPrivileges().stream().map(p -> new SimpleGrantedAuthority(p.getName()))
////			         .forEach(authorities::add);
//			    }
 			    roles.stream().map(p->new SimpleGrantedAuthority(p.getRolename())).forEach(authorities::add);
			    
			     
			    return authorities;
	}


	
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return users.getUserpassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return users.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


	

	
	
}
