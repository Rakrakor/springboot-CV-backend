package com.samrak.CV.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.samrak.CV.entities.Offer;
import com.samrak.CV.entities.Users;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>{

	void save(Optional<Offer> offer);
	
	@Query(value="SELECT * FROM offer WHERE id=:id", nativeQuery = true)
	Optional<Offer> findById(@Param("id") Long id);
	
	
	@Query(value="SELECT * FROM offer WHERE user_id = :user", nativeQuery = true)
	List<Offer> findByUser(@Param("user") Users user);
	
	@Query(value="SELECT * FROM offer", nativeQuery = true)
	List<Offer> retrieveAll();


	

}
