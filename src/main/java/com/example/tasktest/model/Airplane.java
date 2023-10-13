package com.example.tasktest.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "airplane")
public class Airplane {
    @Id
    private String id;
    private AirplaneCharacteristics characteristics;
    private TemporaryPoint position;
    private List<Flight> flights;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AirplaneCharacteristics getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(AirplaneCharacteristics characteristics) {
        this.characteristics = characteristics;
    }

    public TemporaryPoint getPosition() {
        return position;
    }

    public void setPosition(TemporaryPoint position) {
        this.position = position;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    // Constructors, getters, and setters
}