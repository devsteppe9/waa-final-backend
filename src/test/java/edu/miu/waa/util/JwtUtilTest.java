package edu.miu.waa.util;

import static org.junit.jupiter.api.Assertions.*;

import edu.miu.waa.security.util.JwtUtil;
import edu.miu.waa.service.AbstractServiceTest;
import edu.miu.waa.service.UserService;
import java.util.Collections;
import java.util.Date;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

class JwtUtilTest extends AbstractServiceTest {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userDetailsService;


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
}