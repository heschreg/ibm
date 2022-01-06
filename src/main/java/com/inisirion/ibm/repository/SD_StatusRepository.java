package com.inisirion.ibm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inisirion.ibm.entities.SD_Status;
import com.inisirion.ibm.entities.Stellenangebot;

public interface SD_StatusRepository  extends JpaRepository<SD_Status, Long> {

}
