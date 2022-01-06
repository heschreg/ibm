package com.inisirion.ibm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inisirion.ibm.entities.Kanal;
import com.inisirion.ibm.entities.Stellenangebot;

public interface KanalRepository   extends JpaRepository<Kanal, Long>{

}
