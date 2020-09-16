package model;

import utils.CustomerSerialiser;

import javax.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "customer")
// @JsonIdentityInfo(scope = Customer.class,
//   generator = ObjectIdGenerators.PropertyGenerator.class, 
//   property = "id")
@JsonSerialize(using = CustomerSerialiser.class)
public class Customer {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String address;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private List<Booking> bookings;

    public Customer() {
    	
    }
    
    public Customer(String username) {
        this.username = username;
    }
    
    public Customer(int id, String username, String email, String password, String phone, String address,
			List<Booking> bookings) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.bookings = bookings;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void removeBooking(Booking booking) {
        this.getBookings().remove(booking);
    }
}
