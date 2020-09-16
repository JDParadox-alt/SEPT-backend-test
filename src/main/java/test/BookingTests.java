package test;

import model.Booking;
import model.Business;
import service.AllService;
import service.BookingService;
import controller.AllController;
import controller.BookingController;
import service.BusinessServiceService;
import controller.BusinessServiceController;
import model.BusinessService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
//
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookingTests {
	
	private MockMvc mockMvc;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(bookingController)
                .build();
    }
	
	@Test
	public void testGetAllBookings() throws Exception {
	    List<Booking> bookings = Arrays.asList(
	            new Booking(1, null, null, null, null, "notity", null, null),
	            new Booking(2, null, null, null, null, "note", null, null));
	    when(bookingService.getAllBookings()).thenReturn(bookings);
	    mockMvc.perform(get("/bookings"))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[0].id", is(1)))
	            .andExpect(jsonPath("$[0].notes", is("notity")))
	            .andExpect(jsonPath("$[1].id", is(2)))
	            .andExpect(jsonPath("$[1].notes", is("note")));
	    verify(bookingService, times(1)).getAllBookings();
	    verifyNoMoreInteractions(bookingService);
	}
	
	@Test
	public void testGetBookingById() throws Exception {
		Booking booking = new Booking(1, null, null, null, null, "notity", null, null);
		when(bookingService.getBooking(1)).thenReturn(booking);
	    mockMvc.perform(get("/bookings/{id}", 1))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$.id", is(1)))
	            .andExpect(jsonPath("$.notes", is("notity")));
	    verify(bookingService, times(1)).getBooking(1);
	    verifyNoMoreInteractions(bookingService);
	}
	
	@Test
	public void testGetBookingByIdNotFound() throws Exception {
	    when(bookingService.getBooking(1)).thenReturn(null);
	    mockMvc.perform(get("/bookings/{id}", 1))
	            .andExpect(status().isNotFound());
	    verify(bookingService, times(1)).getBooking(1);
	    verifyNoMoreInteractions(bookingService);
	}
	
	@Test
	public void testSaveBooking() throws Exception {
		Booking booking = new Booking("foobar");
	    doNothing().when(bookingService).saveBooking(booking);
	    mockMvc.perform(
	            post("/bookings")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(asJsonString(booking)))
	            .andExpect(status().isCreated())
	            .andExpect(header().string("location", containsString("http://localhost/bookings/")));
	    verify(bookingService, times(1)).saveBooking(refEq(booking));
	    verifyNoMoreInteractions(bookingService);
	}
	
	@Test
	public void testUpdateBookings() throws Exception {
		Booking booking = new Booking(1, null, null, null, null, "notity", null, null);
	    when(bookingService.getBooking(booking.getId())).thenReturn(booking);
	    doNothing().when(bookingService).updateBooking(booking);
	    mockMvc.perform(
	            put("/bookings", booking.getId())
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(asJsonString(booking)))
	            .andExpect(status().isOk());
	    verify(bookingService, times(1)).getBooking(booking.getId());
	    verify(bookingService, times(1)).updateBooking(refEq(booking));
	    verifyNoMoreInteractions(bookingService);
	}
	
	@Test
	public void testUpdateBookingNotFound() throws Exception {
		Booking booking = new Booking(1, null, null, null, null, "notity", null, null);
	    when(bookingService.getBooking(booking.getId())).thenReturn(null);
	    mockMvc.perform(
	            put("/bookings", booking.getId())
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(asJsonString(booking)))
	            .andExpect(status().isNotFound());
	    verify(bookingService, times(1)).getBooking(booking.getId());
	    verifyNoMoreInteractions(bookingService);
	}
	
	@Test
	public void testDeleteBooking() throws Exception {
		Booking booking = new Booking(1, null, null, null, null, "notity", null, null);
	    when(bookingService.getBooking(booking.getId())).thenReturn(booking);
	    doNothing().when(bookingService).deleteBooking(booking.getId());
	    mockMvc.perform(
	            delete("/bookings/{id}", booking.getId()))
	            .andExpect(status().isOk());
	    verify(bookingService, times(1)).getBooking(booking.getId());
	    verify(bookingService, times(1)).deleteBooking(booking.getId());
	    verifyNoMoreInteractions(bookingService);
	}
	
	@Test
	public void testDeleteBookingsNotFound() throws Exception {
		BusinessService booking = new BusinessService(1, null, "HelloWorld", null, null, null);
	    when(bookingService.getBooking(booking.getId())).thenReturn(null);
	    mockMvc.perform(
	            delete("/bookings/{id}", booking.getId()))
	            .andExpect(status().isNotFound());
	    verify(bookingService, times(1)).getBooking(booking.getId());
	    verifyNoMoreInteractions(bookingService);
	}

	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}