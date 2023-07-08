package com.example.oauth2_resourceserver.controllers;

import com.example.oauth2_resourceserver.data.Tea;
import com.example.oauth2_resourceserver.data.TeaRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TeaController {

    private TeaRepository teaRepository;

    @PostMapping(value = "/saveTea", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Tea saveTea(@RequestBody Tea tea) {
        return teaRepository.save(tea);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteTea(@PathVariable Long id) {
        teaRepository.deleteById(id);
    }

    @GetMapping("/test")
    @Profile("test")
    public String test() {
        return "Test!";
    }
}