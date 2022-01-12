package com.inisirion.ibm.serializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class StartDateDeserializer extends JsonDeserializer<Date> {
	static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctx) throws IOException {

		// Originalbeispiel:
		/*
	     * LocalDate localDate = LocalDate.parse(p.getText(), DATE_FORMATTER);
	     * ZonedDateTime zdt = localDate.atStartOfDay(ZoneId.of("UTC"));	    
	     * Long millis = zdt.toInstant().toEpochMilli();
	     */
	    
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		Date date = null;
		
		try {
			date = format.parse (p.getText());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return date;
	    // return date.getTime();
	}
}
