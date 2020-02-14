package com.samrak.CV.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samrak.CV.entities.Me;

@Repository
public interface MeRepository extends JpaRepository<Me, Long> {

	//void save(Optional<Me> me);
}
