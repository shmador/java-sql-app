package com.mycompany.app;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class Controller {
    @RequestMapping("/exercises")
    @CrossOrigin
    public List<DataObject> getExercises(@ModelAttribute Optional<Exercise> exercise) {
        Connection conn = DatabaseConnector.connect();
        return DatabaseOperations.showAll(conn, exercise.orElse(null));
    }
    @PostMapping("/create")
    @CrossOrigin
    public void createExercise(@RequestBody Exercise exercise) {
        System.out.println(exercise);
        Connection conn = DatabaseConnector.connect();
        DatabaseOperations.insertRecord(conn, exercise);
    }
    @PostMapping("/delete")
    @CrossOrigin
    public void deleteExercise(@RequestBody Exercise exercise) {
        System.out.println(exercise);
        Connection conn = DatabaseConnector.connect();
        DatabaseOperations.deleteRecord(conn, exercise);
    }
}
