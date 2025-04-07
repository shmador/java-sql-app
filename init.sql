CREATE DATABASE IF NOT EXISTS db;
USE db;

CREATE TABLE IF NOT EXISTS exercises (
  name VARCHAR(255),
  sets INT,
  reps INT,
  weight FLOAT,
  rest_time INT,
  muscle_group VARCHAR(255),
  workout_date DATE
);

INSERT INTO exercises (name, sets, reps, weight, rest_time, muscle_group, workout_date) VALUES
('Bench Press', 4, 10, 80.5, 90, 'Chest', '2025-04-01'),
('Squat', 5, 8, 100.0, 120, 'Legs', '2025-04-02'),
('Deadlift', 4, 6, 120.0, 150, 'Back', '2025-04-03'),
('Overhead Press', 3, 10, 50.0, 90, 'Shoulders', '2025-04-04'),
('Barbell Row', 4, 8, 70.0, 90, 'Back', '2025-04-05'),
('Leg Press', 4, 12, 180.0, 60, 'Legs', '2025-04-06'),
('Bicep Curl', 3, 15, 20.0, 45, 'Arms', '2025-04-07'),
('Tricep Pushdown', 3, 12, 25.0, 60, 'Arms', '2025-04-01'),
('Lat Pulldown', 4, 10, 60.0, 90, 'Back', '2025-04-02'),
('Lateral Raise', 3, 15, 12.5, 30, 'Shoulders', '2025-04-03');

