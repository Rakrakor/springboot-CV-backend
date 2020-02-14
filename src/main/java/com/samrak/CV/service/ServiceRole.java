package com.samrak.CV.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samrak.CV.entities.Role;
import com.samrak.CV.repository.RoleRepository;

@Service
public class ServiceRole {

	@Autowired
	private RoleRepository roleRepo;
	

	public List<Role> listAll(){
		return roleRepo.findAll();
	}
	
	public void save(Role role) {
		roleRepo.save(role);
	}
	
	public Optional<Role> get(Long roleId) {
		
		return roleRepo.findById(roleId);
	}
	
	
	public void delete(Long id) {
		roleRepo.deleteById(id);
	}
}
