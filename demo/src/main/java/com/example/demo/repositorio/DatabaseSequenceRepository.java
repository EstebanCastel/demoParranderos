package com.example.demo.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.modelo.DatabaseSequence;

public interface DatabaseSequenceRepository extends MongoRepository<DatabaseSequence, String> {

    @Query(value = "{ '_id': ?0 }", fields = "{ 'seq': 1 }")
    DatabaseSequence findSequenceById(String id);
}

