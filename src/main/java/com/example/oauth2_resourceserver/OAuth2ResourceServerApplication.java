package com.example.oauth2_resourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OAuth2ResourceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2ResourceServerApplication.class, args);
    }

//    @Bean
//    public ApplicationRunner dataLoader(TeaRepository teaRepo) {
//        return args -> {
//            Tea ceylon = new Tea("Ceylon Black Tea", "black", "Sri Lanka");
//            Tea Darjeeling = new Tea("Darjeeling", "black", "India");
//            Tea justBlack = new Tea("Black Tea", "black", "China");
//            teaRepo.saveAll(List.of(ceylon, Darjeeling, justBlack));
//        };
//    }

}
