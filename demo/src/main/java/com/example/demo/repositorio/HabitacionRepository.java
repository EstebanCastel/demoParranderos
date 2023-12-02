package com.example.demo.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.modelo.Habitacion;

public interface HabitacionRepository extends MongoRepository<Habitacion, String> {
    
}
