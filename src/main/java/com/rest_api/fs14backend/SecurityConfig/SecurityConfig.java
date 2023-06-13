package com.rest_api.fs14backend.SecurityConfig;

import com.rest_api.fs14backend.filter.CorsConfig;
import com.rest_api.fs14backend.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    @Autowired
    CorsConfig corsFilter;

    @Autowired
    JwtFilter jwtFilter;

    public SecurityConfig(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("POST", "api/v1/auth").permitAll()
                .requestMatchers("GET", "/api/v1/products").permitAll()
                .requestMatchers("GET", "/api/v1/products/*").permitAll()
                .requestMatchers("POST", "api/v1/products").hasRole("ADMIN")
                .requestMatchers("PUT", "api/v1/products/*").hasRole("ADMIN")
                .requestMatchers("DELETE", "api/v1/products/*").hasRole("ADMIN")
                .requestMatchers("GET", "/api/v1/product-categories").permitAll()
                .requestMatchers("POST", "/api/v1/product-categories/*").hasRole("ADMIN")
                .requestMatchers("DELETE", "/api/v1/product-categories/*").hasRole("ADMIN")
                .requestMatchers("POST", "/api/v1/orders").hasAnyRole("ADMIN", "USER")
                .requestMatchers("GET", "/api/v1/orders").hasRole("ADMIN")
                .requestMatchers("GET", "api/v1/users").hasRole("ADMIN")
                .requestMatchers("GET", "api/v1/users/*").hasAnyRole("ADMIN", "USER")
                .requestMatchers("DELETE", "api/v1/users").hasRole("ADMIN")
                .requestMatchers("DELETE", "api/v1/users/*").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter.corsFilter())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("https://e-commerce-frontend-livid.vercel.app/"); // Replace "*" with your frontend application's origin
        configuration.addAllowedMethod("https://e-commerce-frontend-livid.vercel.app/"); // Allow all HTTP methods
        configuration.addAllowedHeader("https://e-commerce-frontend-livid.vercel.app/"); // Allow all headers
        configuration.setAllowCredentials(true); // Enable credentials (e.g., cookies) for cross-origin requests

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply the configuration to all paths

        return source;
    }
}
