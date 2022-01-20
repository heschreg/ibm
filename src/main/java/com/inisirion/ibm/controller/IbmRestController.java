package com.inisirion.ibm.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.inisirion.ibm.entities.Anlage;
import com.inisirion.ibm.entities.Bewerber;
import com.inisirion.ibm.entities.Kommunikation;
import com.inisirion.ibm.entities.Pdf_Stellenangebot;
import com.inisirion.ibm.entities.SD_Anlage;
import com.inisirion.ibm.entities.SD_Kanal;
import com.inisirion.ibm.entities.SD_Kommunikation;
import com.inisirion.ibm.entities.SD_Status;
import com.inisirion.ibm.entities.Stellenangebot;
import com.inisirion.ibm.repository.AnlageRepository;
import com.inisirion.ibm.repository.BewerberRepository;
import com.inisirion.ibm.repository.KommunikationRepository;
import com.inisirion.ibm.repository.Pdf_StellenangebotRepository;
import com.inisirion.ibm.repository.SD_AnlageRepository;
import com.inisirion.ibm.repository.SD_KanalRepository;
import com.inisirion.ibm.repository.SD_KommunikationRepository;
import com.inisirion.ibm.repository.SD_StatusRepository;
import com.inisirion.ibm.repository.StellenangebotRepository;


@RestController
@RequestMapping("/ibm")
public class IbmRestController {

	@Autowired
	private  BewerberRepository bewerberRepository;

	@Autowired
	private  AnlageRepository anlageRepository;
	
	
	@Autowired
	private  StellenangebotRepository stellenangebotRepository;

	@Autowired
	private  Pdf_StellenangebotRepository pdf_StellenangebotRepository;

	/*
	@Autowired
	private  KommunikationRepository kommunikationRepository;
	*/
	
	
	@Autowired
	private  SD_StatusRepository sd_StatusRepository;

	@Autowired
	private  SD_KanalRepository sd_KanalRepository;
	
	@Autowired
	private  SD_KommunikationRepository sd_KommunikationRepository;

	@Autowired
	private  SD_AnlageRepository sd_AnlageRepository;

	
	// ===  Bewerber ====================================================

	// alle Bewerber zu einem Stellenangebot
	@GetMapping("/bewerber/{id}")
	public List<Bewerber> getBewerberByIdStellenangebot(@PathVariable long id) {
		
		// List<Bewerber> list_bewerber = bewerberRepository.findByidstellenangebotOrderByNachname(id);
		List<Bewerber> list_bewerber = bewerberRepository.findByidstellenangebotOrderById(id);
		
		/*
		List<Kommunikation> kommunikationen = list_bewerber.get(0).getKommunikationen();
				
		String anmerkungen                = kommunikationen.get(0).getAnmerkungen(); // Achtung: ist immer nur eine Anmerkung
		Bewerber bewerber                 = kommunikationen.get(0).getBewerber();
		long id_kommunikation             = kommunikationen.get(0).getId();
		SD_Kommunikation sd_kommunikation = kommunikationen.get(0).getSd_kommunikation();
		Date zeitpunkt                    = kommunikationen.get(0).getZeitpunkt();
		*/
				
		return list_bewerber;
	}

	// INSERT eines neuen Datensatzes
	@PostMapping(path= "/bewerber", consumes = "application/json", produces = "application/json")
	@Transactional
	public ResponseEntity<Bewerber> createBewerber(@RequestBody Bewerber bewerberNew) {

		ResponseEntity<Bewerber> bew;
				
		// Instantiieren eines neuen Datensatzes
		Bewerber bewerber = new Bewerber();

		///////////////////////////////////////////////////////
		// Setzen der Properties des neu anzulegenden Bewerbers
		///////////////////////////////////////////////////////
		
		bewerber.setIdstellenangebot(bewerberNew.getIdstellenangebot());
		bewerber.setNachname(bewerberNew.getNachname());
		bewerber.setVorname(bewerberNew.getVorname());
		bewerber.setAnrede(bewerberNew.getAnrede());
		bewerber.setTitel(bewerberNew.getTitel());
		bewerber.setPlz(bewerberNew.getPlz());
		bewerber.setOrt(bewerberNew.getOrt());
		bewerber.setStrasse(bewerberNew.getStrasse());
		bewerber.setHausnummer(bewerberNew.getHausnummer());
		bewerber.setEmail(bewerberNew.getEmail());
		bewerber.setNotizen(bewerberNew.getNotizen());
		bewerber.setSkills(bewerberNew.getSkills());
		Bewerber BewerberInserted = bewerberRepository.save(bewerber);
		
		List<Kommunikation> kommunikationenNew = bewerberNew.getKommunikationen();		
		if (kommunikationenNew.size() > 0 ) {
			
			// Inserten aller bei der Anlage eines Bewerbers gleich mit angelegten Kommunikationshistorien
			
			BewerberInserted =  bewerberRepository.findById( BewerberInserted.getId()).get();

			int anzEintraege = bewerberNew.getKommunikationen().size();
			for (int i = 0; i < anzEintraege; i++)  {
				BewerberInserted.addKommunikation(bewerberNew.getKommunikationen().get(i));
			};
			
			Bewerber BewerberInsertedMitAnlagen = bewerberRepository.save(BewerberInserted);
			
			bew = ResponseEntity.ok(BewerberInsertedMitAnlagen);
		} else {
			bew = ResponseEntity.ok(BewerberInserted);			
		}
		
		return bew;
	}


