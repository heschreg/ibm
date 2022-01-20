package com.inisirion.ibm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "anlage")
public class Anlage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "anmerkung")
	private String anmerkung;
	
		
	@Column(name = "name")
    private String name;

	// zB: pdf oder Word
    @Column(name = "type")
    private String type;

    // image bytes can have large lengths so we specify a value
    // which is more than the default length for binData column
    @Column(name = "binData", length = 10000000)
    private byte[] binData;
    
    @ManyToOne
	@JsonBackReference
	private Bewerber bewerber;
    
    @OneToOne
	@JoinColumn (name = "sd_anlage_id", nullable = false)
	private SD_Anlage sd_anlage;	

    
    // === Constructors =====================
    
	public Anlage() {
		super();
	}

	// === Getter/Setter =====================	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}
	
	public String getAnmerkung() {
		return anmerkung;
	}

	public void setAnmerkung(String anmerkung) {
		this.anmerkung = anmerkung;
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

	public Bewerber getBewerber() {
		return bewerber;
	}

	public void setBewerber(Bewerber bewerber) {
		this.bewerber = bewerber;
	}

	public SD_Anlage getSd_anlage() {
		return sd_anlage;
	}

	public void setSd_anlage(SD_Anlage sd_anlage) {
		this.sd_anlage = sd_anlage;
	}
		
	
	
}
