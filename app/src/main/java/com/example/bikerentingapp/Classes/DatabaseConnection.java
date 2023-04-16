package com.example.bikerentingapp.Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {

    private static String host = "wypozyczalnia-rowerow.mysql.database.azure.com";
    private static String port = "3306";
    private static String dbname = "wypozyczalniadb";
    private static String username = "dev";
    private static String password = "zdamyT3projektowe";

    private static String url="jdbc:mysql://" + host + ":" + port + "/" + dbname + "?useSSL=false&characterEncoding=utf8";

    public static Connection connectToDb(){

        Connection mycon = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            mycon = DriverManager.getConnection(url,  username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return mycon;
    }

    public static String getUserById(int user_id) {

        Connection con = connectToDb();
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            String sql = "SELECT * FROM `klient` WHERE id_klienta=" + user_id;
            pst = connectToDb().prepareCall(sql);
            rs = pst.executeQuery();

            while(rs.next()){
                return rs.getString(5); // return fifth column (username)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "null"; // if not found, return "null"
    }
}
