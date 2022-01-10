package com.inisirion.ibm.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Stellenangebot")
public class Stellenangebot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "bezeichnung")
	private String bezeichnung;

	@Column(name = "beginn")
	private Date beginn;

	@Column(name = "ende")
	private Date ende;
		
	@Column(name = "notizen")
	private String notizen;
	
	@OneToOne
	@JoinColumn (name = "sd_status_id", nullable = false)
	private SD_Status sd_status;

	// Der eine Kanal, der zur Einstellung geführt hat
	@OneToOne
	@JoinColumn (name = "sd_kanal_id", nullable = false)
	private SD_Kanal sd_kanal;

	// Kanäle auf denen die Stellenanzeige geschalten wurde
	// Eine Stellenanzeige wird auf mehreren Kanälen geschalten
	// Ein Kanal wird in mehreren Stellenanzeigen verwendet ==> ManyToMany-Beziehung
	
	@ManyToMany (fetch = FetchType.EAGER)
	@JoinTable  (name = "stellenangebot_kanal",
			joinColumns = {@JoinColumn (name = "stellenangebot_id", referencedColumnName = "id", nullable = false, updatable = false)},
			inverseJoinColumns = {@JoinColumn (name = "sd_kanal_id",   referencedColumnName = "id", nullable = false, updatable = false)}					
	)
	private List <SD_Kanal> kanaele  = new ArrayList<>();
	
	
	public Stellenangebot() {}	
	
	
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

	
	public Date getBeginn() {
		return beginn;
	}

	public void setBeginn(Date beginn) {
		this.beginn = beginn;
	}

	
	public Date getEnde() {
		return ende;
	}

	public void setEnde(Date ende) {
		this.ende = ende;
	}

	
	public String getNotizen() {
		return notizen;
	}

	public void setNotizen(String notizen) {
		this.notizen = notizen;
	}

	public SD_Status getSd_status() {
		return sd_status;
	}

	public void setSd_status(SD_Status sd_status) {
		this.sd_status = sd_status;
	}


	// Der Kanal, der zur Bewerbung bzw. zur Einstellung geführt hat
	public SD_Kanal getSd_kanal() {
		return sd_kanal;
	}

	public void setSd_kanal(SD_Kanal sd_kanal) {
		this.sd_kanal = sd_kanal;
	}

	// Die Liste aller zugeordneten Kanäle
	public List<SD_Kanal> getKanaele() {
		return kanaele;
	}

	public void setKanaele(List<SD_Kanal> kanaele) {
		this.kanaele = kanaele;
	}
	
	public void addKanal( SD_Kanal kanal) {
		this.kanaele.add(kanal);
	}
	
	public void removeKanal( SD_Kanal kanal) {
		this.kanaele.remove(kanal);
	}
}
