package edu.miu.waa.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.waa.model.Property;
import edu.miu.waa.service.PropertyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

class PropertyControllerTest extends AbstractControllerTest {
  
  @Autowired private MockMvc mockMvc;
  
  private final ObjectMapper objectMapper = new ObjectMapper();
  
  @Autowired
  private PropertyService propertyService;

  @Test
  void testFindAll() throws Exception {
    Property p = new Property();
    p.setName("test");
    propertyService.create(p);

    MvcResult result = mockMvc.perform(get("/api/v1/properties")).andExpect(status().isOk()).andReturn();
    Property[] properties = objectMapper.readValue(result.getResponse().getContentAsString(),
        Property[].class);
    assertEquals(1, properties.length);
  }
  
}
