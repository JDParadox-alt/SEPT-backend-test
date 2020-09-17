package controller;

import model.Business;
import model.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import service.BusinessServiceService;

import java.util.List;

/**
 * Created by CoT on 7/29/18.
 */
@CrossOrigin(origins = {"http://localhost:3000", "https://sept-frontend.herokuapp.com", "https://sept-frontend-test.herokuapp.com"})
@RestController
@RequestMapping(path = "/api/")
public class BusinessServiceController {

    @Autowired
    private BusinessServiceService businessServiceService;

    public BusinessServiceController(BusinessServiceService businessServiceService) {
        this.businessServiceService = businessServiceService;
    }

    
    //GET ALL\
    @RequestMapping(path = "businessServices", method = RequestMethod.GET)
    public List<BusinessService> getAllBusinessServices(){
        return businessServiceService.getAllBusinessServices();
    }

    //GET BY ID
    @RequestMapping(path = "businessServices/{businessServiceId}", method = RequestMethod.GET)
    public ResponseEntity<BusinessService> getBusinessService(@PathVariable("businessServiceId") int businessServiceId){
    	BusinessService businessService = businessServiceService.getBusinessService(businessServiceId);

        if (businessService == null){
            return new ResponseEntity<BusinessService>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<BusinessService>(businessService, HttpStatus.OK);
    }
    
    //CREATE
    @RequestMapping(path = "businessServices", method = RequestMethod.POST)
    public ResponseEntity<Void> saveBusinessService(@RequestBody BusinessService businessService, UriComponentsBuilder ucBuilder){

    	businessServiceService.saveBusinessService(businessService);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/businessServices/{id}").buildAndExpand(businessService.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        
    }

    //UPDATE
    @RequestMapping(path = "businessServices", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateBusinessService(@RequestBody BusinessService businessService){
    	BusinessService businessServiceToBeUpdated = businessServiceService.getBusinessService(businessService.getId());

        if (businessServiceToBeUpdated == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        
        businessServiceService.updateBusinessService(businessService);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    
    //DELETE
    @RequestMapping(path = "businessServices/{businessServiceId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteBusinessService(@PathVariable("businessServiceId") int businessServiceId){
    	BusinessService businessService = businessServiceService.getBusinessService(businessServiceId);

        if (businessService == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        businessServiceService.deleteBusinessService(businessServiceId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


}
