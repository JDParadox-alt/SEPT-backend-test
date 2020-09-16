package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import utils.BusinessServiceSerialiser;

@Entity
@Table(name = "businessService")
// @JsonIdentityInfo(scope = BusinessService.class,
//   generator = ObjectIdGenerators.PropertyGenerator.class, 
//   property = "id")
@JsonSerialize(using = BusinessServiceSerialiser.class)
public class BusinessService {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;
    
    @ManyToOne
    // @JsonIgnore
    private Business business;
    
    @Column
    private String description;
    
    @Column
    private ArrayList<Object> workingHours;
    
    @Column
    private ArrayList<String> employees;

    @OneToMany(mappedBy = "businessService", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private List<Booking> bookings;

    public BusinessService() {
    }
    
    public BusinessService(String description) {
		this.description = description;
	}

	public BusinessService(int id, Business business, String description, ArrayList<Object> workingHours,
			ArrayList<String> employees, List<Booking> bookings) {
		super();
		this.id = id;
		this.business = business;
		this.description = description;
		this.workingHours = workingHours;
		this.employees = employees;
		this.bookings = bookings;
	}



	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Object> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(ArrayList<Object> workingHours) {
        this.workingHours = workingHours;
    }

    public ArrayList<String> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<String> employees) {
        this.employees = employees;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void removeBooking (Booking booking) {
        this.getBookings().remove(booking);
    }
}
