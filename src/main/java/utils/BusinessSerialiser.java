package utils;

import model.Booking;
import model.Business;
import model.Customer;
import model.BusinessService;

import java.util.List;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class BusinessSerialiser extends StdSerializer<Business>{
    public BusinessSerialiser() {
        this(null);
    }

    public BusinessSerialiser(Class<Business> t) {
        super(t);
    }
 
    @Override
    public void serialize(
        Business business, JsonGenerator jgen, SerializerProvider provider)
      throws IOException, JsonProcessingException {

        List<BusinessService> businessServices = business.getBusinessServices();
 
        jgen.writeStartObject();

        jgen.writeNumberField("id", business.getId());
        jgen.writeStringField("name", business.getName());
        jgen.writeStringField("email", business.getEmail());

        jgen.writeFieldName("businessServices");
        jgen.writeStartArray();

        for (BusinessService businessService : businessServices) {
            jgen.writeStartObject();

            jgen.writeNumberField("id", businessService.getId());
            jgen.writeStringField("name", businessService.getName());

            jgen.writeStringField("description", businessService.getDescription());
            jgen.writeObjectField("workingHours", businessService.getWorkingHours());
            jgen.writeObjectField("employees", businessService.getEmployees());

            jgen.writeEndObject();
        }
        jgen.writeEndArray();

        jgen.writeStringField("description", business.getDescription());
        jgen.writeStringField("phone", business.getPhone());
        jgen.writeStringField("address", business.getAddress());

        jgen.writeEndObject();
    }
}