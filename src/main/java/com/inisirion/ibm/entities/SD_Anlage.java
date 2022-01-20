package com.inisirion.ibm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SD_Anlage")
public class SD_Anlage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "bezeichnung", nullable=false)
	private String bezeichnung;

	
	// === Constructors =====================

	public SD_Anlage() {	}	
	
	public SD_Anlage(String bezeichnung) {
		super();
		this.bezeichnung = bezeichnung;
	}
	
	public SD_Anlage(long id, String bezeichnung) {
		super();
		this.id = id;
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

}
