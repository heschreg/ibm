package com.inisirion.ibm.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bewerber")
public class Bewerber {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "idstellenangebot")
	private long idstellenangebot;
		
	@Column(name = "nachname")
	private String nachname;

	@Column(name = "vorname")
	private String vorname;

	@Column(name = "anrede")
	private String anrede;

	@Column(name = "titel")
	private String titel;

	@Column(name = "plz")
	private int plz;

	@Column(name = "ort")
	private String ort;
	
	@Column(name = "strasse")
	private String strasse;

	@Column(name = "hausnummer")
	private String hausnummer;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "notizen")
	private String notizen;
	
	@Column(name = "skills")
	private String skills;
	
	
	@OneToMany(
		    mappedBy = "bewerber",
		    cascade = CascadeType.ALL,
		    orphanRemoval = true
		)
	private List<Kommunikation> kommunikationen = new ArrayList<>();	

	// === Constructors =====================

	// === Getter/Setter: =====================

	
	public void addKommunikation(Kommunikation kommunikation) {
		kommunikationen.add(kommunikation);
		kommunikation.setBewerber(this);
	}


	public long getIdstellenangebot() {
		return idstellenangebot;
	}


	public void setIdstellenangebot(long idstellenangebot) {
		this.idstellenangebot = idstellenangebot;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getAnrede() {
		return anrede;
	}

	public void setAnrede(String anrede) {
		this.anrede = anrede;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getHausnummer() {
		return hausnummer;
	}

	public void setHausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNotizen() {
		return notizen;
	}

	public void setNotizen(String notizen) {
		this.notizen = notizen;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public List<Kommunikation> getKommunikationen() {
		return kommunikationen;
	}

	public void setKommunikationen(List<Kommunikation> kommunikationen) {
		this.kommunikationen = kommunikationen;
	}

	public void removeKommunikation(Kommunikation kommunikation) {
		kommunikationen.remove(kommunikation);
		kommunikation.setBewerber(null);
	}	
	

}
