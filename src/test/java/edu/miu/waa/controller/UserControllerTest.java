package edu.miu.waa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.waa.dto.UserInDto;
import edu.miu.waa.dto.UserOutDto;
import edu.miu.waa.model.User;
import edu.miu.waa.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserService userService;

    @Test
    void testFindAll() throws Exception {
        // Count existing users
        int initialUserCount = userService.findAllUsers().size();

        UserInDto u = new UserInDto();
        u.setFirstName("test");
        u.setEmail("test@waa.com");
        u.setLastName("test");
        u.setUsername("testuser");
        u.setPassword("password");
        u.setEnabled(true);
        u.setRole("OWNER");
        userService.addUser(u);

        MvcResult result = mockMvc.perform(get("/api/v1/users")).andExpect(status().isOk()).andReturn();
        UserOutDto[] users = objectMapper.readValue(result.getResponse().getContentAsString(),
                UserOutDto[].class);
        assertEquals(initialUserCount + 1, users.length);
    }

}
