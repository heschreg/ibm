package com.inisirion.ibm.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class StartDateSerializer extends JsonSerializer<Date> {
    
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        try {
        	
        	// Originalbeispiel:
        	/*
            LocalDate datel = Instant.ofEpochMilli(value).atZone(ZoneId.of("UTC")).toLocalDate();            
            String formattedString = datel.format(DATE_FORMATTER);
            LocalDate ldate = LocalDate.from(value.toInstant().atZone(ZoneOffset.UTC));
            String formattedString = DateTimeFormatter.ISO_DATE.format(ldate); 
            */
            
        	SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        	String formattedString = format.format( value );        	            
            
            gen.writeString(formattedString);
            
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
    }
}
