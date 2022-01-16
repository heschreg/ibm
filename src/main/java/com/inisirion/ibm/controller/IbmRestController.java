package com.inisirion.ibm.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.inisirion.ibm.entities.Bewerber;
import com.inisirion.ibm.entities.Pdf_Stellenangebot;
import com.inisirion.ibm.entities.SD_Kanal;
import com.inisirion.ibm.entities.SD_Status;
import com.inisirion.ibm.entities.Stellenangebot;
import com.inisirion.ibm.repository.BewerberRepository;
import com.inisirion.ibm.repository.Pdf_StellenangebotRepository;
import com.inisirion.ibm.repository.SD_KanalRepository;
import com.inisirion.ibm.repository.SD_StatusRepository;
import com.inisirion.ibm.repository.StellenangebotRepository;


@RestController
@RequestMapping("/ibm")
public class IbmRestController {

	@Autowired
	private  BewerberRepository bewerberRepository;
	
	@Autowired
	private  StellenangebotRepository stellenangebotRepository;

	@Autowired
	private  Pdf_StellenangebotRepository pdf_StellenangebotRepository;

	@Autowired
	private  SD_StatusRepository sd_StatusRepository;

	@Autowired
	private  SD_KanalRepository sd_KanalRepository;
	
	
	// ===  Bewerber ====================================================

	// Bewerber zu einem Stellenangebot
	@GetMapping("/bewerber/{id}")
	public List<Bewerber> getBewerberByIdStellenangebot(@PathVariable long id) {
		
		// List<Bewerber> list_bewerber = bewerberRepository.findByidstellenangebotOrderByNachname(id);
		List<Bewerber> list_bewerber = bewerberRepository.findByidstellenangebotOrderById(id);
		return list_bewerber;
	}

	// INSERT eines neuen Datensatzes
	@PostMapping(path= "/bewerber", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Bewerber> createBewerber(@RequestBody Bewerber bewerberDetails) {

		// Instantiieren eines neuen Datensatzes
		Bewerber bewerber = new Bewerber();

		///////////////////////////////////////////////////////
		// Setzen der Properties im neu anzulegenden Datensatz
		///////////////////////////////////////////////////////
		bewerber.setIdstellenangebot(bewerberDetails.getIdstellenangebot());
		bewerber.setNachname(bewerberDetails.getNachname());
		bewerber.setVorname(bewerberDetails.getVorname());
		bewerber.setAnrede(bewerberDetails.getAnrede());
		bewerber.setTitel(bewerberDetails.getTitel());
		bewerber.setPlz(bewerberDetails.getPlz());
		bewerber.setOrt(bewerberDetails.getOrt());
		bewerber.setStrasse(bewerberDetails.getStrasse());
		bewerber.setHausnummer(bewerberDetails.getHausnummer());
		bewerber.setEmail(bewerberDetails.getEmail());
		bewerber.setNotizen(bewerberDetails.getNotizen());
		bewerber.setSkills(bewerberDetails.getSkills());
		bewerber.setKommunikationen(bewerberDetails.getKommunikationen());
				
		Bewerber newBewerber = bewerberRepository.save(bewerber);
		
		ResponseEntity<Bewerber> bew = ResponseEntity.ok(newBewerber);
		
		return bew;
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
	
	
	// INSERT eines neuen Datensatzes
	@PostMapping(path= "/stellenangebot", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Stellenangebot> createStellenangebot(@RequestBody Stellenangebot stellenangebotDetails) {

		// Instantiieren eines neuen Datensatzes
		Stellenangebot stellenangebot = new Stellenangebot();

		///////////////////////////////////////////////////////
		// Setzen der Properties im neu anzulegenden Datensatz
		///////////////////////////////////////////////////////
		
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
