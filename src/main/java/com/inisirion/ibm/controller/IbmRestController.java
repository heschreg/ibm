package com.inisirion.ibm.controller;

import java.io.IOException;
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

import com.inisirion.ibm.entities.Pdf_Stellenangebot;
import com.inisirion.ibm.entities.SD_Kanal;
import com.inisirion.ibm.entities.SD_Status;
import com.inisirion.ibm.entities.Stellenangebot;
import com.inisirion.ibm.repository.Pdf_StellenangebotRepository;
import com.inisirion.ibm.repository.SD_KanalRepository;
import com.inisirion.ibm.repository.SD_StatusRepository;
import com.inisirion.ibm.repository.StellenangebotRepository;


@RestController
@RequestMapping("/ibm")
public class IbmRestController {
	
	@Autowired
	private  StellenangebotRepository stellenangebotRepository;

	@Autowired
	private  SD_StatusRepository sd_StatusRepository;

	@Autowired
	private  SD_KanalRepository sd_KanalRepository;
	
	@Autowired
	private  Pdf_StellenangebotRepository pdf_StellenangebotRepository;
	
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
	
	/*******************/
	/** DOWNLOAD Pdf  **/
	/*******************/
	@GetMapping(path = { "/downloadpdf/{fileName}" })
	public Pdf_Stellenangebot getPdf(@PathVariable("fileName") String dateiName) throws IOException {

		// neuere optimiere kürzere Version
		Optional<Pdf_Stellenangebot> retrievedPdf = pdf_StellenangebotRepository.findByName(dateiName);
		Pdf_Stellenangebot pdf = retrievedPdf.get();
		
		/*
		byte[] bytes_dnld = img.getPicByte();
		long len = img.getPicByte().length;
				
		// andere, umständlichere Version
	    Pdf_Stellenangebot pdf = new Pdf_Stellenangebot(
	    		retrievedPdf.get().getName(), 
	    		retrievedPdf.get().getType(),
	    		decompressBytes(retrievedPdf.get().getBinData())
	    	
	    );
	    */
	    
	    return pdf;
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
		
	// =======================================================

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
	
	// get alle Stellenangebote
	@GetMapping("/stellenangebot")
	public List<Stellenangebot> getAllStellenangebote(){		
		List<Stellenangebot> list_stellenangebote = stellenangebotRepository.findAll();		
		return list_stellenangebote;
	}		

	// get employee by id rest api
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
	
	@PostMapping(path= "/stellenangebot", consumes = "application/json", produces = "application/json")
	public Stellenangebot createStellenangebot(@RequestBody Stellenangebot stellenangebot) {
		return stellenangebotRepository.save(stellenangebot);
	}

	@PostMapping("/stellenangebot/{id}")
	public ResponseEntity<Stellenangebot> postStellenangebot(@PathVariable Long id, @RequestBody Stellenangebot stellenangebotDetails){
		
		Optional<Stellenangebot> stellenangebot_opt = stellenangebotRepository.findById(id);
		 	// .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		Stellenangebot stellenangebot = stellenangebot_opt.get();
		
		// stellenangebot.setBeginn(stellenangebotDetails.getBeginn());
		stellenangebot.setBezeichnung(stellenangebotDetails.getBezeichnung());
		// stellenangebot.setEnde(stellenangebotDetails.getEnde());
		
		stellenangebot.setKanaele(stellenangebotDetails.getKanaele());
		stellenangebot.setNotizen(stellenangebotDetails.getNotizen());
		stellenangebot.setSd_kanal(stellenangebotDetails.getSd_kanal());
		stellenangebot.setSd_status(stellenangebotDetails.getSd_status());
				
		Stellenangebot updatedStellenangebot = stellenangebotRepository.save(stellenangebot);
		
		ResponseEntity<Stellenangebot> sa = ResponseEntity.ok(updatedStellenangebot);
		
		return sa;
	}
	
	
	@PutMapping("/stellenangebot/{id}")
	public ResponseEntity<Stellenangebot> updateStellenangebot(@PathVariable Long id, @RequestBody Stellenangebot stellenangebotDetails){
		
		Optional<Stellenangebot> stellenangebot_opt = stellenangebotRepository.findById(id);
		 	// .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		Stellenangebot stellenangebot = stellenangebot_opt.get();
		
		// stellenangebot.setBeginn(stellenangebotDetails.getBeginn());
		stellenangebot.setBezeichnung(stellenangebotDetails.getBezeichnung());
		// stellenangebot.setEnde(stellenangebotDetails.getEnde());
		
		stellenangebot.setKanaele(stellenangebotDetails.getKanaele());
		stellenangebot.setNotizen(stellenangebotDetails.getNotizen());
		stellenangebot.setSd_kanal(stellenangebotDetails.getSd_kanal());
		stellenangebot.setSd_status(stellenangebotDetails.getSd_status());
				
		Stellenangebot updatedStellenangebot = stellenangebotRepository.save(stellenangebot);
		
		ResponseEntity<Stellenangebot> sa = ResponseEntity.ok(updatedStellenangebot);
		
		return sa;
	}
	
	/*
	public ResponseEntity<Stellenangebot> addEmployee(@RequestBody Stellenangebot stellenangebot) throws Exception 
	{ 	  
		
	}
	*/      
		

	
}