	// UPDATE eines Bewerber-Datensatzes und Inserten/Updaten/Deleten von Childds (Kommunikationen
	@PutMapping(path= "/bewerber/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Bewerber> updateBewerber(@PathVariable Long id, @RequestBody Bewerber bewerberChanged){
		
		Optional<Bewerber> bewerber_opt = bewerberRepository.findById(id);
		Bewerber bewerber = bewerber_opt.get();
		
		bewerber.setIdstellenangebot(bewerberChanged.getIdstellenangebot());
		bewerber.setNachname(bewerberChanged.getNachname());
		bewerber.setVorname(bewerberChanged.getVorname());
		bewerber.setAnrede(bewerberChanged.getAnrede());
		bewerber.setTitel(bewerberChanged.getTitel());
		bewerber.setPlz(bewerberChanged.getPlz());
		bewerber.setOrt(bewerberChanged.getOrt());
		bewerber.setStrasse(bewerberChanged.getStrasse());
		bewerber.setHausnummer(bewerberChanged.getHausnummer());
		bewerber.setEmail(bewerberChanged.getEmail());
		bewerber.setNotizen(bewerberChanged.getNotizen());
		bewerber.setSkills(bewerberChanged.getSkills());
		
		// alle existierenden Historien in "bewerber" wegnehmen
		int anzEintraege = bewerber.getKommunikationen().size();
		for (int i = 0; i < anzEintraege; i++)  {
			Kommunikation komm = bewerber.getKommunikationen().get(0);
			bewerber.removeKommunikation(komm);			
		};
		
		// Ergänzen aller existierenden Kommunikationens aus "bewerberChanged"
		bewerberChanged.getKommunikationen().forEach( (k) -> {
			bewerber.addKommunikation(k);			
		});
						
		Bewerber updatedBewerber = bewerberRepository.save(bewerber);		
		ResponseEntity<Bewerber> bew = ResponseEntity.ok(updatedBewerber);
		
		return bew;
		
		/*
		 * funktionerenden Bsp. zum Ergänzen von Kommunikationshistorien zu einem bestehenden Bewerber
		 * 
		 * Der upzudatende Bewerber steht im Parameter "bewerberChanged"

			// 1. Kommunikationshistorie
			Kommunikation komm = new Kommunikation();
			komm.setSd_kommunikation(sd_KommunikationRepository.findById(1L).get());		
			komm.setAnmerkungen("Eingang wurde bestätigt?");
			komm.setBewerber(bewerberChanged);		
			komm.setZeitpunkt(new Date());
			listKommunikation.add(komm);
	
			// 2. Kommunikationshistorie
			komm = new Kommunikation();
			komm.setSd_kommunikation(sd_KommunikationRepository.findById(2L).get());		
			komm.setAnmerkungen("Rückfrage muss noch geklärt werden?");
			komm.setBewerber(bewerberChanged);		
			komm.setZeitpunkt(new Date());
			listKommunikation.add(komm);

			bewerber.setKommunikationen(listKommunikation);
		 
			Bewerber updatedBewerber = bewerberRepository.save(bewerber);		
			ResponseEntity<Bewerber> bew = ResponseEntity.ok(updatedBewerber);		
			return bew;
		 */		
	}
	
	
	// ===  Kommunikation (wird wohl gar nicht benötigt) ========================================
	
