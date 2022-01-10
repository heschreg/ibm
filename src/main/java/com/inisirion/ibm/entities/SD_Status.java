package com.inisirion.ibm.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SD_Status")
public class SD_Status  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "bezeichnung", nullable=false)
	private String bezeichnung;
	
	
	public SD_Status() {		
	}

	public SD_Status(String bezeichnung) {
		super();
		this.bezeichnung = bezeichnung;
	}

	public SD_Status(long id, String bezeichnung) {
		this.id = id;
		this.bezeichnung = bezeichnung;
	}

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
