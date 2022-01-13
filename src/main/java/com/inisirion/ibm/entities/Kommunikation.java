package com.inisirion.ibm.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.inisirion.ibm.serializer.StellenangebotDateDeserializer;
import com.inisirion.ibm.serializer.StellenangebotDateSerializer;

@Entity
@Table(name = "kommunikation")
public class Kommunikation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "zeitpunkt")
    @JsonSerialize(using = StellenangebotDateSerializer.class)    	
    @JsonDeserialize(using = StellenangebotDateDeserializer.class)    	
	private Date zeitpunkt;
	
	@Column(name = "anmerkungen")
	private String anmerkungen;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Bewerber bewerber;

	@OneToOne
	@JoinColumn (name = "sd_kommunikation_id", nullable = false)
	private SD_Kommunikation sd_kommunikation;	

	// === Constructors: =====================
	
	public Kommunikation() {}	
	
	// === Getter/Setter: =====================
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getZeitpunkt() {
		return zeitpunkt;
	}

	public void setZeitpunkt(Date zeitpunkt) {
		this.zeitpunkt = zeitpunkt;
	}

	public String getAnmerkungen() {
		return anmerkungen;
	}

	public void setAnmerkungen(String anmerkungen) {
		this.anmerkungen = anmerkungen;
	}

	public Bewerber getBewerber() {
		return bewerber;
	}

	public void setBewerber(Bewerber bewerber) {
		this.bewerber = bewerber;
	}	

	
	
	}
