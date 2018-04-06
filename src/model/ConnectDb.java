package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnectDb {

    private static Connection conn = null;
    public  static dbCredentials firstData;

    public static Connection connectDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("jdbc:mysql://" + firstData.getHost()+"/"+firstData.getDatabaseName()+" " + firstData.getDatabaseName()+ " " + firstData.getDatabasePass());
            conn = DriverManager.getConnection("jdbc:mysql://" + firstData.getHost()+"/"+firstData.getDatabaseName(), firstData.getDatabaseName(), firstData.getDatabasePass());
            //conn = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net/sql11219654", "sql11219654", "PdKKGZsc54");
            System.out.println("Connection acomplished.");
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Conection failed!");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static void updateDbCred(dbCredentials db) {
        firstData = db;
    }

}
