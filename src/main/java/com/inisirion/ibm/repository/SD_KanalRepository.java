package com.inisirion.ibm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inisirion.ibm.entities.SD_Kanal;
import com.inisirion.ibm.entities.Stellenangebot;

public interface SD_KanalRepository  extends JpaRepository<SD_Kanal, Long> {

}
