package com.inisirion.ibm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inisirion.ibm.entities.Anlage;

public interface AnlageRepository extends JpaRepository<Anlage, Long> {

	@Query( value = "SELECT * FROM anlage u WHERE u.id = :id and u.bewerber_id = :bewerber_id", nativeQuery = true)
	List<Anlage> findAllActiveAnlageNative(
		@Param("id") Long id, 
		@Param("bewerber_id") Long bewreber_id			
	);	
}
