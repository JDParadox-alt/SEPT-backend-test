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

public class CustomerSerialiser extends StdSerializer<Customer>{
    public CustomerSerialiser() {
        this(null);
    }

    public CustomerSerialiser(Class<Customer> t) {
        super(t);
    }
 
    @Override
    public void serialize(
        Customer customer, JsonGenerator jgen, SerializerProvider provider)
      throws IOException, JsonProcessingException {
        
        List<Booking> bookings = customer.getBookings();

        jgen.writeStartObject();

        jgen.writeNumberField("id", customer.getId());
        jgen.writeStringField("username", customer.getUsername());
        jgen.writeStringField("email", customer.getEmail());
        jgen.writeStringField("phone", customer.getPhone());
        jgen.writeStringField("address", customer.getAddress());

        jgen.writeFieldName("bookings");
        jgen.writeStartArray();
        for (Booking booking : bookings) {
            jgen.writeStartObject();
            jgen.writeNumberField("id", booking.getId());
        
            jgen.writeFieldName("businessService");
            if (booking.getBusinessService() != null) {
                jgen.writeStartObject();
                jgen.writeNumberField("id", booking.getBusinessService().getId());
                jgen.writeStringField("name", booking.getBusinessService().getName());
            
                jgen.writeFieldName("business");
                if (booking.getBusinessService().getBusiness() != null) {
                    jgen.writeStartObject();
                    jgen.writeNumberField("id", booking.getBusinessService().getBusiness().getId());
                    jgen.writeStringField("name", booking.getBusinessService().getBusiness().getName());
                    jgen.writeStringField("email", booking.getBusinessService().getBusiness().getEmail());
                    jgen.writeEndObject();
                }
                else{
                    jgen.writeNull();
                }       
                jgen.writeEndObject();
            }
            else{
                jgen.writeNull();
            }
           
            jgen.writeStringField("startDateTime", booking.getStartDateTime());
            jgen.writeStringField("endDateTime", booking.getEndDateTime());

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