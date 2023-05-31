package com.example.oauth2_resourceserver.data;

import org.springframework.data.repository.CrudRepository;

public interface TeaRepository extends CrudRepository<Tea, Long> {

    Tea[] findAllBy();

}
