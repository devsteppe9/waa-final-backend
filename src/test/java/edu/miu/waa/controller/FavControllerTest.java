package edu.miu.waa.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.miu.waa.model.Property;
import edu.miu.waa.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class FavControllerTest extends AbstractControllerTest{
  @Autowired
  private MockMvc mockMvc;
  
  @Test
  void testAdd() throws Exception {
    Property property = createProperty("test1");
    User user = createUser("jack", "CUSTOMER");
    
    mockMvc.perform(post("/favourite")
      .contentType(MediaType.APPLICATION_JSON)
      .content("""
{"userId": %d, "propertyId": %d}""".formatted(user.getId(), property.getId())))
      .andExpect(status().isOk());
  }
}
