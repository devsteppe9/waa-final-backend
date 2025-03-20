package edu.miu.waa.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.waa.dto.response.PropertyResponseDto;
import edu.miu.waa.model.Property;
import edu.miu.waa.repo.FileResourceRepo;
import edu.miu.waa.service.PropertyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

class PropertyControllerTest extends AbstractControllerTest {
  
  
  private final ObjectMapper objectMapper = new ObjectMapper();
  
  @Autowired
  private PropertyService propertyService;
  
  @Autowired
  private MockMvc mockMvc;
  
  @Test
  void testCreate() throws Exception {
    MvcResult resultCreate =
        mockMvc
            .perform(
                post("/api/v1/properties")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .content(
                        """
{"name": "test", 
"description": "test", 
"price": 1560000000.0,
"address": "test",
"state": "test",
"zipcode": 12345,
"city": "test",
"country": "test",
"totalBathrooms": 1,
"totalBedrooms": 1,
"totalArea": 1}
"""))
            .andExpect(status().isCreated())
            .andReturn();
    PropertyResponseDto property = objectMapper.readValue(resultCreate.getResponse().getContentAsString(),
        PropertyResponseDto.class);

    assertEquals("test", property.getName());
    assertEquals("test", property.getDescription());
    assertEquals(Double.valueOf("1560000000.0"), property.getPrice());
    assertEquals("test", property.getAddress());
    assertEquals("test", property.getState());
    assertEquals(Integer.valueOf(12345), property.getZipcode());
    assertEquals("test", property.getCity());
    assertEquals("test", property.getCountry());
    assertEquals(1, property.getTotalBathrooms());
    assertEquals(1, property.getTotalBedrooms());
    assertEquals(1, property.getTotalArea());
  }

  @Test
  void testFindAll() throws Exception {
    createProperty("test1");
    createProperty("test2");
    
    MvcResult result = mockMvc.perform(get("/api/v1/properties")).andExpect(status().isOk()).andReturn();
    PropertyResponseDto[] properties = objectMapper.readValue(result.getResponse().getContentAsString(),
        PropertyResponseDto[].class);
    assertEquals(2, properties.length);
  }
  
  @Test
  void testFindById() throws Exception {
    Property p = createProperty("test");

    MvcResult result = mockMvc.perform(get("/api/v1/properties/" + p.getId())).andExpect(status().isOk()).andReturn();
    PropertyResponseDto property = objectMapper.readValue(result.getResponse().getContentAsString(),
        PropertyResponseDto.class);
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
    PropertyResponseDto property = objectMapper.readValue(result.getResponse().getContentAsString(),
        PropertyResponseDto.class);
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
    PropertyResponseDto property = objectMapper.readValue(result.getResponse().getContentAsString(),
        PropertyResponseDto.class);
    assertEquals("test2", property.getName());
    
  }
  
  @Test
  public void shouldSaveUploadedFile() throws Exception {
    Property p = createProperty("test");
    MockMultipartFile multipartFile = new MockMultipartFile("files", "test.txt",
        MediaType.MULTIPART_FORM_DATA_VALUE, "Spring Framework".getBytes());
    MvcResult res =
        mockMvc
            .perform(multipart("/api/v1/properties/%d/images".formatted(p.getId())).file(multipartFile))
            .andExpect(status().isCreated())
            .andReturn();
    
    PropertyResponseDto property = objectMapper.readValue(res.getResponse().getContentAsString(), PropertyResponseDto.class);
    assertEquals(1, property.getFileResources().size());
    assertEquals("test.txt", property.getFileResources().get(0).getFileName());
    assertNotNull(property.getFileResources().get(0).getStorageKey());
    assertNotNull(property.getFileResources().get(0).getHref());
    String storageKey = property.getFileResources().get(0).getStorageKey();
    MvcResult result = mockMvc.perform(get("/api/v1/file-resources/%s".formatted(storageKey))).andExpect(status().isOk()).andReturn();
    byte[] file = result.getResponse().getContentAsByteArray();
    assertNotNull(file);
    assertTrue(file.length > 0);
  }
  
  private Property createProperty(String name) {
    Property p = new Property();
    p.setName(name);
    propertyService.create(p);
    return p;
  }
  
}
