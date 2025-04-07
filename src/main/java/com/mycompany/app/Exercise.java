package com.mycompany.app;

public class Exercise implements DataObject{

    private String name;
    private Integer reps;
    private Integer sets;
    private Double weight;
    private Integer rest;
    private String muscle;
    private String date;

    public Exercise() {

    }

    public Exercise(String name, Integer reps, Integer sets, Double weight, Integer rest, String muscle, String date) {
        this.name = name;
        this.reps = reps;
        this.sets = sets;
        this.weight = weight;
        this.rest = rest;
        this.muscle = muscle;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getRest() {
        return rest;
    }

    public void setRest(Integer rest) {
        this.rest = rest;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return name + reps + sets + weight + rest + muscle + date;
    }
}
