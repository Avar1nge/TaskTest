package com.example.tasktest.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "flights")
public class Flight {
    @Id
    private String id;
    private Long length;
    private List<WayPoint> waypoints;
    private List<TemporaryPoint> passedPoints;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public List<WayPoint> getWaypoints() {
        return waypoints == null ? List.of() : waypoints;
    }

    public void setWaypoints(List<WayPoint> waypoints) {
        this.waypoints = waypoints;
    }

    public List<TemporaryPoint> getPassedPoints() {
        return passedPoints;
    }

    public void setPassedPoints(List<TemporaryPoint> passedPoints) {
        this.passedPoints = passedPoints;
    }

    // Constructors, getters, and setters
}