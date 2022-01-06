package com.inisirion.ibm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SD_Kanal")
public class SD_Kanal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "bezeichnung", nullable=false)
	private String bezeichnung;
	
	
	public SD_Kanal() {
		
	}


	public SD_Kanal(long id, String bezeichnung) {
		this.id = id;
		this.bezeichnung = bezeichnung;
	}		
	
	

}
