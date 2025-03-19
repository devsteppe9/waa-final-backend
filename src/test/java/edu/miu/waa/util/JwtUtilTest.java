package edu.miu.waa.util;

import edu.miu.waa.model.Role;
import edu.miu.waa.security.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class JwtUtilTest {

    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        userDetailsService = Mockito.mock(UserDetailsService.class);
        jwtUtil = new JwtUtil();
    }

    @Test
    void testGenerateToken() {
        UserDetails userDetails = new User("testuser", "password", Collections.singletonList(new  SimpleGrantedAuthority("OWNER")));
        String token = jwtUtil.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    void testValidateToken() {
        UserDetails userDetails = new User("testuser", "password", Collections.singletonList(new  SimpleGrantedAuthority("OWNER")));
        String token = jwtUtil.generateToken(userDetails);
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void testGetUsernameFromToken() {
        UserDetails userDetails = new User("testuser", "password", Collections.singletonList(new  SimpleGrantedAuthority("OWNER")));
        String token = jwtUtil.generateToken(userDetails);
        String username = jwtUtil.getUsernameFromToken(token);
        assertEquals("testuser", username);
    }

    @Test
    void testIsTokenExpired() {
        String token = jwtUtil.doGenerateToken("testuser");
        assertFalse(jwtUtil.isTokenExpired(token));
    }

    @Test
    void testGetExpirationDateFromToken() {
        String token = jwtUtil.doGenerateToken("testuser");
        Date expiration = jwtUtil.getExpirationDateFromToken(token);
        assertNotNull(expiration);
    }

    @Test
    void testGetAuthentication() {
        UserDetails userDetails = new User("testuser", "password", Collections.singletonList(new  SimpleGrantedAuthority("OWNER")));
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);
        String token = jwtUtil.generateToken(userDetails);
        assertNotNull(jwtUtil.getAuthentication(token));
    }
}