package com.samrak.CV.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.samrak.CV.entities.Skills;
import com.samrak.CV.repository.SkillsRepository;

@Service
public class ServiceSkills {

	
	@Autowired 
	private SkillsRepository skillsRepo;
	
	
	public List<Skills> listAll(){
		return skillsRepo.findAll();
	}
	
	public void save(Skills skill) {
		skillsRepo.save(skill);
	}
	
	public Optional<Skills> get(Long id) {
		return skillsRepo.findById(id);
	}
	
	public void delete(Long id) {
		skillsRepo.deleteById(id);
	}
}
