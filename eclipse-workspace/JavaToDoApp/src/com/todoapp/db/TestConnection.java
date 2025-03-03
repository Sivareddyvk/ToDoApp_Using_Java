package com.todoapp.db;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            System.out.println(" Connection Successful!");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
