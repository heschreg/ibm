package com.inisirion.ibm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inisirion.ibm.entities.Kommunikation;
import com.inisirion.ibm.entities.SD_Status;

public interface KommunikationRepository  extends JpaRepository<Kommunikation, Long> {

}
