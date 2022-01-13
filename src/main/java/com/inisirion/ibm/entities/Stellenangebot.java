package com.inisirion.ibm.entities;

import java.io.Serializable;

import com.inisirion.ibm.serializer.StellenangebotDateSerializer;
import com.inisirion.ibm.serializer.StellenangebotDateDeserializer;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "Stellenangebot")
public class Stellenangebot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "bezeichnung")
	private String bezeichnung;

	@Column(name = "beginn")
    @JsonSerialize(using = StellenangebotDateSerializer.class)    	
    @JsonDeserialize(using = StellenangebotDateDeserializer.class)    	
	private Date beginn;

	@Column(name = "ende")
	@JsonSerialize(using = StellenangebotDateSerializer.class)    	
    @JsonDeserialize(using = StellenangebotDateDeserializer.class)    	
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

	// Falls ein Stellenagebot als PDF vorliegt, kann man dieses hier mit hinterlegen
	// In dieser Entität(Stellenangebot) wird dadurch ein Feld "pdf_stellenangebot_id", 
	// das mit dem Feld "id" in der Tabelle "Pdf_STellenangebot" verknüpft ist
	@OneToOne
	@JoinColumn (name = "pdf_stellenangebot_id", nullable = false)
	private Pdf_Stellenangebot pdf_stellenangebot;
	
	// === Constructors =====================
		
	public Stellenangebot() {}	

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


	// Handling des zugeoirdnetn pdf-Dokumentes
	public Pdf_Stellenangebot getPdf_stellenangebot() {
		return pdf_stellenangebot;
	}


	public void setPdf_stellenangebot(Pdf_Stellenangebot pdf_stellenangebot) {
		this.pdf_stellenangebot = pdf_stellenangebot;
	}
	
	
}
