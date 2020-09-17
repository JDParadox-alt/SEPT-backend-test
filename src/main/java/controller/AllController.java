package controller;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import service.AllService;
import java.util.List;

/**
 * Created by CoT on 7/29/18.
 */
@CrossOrigin(origins = {"http://localhost:3000", "https://sept-frontend.herokuapp.com/", "https://sept-frontend-test.herokuapp.com/"})
@RestController
@RequestMapping(path = "/api/")
public class AllController {

    @Autowired
    private AllService allService;

    //Assign Controllers

    //Business
    @RequestMapping(path="businesses", method = RequestMethod.GET)
    public List<Business> getBusinesses() {
        return allService.getAllBusinesses();
    }

    @RequestMapping(path = "businesses/{businessId}", method = RequestMethod.GET)
    public ResponseEntity<Business> getBusiness(@PathVariable("businessId") int businessId){
    	Business business = allService.getBusiness(businessId);

        if (business == null){
            return new ResponseEntity<Business>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Business>(business, HttpStatus.OK);
    }

    @RequestMapping(path = "businesses", method = RequestMethod.POST)
    public ResponseEntity<Void> saveBusiness(@RequestBody Business business, UriComponentsBuilder ucBuilder){

        allService.saveBusiness(business);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/businesses/{id}").buildAndExpand(business.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        
    }

    @RequestMapping(path = "businesses", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateBusiness(@RequestBody Business business){
        Business businessToBeUpdated = allService.getBusiness(business.getId());

        if (businessToBeUpdated == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        
        allService.updateBusiness(business);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(path = "businesses/{businessId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteBusiness(@PathVariable("businessId") int businessId){
    	Business business = allService.getBusiness(businessId);

        if (business == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        allService.deleteBusiness(businessId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    //Customer
    @RequestMapping(path="customers", method = RequestMethod.GET)
    public List<Customer> getCustomers() {
        return allService.getAllCustomers();
    }

    @RequestMapping(path = "customers/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomer(@PathVariable("customerId") int customerId){
    	Customer customer = allService.getCustomer(customerId);

        if (customer == null){
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    @RequestMapping(path = "customers", method = RequestMethod.POST)
    public ResponseEntity<Void> saveCustomer(@RequestBody Customer customer, UriComponentsBuilder ucBuilder){

        if (allService.customerExists(customer.getUsername())){
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        allService.saveCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/customers/{id}").buildAndExpand(customer.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        
    }

    @RequestMapping(path = "customers", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateCustomer(@RequestBody Customer customer){
        Customer customerToBeUpdated = allService.getCustomer(customer.getId());

        if (customerToBeUpdated == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        
        allService.updateCustomer(customer);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(path = "customers/{customerId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") int customerId){
    	Customer customer = allService.getCustomer(customerId);

        if (customer == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        allService.deleteCustomer(customerId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
