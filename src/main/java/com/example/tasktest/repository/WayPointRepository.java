package com.example.tasktest.repository;

import com.example.tasktest.model.WayPoint;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WayPointRepository extends MongoRepository<WayPoint, String> {
}