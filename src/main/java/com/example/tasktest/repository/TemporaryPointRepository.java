package com.example.tasktest.repository;

import com.example.tasktest.model.TemporaryPoint;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemporaryPointRepository extends MongoRepository<TemporaryPoint, String> {
}