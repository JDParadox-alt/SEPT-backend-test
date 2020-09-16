package model;

import javax.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import utils.BusinessSerialiser;

@Entity
@Table(name = "business")
// @JsonIdentityInfo(scope = Business.class,
//   generator = ObjectIdGenerators.PropertyGenerator.class, 
//   property = "id")
@JsonSerialize(using = BusinessSerialiser.class)
public class Business {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column
    private String name;

    @Column
    private String email;

    @OneToMany(mappedBy = "business", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private List<BusinessService> businessServices;

    @Column
    private String description;

    @Column
    private String phone;

    @Column
    private String address;

    public Business() {
    }
    
    public Business(String name) {
		this.name = name;
	}
    
    public Business(int id, String name, String email, String password, List<BusinessService> businessServices,
			String description, String phone, String address) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.businessServices = businessServices;
		this.description = description;
		this.phone = phone;
		this.address = address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<BusinessService> getBusinessServices() {
        return businessServices;
    }

    public void setBusiness_services(List<BusinessService> businessServices) {
        this.businessServices = businessServices;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void removeBusinessService(BusinessService businessService){
        this.getBusinessServices().remove(businessService);
    }
}



