package com.inisirion.ibm.entities;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "pdf_stellenangebot")
public class Pdf_Stellenangebot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    // image bytes can have large lengths so we specify a value
    // which is more than the default length for binData column
    @Column(name = "binData", length = 10000000)
    private byte[] binData;
    
    // =============

	public Pdf_Stellenangebot() {
		super();
	}

	public Pdf_Stellenangebot(String name, String type, byte[] binData) {
		super();
		this.name = name;
		this.type = type;
		this.binData = binData;
	}
	
    // =============
   
	
	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public byte[] getBinData() {
		return binData;
	}

	public void setBinData(byte[] binData) {
		this.binData = binData;
	}

	
	@Override
	public String toString() {
		return "Pdf_Stellenangebot [id=" + id + ", name=" + name + ", type=" + type + ", binData="
				+ Arrays.toString(binData) + "]";
	}

   
}
