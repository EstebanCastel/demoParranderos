package com.example.demo.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.modelo.ServicioProducto;

import java.util.List;

public interface ServicioProductoRepository extends MongoRepository<ServicioProducto, String> {

        List<ServicioProducto> findByNombre(String nombre);
}