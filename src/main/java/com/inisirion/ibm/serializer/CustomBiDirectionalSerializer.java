package com.inisirion.ibm.serializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.inisirion.ibm.entities.Kommunikation;

public class CustomBiDirectionalSerializer extends StdSerializer<List<Kommunikation>> {
		 
	public CustomBiDirectionalSerializer() {
		this(null);
	    }
	 
	public CustomBiDirectionalSerializer(Class<List<Kommunikation>> t) {
		super(t);
	}
	 
	@Override
	public void serialize(
		List<Kommunikation> kommunikationen, 
		JsonGenerator jsonGenerator, 
		SerializerProvider provider)  throws IOException {
		
	    List<Long> kommIds = new ArrayList();
	    
	    for (Kommunikation komm : kommunikationen) {
	    	kommIds.add(komm.getId());
	    }
	    jsonGenerator.writeObject(kommIds);
	}
}	
