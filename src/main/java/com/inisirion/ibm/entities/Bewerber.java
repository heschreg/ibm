package com.inisirion.ibm.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "bewerber")
// @JsonIgnoreProperties(value={"kommunikationen"})  
// @JsonIgnoreProperties(value={"anlagen"})  
public class Bewerber implements Serializable {
	
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
		    fetch = FetchType.EAGER, 
		    orphanRemoval = true
		)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonManagedReference
	private List<Kommunikation> kommunikationen = new ArrayList<>();	

	//fetch = FetchType.EAGER, 
	@OneToMany(
		    mappedBy = "bewerber",
		    cascade = CascadeType.ALL,
		    orphanRemoval = true		    
		)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonManagedReference
	private List<Anlage> anlagen = new ArrayList<>();	
	
	
	// === Constructors =====================

	public Bewerber() {	}

	
	// === Getter/Setter: =====================


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

	public List<Anlage> getAnlagen() {
		return anlagen;
	}


	public void setAnlagen(List<Anlage> anlagen) {
		this.anlagen = anlagen;
	}

	
	// Hilfsmethoden bzgl. der Many-Relation zur Entität "kommunikation"
	
	public void removeKommunikation(Kommunikation kommunikation) {
		kommunikationen.remove(kommunikation);
		kommunikation.setBewerber(null);
	}	

	public void addKommunikation(Kommunikation kommunikation) {
		kommunikationen.add(kommunikation);
		kommunikation.setBewerber(this);
	}

	// Hilfsmethoden bzgl. der Many-Relation zur Entität "anlage"

	public void removeAnlage(Anlage anlage) {
		anlagen.remove(anlage);
		anlage.setBewerber(null);
	}	

	public void addAnlage(Anlage anlage) {
		anlagen.add(anlage);
		anlage.setBewerber(this);
	}
	
}
