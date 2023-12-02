package com.example.demo.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.modelo.TipoHabitacion;

public interface TipoHabitacionRepository extends MongoRepository<TipoHabitacion, String> {
    
}
