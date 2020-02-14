//package com.samrak.CV.service;
//
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.samrak.CV.entities.Recruiter;
//import com.samrak.CV.exceptions.LoginNotFoundException;
//import com.samrak.CV.repository.RecuiterRepository;
//
//@Service
//public class ServiceRecruiter {
//	
//	@Autowired
//	private RecuiterRepository recruitRepo;
//	
//	
//
//	public List<Recruiter> listAll(){
//		return recruitRepo.findAll();
//	}
//	
//	public void save(Recruiter recruiter) {
//		recruitRepo.save(recruiter);
//	}
//	
//	public Optional<Recruiter> get(Long id) {
//		return recruitRepo.findById(id);
//	}
//	
//	public Long searchLogin(String username,String companyname, String email) throws LoginNotFoundException{
//		 List <Recruiter> recruiterList = new ArrayList<>();
//		 recruiterList= recruitRepo.findAll();
//		 
//		 Long recruiterID=null;
//		 if(!recruiterList.isEmpty()) {
//		 
//		 Optional<Recruiter> loginRecruiter=recruiterList.stream()
//				 .filter(x->x.getUserName().equals(username))
//				 .filter(x->x.getCompanyName().equals(companyname))
//				 .filter(x->x.getEmail().equals(email)).findFirst();                                // If 'findAny' then return found
//                
//                 if(loginRecruiter.isPresent()) {
//                	 recruiterID= loginRecruiter.get().getId();
//                	 
//                 }
//		 }
//		return recruiterID;
//		
//	}
//	
//	public void delete(Long id) {
//		recruitRepo.deleteById(id);
//	}
//	
//
//}
