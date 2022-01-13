package com.inisirion.ibm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inisirion.ibm.entities.SD_Kommunikation;
import com.inisirion.ibm.entities.SD_Status;

public interface SD_KommunikationRepository extends JpaRepository<SD_Kommunikation, Long> {

}
