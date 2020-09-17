package controller;

import model.Booking;
import model.BusinessService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import service.BookingService;

import java.util.List;

/**
 * Created by CoT on 7/29/18.
 */
@CrossOrigin(origins = {"http://localhost:3000", "https://sept-frontend.herokuapp.com", "https://sept-frontend-test.herokuapp.com"})
@RestController
@RequestMapping(path = "/api/")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    //GET ALL
    @RequestMapping(path = "bookings", method = RequestMethod.GET)
    public List<Booking> getAllBookings(){
        return bookingService.getAllBookings();
    }
    
    //GET BY ID
    @RequestMapping(path = "bookings/{bookingId}", method = RequestMethod.GET)
    public ResponseEntity<Booking> getBooking(@PathVariable("bookingId") int bookingId){
    	Booking booking = bookingService.getBooking(bookingId);

        if (booking == null){
            return new ResponseEntity<Booking>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Booking>(booking, HttpStatus.OK);
    }

    //CREATE
    @RequestMapping(path = "bookings", method = RequestMethod.POST)
    public ResponseEntity<Void> saveBooking(@RequestBody Booking booking, UriComponentsBuilder ucBuilder){

    	bookingService.saveBooking(booking);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/bookings/{id}").buildAndExpand(booking.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        
    }
    
    //UPDATE
    @RequestMapping(path = "bookings", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateBooking(@RequestBody Booking booking){
    	Booking bookingToBeUpdated = bookingService.getBooking(booking.getId());

        if (bookingToBeUpdated == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        
        bookingService.updateBooking(booking);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    //DELETE
    @RequestMapping(path = "bookings/{bookingId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteBusinessService(@PathVariable("bookingId") int bookingId){
    	Booking booking = bookingService.getBooking(bookingId);

        if (booking == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        bookingService.deleteBooking(bookingId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


}
