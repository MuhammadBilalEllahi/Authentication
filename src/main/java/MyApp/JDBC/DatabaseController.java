package MyApp.JDBC;

import java.sql.*;

import static MyApp.JDBC.SingeltonJDBC.*;

public class DatabaseController {


    public  DatabaseController() {
        try {
            // Step 1: Load the MyApp.JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void connect() {
        try {
            // Step 2: Establish the database connection
            connection = DriverManager.getConnection(url, user , password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void pauseDatabase() {
        try {
            // Step 3: Execute the SQL command to pause the database
            Statement statement = connection.createStatement();
            String sql = "ALTER DATABASE Credentials SET OFFLINE WITH ROLLBACK IMMEDIATE";
            statement.executeUpdate(sql);
            System.out.println(" Pausing the database");
            isDatabaseResumed();
            isDatabasePaused();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void resumeDatabase() {
        try {
            // Step 3: Execute the SQL command to resume the database
            Statement statement = connection.createStatement();
            String sql = "ALTER DATABASE Credentials SET ONLINE";
            statement.executeUpdate(sql);
            System.out.println(" Resuming the database");
            isDatabaseResumed();
            isDatabasePaused();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean isDatabasePaused() {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT state_desc FROM sys.databases WHERE name = 'Credentials'";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String state = resultSet.getString("state_desc");
                return state.equalsIgnoreCase("offline");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isDatabaseResumed() {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT state_desc FROM sys.databases WHERE name = 'Credentials'";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String state = resultSet.getString("state_desc");
                return state.equalsIgnoreCase("online");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void disconnect() {
        try {
            // Close the database connection
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
