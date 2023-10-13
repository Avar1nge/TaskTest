package com.example.tasktest.repository;

import com.example.tasktest.model.Airplane;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AirplaneRepository extends MongoRepository<Airplane, String> {
}