package com.samrak.CV.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samrak.CV.entities.Offer;
import com.samrak.CV.entities.Users;
import com.samrak.CV.repository.OfferRepository;

@Service
public class ServiceOffer {
	
	@Autowired
	private OfferRepository offerRepo;
	

	public List<Offer> listAll(){
		return offerRepo.findAll();
	}
	
	public List<Offer> retrieveAll(){
		return offerRepo.retrieveAll();
	}
	
	
	public List<Offer> listUserOffers(Users users){
		return offerRepo.findByUser(users);
	}
	
	public void save(Offer offer) {
		offerRepo.save(offer);
	}
	
	
	public Optional<Offer> get(Long id) {
		return offerRepo.findById(id);
	}
	
	
	public void delete(Long id) {
		offerRepo.deleteById(id);
	}



}