	@GetMapping("/kommunikation/{id_bewerber}")
	public List<Kommunikation> getKommunikationByIdBewerber(@PathVariable long id_bewerber) {
		
		// List<Kommunikation> list_komunikation = kommunikationRepository.findBybewerberId(id_bewerber);
		List<Kommunikation> list_komunikation = null;
		return list_komunikation;
	}
	
	
	// ===  Stellenangebote ====================================================
	
	// alle Stellenangebote
	@GetMapping("/stellenangebot")
	public List<Stellenangebot> getAllStellenangebote(){		
		List<Stellenangebot> list_stellenangebote = stellenangebotRepository.findAll();		
		return list_stellenangebote;
	}			
	

	// Stellenangebot by id
	@GetMapping("/stellenangebot/{id}")
	public ResponseEntity<Stellenangebot> getStellenangebotById(@PathVariable Long id) {
		
		Optional<Stellenangebot> stellenangebot_opt = stellenangebotRepository.findById(id);		
		Stellenangebot stellenangebot = stellenangebot_opt.get();
		
		/*
		String json = null;
		try {
			json = new ObjectMapper().writeValueAsString(stellenangebot);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println(json);
        */
		
		return ResponseEntity.ok(stellenangebot);
	}
	
	// UPDATE eines Stellenangebotes
	@PutMapping(path= "/stellenangebot/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Stellenangebot> updateStellenangebot(@PathVariable Long id, @RequestBody Stellenangebot stellenangebotDetails){
		
		Optional<Stellenangebot> stellenangebot_opt = stellenangebotRepository.findById(id);
		Stellenangebot stellenangebot = stellenangebot_opt.get();
		
		Date begDate = stellenangebotDetails.getBeginn();
		stellenangebot.setBeginn(begDate);
		
		stellenangebot.setBezeichnung(stellenangebotDetails.getBezeichnung());
		
		Date endDate = stellenangebotDetails.getEnde();
		stellenangebot.setEnde(endDate);
		
		stellenangebot.setKanaele(stellenangebotDetails.getKanaele());
		stellenangebot.setNotizen(stellenangebotDetails.getNotizen());
		stellenangebot.setSd_kanal(stellenangebotDetails.getSd_kanal());
		stellenangebot.setSd_status(stellenangebotDetails.getSd_status());

		Pdf_Stellenangebot pdf_Stellenangebot = stellenangebotDetails.getPdf_stellenangebot();
		// stellenangebot.setPdf_stellenangebot(pdf_Stellenangebot);
		
		Stellenangebot updatedStellenangebot = stellenangebotRepository.save(stellenangebot);		
		ResponseEntity<Stellenangebot> sa = ResponseEntity.ok(updatedStellenangebot);		
		return sa;
	}
	
	
	// INSERT eines neuen Stellenangebotes
	@PostMapping(path= "/stellenangebot", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Stellenangebot> createStellenangebot(@RequestBody Stellenangebot stellenangebotDetails) {

		// Instantiieren eines neuen Datensatzes
		Stellenangebot stellenangebot = new Stellenangebot();

		////////////////////////////////////////////////////////////
		// Setzen der Properties im neu anzulegenden Stellenangebot
		////////////////////////////////////////////////////////////
		
		Date begDate = stellenangebotDetails.getBeginn();
		stellenangebot.setBeginn(begDate);
		
		stellenangebot.setBezeichnung(stellenangebotDetails.getBezeichnung());
		
		Date endDate = stellenangebotDetails.getEnde();
		stellenangebot.setEnde(endDate);
		
		stellenangebot.setKanaele(stellenangebotDetails.getKanaele());
		stellenangebot.setNotizen(stellenangebotDetails.getNotizen());
		stellenangebot.setSd_kanal(stellenangebotDetails.getSd_kanal());
		stellenangebot.setSd_status(stellenangebotDetails.getSd_status());
	
		Pdf_Stellenangebot pdf_sa = new Pdf_Stellenangebot();
		pdf_StellenangebotRepository.save(pdf_sa);		
		stellenangebot.setPdf_stellenangebot(pdf_sa);
		
		Stellenangebot newStellenangebot = stellenangebotRepository.save(stellenangebot);
		
		ResponseEntity<Stellenangebot> sa = ResponseEntity.ok(newStellenangebot);
		
		return sa;
	}
	
	/************************************************************************************************
	 * 
	 *  POST: UPLOAD Pdf mit Verknüpfung zur zentralen Entität  "bewerber"
	 * 
	 *  1 Path-Parameter:
	 *  =================
	 *  Long id_bewerber : Verweis auf die Bewerberentität, der die Anlage hinzuzufügen ist
	 *  
	 *  3 Request-Parameter:
	 *  =================
	 *  "id_sd_anlage": id der zugehörigen Kategorie (Lebenslauf, Arbeitszeugnis Anschreiben ...) 
	 *  "anmerkung":    individuell im Frontend eingegebene Anmerkung
	 *  "pdfFile"       Objekt vom Typ "MultipartFile" (Dateiname, Dateityp, content in Bytes
	 *  
	 * 
	 *  Aufruf im Frontend:
	 *  ===================
	 * 
	 * 	const formData = new FormData();
     *	formData.append("id_sd_anlage", anlage.sd_anlage.id.toString());
     *	formData.append("anmerkung", anlage.anmerkung);
     *	formData.append('pdfFile', pdfFile, pdfFile.name);
     *	return this.httpClient.post(`${this.baseURL}/upldpdfanlage/${id}`, formData);
     * 
	 *****************************************************************************************/
	
	@PostMapping(path = "/upldpdfanlage/{id_bewerber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Bewerber> uploadPdfAnlage(
			@PathVariable Long id_bewerber, 
			@RequestParam("id_sd_anlage") String sIdAnlage, 
			@RequestParam("anmerkung")   String anmerkung, 
			@RequestParam("pdfFile") MultipartFile pdfDatei
		) throws IOException {
		
		// Holen der Bewerber - Entität anhand der übergebenen id_bewerber
		Bewerber bewerber = bewerberRepository.findById(id_bewerber).get();
		
		// Verarbeiten der übergebenen PDF-Datei
		String pdfFilename = StringUtils.cleanPath(pdfDatei.getOriginalFilename());
		String pdfContentType = pdfDatei.getContentType();
		byte[] pdfBytes = pdfDatei.getBytes();
		
		// Pdf_Stellenangebot pdfStellenangebot = new Pdf_Stellenangebot(pdfFilename, pdfContentType, pdfDatei.getBytes());		
		List<Anlage> list_anlage = bewerber.getAnlagen();
			
		Anlage anlage = new Anlage();
		
		anlage.setBewerber(bewerber);
		
		long id_sd_anlage = Long.parseLong(sIdAnlage.trim());
		SD_Anlage sdAnlage = sd_AnlageRepository.findById(id_sd_anlage).get();				
		anlage.setSd_anlage(sdAnlage);

		anlage.setAnmerkung(anmerkung);

		anlage.setName(pdfFilename);
		anlage.setType(pdfContentType);
		anlage.setBinData(pdfBytes);

		list_anlage.add(anlage);
		
		bewerber.setAnlagen(list_anlage);
				
		Bewerber updatedBewerber = bewerberRepository.save(bewerber);
		
		ResponseEntity<Bewerber> bew = ResponseEntity.ok(updatedBewerber);
		
		return bew;		
	}

	/************************************************************************************************
	 * 
	 *  GET: Vorbereiten eines Bytestreams, der von windows.Open(url) im FE benötigt wird
	 * 
	 *  Path-Parameter:
	 *  =================
	 *  Long id_anlagw: Verweis auf den Datensatz in der Entität "anlage", der  die byte-Daten enthält
	 *  
	 * 
	 *  Aufruf im Frontend:
	 *  ===================
	 * 
     *	return this.httpClient.get(`${this.baseURL}/file/${id}`);
     * 
	 *****************************************************************************************/
	
	@GetMapping("/file/{id}")
	public ResponseEntity<byte[]> getBinaryFile(@PathVariable Long id) {
		  		
		Optional<Anlage> anlageOpt = anlageRepository.findById(id);
		Anlage anlage = anlageOpt.get();
	    
	    String fname = anlage.getName();
	    byte[] datenbytes = anlage.getBinData(); // binäre Daten, die das pdf-Dokument repräsentieren

	    ResponseEntity<byte[]> bbytes =  ResponseEntity
	    		.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fname + "\"")
	            .body(datenbytes);
	    
	    return bbytes;	   
	}	

