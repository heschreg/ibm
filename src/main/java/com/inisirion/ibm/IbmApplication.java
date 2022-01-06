package com.inisirion.ibm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.inisirion.ibm.entities.Kanal;
import com.inisirion.ibm.entities.SD_Kanal;
import com.inisirion.ibm.entities.SD_Status;
import com.inisirion.ibm.entities.Stellenangebot;
import com.inisirion.ibm.repository.KanalRepository;
import com.inisirion.ibm.repository.SD_KanalRepository;
import com.inisirion.ibm.repository.SD_StatusRepository;
import com.inisirion.ibm.repository.StellenangebotRepository;

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
		private KanalRepository kanalRepository;	
		

		@Override
		public void run(String... args) throws Exception {

			////////////////////////////////////////////////
			// Anlegen von Stammdaten für Status und Kanäle
			////////////////////////////////////////////////
			
			sdStatusRepository.save(new SD_Status(444441L, "in Vorbereitung"));
			sdStatusRepository.save(new SD_Status(444442L, "online"));
			sdStatusRepository.save(new SD_Status(444443L, "eingestellt"));
			sdStatusRepository.save(new SD_Status(444444L, "abgesagt"));
			sdStatusRepository.save(new SD_Status(444445L, "pausieren"));

			sdKanalRepository.save(new SD_Kanal(555551L, "Presse"));
			sdKanalRepository.save(new SD_Kanal(555552L, "Jobbörse"));
			sdKanalRepository.save(new SD_Kanal(555553L, "Xing"));
			sdKanalRepository.save(new SD_Kanal(555554L, "LinkedIn"));
			

			///////////////////////////////////
			// Anlegen von Stellenangeboten 
			///////////////////////////////////
			
			// *** Stellenangebot 1 *****
			
			// der aktuelle Status
			Optional<SD_Status> sd_status_opt = sdStatusRepository.findById(1L);
			
			// nach einer evtl. Einstellung: der Kanal, der zum ERfolg führte
			Optional<SD_Kanal>  sd_kanal_success_opt  = sdKanalRepository.findById(1L);

			// Liste mit allen Kanälen aufbauen, die im konkreten Stellenangebot geschaltet wurden
			// // Die Liste aller zugeordneten Kanäle
			// public List<SD_Kanal> getKanaele() {
			List<SD_Kanal> kanaele = new ArrayList<>();
			kanaele.add(sdKanalRepository.findById(2L).get());
						
			Stellenangebot stellenangebot = new Stellenangebot();
			stellenangebot.setBezeichnung("Erste Stellenanzeige");
			stellenangebot.setBeginn(new Date());
			stellenangebot.setEnde(new Date());
			stellenangebot.setNotizen("das ist meine erste Notiz");
			stellenangebot.setSd_status(sd_status_opt.get());
			stellenangebot.setSd_kanal(sd_kanal_success_opt.get());
			stellenangebot.setKanaele(kanaele);
			
			stellenangebotRepository.save(stellenangebot);
			
			// **** Stellenangebot 2 *****
									
			sd_status_opt = sdStatusRepository.findById(2L);
			sd_kanal_success_opt  = sdKanalRepository.findById(2L);			

			// Liste mit allen Kanälen aufbauen, die im konkreten Stellenangebot geschaltet wurden
			kanaele.clear();
			kanaele.add(sdKanalRepository.findById(1L).get());
			kanaele.add(sdKanalRepository.findById(4L).get());
						
			stellenangebot = new Stellenangebot();			
			stellenangebot.setBezeichnung("zweite Stellenanzeige");
			stellenangebot.setBeginn(new Date());
			stellenangebot.setEnde(new Date());
			stellenangebot.setNotizen("das ist meine zweite Notiz");
			stellenangebot.setSd_status(sd_status_opt.get());
			stellenangebot.setSd_kanal(sd_kanal_success_opt.get());
			stellenangebot.setKanaele(kanaele);
			
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
			stellenangebot.setBezeichnung("dritte Stellenanzeige");
			stellenangebot.setBeginn(new Date());
			stellenangebot.setEnde(new Date());
			stellenangebot.setNotizen("das ist meine dritte Notiz");
			stellenangebot.setSd_status(sd_status_opt.get());
			stellenangebot.setSd_kanal(sd_kanal_success_opt.get());
			stellenangebot.setKanaele(kanaele);
			
			stellenangebotRepository.save(stellenangebot);
		}
				
	}

}
