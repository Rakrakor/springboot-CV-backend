package com.samrak.CV.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samrak.CV.entities.Skills;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Long> {

}
