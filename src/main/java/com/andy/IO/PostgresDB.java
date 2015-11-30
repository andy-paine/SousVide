package com.andy.IO;

import com.andy.Util.*;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 05/10/15.
 */
public class PostgresDB implements IDataStore {
    private Connection conn = null;
    private final String dbURL = "jdbc:postgresql://localhost:5432/sousvide";
    private final String username = "postgres";
    private final String password = "password";

    public PostgresDB() {
        try {
            conn = DriverManager.getConnection(dbURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean newCycle(Cycle cycle) {
        try {
            int id = -1;

            String query = "INSERT INTO cycle (start_datetime, food, rating, comments, completed) VALUES (?, '"
                    + cycle.getFood() + "', " + cycle.getRating() + ", '" + cycle.getComments() + "', FALSE)" +
                    "RETURNING id";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setTimestamp(1, new Timestamp(cycle.getDate().getTime()));
            System.out.println(query);
            ResultSet key = statement.executeQuery();
            if (key.next())
                id = key.getInt("id");
            statement.close();

            for (Stage stage: cycle.getStages()) {
                String stageQuery = "INSERT INTO stage (cycle_id, temperature, duration) VALUES (" +
                        id + ", " + stage.getTemperature() + ", "+ stage.getDuration().toMinutes() +")";
                System.out.println(stageQuery);
                PreparedStatement preparedStatement = conn.prepareStatement(stageQuery);
                preparedStatement.execute();
                preparedStatement.close();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Cycle> getQueue() {
        try {
            String query = "SELECT * FROM cycle WHERE completed = FALSE";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet cycles = statement.executeQuery();
            return getCyclesFromResults(cycles);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cycle> getHistory() {
        try {
            String query = "SELECT * FROM cycle WHERE completed = TRUE";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet cycles = statement.executeQuery();
            return getCyclesFromResults(cycles);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Temperature getTemperature() {
        List<Temperature> temps = getTemperatures();
        if (!temps.isEmpty())
            return temps.get(temps.size()-1);
        return null;
    }

    @Override
    public List<Temperature> getTemperatures() {
        try {
            List<Temperature> temps = new ArrayList<>();
            String query = "SELECT * FROM temperatures";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet temperatures = statement.executeQuery();
            while (temperatures.next())
                temps.add(new Temperature(temperatures.getDouble("temperature"), new Date(temperatures.getDate("datetime").getTime())));
            return temps;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean logTemperature(Temperature temp) {
        Double temperature = temp.temperature();
        Date date = temp.date();
        try {
            String query = "INSERT INTO temperatures VALUES (?, " + temperature + ")";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setTimestamp(1, new Timestamp(date.getTime()));
            System.out.println(query);
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateCycle(String id, List<Pair<String, ?>> updates) {
        return false;
    }

    @Override
    public boolean cancelCycle(String cycleID) {
        try {
            String updateQuery = "DELETE FROM cycle WHERE id = " + Integer.getInteger(cycleID);
            Statement statement = conn.createStatement();
            return statement.executeUpdate(updateQuery) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean moveToHistory(String cycleID) {
        try {
            String updateQuery = "UPDATE cycle SET completed = TRUE WHERE id = " + Integer.getInteger(cycleID);
            Statement statement = conn.createStatement();
            return statement.executeUpdate(updateQuery) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Cycle> getCyclesFromResults(ResultSet results) throws SQLException {
        List<Cycle> queue = new ArrayList<Cycle>();
        while (results.next()) {
            Cycle cycle = new Cycle(
                    results.getTimestamp("start_datetime"),
                    results.getString("food"),
                    results.getInt("rating"),
                    results.getString("comments"),
                    new ArrayList<Stage>(),
                    false);
            String stageQuery = "SELECT * FROM stage WHERE cycle_id = " + results.getInt("id");
            ResultSet stages = conn.prepareStatement(stageQuery).executeQuery();
            while (stages.next())
                cycle.addStage(new Stage(stages.getDouble("temperature"), stages.getInt("duration")));
            queue.add(cycle);
        }
        return queue;
    }
}
