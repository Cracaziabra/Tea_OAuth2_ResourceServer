package com.example.oauth2_resourceserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${jwkUri}")
    private String jwkUri;

    @Bean
    @Profile("dev")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(
                r -> r.jwt().jwkSetUri(jwkUri)
                        .jwtAuthenticationConverter(new CustomJwtAuthenticationTokenConverter())
        );

        http.authorizeHttpRequests()
                .requestMatchers("/tea/search/**").hasAuthority("read")
                .requestMatchers("/delete/**").hasAuthority("delete")
                .requestMatchers("/saveTea").hasAuthority("write")
                .requestMatchers("/test").hasAuthority("testAuthority")
                .anyRequest().denyAll();

        return http.build();
    }

    @Bean
    @Profile("test")
    public SecurityFilterChain securityFilterChainTest(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(
                r -> r.jwt()
                        .jwtAuthenticationConverter(new CustomJwtAuthenticationTokenConverter())
        );

        http.authorizeHttpRequests()
                .requestMatchers("/tea/search/**").hasAuthority("read")
                .requestMatchers("/delete/**").hasAuthority("delete")
                .requestMatchers("/saveTea").hasAuthority("write")
                .requestMatchers("/test").hasAuthority("testAuthority")
                .anyRequest().denyAll();

        return http.build();
    }

}
