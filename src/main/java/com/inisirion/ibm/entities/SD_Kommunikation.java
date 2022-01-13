package com.inisirion.ibm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SD_Kommunikation")
public class SD_Kommunikation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "bezeichnung", nullable=false)
	private String bezeichnung;

	// === Constructors =====================

	public SD_Kommunikation() {	}
	
	
	
	public SD_Kommunikation(String bezeichnung) {
		super();
		this.bezeichnung = bezeichnung;
	}

	// === Getter/Setter: =====================

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

	public SD_Kommunikation(long id, String bezeichnung) {
		super();
		this.id = id;
		this.bezeichnung = bezeichnung;
	}	
	
	// =====================================================
	
	

}
