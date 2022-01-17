package com.inisirion.ibm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inisirion.ibm.entities.Bewerber;
import com.inisirion.ibm.entities.Kommunikation;
import com.inisirion.ibm.entities.SD_Status;

public interface KommunikationRepository  extends JpaRepository<Kommunikation, Long> {
	
	// Holen der Kommunikationshistorie eines bestimmten Bewerbers
	List<Kommunikation> findBybewerberId(long bewerberid);
	// List<Kommunikation> findBybewerberIdOrderByNachname(long bewerberid);
	

}
