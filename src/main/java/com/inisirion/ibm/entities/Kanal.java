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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

// Ein Datensatz für jeden Kanal, der bei einem Stellenangebot genutzt wird

@Entity
@Table(name = "Kanal")
public class Kanal implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "bezeichnung", nullable=false)
	private String bezeichnung;

	// 1 Kanal kann in vielen Stellenangeboten verwendet werden
	// 1 Stellenangebot kann viele Kanäle verwenden
	@ManyToMany(mappedBy = "kanaele",fetch = FetchType.LAZY)
	private List <Stellenangebot>  stellenangebote = new ArrayList <>();

	/*
	// Zusätzlich speichere ich nicht nur den Text des Kanals, sondern auch die Id aus der Stammdatentabelle sd_kanal
	@Column(name = "id_sd_kanal", nullable=false)
	private long id_sd_kanal;
	*/
	
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	/*
	public long getId_sd_kanal() {
		return id_sd_kanal;
	}


	public void setId_sd_kanal(long id_sd_kanal) {
		this.id_sd_kanal = id_sd_kanal;
	}
	*/


	public String getBezeichnung() {
		return bezeichnung;
	}


	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}



	public List<Stellenangebot> getStellenangebote() {
		return stellenangebote;
	}


	public void setStellenangebote(List<Stellenangebot> stellenangebote) {
		this.stellenangebote = stellenangebote;
	}


	public Kanal() {	}		
}	

