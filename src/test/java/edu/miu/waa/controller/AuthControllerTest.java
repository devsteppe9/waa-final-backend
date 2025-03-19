package edu.miu.waa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.waa.dto.UserDto;
import edu.miu.waa.model.User;
import edu.miu.waa.security.dto.LoginRequest;
import edu.miu.waa.security.dto.LoginResponse;
import edu.miu.waa.security.dto.RefreshTokenRequest;
import edu.miu.waa.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void testLogin() throws Exception {

        User u1 = new User();
        u1.setEnabled(true);
        u1.setUsername("nqthanh");
        u1.setPassword("12345678");
        userService.addUser(u1);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("nqthanh");
        loginRequest.setPassword("12345678");

        User u = userService.findByUsername("nqthanh");
        assertNotNull(u);
        assertEquals("nqthanh", u.getUsername());

        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        LoginResponse loginResponse = objectMapper.readValue(responseContent, LoginResponse.class);

        assertTrue(loginResponse.getAccessToken().length() > 0);
        assertTrue(loginResponse.getRefreshToken().length() > 0);
    }


    @Test
    void testLogout() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/auth/logout"))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertEquals("Logged out successfully", responseContent);
    }

}
