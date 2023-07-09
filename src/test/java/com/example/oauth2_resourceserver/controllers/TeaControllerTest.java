package com.example.oauth2_resourceserver.controllers;

import com.example.oauth2_resourceserver.config.SecurityConfig;
import com.example.oauth2_resourceserver.data.Tea;
import com.example.oauth2_resourceserver.data.TeaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TeaController.class)
@Import(TeaController.class)
@ContextConfiguration(classes = SecurityConfig.class)
@ActiveProfiles("test")
class TeaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TeaRepository teaRepository;

    @MockBean
    JwtDecoder jwtDecoder;

    @Test
    void shouldReturn401WithoutAuthorization() throws Exception {
        this.mockMvc.perform(get("/test"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn403WithoutAuthority() throws Exception {
        this.mockMvc.perform(get("/test")
                        .with(jwt()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldWorkWithAuthority() throws Exception {
        this.mockMvc.perform(get("/test")
                        .with(jwt().authorities(new SimpleGrantedAuthority("testAuthority"))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldSaveTeaWithAuthority() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        Tea testTea = new Tea("chai", "black", "Russia");
        this.mockMvc.perform(post("/saveTea")
                        .with(jwt().authorities(new SimpleGrantedAuthority("write")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testTea)))
                .andDo(print())
                .andExpect(status().isCreated());
        verify(teaRepository).save(eq(testTea));
    }

    @Test
    void shouldntSaveWithoutAuth() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        Tea testTea = new Tea("chai", "black", "Russia");
        this.mockMvc.perform(post("/saveTea")
                        .with(jwt().authorities(new SimpleGrantedAuthority("notWrite")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testTea)))
                .andDo(print())
                .andExpect(status().isForbidden());
        verify(teaRepository, never()).save(eq(testTea));
    }

    @Test
    void shouldDeleteTeaWithAuthority() throws Exception {
        this.mockMvc.perform(delete("/delete/{id}", 1)
                        .with(jwt().authorities(new SimpleGrantedAuthority("delete"))))
                .andDo(print())
                .andExpect(status().isOk());
        verify(teaRepository).deleteById(1L);
    }

    @Test
    void shouldntDeleteTeaWithoutAuth() throws Exception {
        this.mockMvc.perform(delete("/delete/{id}", 1)
                        .with(jwt().authorities(new SimpleGrantedAuthority("wrongDelete"))))
                .andDo(print())
                .andExpect(status().isForbidden());
        verify(teaRepository, never()).deleteById(1L);
    }
}