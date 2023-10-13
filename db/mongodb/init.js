
db = db.getSiblingDB('airplanes');

db.createUser({
    user: "airplanesUser",
    pwd: "airplanesPass",
    roles: [{ role: "readWrite", db: "airplanes" }]
});

db.createCollection('airplane');
db.createCollection('airplaneCharacteristics');
db.createCollection('flights');
db.createCollection('waypoints');
db.createCollection('temporarypoints');

const airplanesData = [
    {
        characteristics: {
            maxSpeed: 300.0,
            maxAcceleration: 10.0,
            maxVerticalSpeed: 5.0,
            maxCourseChangeSpeed: 30.0
        },
        flights: [
            {
                length: 125,
                waypoints: [],
                passedPoints: []
            },
            {
                length: 200,
                waypoints: [],
                passedPoints: []
            },
            {
                length: 0,
                waypoints: [
                    { latitude: 90, longitude: 90, altitude: 1000, speed: 200 },
                    { latitude: 41.7128, longitude: -74.0060, altitude: 1200, speed: 220 }
                ],
                passedPoints: []
            },
        ],
        position: {
            latitude: 75,
            longitude: 75,
            altitude: 1000,
            speed: 0,
            course: 45
        }
    },
    {
        characteristics: {
            maxSpeed: 350.0,
            maxAcceleration: 15.0,
            maxVerticalSpeed: 6.0,
            maxCourseChangeSpeed: 35.0
        },
        flights: [
            {
                length: 125,
                waypoints: [],
                passedPoints: []
            },
            {
                length: 300,
                waypoints: [],
                passedPoints: []
            },
            {
                length: 0,
                waypoints: [
                    { latitude: 60, longitude: 60, altitude: 800, speed: 180 },
                    { latitude: 43.7128, longitude: -74.0060, altitude: 900, speed: 210 },
                    { latitude: 44.7128, longitude: -74.0060, altitude: 1100, speed: 230 }
                ],
                passedPoints: []
            }
        ],
        position: {
            latitude: 30,
            longitude: 30,
            altitude: 800,
            speed: 0,
            course: 45
        }
    },
    {
        characteristics: {
            maxSpeed: 320.0,
            maxAcceleration: 12.0,
            maxVerticalSpeed: 4.0,
            maxCourseChangeSpeed: 28.0
        },
        flights: [
            {
                length: 125,
                waypoints: [],
                passedPoints: []
            },
            {
                length: 90,
                waypoints: [],
                passedPoints: []
            },
            {
                length: 0,
                waypoints: [
                    { latitude: 46.7128, longitude: -75.0060, altitude: 950, speed: 190 },
                    { latitude: 41.7128, longitude: -82.0060, altitude: 1050, speed: 210 }
                ],
                passedPoints: []
            }
        ],
        position: {
            latitude: 45.7128,
            longitude: -74.0060,
            altitude: 950,
            speed: 0,
            course: 0
        }
    }
];

db.airplane.insertMany(airplanesData);

print("Initialization script completed.");
