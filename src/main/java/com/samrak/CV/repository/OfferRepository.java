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

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>{

	void save(Optional<Offer> offer);
	
	@Query(value="SELECT * FROM Offer WHERE user_id = :id", nativeQuery = true)
	List<Offer> findByUserID(@Param("id") Long id);
}
