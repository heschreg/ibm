package com.inisirion.ibm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inisirion.ibm.entities.Bewerber_Anlagen;
import com.inisirion.ibm.entities.SD_Status;

public interface Bewerber_AnlagenRepository extends JpaRepository <Bewerber_Anlagen, Long> {

}