	// =======================================================================

	/***************************************************************************************
	 * 
	 * POST: UPLOAD Pdf mit Verknüpfung zur zentralen Entität  "stellenangebot" 
	 * Parameter 1: Id des betroffenen Stellenangebots 
	 * Parameter 2: Multifile-Parameter bgzl. der upzuloadenden pdf-Datei 
	 * 
	 * Aufruf im Frontend:
	 * ===================
	 * 
	 * const uploadData = new FormData();
     * uploadData.append('pdfFile', pdfFile, pdfFile.name);
     * 
	 * @PostMapping(path = "/uploadpdfsa/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
     * return this.httpClient.post(`localhost:8080/ibm/uploadpdfsa/${id}`, uploadData);
     * 
	 *****************************************************************************************/
	
	@PostMapping(path = "/uploadpdfsa/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Stellenangebot> uploadPdfSa(@PathVariable Long id, @RequestParam("pdfFile") MultipartFile pdfDatei) throws IOException {
		
		// Holen des betroffenen Stellenangebots anhand der übergebenen id
		Stellenangebot stellenangebot = stellenangebotRepository.findById(id).get();
		
		// Verarbeiten der übergebenen PDF-Datei
		String pdfFilename = StringUtils.cleanPath(pdfDatei.getOriginalFilename());
		String pdfContentType = pdfDatei.getContentType();
		byte[] pdfBytes = pdfDatei.getBytes();
		// Pdf_Stellenangebot pdfStellenangebot = new Pdf_Stellenangebot(pdfFilename, pdfContentType, pdfDatei.getBytes());		
		Pdf_Stellenangebot pdfStellenangebot = stellenangebot.getPdf_stellenangebot();
		
		pdfStellenangebot.setName(pdfFilename);
		pdfStellenangebot.setType(pdfContentType);
		pdfStellenangebot.setBinData(pdfBytes);		
		pdf_StellenangebotRepository.save(pdfStellenangebot);
		
		// Hinterlegen der pdf-Daten in der Property, die den Eintrag in der Tabelle "pdf_stellenangebot" beinhaltet +  Updaten
		stellenangebot.setPdf_stellenangebot(pdfStellenangebot);
		Stellenangebot updatedStellenangebot = stellenangebotRepository.save(stellenangebot);
		
		ResponseEntity<Stellenangebot> sa = ResponseEntity.ok(updatedStellenangebot);		
		return sa;		
	}


