package com.example.tasktest.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "airplaneCharacteristics")
public class AirplaneCharacteristics {
    @Id
    private String id;
    private double maxSpeed;
    private double maxAcceleration;
    private double maxVerticalSpeed;
    private double maxCourseChangeSpeed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getMaxAcceleration() {
        return maxAcceleration;
    }

    public void setMaxAcceleration(double maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }

    public double getMaxVerticalSpeed() {
        return maxVerticalSpeed;
    }

    public void setMaxVerticalSpeed(double maxVerticalSpeed) {
        this.maxVerticalSpeed = maxVerticalSpeed;
    }

    public double getMaxCourseChangeSpeed() {
        return maxCourseChangeSpeed;
    }

    public void setMaxCourseChangeSpeed(double maxCourseChangeSpeed) {
        this.maxCourseChangeSpeed = maxCourseChangeSpeed;
    }

    // Constructors, getters, and setters
}