package edu.miu.waa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.waa.dto.UserDto;
import edu.miu.waa.model.User;
import edu.miu.waa.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserService userService;


    @Test
    void testRegister() throws Exception {

        // Count existing users
        int initialUserCount = userService.findAllUsers().size();

        User u = new User();
        u.setFirstName("testNewUser");
        u.setEmail("test@waa.com");
        u.setLastName("test");
        u.setUsername("newUser");
        u.setPassword("password");
        u.setEnabled(true);

        // Perform POST request to register the user
        MvcResult result = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(u)))
                .andExpect(status().isOk())
                .andReturn();

        // Verify the response
        String responseContent = result.getResponse().getContentAsString();
        assertEquals("User registered successfully", responseContent);

        // Verify the user count has increased by 1
        int newUserCount = userService.findAllUsers().size();
        assertEquals(initialUserCount + 1, newUserCount);
    }

}