	/************************************************************************************************************/
	/** DOWNLOAD Pdf-Bytes für windows.open ( BinaryDate ) über die Id in der Tabelle "ibm.pdf_stellenangebot" **/
	/************************************************************************************************************/
	@GetMapping(path = { "/dnldpdfbyid/{id}" })
	public ResponseEntity<byte[]> getBinaryFileByName(@PathVariable("id") Long id) {
		  
		// Bsp. für einen Aufruf: http://localhost:8081/binary/file/anzeige.pdf
			 
		// Man holt sich hier zunächst ganz normal einen Datensatz aus der DB-Tabelle "ibm.pdf_stellenangebot".
		
		// Interessant ist der Rückgabewert "bbytes, der genau so benötigt wird, damit woindows.open() im
		// Frontend mit diesen Daten etwas anfangen kann, bzw. automatischden Downlaod der Pdf-Datei anstößt
		
		Optional<Pdf_Stellenangebot> pdfFile = pdf_StellenangebotRepository.findById(id);
		Pdf_Stellenangebot fileEntry = pdfFile.get();
	    
	    String fname = fileEntry.getName();
	    byte[] bytes = fileEntry.getBinData(); // Das sind die binären Daten, die das pdf-Dokument enthalten

	    ResponseEntity<byte[]> bbytes =  ResponseEntity
	    		.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fname + "\"")
	            .body(bytes);
	    
