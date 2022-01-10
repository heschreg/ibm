package com.inisirion.ibm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inisirion.ibm.entities.Pdf_Stellenangebot;

public interface Pdf_StellenangebotRepository extends JpaRepository<Pdf_Stellenangebot, Long> {
	Optional<Pdf_Stellenangebot> findByName(String name);

}
