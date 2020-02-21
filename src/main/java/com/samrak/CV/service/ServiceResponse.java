package com.samrak.CV.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.samrak.CV.entities.Response;
import com.samrak.CV.repository.ResponseRepository;

@Service
public class ServiceResponse {
	
	@Autowired
	private ResponseRepository respRepo;
	

	public List<Response> listAll(){
		return respRepo.findAll();
	}
	
	public void save(Response response) {
		respRepo.save(response);
	}
	
	public Optional<Response> get(Long id) {
		return respRepo.findById(id);
	}
	
	public List<Response> findResponseByOffer(Long id) {
		return respRepo.findResponseByOffer(id);
	}
	
	public void delete(Long id) {
		respRepo.deleteById(id);
	}
	
	

}
