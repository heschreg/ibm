package com.inisirion.ibm.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.inisirion.ibm.entities.Stellenangebot;

@Repository
@Transactional
public interface StellenangebotRepository extends JpaRepository<Stellenangebot, Long> {
	
	
}
