package test;

import model.Customer;
import service.AllService;
import controller.AllController;

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

public class CustomerTests {
	
	private MockMvc mockMvc;

    @Mock
    private AllService allService;

    @InjectMocks
    private AllController allController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(allController)
                .build();
    }
	
	@Test
	public void testGetAllCustomers() throws Exception {
	    List<Customer> customers = Arrays.asList(
	            new Customer(1, "Bob Ross", "lol", "lol", "lol", "lol", null),
	            new Customer(2, "Ross Bob", "lol", "lol", "lol", "lol", null));
	    when(allService.getAllCustomers()).thenReturn(customers);
	    mockMvc.perform(get("/customers"))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[0].id", is(1)))
	            .andExpect(jsonPath("$[0].username", is("Bob Ross")))
	            .andExpect(jsonPath("$[1].id", is(2)))
	            .andExpect(jsonPath("$[1].username", is("Ross Bob")));
	    verify(allService, times(1)).getAllCustomers();
	    verifyNoMoreInteractions(allService);
	}
	
	@Test
	public void testGetCustomerById() throws Exception {
		Customer customer = new Customer(1, "Bob Ross", "lol", "lol", "lol", "lol", null);
		when(allService.getCustomer(1)).thenReturn(customer);
	    mockMvc.perform(get("/customers/{id}", 1))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$.id", is(1)))
	            .andExpect(jsonPath("$.username", is("Bob Ross")));
	    verify(allService, times(1)).getCustomer(1);
	    verifyNoMoreInteractions(allService);
	}
	
	@Test
	public void testGetCustomerByIdNotFound() throws Exception {
	    when(allService.getCustomer(1)).thenReturn(null);
	    mockMvc.perform(get("/customers/{id}", 1))
	            .andExpect(status().isNotFound());
	    verify(allService, times(1)).getCustomer(1);
	    verifyNoMoreInteractions(allService);
	}
	
	@Test
	public void testSaveCustomer() throws Exception {
		Customer customer = new Customer("JohnDoe");
	    when(allService.customerExists(customer.getUsername())).thenReturn(false);
	    doNothing().when(allService).saveCustomer(customer);
	    mockMvc.perform(
	            post("/customers")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(asJsonString(customer)))
	            .andExpect(status().isCreated())
	            .andExpect(header().string("location", containsString("http://localhost/customers/")));
	    verify(allService, times(1)).customerExists(customer.getUsername());
	    verify(allService, times(1)).saveCustomer(refEq(customer));
	    verifyNoMoreInteractions(allService);
	}
	
	@Test
	public void testCustomerUsernameAlreadyExists() throws Exception {
		Customer customer = new Customer("JohnDoe");
	    when(allService.customerExists(customer.getUsername())).thenReturn(true);
	    doNothing().when(allService).saveCustomer(customer);
	    mockMvc.perform(
	            post("/customers")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(asJsonString(customer)))
	            .andExpect(status().isConflict());
	    verify(allService, times(1)).customerExists(customer.getUsername());
	    verifyNoMoreInteractions(allService);
	}
	
	@Test
	public void testUpdateCustomer() throws Exception {
		Customer customer = new Customer(1, "JoeBiden", null, null, null, null, null);
	    when(allService.getCustomer(customer.getId())).thenReturn(customer);
	    doNothing().when(allService).updateCustomer(customer);
	    mockMvc.perform(
	            put("/customers", customer.getId())
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(asJsonString(customer)))
	            .andExpect(status().isOk());
	    verify(allService, times(1)).getCustomer(customer.getId());
	    verify(allService, times(1)).updateCustomer(refEq(customer));
	    verifyNoMoreInteractions(allService);
	}
	
	@Test
	public void testUpdateCustomerNotFound() throws Exception {
		Customer customer = new Customer(1, "JoeBiden", null, null, null, null, null);
	    when(allService.getCustomer(customer.getId())).thenReturn(null);
	    mockMvc.perform(
	            put("/customers", customer.getId())
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(asJsonString(customer)))
	            .andExpect(status().isNotFound());
	    verify(allService, times(1)).getCustomer(customer.getId());
	    verifyNoMoreInteractions(allService);
	}
	
	@Test
	public void testDeleteCustomer() throws Exception {
		Customer customer = new Customer(1, "GoodbyeWorld", null, null, null, null, null);
	    when(allService.getCustomer(customer.getId())).thenReturn(customer);
	    doNothing().when(allService).deleteCustomer(customer.getId());
	    mockMvc.perform(
	            delete("/customers/{id}", customer.getId()))
	            .andExpect(status().isOk());
	    verify(allService, times(1)).getCustomer(customer.getId());
	    verify(allService, times(1)).deleteCustomer(customer.getId());
	    verifyNoMoreInteractions(allService);
	}
	
	@Test
	public void testDeleteCustomerNotFound() throws Exception {
		Customer customer = new Customer(1, "JoeBiden", null, null, null, null, null);
	    when(allService.getCustomer(customer.getId())).thenReturn(null);
	    mockMvc.perform(
	            delete("/customers/{id}", customer.getId()))
	            .andExpect(status().isNotFound());
	    verify(allService, times(1)).getCustomer(customer.getId());
	    verifyNoMoreInteractions(allService);
	}
	

	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}