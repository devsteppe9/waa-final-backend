package edu.miu.waa.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.waa.model.Property;
import edu.miu.waa.service.PropertyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

class PropertyControllerTest extends AbstractControllerTest {
  
  @Autowired private MockMvc mockMvc;
  
  private final ObjectMapper objectMapper = new ObjectMapper();
  
  @Autowired
  private PropertyService propertyService;

  @Test
  void testFindAll() throws Exception {
    createProperty("test1");
    createProperty("test2");
    
    MvcResult result = mockMvc.perform(get("/api/v1/properties")).andExpect(status().isOk()).andReturn();
    Property[] properties = objectMapper.readValue(result.getResponse().getContentAsString(),
        Property[].class);
    assertEquals(2, properties.length);
  }
  
  @Test
  void testFindById() throws Exception {
    Property p = createProperty("test");

    MvcResult result = mockMvc.perform(get("/api/v1/properties/" + p.getId())).andExpect(status().isOk()).andReturn();
    Property property = objectMapper.readValue(result.getResponse().getContentAsString(),
        Property.class);
    assertEquals(p.getName(), property.getName());
  }
  
  @Test
  void testUpdate() throws Exception {
    Property p = createProperty("test");

    mockMvc.perform(
            patch("/api/v1/properties/{id}",
                p.getId())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content("""
                    {"id":%d,"name": "test2"}"""
                    .formatted(p.getId())))
        .andExpect(status().isOk());


    MvcResult result = mockMvc.perform(get("/api/v1/properties/" + p.getId())).andExpect(status().isOk()).andReturn();
    Property property = objectMapper.readValue(result.getResponse().getContentAsString(),
        Property.class);
    assertEquals("test2", property.getName());
  }
  
  @Test
  void testDelete() throws Exception {
    Property p = createProperty("test");
    mockMvc.perform(delete("/api/v1/properties/" + p.getId())).andExpect(status().isNoContent());
    assertEquals(0, propertyService.findAllProperties().size());
  }
  
  @Test
  void testPatch() throws Exception {
    Property p = createProperty("test");

    mockMvc.perform(
            patch("/api/v1/properties/{id}",
                p.getId())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content("""
                    {"name": "test2"}"""))
        .andExpect(status().isOk());


    MvcResult result = mockMvc.perform(get("/api/v1/properties/" + p.getId())).andExpect(status().isOk()).andReturn();
    Property property = objectMapper.readValue(result.getResponse().getContentAsString(),
        Property.class);
    assertEquals("test2", property.getName());
    
  }
  
  private Property createProperty(String name) {
    Property p = new Property();
    p.setName(name);
    propertyService.create(p);
    return p;
  }
  
}
