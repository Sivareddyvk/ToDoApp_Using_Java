package com.todoapp.services;

import com.todoapp.db.DatabaseConnection;
import com.todoapp.models.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskService {
    //   Add Task
    public void addTask(String title, String description, String priority) {
        String query = "INSERT INTO tasks (title, description, priority) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setString(3, priority);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //   Update Task
    public void updateTask(int id, String title, String description, String priority) {
        String query = "UPDATE tasks SET title = ?, description = ?, priority = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setString(3, priority);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //  Delete Task
    public void deleteTask(int id) {
        String query = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get All Tasks (with Optional Sorting)
    public List<Task> getTasks(String sortQuery) {
        List<Task> taskList = new ArrayList<>();
        String query = "SELECT * FROM tasks " + sortQuery;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String priority = rs.getString("priority");

                taskList.add(new Task(id, title, description, priority));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskList;
    }

    //  Get Sorted Tasks by Priority
    public List<Task> getSortedTasks() {
        return getTasks("ORDER BY priority ASC");  //  Now this works!
    }

    //  Get All Tasks without Sorting
    public List<Task> getTasks() {
        return getTasks("");  //  Works as intended now!
    }
}
