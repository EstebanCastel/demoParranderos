package com.example.demo.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.modelo.CuentaServicio;

import java.util.List;

public interface CuentaServicioRepository extends MongoRepository<CuentaServicio, String> {

        List<CuentaServicio> findByDescripcion(String descripcion);
}