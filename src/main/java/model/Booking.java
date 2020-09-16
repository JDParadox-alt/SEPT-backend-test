package model;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import utils.BookingSerialiser;

@Entity
@Table(name = "booking")
// @JsonIdentityInfo(scope=Booking.class, generator = ObjectIdGenerators.PropertyGenerator.class, 
//   property = "id")
@JsonSerialize(using = BookingSerialiser.class)
public class Booking {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
//    @JsonIgnore
    private BusinessService businessService;
    
    @Column
    private String startDateTime;
    
    @Column
    private String endDateTime;
    
    @ManyToOne
//    @JsonIgnore
    private Customer customer;
    
    @Column
    private String notes;

    @Column 
    private String notify;

    @Column
    private String status;

    public Booking() {
    }
    
    public Booking(String notes) {
		this.notes = notes;
	}

    public Booking(int id, BusinessService businessService, String startDateTime, String endDateTime, Customer customer,
			String notes, String notify, String status) {
		super();
		this.id = id;
		this.businessService = businessService;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.customer = customer;
		this.notes = notes;
		this.notify = notify;
		this.status = status;
	}



	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BusinessService getBusinessService() {
        return businessService;
    }

    public void setBusinessService(BusinessService businessService) {
        this.businessService = businessService;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