	    return bbytes;	   
	}	
	
	/************************************************************************************************************/
	/** DOWNLOAD Pdf-Bytes für windows.open ( BinaryDate ) über die Id in der Tabelle "ibm.pdf_stellenangebot" **/
	/************************************************************************************************************/
	@GetMapping(path = { "/downloadpdf2/{fileName2}" })
	public ResponseEntity<byte[]> getBinaryFileByName(@PathVariable("fileName2") String dateiName) {
		  
		// Bsp. für einen Aufruf: http://localhost:8081/binary/file/anzeige.pdf
			 
		// Man holt sich hier zunächst ganz normal einen Datensatz aus der DB-Tabelle "ibm.pdf_stellenangebot".
		
		// Interessant ist der Rückgabewert "bbytes, der genau so benötigt wird, damit woindows.open() im
		// Frontend mit diesen Daten etwas anfangen kann, bzw. automatischden Downlaod der Pdf-Datei anstößt
		
		Optional<Pdf_Stellenangebot> pdfFile = pdf_StellenangebotRepository.findByName(dateiName);
		Pdf_Stellenangebot fileEntry = pdfFile.get();
	    
	    String fname = fileEntry.getName();
	    byte[] bytes = fileEntry.getBinData(); // Das sind die binären Daten, die das pdf-Dokument enthalten

	    ResponseEntity<byte[]> bbytes =  ResponseEntity
	    		.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fname + "\"")
	            .body(bytes);
	    
	    return bbytes;	   
	}	

	// ===  Stammdaten ====================================================
	
	// Holen aller Stammdaten für Kommunikation 
	@GetMapping("/sd_kommunikation")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<SD_Kommunikation> getAllKommunikation(){
		List<SD_Kommunikation> list_kommunikation = sd_KommunikationRepository.findAll();		
		return list_kommunikation;
	}

	// Holen aller Stammdaten für Anlage
	@GetMapping("/sd_anlage")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<SD_Anlage> getAllAnlage(){
		List<SD_Anlage> list_anlage = sd_AnlageRepository.findAll();		
		return list_anlage;
	}
	
	// Holen aller Stammdaten für Status 
	@GetMapping("/sd_status")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<SD_Status> getAllStatus(){
		List<SD_Status> list_status = sd_StatusRepository.findAll();		
		return list_status;
	}

	// Holen aller Stammdaten für Kanäle 
	@GetMapping("/sd_kanaele")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<SD_Kanal> getAllKanaele(){
		List<SD_Kanal> list_kanaele = sd_KanalRepository.findAll();	
		return list_kanaele;
	}		
	
	
	/********************************************************************************************
	 * Die folgenden Endpoints bzl. Up und Down von pdf-Dateien werden derzeit nicht verwendet
	 ********************************************************************************************/
	
	
	/**********************/
	/** POST: UPLOAD Pdf **/
	/**********************/
	
	@PostMapping(path = "/uploadpdf", produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Pdf_Stellenangebot> uploadPdf(@RequestParam("pdfFile") MultipartFile pdfDatei) throws IOException {

		String pdfFilename = StringUtils.cleanPath(pdfDatei.getOriginalFilename());
		Pdf_Stellenangebot pdfStellenangebot = new Pdf_Stellenangebot(pdfFilename, pdfDatei.getContentType(), pdfDatei.getBytes());
		
		pdf_StellenangebotRepository.save(pdfStellenangebot);

		ResponseEntity<Pdf_Stellenangebot> sa = ResponseEntity.ok(pdfStellenangebot);
		
		return sa;		
	}
	
	/************************************************************************************************************/
	/** DOWNLOAD Pdf-Bytes für windows.open ( BinaryDate ) über die Id in der Tabelle "ibm.pdf_stellenangebot" **/
	/************************************************************************************************************/
	@GetMapping("/byfileid/{id}")
	public ResponseEntity<byte[]> getBinaryFileById(@PathVariable Long id) {
		  
		// Bsp. für einen Aufruf: http://localhost:8081/binary/file/428ecca1-db22-4a12-bcdc-277b9a9c0925
			 
		// Man holt sich hier zunächst ganz normal einen Datensatz aus der DB-Tabelle "ibm.pdf_stellenangebot".
		
		// Interessant ist der Rückgabewert "bbytes, der genau so benötigt wird, damit woindows.open() im
		// Frontend mit diesen Daten etwas anfangen kann, bzw. automatischden Downlaod der Pdf-Datei anstößt
		
		Optional<Pdf_Stellenangebot> pdfFile = pdf_StellenangebotRepository.findById(id);
		Pdf_Stellenangebot fileEntry = pdfFile.get();
	    
	    String fname = fileEntry.getName();
	    byte[] bytes = fileEntry.getBinData(); // Das sind die binären Daten, die das pdf-Dokument enthalten

	    ResponseEntity<byte[]> bbytes =  ResponseEntity
	    		.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fname + "\"")
	            .body(bytes);
	    
	    return bbytes;	   
	}	
	
	
	/*******************/
	/** DOWNLOAD Pdf  **/
	/*******************/
	@GetMapping(path = { "/downloadpdf/{fileName}" })
	public Pdf_Stellenangebot getPdf(@PathVariable("fileName") String dateiName) throws IOException {

		// neuere optimiere kürzere Version
		Optional<Pdf_Stellenangebot> retrievedPdf = pdf_StellenangebotRepository.findByName(dateiName);
		Pdf_Stellenangebot pdf = retrievedPdf.get();
		
	    return pdf;
	}	

	
	
}
