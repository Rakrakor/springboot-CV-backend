package com.samrak.CV.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.samrak.CV.entities.Response;

public interface ResponseRepository extends JpaRepository<Response, Long> {

	
	@Query(value="SELECT * FROM Response WHERE offer_id = :offerID", nativeQuery = true)
	List<Response> findResponseByOffer(@Param("offerID") Long offerID);
}
