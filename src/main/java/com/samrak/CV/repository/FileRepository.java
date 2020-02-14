package com.samrak.CV.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.samrak.CV.entities.File;

@Repository
public interface FileRepository extends JpaRepository<File,Long>{

//	@Modifying
//	@Query("delete from File f where f.id=:id")
//	void deleteMyFile(@Param("id") Long fileId);
}
