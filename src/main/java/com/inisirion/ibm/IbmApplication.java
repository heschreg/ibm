package com.inisirion.ibm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.inisirion.ibm.entities.SD_Kanal;
import com.inisirion.ibm.entities.SD_Status;
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
		KanalRepository kanalRepository;	

		@Override
		public void run(String... args) throws Exception {
			
			sdStatusRepository.save(new SD_Status(444441L, "in Vorbereitung"));
			sdStatusRepository.save(new SD_Status(444442L, "online"));
			sdStatusRepository.save(new SD_Status(444443L, "eingestellt"));
			sdStatusRepository.save(new SD_Status(444444L, "abgesagt"));
			sdStatusRepository.save(new SD_Status(444445L, "pausieren"));

			sdKanalRepository.save(new SD_Kanal(555551L, "Presse"));
			sdKanalRepository.save(new SD_Kanal(555552L, "Jobbörse"));
			sdKanalRepository.save(new SD_Kanal(555553L, "Xing"));
			sdKanalRepository.save(new SD_Kanal(555554L, "LinkedIn"));

						
			
		}
				
	}

}
