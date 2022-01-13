package com.inisirion.ibm.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SD_Kanal")
public class SD_Kanal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "bezeichnung", nullable=false)
	private String bezeichnung;
	
	// 1 Kanal kann in vielen Stellenangeboten verwendet werden
	// 1 Stellenangebot kann viele Kanäle verwenden
	@ManyToMany(mappedBy = "kanaele",fetch = FetchType.LAZY)
	private List <Stellenangebot>  stellenangebote = new ArrayList <>();

	// === Constructors =====================
	
	public SD_Kanal() {}

	public SD_Kanal(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	public SD_Kanal(long id, String bezeichnung) {
		super();
		this.bezeichnung = bezeichnung;
	}

	// === Getter/Setter =====================
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	/*
	public List<Stellenangebot> getStellenangebote() {
		return stellenangebote;
	}

	public void setStellenangebote(List<Stellenangebot> stellenangebote) {
		this.stellenangebote = stellenangebote;
	}
	*/		
	
}
