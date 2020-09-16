package test;

import model.Business;
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

public class BusinessTests {
	
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
	public void testGetAllBusinesses() throws Exception {
	    List<Business> businesses = Arrays.asList(
	            new Business(1, "Buckstar", null, null, null, null, null, null),
	            new Business(2, "Bookface", null, null, null, null, null, null));
	    when(allService.getAllBusinesses()).thenReturn(businesses);
	    mockMvc.perform(get("/businesses"))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[0].id", is(1)))
	            .andExpect(jsonPath("$[0].name", is("Buckstar")))
	            .andExpect(jsonPath("$[1].id", is(2)))
	            .andExpect(jsonPath("$[1].name", is("Bookface")));
	    verify(allService, times(1)).getAllBusinesses();
	    verifyNoMoreInteractions(allService);
	}
	
	@Test
	public void testGetBusinessById() throws Exception {
		Business business = new Business(1, "Buckstar", null, null, null, null, null, null);
		when(allService.getBusiness(1)).thenReturn(business);
	    mockMvc.perform(get("/businesses/{id}", 1))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$.id", is(1)))
	            .andExpect(jsonPath("$.name", is("Buckstar")));
	    verify(allService, times(1)).getBusiness(1);
	    verifyNoMoreInteractions(allService);
	}
	
	@Test
	public void testGetBusinessByIdNotFound() throws Exception {
	    when(allService.getBusiness(1)).thenReturn(null);
	    mockMvc.perform(get("/businesses/{id}", 1))
	            .andExpect(status().isNotFound());
	    verify(allService, times(1)).getBusiness(1);
	    verifyNoMoreInteractions(allService);
	}
	
	@Test
	public void testSaveBusiness() throws Exception {
		Business business = new Business("Buckstar");
	    doNothing().when(allService).saveBusiness(business);
	    mockMvc.perform(
	            post("/businesses")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(asJsonString(business)))
	            .andExpect(status().isCreated())
	            .andExpect(header().string("location", containsString("http://localhost/businesses/")));
	    verify(allService, times(1)).saveBusiness(refEq(business));
	    verifyNoMoreInteractions(allService);
	}
	
	@Test
	public void testUpdateBusiness() throws Exception {
		Business business = new Business(1, "Buckstar", null, null, null, null, null, null);
	    when(allService.getBusiness(business.getId())).thenReturn(business);
	    doNothing().when(allService).updateBusiness(business);
	    mockMvc.perform(
	            put("/businesses", business.getId())
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(asJsonString(business)))
	            .andExpect(status().isOk());
	    verify(allService, times(1)).getBusiness(business.getId());
	    verify(allService, times(1)).updateBusiness(refEq(business));
	    verifyNoMoreInteractions(allService);
	}
	
	@Test
	public void testUpdateBusinessNotFound() throws Exception {
		Business business = new Business(1, "Buckstar", null, null, null, null, null, null);
	    when(allService.getBusiness(business.getId())).thenReturn(null);
	    mockMvc.perform(
	            put("/businesses", business.getId())
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(asJsonString(business)))
	            .andExpect(status().isNotFound());
	    verify(allService, times(1)).getBusiness(business.getId());
	    verifyNoMoreInteractions(allService);
	}
	
	@Test
	public void testDeleteBusiness() throws Exception {
		Business business = new Business(1, "GoneBankrupt", null, null, null, null, null, null);
	    when(allService.getBusiness(business.getId())).thenReturn(business);
	    doNothing().when(allService).deleteBusiness(business.getId());
	    mockMvc.perform(
	            delete("/businesses/{id}", business.getId()))
	            .andExpect(status().isOk());
	    verify(allService, times(1)).getBusiness(business.getId());
	    verify(allService, times(1)).deleteBusiness(business.getId());
	    verifyNoMoreInteractions(allService);
	}
	
	@Test
	public void testDeleteCustomerNotFound() throws Exception {
		Business business = new Business(1, "GoneBankrupt", null, null, null, null, null, null);
	    when(allService.getBusiness(business.getId())).thenReturn(null);
	    mockMvc.perform(
	            delete("/businesses/{id}", business.getId()))
	            .andExpect(status().isNotFound());
	    verify(allService, times(1)).getBusiness(business.getId());
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