package com.inisirion.ibm.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class StellenangebotDateSerializer extends JsonSerializer<Date> {
	
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public void serialize(Date dateValue, JsonGenerator gen, SerializerProvider provider) throws IOException {
        try {
        	
        	// Konvertieren des dateValue in einen long value
        	/*
        	long longValue  = dateValue.getTime();
        
            LocalDate date = Instant.ofEpochMilli(longValue)
                                    .atZone(ZoneId.of("UTC"))
                                    .toLocalDate();
            String formattedString = dateValue.format(DATE_FORMATTER);
            */
            
        	SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        	String formattedString = format.format(dateValue);        	

        	// Es wird z. B. folgender Json weggeschrieben: "05.01.2022"
            gen.writeString(formattedString);
            
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
    }	

}
