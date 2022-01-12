package com.inisirion.ibm;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inisirion.ibm.entities.Pdf_Stellenangebot;
import com.inisirion.ibm.entities.SD_Kanal;
import com.inisirion.ibm.entities.SD_Status;
import com.inisirion.ibm.entities.Stellenangebot;
import com.inisirion.ibm.repository.Pdf_StellenangebotRepository;
import com.inisirion.ibm.repository.SD_KanalRepository;
import com.inisirion.ibm.repository.SD_StatusRepository;
import com.inisirion.ibm.repository.StellenangebotRepository;
import com.inisirion.ibm.serializer.CustomHttpMessageConverter;
import com.inisirion.ibm.serializer.Employee;

@SpringBootApplication
public class IbmApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbmApplication.class, args);
	}
	
	
	@Component
	public class MyRunner implements CommandLineRunner  {

		@Autowired
		SD_StatusRepository sdStatusRepository;	
		
		@Autowired
		SD_KanalRepository sdKanalRepository;	
		
		@Autowired 
		private StellenangebotRepository stellenangebotRepository;	

		@Autowired 
		private Pdf_StellenangebotRepository pdf_stellenangebotRepository;	
		
		
		@Override
		public void run(String... args) throws Exception {

			// Anlage von Stammdaten 
			this.insert_stammdaten();

			// Anlage von 3 Stellenangeboten 
			// this.insert_stellenangebote();
			
			// funkionierendes Bsp, um ein Objekt mit einer Property vom Typ "Date" zu serialisieren ===> {"startdateemployee": "21.04.2021"}
			// this.exampleJsonSerializer();
			
			// funkionierendes Bsp, um einen Json: {"dateemployee": "21.04.2021"} in ein  Objekt mit einer Poperty vom Typ "Date" zu deserialisieren
			//  this.exampleJsonDeserializer();
		}
		
		
		private void exampleJsonSerializer() throws JsonProcessingException {
			
			/*
			// "yyyy-mm-dd" ist der Standard
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			LocalDate employmentDate = LocalDate.parse("19.04.2021", formatter);			
			ZonedDateTime zdt = employmentDate.atStartOfDay(ZoneId.of("UTC"));
			*/
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 2022);
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DAY_OF_MONTH, 6);
			Date date = cal.getTime();
			
			Employee employee = new Employee();
			employee.setId("A17");
			employee.setFirstName("Frank");
			employee.setLastName("Smith");			
			employee.setEmployeeStartDate(date);
			//  employee.setEmployeeStartDate(zdt.toInstant().toEpochMilli());

			ObjectMapper mapper = new ObjectMapper();            
			System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(employee));			
		}

		
		private void exampleJsonDeserializer() throws JsonProcessingException {
						
			ObjectMapper mapper = new ObjectMapper();  
            
			String json = "{\r\n"
			        + "  \"id\" : \"A17\",\r\n"
			        + "  \"lastName\" : \"Smith\",\r\n"
			        + "  \"firstName\" : \"Frank\",\r\n"
			        + "  \"employeeStartDate\" : \"19.04.2021\"\r\n"
			        + "}";
		    // aus dem Json-Inhalt: "19.04.2021" wird eine Date-Property in der Instanz von Employee        
			Employee employee = mapper.readerFor(Employee.class).readValue(json);
			System.err.println(employee);						
		}
					
		
		//  nur ein erster Versuch
		/*
		@Bean
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		    return new CustomHttpMessageConverter();
		}
		*/		
		
		
		/*
		 * Anlegen von Stammdaten für Status und Kanäle
		 * 
		 */
		private void insert_stammdaten() {
			
			/*
			sdStatusRepository.save(new SD_Status(444441L, "in Vorbereitung"));
			sdStatusRepository.save(new SD_Status(444442L, "online"));
			sdStatusRepository.save(new SD_Status(444443L, "eingestellt"));
			sdStatusRepository.save(new SD_Status(444444L, "abgesagt"));
			sdStatusRepository.save(new SD_Status(444445L, "pausieren"));

			sdKanalRepository.save(new SD_Kanal(555551L, "Presse"));
			sdKanalRepository.save(new SD_Kanal(555552L, "Jobbörse"));
			sdKanalRepository.save(new SD_Kanal(555553L, "Xing"));
			sdKanalRepository.save(new SD_Kanal(555554L, "LinkedIn"));
			*/
			sdKanalRepository.save(new SD_Kanal(555551L, "unklar"));
					
			return;
		}
		
		/*
		 * Anlegen von Stellenangeboten inkl. der Verknüpfungen für die abhängigen Tabellen
		 * 
		 */
		private void insert_stellenangebote() {
			
			
			// *** Stellenangebot 1 *****
			
			// der aktuelle Status
			Optional<SD_Status> sd_status_opt = sdStatusRepository.findById(1L);
			
			// nach einer evtl. Einstellung: der Kanal, der zum ERfolg führte
			Optional<SD_Kanal>  sd_kanal_success_opt  = sdKanalRepository.findById(1L);

			// Liste mit allen Kanälen aufbauen, die im konkreten Stellenangebot geschaltet werden
			// Die Liste aller zugeordneten Kanäle
			List<SD_Kanal> kanaele = new ArrayList<>();
			kanaele.add(sdKanalRepository.findById(2L).get());
			
			Stellenangebot stellenangebot = new Stellenangebot();
			stellenangebot.setBezeichnung("Fullstack Entwickler");
			stellenangebot.setBeginn(new Date());
			stellenangebot.setEnde(new Date());
			stellenangebot.setNotizen("das ist meine erste Notiz");
			stellenangebot.setSd_status(sd_status_opt.get());
			stellenangebot.setSd_kanal(sd_kanal_success_opt.get());
			stellenangebot.setKanaele(kanaele);
						
			// Man muss zunächst in der Tabelle "pdf_Stellenangebot" für jedes Stellenangebot 
			// einen (leeren9 Eintrag hinterlegen, auch wenn niemals ein echtes pdf-Dokument hintergelegt wird.
			Pdf_Stellenangebot pdf_sa1 = new Pdf_Stellenangebot();
			pdf_stellenangebotRepository.save(pdf_sa1);
			stellenangebot.setPdf_stellenangebot(pdf_sa1);
			
			stellenangebotRepository.save(stellenangebot);

			
			// **** Stellenangebot 2 *****
									
			sd_status_opt = sdStatusRepository.findById(2L);
			sd_kanal_success_opt  = sdKanalRepository.findById(2L);			

			// Liste mit allen Kanälen aufbauen, die im konkreten Stellenangebot geschaltet wurden
			kanaele.clear();
			kanaele.add(sdKanalRepository.findById(1L).get());
			kanaele.add(sdKanalRepository.findById(4L).get());
						
			stellenangebot = new Stellenangebot();			
			stellenangebot.setBezeichnung("Angular Entwickler");
			stellenangebot.setBeginn(new Date());
			stellenangebot.setEnde(new Date());
			stellenangebot.setNotizen("das ist meine zweite Notiz");
			stellenangebot.setSd_status(sd_status_opt.get());
			stellenangebot.setSd_kanal(sd_kanal_success_opt.get());
			stellenangebot.setKanaele(kanaele);

			// Man muss zunächst in der Tabelle "pdf_Stellenangebot" für jedes Stellenangebot 
			// einen (leeren9 Eintrag hinterlegen, auch wenn niemals ein echtes pdf-Dokument hintergelegt wird.
			Pdf_Stellenangebot pdf_sa2 = new Pdf_Stellenangebot();
			pdf_stellenangebotRepository.save(pdf_sa2);
			stellenangebot.setPdf_stellenangebot(pdf_sa2);
			
			stellenangebotRepository.save(stellenangebot);
						
			
			// **** Stellenangebot 3 *****
			
			sd_status_opt = sdStatusRepository.findById(3L);
			sd_kanal_success_opt  = sdKanalRepository.findById(3L);			

			// Liste mit allen Kanälen aufbauen, die im konkreten Stellenangebot geschaltet wurden
			kanaele.clear();
			kanaele.add(sdKanalRepository.findById(2L).get());
			kanaele.add(sdKanalRepository.findById(3L).get());
			kanaele.add(sdKanalRepository.findById(4L).get());

			stellenangebot = new Stellenangebot();			
			stellenangebot.setBezeichnung("Datenbankspezialisten");
			stellenangebot.setBeginn(new Date());
			stellenangebot.setEnde(new Date());
			stellenangebot.setNotizen("das ist meine dritte Notiz");
			stellenangebot.setSd_status(sd_status_opt.get());
			stellenangebot.setSd_kanal(sd_kanal_success_opt.get());
			stellenangebot.setKanaele(kanaele);

			// Man muss zunächst in der Tabelle "pdf_Stellenangebot" für jedes Stellenangebot 
			// einen (leeren9 Eintrag hinterlegen, auch wenn niemals ein echtes pdf-Dokument hintergelegt wird.
			Pdf_Stellenangebot pdf_sa3 = new Pdf_Stellenangebot();
			pdf_stellenangebotRepository.save(pdf_sa3);
			stellenangebot.setPdf_stellenangebot(pdf_sa3);
			
			stellenangebotRepository.save(stellenangebot);
			
			Stellenangebot sa = stellenangebotRepository.findById(3L).get();
								
			return;
		}
		
	}
}
