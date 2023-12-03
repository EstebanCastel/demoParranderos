package com.example.demo.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.modelo.Estadia;

public interface EstadiaRepository extends MongoRepository<Estadia, String> {
}
