package com.inisirion.ibm.serializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

// Man muss zusehen, dass für die Properties "beginn" und "ende" der Entitaet "stellenangebot"
// folgendes Format zurückgegeben wird:
// "beginn" : "13.01.2022"
// "ende"   : "10.02.2022"


public class StellenangebotDateDeserializer extends JsonDeserializer<Date> {
	
	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctx) throws IOException {

		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		Date date = null;
		
    	// In p.getText() kommt zB folgender Json rein: "05.01.2022"; dieser wird zu einer Object-Property(beginn, ende) vom Typ Date
		// In der Datenbank sind die korrespondierenden Felder vom Typ "DATETIME"
		
		try {
			String s = p.getText();
			date = format.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	    return date;
	}
}

