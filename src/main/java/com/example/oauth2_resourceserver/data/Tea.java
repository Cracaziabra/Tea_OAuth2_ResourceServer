package com.example.oauth2_resourceserver.data;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.rest.core.annotation.RestResource;

@Data
@Entity
@Table(name = "tea")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@RestResource(path = "tea", rel = "tea")
public class Tea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final String name;
    private final String color;
    private final String origin;
}
