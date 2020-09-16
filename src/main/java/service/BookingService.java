package service;

import model.Booking;
import model.BusinessService;
import model.Customer;
import model.BusinessService;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by CoT on 10/14/17.
 */
@Transactional
@Service
public class BookingService {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //GET ALL
    public List<Booking> getAllBookings(){
        Query query = sessionFactory.getCurrentSession().createQuery("from Booking");
        return query.list();
    }
    
    //GET BY ID
    public Booking getBooking(int id){
        List<Booking> results = sessionFactory.getCurrentSession().createQuery("from Booking where id=" + id).list();
        if (results.size() == 1) {
            return (Booking) results.get(0);
        }
        else {
            return null;
        }    }
    
    //CREATE
    public void saveBooking(Booking booking){
        sessionFactory.getCurrentSession().save(booking);
    }
    
    //UPDATE
    public void updateBooking(Booking booking){
        sessionFactory.getCurrentSession().update(booking);
    }
    
    //DELETE
    public void deleteBooking(int id){
        Booking booking = getBooking(id);

        BusinessService businessService = booking.getBusinessService();
        Customer customer = booking.getCustomer();

        if (businessService != null) {
            businessService.removeBooking(booking);
            sessionFactory.getCurrentSession().update(businessService);
        }
        if (customer != null) {
            customer.removeBooking(booking);
            sessionFactory.getCurrentSession().update(customer);        
        }

        sessionFactory.getCurrentSession().delete(booking);
    }

}
