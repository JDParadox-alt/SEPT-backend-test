package service;

import model.*;

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
public class AllService {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    private BusinessServiceService businessServiceService;

    @Autowired
    private BookingService bookingService;
    
    //Assign Services

    //Businesses
    public List<Business> getAllBusinesses(){
        return sessionFactory.getCurrentSession().createQuery("from Business").list();
    }

    public Business getBusiness(int businessId){
        List<Business> results = sessionFactory.getCurrentSession().createQuery("from Business where id=" + businessId).list();
        if (results.size() == 1) {
            return (Business) results.get(0);
        }
        else {
            return null;
        }
    }
    
    public void saveBusiness(Business business){
        sessionFactory.getCurrentSession().save(business);
    }
    
    public void updateBusiness(Business business){
        sessionFactory.getCurrentSession().update(business);
    }
    
    public void deleteBusiness(int  businessId){
        Business business = getBusiness(businessId);

        List<BusinessService> services = business.getBusinessServices();

        for (BusinessService service : services) {
            service.setBusiness(null);
            sessionFactory.getCurrentSession().update(service);
        }

        sessionFactory.getCurrentSession().delete(business);

        for (BusinessService service : services) {
            businessServiceService.deleteBusinessService(service.getId());
        }
    }

    
    //Customer
    public List<Customer> getAllCustomers(){
        return sessionFactory.getCurrentSession().createQuery("from Customer").list();
    }

    public Customer getCustomer(int customerId){
        List<Customer> results = sessionFactory.getCurrentSession().createQuery("from Customer where id=" + customerId).list();
        if (results.size() == 1) {
            return (Customer) results.get(0);
        }
        else {
            return null;
        }    }
    
    public Customer getCustomerByUsername(String username) {
    	Query query = sessionFactory.getCurrentSession().createQuery("from Customer s where s.username like :username");
    	query.setString("username", "%"+username+"%");
    	return (Customer) query.uniqueResult();
    }
    
    public void saveCustomer(Customer customer){
        sessionFactory.getCurrentSession().save(customer);
    }

    public void updateCustomer(Customer customer){
        sessionFactory.getCurrentSession().update(customer);
    }

    public void deleteCustomer(int customerId){
        Customer customer = getCustomer(customerId);

        List<Booking> bookings = customer.getBookings();

        for (Booking booking : bookings) {

            booking.setCustomer(null);
            sessionFactory.getCurrentSession().update(booking);

            if (booking.getCustomer() == null & booking.getBusinessService() == null) {
                bookingService.deleteBooking(booking.getId());
            }
        }

        sessionFactory.getCurrentSession().delete(customer);
    }
    
    public boolean customerExists(String username) {
    	return getCustomerByUsername(username) != null;
    }
}
