package com.example.tasktest.repository;

import com.example.tasktest.model.AirplaneCharacteristics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AirplaneCharacteristicsRepository extends MongoRepository<AirplaneCharacteristics, String> {
}