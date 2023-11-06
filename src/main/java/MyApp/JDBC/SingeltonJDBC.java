package MyApp.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingeltonJDBC {

    private static SingeltonJDBC single_instance = null;
    public  static Connection connection ;

    public  static String url = "jdbc:sqlserver://localhost:1433;database=Credentials;encrypt=true;trustServerCertificate=true;";
//this will save you from many errors
    public  static String user = "sa";
    public  static String password = "reallyStrongPwd123";

    private SingeltonJDBC(){
            System.out.println(">Wait a minute please<");



            try {
                connection = DriverManager.getConnection(url,user,password);
                System.out.println("Connected! AHAHAHHA");
            } catch (SQLException e) {
                System.out.println("NOOOOOOO!!!! NOT-CONNECTED");
                throw new RuntimeException(e);
            }
    }
    public static synchronized SingeltonJDBC getInstance() {
            if (single_instance == null)
                single_instance = new SingeltonJDBC();

            return single_instance;
    }

    public  Connection getConnection() {
        return connection;
    }

    public boolean isConnected(){
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

