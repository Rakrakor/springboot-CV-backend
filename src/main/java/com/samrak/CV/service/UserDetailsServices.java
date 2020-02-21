package com.samrak.CV.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.samrak.CV.entities.Role;
import com.samrak.CV.entities.Users;
import com.samrak.CV.repository.UserRepository;
import com.samrak.CV.security.UserPrincipal;

@Service
public class UserDetailsServices implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ServiceRole serveRole;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user=userRepo.findByUsername(username);
		if(user==null)
			throw new UsernameNotFoundException("User 404");
		
		return new UserPrincipal(user);
		
	}
	
	
	public Long userCurrentID(String name) {
		
		Users user=userRepo.findByUsername(name);
		Long userID=user.getId();
		return userID;
	}
	
	public void registerUser(Users user) {
		
		Users checkExistingUser=userRepo.findByUsername(user.getUsername());
//		if(checkExistingUser!=null) {
//			//do sthing
//		}
		
		
		if(checkExistingUser==null) {
			
			Optional<Role> roleUser=serveRole.get(2L);
			Set<Role>userRole=new HashSet<Role>();
			userRole.add(roleUser.get());
			user.setRoles(userRole);
			
			user.setUserpassword(passwordEncoder.encode(user.getUserpassword()));
			userRepo.save(user);
		}
		
	}
	
	public Users findByUsername(String username) {
		
		return userRepo.findByUsername(username);
	}
	
	
}
