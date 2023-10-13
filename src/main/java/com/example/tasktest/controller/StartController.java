package com.example.tasktest.controller;

import com.example.tasktest.job.AirplaneCalculationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/start")
public class StartController {

  private final AirplaneCalculationService airplaneCalculationService;

  public StartController(AirplaneCalculationService airplaneCalculationService) {
    this.airplaneCalculationService = airplaneCalculationService;
  }

  @PostMapping
  public void startFlights() {
    airplaneCalculationService.startFlights();
  }
}