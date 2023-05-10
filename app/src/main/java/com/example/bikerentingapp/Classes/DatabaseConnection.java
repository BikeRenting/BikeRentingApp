package com.example.bikerentingapp.Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import android.util.Pair;

public class DatabaseConnection {

    private static String host = "wypozyczalnia-rowerow.mysql.database.azure.com";
    private static String port = "3306";
    private static String dbname = "wypozyczalniadb";
    private static String username = "dev";
    private static String password = "zdamyT3projektowe";

    private static String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname + "?useSSL=false&characterEncoding=utf8";

    public static Connection connectToDb() {

        Connection mycon = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            mycon = DriverManager.getConnection(url, username, password);
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

            while (rs.next()) {
                return rs.getString(5); // return fifth column (username)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "null"; // if not found, return "null"
    }

    public static ArrayList<Station> getStations() {

        Connection con = connectToDb();
        ResultSet rs = null;
        PreparedStatement pst = null;

        ArrayList<Station> stations = new ArrayList<Station>();

        try {
            String sql = "SELECT * FROM stacja";
            pst = connectToDb().prepareCall(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                stations.add(
                        new Station(rs.getInt("id_stacji"),
                                rs.getInt("wolne_miejsca"),
                                rs.getInt("max_pojemnosc"),
                                new Pair<>(rs.getDouble("szerokosc_geo"),
                                        rs.getDouble("dlugosc_geo"))));
            }
            return stations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // if not found, return "null"
    }

    public static ArrayList<Integer> getAvailableBikes() {

        Connection con = connectToDb();
        ResultSet rs = null;
        PreparedStatement pst = null;

        ArrayList<Integer> availableBikes = new ArrayList<Integer>();

        try {
            String sql = "SELECT dostepny from rower;";
            pst = connectToDb().prepareCall(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                availableBikes.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableBikes;
    }

    public static ArrayList<Integer> getAvailableStations() {

        Connection con = connectToDb();
        ResultSet rs = null;
        PreparedStatement pst = null;

        ArrayList<Integer> availableStations = new ArrayList<Integer>(); //indexing from 0 (availableBikes[0] is number of available bikes from station 1)

        try {
            String sql = "SELECT ID_stacji from stacja; ";
            pst = connectToDb().prepareCall(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                availableStations.add(rs.getInt("id_stacji"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableStations;
    }


    public static boolean ifExist(String username, String email, String phone) {
        Connection con = connectToDb();
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM `klient` WHERE nazwa_uzytkownika='" + username + "' OR adres_email='" + email + "' OR nr_telefonu=" + phone;
            rs = con.prepareCall(sql).executeQuery();

            if (rs.next() == false) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean createAccount(String username, String email, String phone, String password) {
        Connection con = connectToDb();
        try {
            String sql = "INSERT INTO klient (adres_email, nr_telefonu, stan_konta, nazwa_uzytkownika, haslo) VALUES ('" + email + "', '" + phone + "', 0, '" + username + "', '" + password + "')";
            con.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ResultSet getCustomer(String username) {
        Connection con = connectToDb();
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM `klient` WHERE nazwa_uzytkownika='" + username + "'";
            rs = con.prepareCall(sql).executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return rs;
        }
    }

    public static ResultSet getServiceman(String username) {
        Connection con = connectToDb();
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM `pracownik` WHERE nazwa_uzytkownika='" + username + "'";
            rs = con.prepareCall(sql).executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return rs;
        }
    }

    public static int getLastHireID() {
        Connection con = connectToDb();
        ResultSet rs = null;
        int id = 0;
        try {
            String sql = "SELECT MAX(id_wypozyczenia) FROM wypozyczenie;";
            rs = con.prepareCall(sql).executeQuery();
            while (rs.next()) {
                id = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return id;
    }

    public static Bike getBike(int bikeID) {
        Connection con = connectToDb();
        ResultSet rs = null;
        Bike bike = null;
        try {
            String sql = "SELECT * FROM rower WHERE id_roweru = " + bikeID + " ;";
            rs = con.prepareCall(sql).executeQuery();
            while (rs.next()) {
                bike = new Bike(rs.getInt("id_roweru"),
                        rs.getString("stan_techniczny"),
                        rs.getInt("id_stacji"),
                        rs.getInt("dostepny") != 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return bike;
    }

    public static boolean addNewHire(int hire_id, int time, int length, double payment, int bikeID, String date, int isPaymentRealized, int customer_id) {
        Connection con = connectToDb();
        try {
            String sql = "INSERT INTO `wypozyczenie` (`id_wypozyczenia`, `czas_przejazdu`, `id_klienta`, `id_roweru`, `data_rozpoczecia`, `dystans`, `kwota`, `czy_oplacone`) VALUES (" +
                    hire_id + ", " + //id_wypozyczenia
                    time + ", " + //czas
                    customer_id + ", " + //klient_id_klienta
                    bikeID + ", " + //id_roweru
                    "\"" + date + "\"" + ", " + //data_rozpoczecia
                    length + ", " + //dystans
                    payment + ", " + //kwota
                    isPaymentRealized + // czy_oplacone
                    ");";
            con.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateHire(int hire_id, int time, int length, double payment) {
        Connection con = connectToDb();
        try {
            String sql = "UPDATE `wypozyczenie` SET " + "czas_przejazdu = " + time + ", dystans = " + length + ", kwota = " + payment + " WHERE id_wypozyczenia = " + hire_id + ";";
            con.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateBike(int bike_id, String condition, int station_id, int available) {
        Connection con = connectToDb();
        try {
            String sql = "UPDATE `rower` SET " + "dostepny = " + available + ", id_stacji = " + station_id + ", stan_techniczny = " + "\'" + condition + "\'" + " WHERE id_roweru = " + bike_id + ";";
            con.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean rechargeWallet(int userID, double balance) {
        Connection con = connectToDb();
        try {
            String sql = "UPDATE `klient` SET stan_konta = " + balance + " WHERE id_klienta =" + userID + ";";
            con.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
