package com.inisirion.ibm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bewerber_anlagen")
public class Bewerber_Anlagen {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "id_bewerber", nullable=false)
	private long id_bewerber;
	
	@Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    // image bytes can have large lengths so we specify a value
    // which is more than the default length for binData column
    @Column(name = "binData", length = 10000000)
    private byte[] binData;

    
    // === Constructors =====================
    
	public Bewerber_Anlagen() {
		super();
	}

	public Bewerber_Anlagen(long id_bewerber, String name, String type, byte[] binData) {
		super();
		this.id_bewerber = id_bewerber;
		this.name = name;
		this.type = type;
		this.binData = binData;
	}

	// === Getter/Setter =====================
		
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId_bewerber() {
		return id_bewerber;
	}

	public void setId_bewerber(long id_bewerber) {
		this.id_bewerber = id_bewerber;
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

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getBinData() {
		return binData;
	}

	public void setBinData(byte[] binData) {
		this.binData = binData;
	}
	
}
