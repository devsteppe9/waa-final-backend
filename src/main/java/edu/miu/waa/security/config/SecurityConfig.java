package edu.miu.waa.security.config;

import edu.miu.waa.security.filter.JwtFilter;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Profile(value = {"development", "production"})
public class SecurityConfig {
  
  private final JwtFilter jwtFilter;
  String[] roles = {"OWNER", "CUSTOMER"};
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(c -> c.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests()
        .requestMatchers("/api/v1/auth/**","/api/v1/system/**").permitAll()  // Permit all for authentication endpoints
        .requestMatchers("/api/v1/users/**").hasAuthority("ADMIN") // Require "ADMIN" authority for users endpoints
        .requestMatchers(HttpMethod.GET, ".api/v1/file-resources/**").permitAll() // Permit all GET request for images
        .requestMatchers(HttpMethod.POST, ".api/v1/file-resources/**").hasAuthority("OWNER") // Require "OWNER" authority for POST request for images
        .requestMatchers(HttpMethod.GET, ".api/v1/properties/**").permitAll() // Permit all GET request for properties
        .requestMatchers(HttpMethod.POST, ".api/v1/properties/**").hasAuthority("OWNER") // Require "OWNER" authority for POST request for properties
        .requestMatchers(HttpMethod.PUT, ".api/v1/properties/**").hasAuthority("OWNER") // Require authentication for PUT requests
        .requestMatchers(".api/v1/offers/**").hasAnyAuthority(roles) // Require "OWNER" or "CUSTOMER" authority for offers endpoints Permit all GET request for images
        .anyRequest()
        .authenticated() // All other requests require authentication
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  UrlBasedCorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("GET","POST"));
    configuration.setAllowedHeaders(Arrays.asList("AUTHORIZATION", "CONTENT-TYPE"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }
}
