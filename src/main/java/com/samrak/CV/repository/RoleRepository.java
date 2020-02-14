package com.samrak.CV.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samrak.CV.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


	
}

