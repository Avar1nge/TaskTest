package com.example.tasktest.job;

import com.example.tasktest.model.Airplane;
import com.example.tasktest.repository.AirplaneRepository;
import com.example.tasktest.model.Flight;
import com.example.tasktest.model.TemporaryPoint;
import com.example.tasktest.repository.TemporaryPointRepository;
import com.example.tasktest.model.WayPoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AirplaneCalculationService {

  private final TemporaryPointRepository temporaryPointRepository;
  private final AirplaneRepository airplaneRepository;
  private final List<Airplane> onFlight = new ArrayList<>();

  public AirplaneCalculationService(TemporaryPointRepository temporaryPointRepository, AirplaneRepository airplaneRepository) {
    this.temporaryPointRepository = temporaryPointRepository;
    this.airplaneRepository = airplaneRepository;
  }

  public void startFlights() {
    List<Airplane> airplanes = airplaneRepository.findAll();
    onFlight.addAll(airplanes);
    airplanes.forEach(a -> {
      List<Flight> finishedFlights = a.getFlights().stream().filter(f -> f.getWaypoints().isEmpty()).collect(Collectors.toList());
      System.out.println("Airplane id: " + a.getId() + " flights finished: " + finishedFlights.size() +
              " total time: " + finishedFlights.stream().mapToDouble(Flight::getLength).sum() + "s");
    });
  }

  @Scheduled(fixedRate = 1000)
  public void updateAirplaneStats() {
    List<Airplane> airplanesToRemove = new ArrayList<>();
    for (Airplane airplane : onFlight) {
      Optional<Flight> optionalFlight = airplane.getFlights().stream().filter(f -> !f.getWaypoints().isEmpty()).findFirst();
      if (optionalFlight.isEmpty()) {
        airplanesToRemove.add(airplane);
        continue;
      }
      Flight currentFlight = optionalFlight.get();
      List<WayPoint> wayPoints = currentFlight.getWaypoints();

      TemporaryPoint currentPosition = airplane.getPosition();
      WayPoint nextWayPoint = wayPoints.get(0);

      double distance = calculateDistance(currentPosition, nextWayPoint);
      double timeToNextPoint = distance / airplane.getCharacteristics().getMaxSpeed();
      double courseChangeSpeed = airplane.getCharacteristics().getMaxCourseChangeSpeed();

      if (timeToNextPoint <= 1) {
        wayPoints.remove(0);
      }

      //calculating targetCourse
      double targetCourse = calculateCourse(currentPosition, nextWayPoint);
      double courseDifference = targetCourse - currentPosition.getCourse();
      double newCourse;
      if (Math.abs(courseDifference) < courseChangeSpeed) {
        newCourse = targetCourse;
      } else {
        newCourse = courseDifference < 0 ? currentPosition.getCourse() - courseChangeSpeed
                : currentPosition.getCourse() + courseChangeSpeed;
      }
      //Calculating avg speed
      double currentSpeed = Math.min(airplane.getCharacteristics().getMaxSpeed(), currentPosition.getSpeed() + airplane.getCharacteristics().getMaxAcceleration());
      double avgSpeed = (currentSpeed - currentPosition.getSpeed()) / 2;

      TemporaryPoint newPosition = calculateNewPosition(currentPosition,
              avgSpeed, airplane.getCharacteristics().getMaxVerticalSpeed(), targetCourse, newCourse);

      temporaryPointRepository.save(newPosition);

      airplane.setPosition(newPosition);
      currentFlight.getPassedPoints().add(newPosition);
      currentFlight.setLength(currentFlight.getLength() + 1);
      airplaneRepository.save(airplane);
    }
    onFlight.removeAll(airplanesToRemove);
  }

  private double calculateDistance(TemporaryPoint point1, WayPoint point2) {
    double lat1 = Math.toRadians(point1.getLatitude());
    double lon1 = Math.toRadians(point1.getLongitude());
    double lat2 = Math.toRadians(point2.getLatitude());
    double lon2 = Math.toRadians(point2.getLongitude());

    double dLat = lat2 - lat1;
    double dLon = lon2 - lon1;

    double a = Math.pow(Math.sin(dLat / 2), 2) +
            Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon / 2), 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    double earthRadius = 6400;
    return earthRadius * c;
  }

  private double calculateCourse(TemporaryPoint currentPoint, WayPoint nextPoint) {
    double lat1 = Math.toRadians(currentPoint.getLatitude());
    double lon1 = Math.toRadians(currentPoint.getLongitude());
    double lat2 = Math.toRadians(nextPoint.getLatitude());
    double lon2 = Math.toRadians(nextPoint.getLongitude());

    double dLon = lon2 - lon1;

    double y = Math.sin(dLon) * Math.cos(lat2);
    double x = Math.cos(lat1) * Math.sin(lat2) -
            Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);

    double initialBearing = Math.atan2(y, x);

    return (Math.toDegrees(initialBearing) + 360) % 360;
  }

  private TemporaryPoint calculateNewPosition(TemporaryPoint currentPoint, double avgSpeed,
                                              double altSpeed, double bearing, double course) {
    double timeInterval = 1.0;
    double distanceCovered = avgSpeed * timeInterval;

    double initialLat = Math.toRadians(currentPoint.getLatitude());
    double initialLon = Math.toRadians(currentPoint.getLongitude());

    double angularBearing = Math.toRadians(bearing);

    double angularDistance = distanceCovered / 6371000;

    double sinInitialLat = Math.sin(initialLat);
    double cosInitialLat = Math.cos(initialLat);
    double sinAngularDistance = Math.sin(angularDistance);
    double cosAngularDistance = Math.cos(angularDistance);
    double sinTargetLat = sinInitialLat * cosAngularDistance + cosInitialLat * sinAngularDistance * Math.cos(angularBearing);

    double targetLonRad = initialLon + Math.atan2(
            Math.sin(angularBearing) * sinAngularDistance * cosInitialLat,
            cosAngularDistance - sinInitialLat * sinTargetLat
    );

    double newLat = Math.toDegrees(Math.asin(sinTargetLat));
    double newLon = Math.toDegrees(targetLonRad);

    return new TemporaryPoint(newLat, newLon, currentPoint.getAltitude() + altSpeed, avgSpeed, course);
  }

}
