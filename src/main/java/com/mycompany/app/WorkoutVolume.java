package com.mycompany.app;

public class WorkoutVolume implements DataObject{
    private int totalSets;
    private int totalReps;
    private String muscleGroup;

    public WorkoutVolume(int totalSets, int totalReps, String muscleGroup) {
        this.totalSets = totalSets;
        this.totalReps = totalReps;
        this.muscleGroup = muscleGroup;
    }

    public int getTotalSets() {
        return totalSets;
    }

    public void setTotalSets(int totalSets) {
        this.totalSets = totalSets;
    }

    public int getTotalReps() {
        return totalReps;
    }

    public void setTotalReps(int totalReps) {
        this.totalReps = totalReps;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }
}
