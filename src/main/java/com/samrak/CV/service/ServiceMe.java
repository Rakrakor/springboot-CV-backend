package com.samrak.CV.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samrak.CV.entities.Me;
import com.samrak.CV.repository.MeRepository;


@Service
public class ServiceMe {

	@Autowired
	private MeRepository meRepo;
	

	public List<Me> listAll(){
		return meRepo.findAll();
	}
	
	public void save(Me me) {
		meRepo.save(me);
	}
	
	public Optional<Me> get(Long id) {
		return meRepo.findById(id);
	}
	
	public void delete(Long id) {
		meRepo.deleteById(id);
	}
	
	
}
