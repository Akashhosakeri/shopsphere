package com.shopsphere.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shopsphere.security.CustomUserDetailsService;
import com.shopsphere.security.JwtAuthenticationFilter;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            CustomUserDetailsService customUserDetailsService,
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth ->
                auth

                    // Authentication APIs
                    .requestMatchers("/api/auth/**")
                    .permitAll()

                    // Product read APIs
                    .requestMatchers(
                        org.springframework.http.HttpMethod.GET,
                        "/api/products",
                        "/api/products/**"
                    )
                    .authenticated()

                    // Category read APIs
                    .requestMatchers(
                        org.springframework.http.HttpMethod.GET,
                        "/api/categories",
                        "/api/categories/**"
                    )
                    .authenticated()

                    // Admin-only product operations
                    .requestMatchers(
                        org.springframework.http.HttpMethod.POST,
                        "/api/products/**"
                    )
                    .hasRole("ADMIN")

                    .requestMatchers(
                        org.springframework.http.HttpMethod.PUT,
                        "/api/products/**"
                    )
                    .hasRole("ADMIN")

                    .requestMatchers(
                        org.springframework.http.HttpMethod.DELETE,
                        "/api/products/**"
                    )
                    .hasRole("ADMIN")

                    // Admin-only category operations
                    .requestMatchers(
                        org.springframework.http.HttpMethod.POST,
                        "/api/categories/**"
                    )
                    .hasRole("ADMIN")

                    .requestMatchers(
                        org.springframework.http.HttpMethod.PUT,
                        "/api/categories/**"
                    )
                    .hasRole("ADMIN")

                    .requestMatchers(
                        org.springframework.http.HttpMethod.DELETE,
                        "/api/categories/**"
                    )
                    .hasRole("ADMIN")

                    // Admin-only user management
                    .requestMatchers("/api/users/**")
                    .hasRole("ADMIN")

                    // Admin-only order status management
                    .requestMatchers(
                        org.springframework.http.HttpMethod.PUT,
                        "/api/orders/*/status"
                    )
                    .hasRole("ADMIN")

                    // Everything else requires authentication
                    .anyRequest()
                    .authenticated()
            )

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(
                        customUserDetailsService
                );

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}