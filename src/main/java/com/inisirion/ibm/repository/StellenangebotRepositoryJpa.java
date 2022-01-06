package com.inisirion.ibm.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.inisirion.ibm.entities.Stellenangebot;

public class StellenangebotRepositoryJpa {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;
	
	public Stellenangebot findById (Long id) {
		
		Stellenangebot sa = em.find(Stellenangebot.class, id);
		
		return sa;
	}
	
	public void getAllStellenangebote () {
		
		TypedQuery<Stellenangebot> query = em.createQuery(" Select s From Stellenangebot s", Stellenangebot.class);
		List<Stellenangebot> resultList = query.getResultList();
		
	}
	
	public Stellenangebot save (Stellenangebot stellenangebot) {
		
		if (stellenangebot.getId() == 0) {
			em.persist(stellenangebot);			
		} else {
			em.merge(stellenangebot);			
			
		}
		
		return stellenangebot;
	}
	
	
}
	
