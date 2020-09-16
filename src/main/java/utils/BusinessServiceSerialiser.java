package utils;

import model.Booking;
import model.Business;
import model.Customer;
import model.BusinessService;

import java.io.IOException;

import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class BusinessServiceSerialiser extends StdSerializer<BusinessService>{
    public BusinessServiceSerialiser() {
        this(null);
    }

    public BusinessServiceSerialiser(Class<BusinessService> t) {
        super(t);
    }
 
    @Override
    public void serialize(
        BusinessService businessService, JsonGenerator jgen, SerializerProvider provider)
      throws IOException, JsonProcessingException {

        Business business = businessService.getBusiness();
        List<Booking> bookings = businessService.getBookings();

        jgen.writeStartObject();
        jgen.writeNumberField("id", businessService.getId());
        jgen.writeStringField("name", businessService.getName());

        jgen.writeFieldName("business");
        jgen.writeStartObject();
        jgen.writeNumberField("id", business.getId());
        jgen.writeStringField("name", business.getName());
        jgen.writeStringField("email", business.getEmail());
        jgen.writeStringField("description", business.getDescription());
        jgen.writeStringField("phone", business.getPhone());
        jgen.writeStringField("address", business.getAddress());
        jgen.writeEndObject();

        jgen.writeStringField("description", businessService.getDescription());
        jgen.writeObjectField("workingHours", businessService.getWorkingHours());
        jgen.writeObjectField("employees", businessService.getEmployees());

        jgen.writeFieldName("bookings");
        if (!bookings.isEmpty()) {
            
        }
        jgen.writeStartArray();
        for (Booking booking : bookings) {
            jgen.writeStartObject();
            jgen.writeNumberField("id", booking.getId());
        
            jgen.writeStringField("startDateTime", booking.getStartDateTime());
            jgen.writeStringField("endDateTime", booking.getEndDateTime());

            jgen.writeFieldName("customer");
            if (booking.getCustomer() != null) {
                jgen.writeStartObject();
                jgen.writeNumberField("id", booking.getCustomer().getId());
                jgen.writeStringField("username", booking.getCustomer().getUsername());
                jgen.writeStringField("email", booking.getCustomer().getEmail());
                jgen.writeStringField("phone", booking.getCustomer().getPhone());
                jgen.writeStringField("address", booking.getCustomer().getAddress());
                jgen.writeEndObject();
            }
            else{
                jgen.writeNull();
            }

            jgen.writeStringField("notes", booking.getNotes());

            if (booking.getNotify() != null) {
                jgen.writeStringField("notify", booking.getNotify());
            }
            else {
                jgen.writeNullField("notify");
            }
        
            jgen.writeStringField("status", booking.getStatus());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();

        jgen.writeEndObject();
    }
}