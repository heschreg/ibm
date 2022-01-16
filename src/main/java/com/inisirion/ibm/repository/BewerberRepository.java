package com.inisirion.ibm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inisirion.ibm.entities.Bewerber;
import com.inisirion.ibm.entities.SD_Status;

public interface BewerberRepository  extends JpaRepository<Bewerber, Long> {
	
	List<Bewerber> findByidstellenangebotOrderByNachname(long idstellenangebot);
	List<Bewerber> findByidstellenangebotOrderById(long idstellenangebot);
	// List<Bewerber> findByNachname(String  name);
	// List<Bewerber> findByNotizen(String  name);
	// List<Bewerber> findByidxxx(long id);
	

}
