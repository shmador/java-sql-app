package com.mycompany.app;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {
    private static final String TABLE_NAME = "exercises";

    /* 
    public static void createTable(Connection conn) {
        String sql = "CREATE TABLE " + TABLE_NAME + " ( name varchar(255), sets int, reps int, weight float, rest_time int, muscle_group varchar(255), workout_date date )";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void dropTable(Connection conn) {
        String sql = "DROP TABLE " + TABLE_NAME;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    */
    public static void insertRecord(Connection conn, Exercise exercise) {
        String sql = "INSERT INTO " + TABLE_NAME  + " ( name, sets, reps, weight, rest_time, muscle_group, workout_date ) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setParams(pstmt, exercise);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateRecord(Connection conn, Exercise exercise) {
        String sql = "UPDATE " + TABLE_NAME + " SET name = ?, sets = ?, reps = ?, weight = ?, rest_time = ?, muscle_group WHERE name = ? AND workout_date = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setParams(pstmt, exercise);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteRecord(Connection conn, Exercise exercise) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE name = ? AND reps = ? AND sets = ? AND weight = ? AND rest_time = ? AND muscle_group = ? AND workout_date = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setParams(pstmt, exercise);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<DataObject> showAll (Connection conn, Exercise exercise) {
        List<DataObject> exercises = new ArrayList<>();
        
        String sql = 
        "SELECT *" +
        " FROM " + TABLE_NAME +
        " WHERE 1=1";
        
        String volumeQuery = 
        "SELECT" +
        " muscle_group, " +
        " SUM(sets) as total_sets," +
        " SUM(reps*sets) as total_reps" +
        " FROM " + TABLE_NAME +
        " WHERE 1=1";
        
        String query="";

        boolean[] flags = {
            exercise.getName() != null, 
            exercise.getSets() != null, 
            exercise.getReps() != null, 
            exercise.getWeight() != null, 
            exercise.getRest() != null, 
            exercise.getMuscle() != null, 
            exercise.getDate() != null
        };

        int i = 0;

        if (flags[i++])
            query += " AND name = ?";
        if (flags[i++])
            query += " AND sets = ?";
        if (flags[i++])
            query += " AND reps = ?";
        if (flags[i++])
            query += " AND weight = ?";
        if (flags[i++])
            query += " AND rest_time = ?";
        if (flags[i++])
            query += " AND muscle_group = ?";
        if (flags[i++])
            query += " AND workout_date = ?";

        sql += query + " ORDER BY workout_date DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setParams(pstmt, exercise, flags);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                exercises.add(getExerciseFromParams(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        volumeQuery += query + " GROUP BY muscle_group";

        try (PreparedStatement pstmt = conn.prepareStatement(volumeQuery)) {
            setParams(pstmt, exercise, flags);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                exercises.add(getVolumeFromParams(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return exercises;
    }

    private static void setParams(PreparedStatement pstmt, Exercise exercise) throws SQLException{
        pstmt.setString(1, exercise.getName());
        pstmt.setInt(2, exercise.getSets());
        pstmt.setInt(3, exercise.getReps());
        pstmt.setDouble(4, exercise.getWeight());
        pstmt.setInt(5, exercise.getRest());
        pstmt.setString(6, exercise.getMuscle());
        if (exercise.getDate() != null)
            pstmt.setDate(7, Date.valueOf(exercise.getDate()));
        else
            pstmt.setNull(7, java.sql.Types.DATE);
    }

    private static void setParams(PreparedStatement pstmt, Exercise exercise, boolean[] flags) throws SQLException{
        int i = 0;
        
        if (flags[0])
            pstmt.setString(++i, exercise.getName());
        if (flags[1])
            pstmt.setInt(++i, exercise.getSets());
        if (flags[2])
            pstmt.setInt(++i, exercise.getReps());
        if (flags[3])
            pstmt.setDouble(++i, exercise.getWeight());
        if (flags[4])
            pstmt.setInt(++i, exercise.getRest());
        if (flags[5])
            pstmt.setString(++i, exercise.getMuscle());
        if (flags[6]) {
            pstmt.setDate(++i, Date.valueOf(exercise.getDate()));
        }
    }

    private static Exercise getExerciseFromParams(ResultSet rs) throws SQLException{
        return new Exercise(
            rs.getString("name"), 
            rs.getInt("sets"), 
            rs.getInt("reps"),  
            rs.getDouble("weight"), 
            rs.getInt("rest_time"), 
            rs.getString("muscle_group"), 
            rs.getDate("workout_date").toString()
        );
    }

    private static WorkoutVolume getVolumeFromParams(ResultSet rs) throws SQLException {
        return new WorkoutVolume(
            rs.getInt("total_sets"),
            rs.getInt("total_reps"), 
            rs.getString("muscle_group")
        );
    }
}