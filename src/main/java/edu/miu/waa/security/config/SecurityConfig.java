package edu.miu.waa.security.config;

import edu.miu.waa.security.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private JwtFilter jwtFilter;
  String[] roles = {"OWNER","CUSTOMER"};

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("api/v1/auth/**","api/v1/system/**").permitAll()  // Permit all for authentication endpoints
                    .requestMatchers("api/v1/users/**").hasAuthority("ADMIN") // Require "ADMIN" authority for users endpoints
                    .requestMatchers(HttpMethod.GET, "api/v1/file-resources/**").permitAll() // Permit all GET request for images
                    .requestMatchers(HttpMethod.POST, "api/v1/file-resources/**").hasAuthority("OWNER") // Require "OWNER" authority for POST request for images
                    .requestMatchers(HttpMethod.GET, "api/v1/properties/**").permitAll() // Permit all GET request for properties
                    .requestMatchers(HttpMethod.POST, "api/v1/properties/**").hasAuthority("OWNER") // Require "OWNER" authority for POST request for properties
                    .requestMatchers(HttpMethod.PUT, "api/v1/properties/**").hasAuthority("OWNER") // Require authentication for PUT requests
                    .requestMatchers("api/v1/offers/**").hasAnyAuthority(roles) // Require "OWNER" or "CUSTOMER" authority for offers endpoints
                    .anyRequest().authenticated()  // All other requests require authentication
            )
            // Configure session management (stateless)
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // Add custom JWT filter before UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }


  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

}
