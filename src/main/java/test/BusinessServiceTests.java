package test;

import model.Business;
import service.AllService;
import controller.AllController;
import service.BusinessServiceService;
import controller.BusinessServiceController;
import model.BusinessService;

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

public class BusinessServiceTests {
	
	private MockMvc mockMvc;

    @Mock
    private BusinessServiceService businessServiceService;

    @InjectMocks
    private BusinessServiceController businessServiceController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(businessServiceController)
                .build();
    }
	
	@Test
	public void testGetAllBusinessServices() throws Exception {
	    List<BusinessService> businessServices = Arrays.asList(
	            new BusinessService(1, null, "HelloWorld", null, null, null),
	            new BusinessService(2, null, "HelloTest", null, null, null));
	    when(businessServiceService.getAllBusinessServices()).thenReturn(businessServices);
	    mockMvc.perform(get("/businessServices"))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[0].id", is(1)))
	            .andExpect(jsonPath("$[0].description", is("HelloWorld")))
	            .andExpect(jsonPath("$[1].id", is(2)))
	            .andExpect(jsonPath("$[1].description", is("HelloTest")));
	    verify(businessServiceService, times(1)).getAllBusinessServices();
	    verifyNoMoreInteractions(businessServiceService);
	}
	
	@Test
	public void testGetBusinessServiceById() throws Exception {
		BusinessService businessService = new BusinessService(1, null, "HelloWorld", null, null, null);
		when(businessServiceService.getBusinessService(1)).thenReturn(businessService);
	    mockMvc.perform(get("/businessServices/{id}", 1))
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	            .andExpect(jsonPath("$.id", is(1)))
	            .andExpect(jsonPath("$.description", is("HelloWorld")));
	    verify(businessServiceService, times(1)).getBusinessService(1);
	    verifyNoMoreInteractions(businessServiceService);
	}
//	
	@Test
	public void testGetBusinessServiceByIdNotFound() throws Exception {
	    when(businessServiceService.getBusinessService(1)).thenReturn(null);
	    mockMvc.perform(get("/businessServices/{id}", 1))
	            .andExpect(status().isNotFound());
	    verify(businessServiceService, times(1)).getBusinessService(1);
	    verifyNoMoreInteractions(businessServiceService);
	}
	
	@Test
	public void testSaveBusinessService() throws Exception {
		BusinessService businessService = new BusinessService("foobar");
	    doNothing().when(businessServiceService).saveBusinessService(businessService);
	    mockMvc.perform(
	            post("/businessServices")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(asJsonString(businessService)))
	            .andExpect(status().isCreated())
	            .andExpect(header().string("location", containsString("http://localhost/businessServices/")));
	    verify(businessServiceService, times(1)).saveBusinessService(refEq(businessService));
	    verifyNoMoreInteractions(businessServiceService);
	}
	
	@Test
	public void testUpdateBusinessService() throws Exception {
		BusinessService businessService = new BusinessService(1, null, "HelloWorld", null, null, null);
	    when(businessServiceService.getBusinessService(businessService.getId())).thenReturn(businessService);
	    doNothing().when(businessServiceService).updateBusinessService(businessService);
	    mockMvc.perform(
	            put("/businessServices", businessService.getId())
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(asJsonString(businessService)))
	            .andExpect(status().isOk());
	    verify(businessServiceService, times(1)).getBusinessService(businessService.getId());
	    verify(businessServiceService, times(1)).updateBusinessService(refEq(businessService));
	    verifyNoMoreInteractions(businessServiceService);
	}
	
	@Test
	public void testUpdateBusinessServiceNotFound() throws Exception {
		BusinessService businessService = new BusinessService(1, null, "HelloWorld", null, null, null);
	    when(businessServiceService.getBusinessService(businessService.getId())).thenReturn(null);
	    mockMvc.perform(
	            put("/businessServices", businessService.getId())
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(asJsonString(businessService)))
	            .andExpect(status().isNotFound());
	    verify(businessServiceService, times(1)).getBusinessService(businessService.getId());
	    verifyNoMoreInteractions(businessServiceService);
	}
	
	@Test
	public void testDeleteBusinessService() throws Exception {
		BusinessService businessService = new BusinessService(1, null, "HelloWorld", null, null, null);
	    when(businessServiceService.getBusinessService(businessService.getId())).thenReturn(businessService);
	    doNothing().when(businessServiceService).deleteBusinessService(businessService.getId());
	    mockMvc.perform(
	            delete("/businessServices/{id}", businessService.getId()))
	            .andExpect(status().isOk());
	    verify(businessServiceService, times(1)).getBusinessService(businessService.getId());
	    verify(businessServiceService, times(1)).deleteBusinessService(businessService.getId());
	    verifyNoMoreInteractions(businessServiceService);
	}
	
	@Test
	public void testDeleteCustomerNotFound() throws Exception {
		BusinessService businessService = new BusinessService(1, null, "HelloWorld", null, null, null);
	    when(businessServiceService.getBusinessService(businessService.getId())).thenReturn(null);
	    mockMvc.perform(
	            delete("/businessServices/{id}", businessService.getId()))
	            .andExpect(status().isNotFound());
	    verify(businessServiceService, times(1)).getBusinessService(businessService.getId());
	    verifyNoMoreInteractions(businessServiceService);
	}

	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}