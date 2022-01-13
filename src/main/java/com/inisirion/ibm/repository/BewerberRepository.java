package com.inisirion.ibm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inisirion.ibm.entities.Bewerber;
import com.inisirion.ibm.entities.SD_Status;

public interface BewerberRepository  extends JpaRepository<Bewerber, Long> {

}
