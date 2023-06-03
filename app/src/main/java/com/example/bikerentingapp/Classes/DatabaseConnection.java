package com.example.bikerentingapp.Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import android.util.Pair;

public class DatabaseConnection {

    private static String host = "wypozyczalnia-rowerow.mysql.database.azure.com";
    private static String port = "3306";
    private static String dbname = "wypozyczalniadb";
    private static String username = "dev";
    private static String password = "zdamyT3projektowe";

    private static String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname + "?useSSL=false&characterEncoding=utf8";

    private static Connection con;

    public static void connectToDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getUserById(int user_id) {
        if (!isConnectionValid())
            connectToDb();
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            String sql = "SELECT * FROM `klient` WHERE id_klienta=" + user_id;
            pst = con.prepareCall(sql);
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
        if (!isConnectionValid())
            connectToDb();
        ResultSet rs = null;
        PreparedStatement pst = null;

        ArrayList<Station> stations = new ArrayList<Station>();

        try {
            String sql = "SELECT * FROM stacja";
            pst = con.prepareCall(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                stations.add(
                        new Station(rs.getInt("id_stacji"),
                                rs.getInt("wolne_miejsca"),
                                rs.getInt("max_pojemnosc"),
                                new Pair<>(rs.getDouble("szerokosc_geo"),
                                        rs.getDouble("dlugosc_geo"))));
            }
            stations.remove(0);
            return stations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // if not found, return "null"
    }

    public static boolean isBikeAvailable(int bikeID) {
        if (!isConnectionValid())
            connectToDb();
        ResultSet rs = null;
        PreparedStatement pst = null;

        boolean isAvailable = false;

        try {
            String sql = "SELECT dostepny FROM rower WHERE id_roweru = " + bikeID;
            pst = con.prepareCall(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                if (rs.getInt(1) == 1)
                    isAvailable = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAvailable;
    }

    public static ArrayList<Integer> getAvailableBikes(int availability) {
        if (!isConnectionValid())
            connectToDb();
        ResultSet rs = null;
        PreparedStatement pst = null;

        ArrayList<Integer> availableBikes = new ArrayList<Integer>();

        try {
            String sql = "SELECT stacja.id_stacji, COUNT(case rower.dostepny when '" + availability + "' then 1 else null end) FROM stacja LEFT JOIN rower ON stacja.id_stacji = rower.id_stacji GROUP BY stacja.id_stacji;";
            pst = con.prepareCall(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                availableBikes.add(rs.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableBikes;
    }

    public static ArrayList<Integer> getAvailableStations() {
        if (!isConnectionValid())
            connectToDb();
        ResultSet rs = null;
        PreparedStatement pst = null;

        ArrayList<Integer> availableStations = new ArrayList<Integer>();

        try {
            String sql = "SELECT ID_stacji from stacja; ";
            pst = con.prepareCall(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                availableStations.add(rs.getInt("id_stacji"));
            }
            availableStations.remove(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableStations;
    }


    public static Station getStation(int stationID) {
        if (!isConnectionValid())
            connectToDb();
        ResultSet rs = null;
        Station station = null;

        try {
            String sql = "SELECT * FROM `stacja` WHERE id_stacji = " + stationID + ";";
            rs = con.prepareCall(sql).executeQuery();
            while (rs.next()) {
                station = new Station(rs.getInt("id_stacji"),
                        rs.getInt("wolne_miejsca"),
                        rs.getInt("max_pojemnosc"),
                        new Pair<>(rs.getDouble("szerokosc_geo"),
                                rs.getDouble("dlugosc_geo")));
            }
            return station;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean ifExist(String username, String email, String phone) {
        if (!isConnectionValid())
            connectToDb();
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
        if (!isConnectionValid())
            connectToDb();
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
        if (!isConnectionValid())
            connectToDb();
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
        if (!isConnectionValid())
            connectToDb();
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
        if (!isConnectionValid())
            connectToDb();
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

    public static int getLastReservationID() {
        if (!isConnectionValid())
            connectToDb();
        ResultSet rs = null;
        int id = 0;
        try {
            String sql = "SELECT MAX(id_rezerwacji) FROM rezerwacja;";
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
        if (!isConnectionValid())
            connectToDb();
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

    public static boolean removeBike(int bikeID) {
        if (!isConnectionValid()) {
            connectToDb();
        }
        try {
            String sql = "DELETE FROM rower WHERE id_roweru = " + bikeID + ";";
            con.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Bike> getBikesInStation(int stationID) {
        ResultSet rs = null;
        ArrayList<Bike> bikes = new ArrayList<>();
        if (!isConnectionValid())
            connectToDb();
        try {
            String sql = "SELECT * FROM `rower` WHERE id_stacji = " + stationID + ";";
            rs = con.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                bikes.add(new Bike(
                        rs.getInt("id_roweru"),
                        rs.getString("stan_techniczny"),
                        rs.getInt("id_stacji"),
                        rs.getInt("dostepny") != 0
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bikes;
    }

    public static ArrayList<Bike> getBikesAvailableInStation(int stationID) {
        ResultSet rs = null;
        ArrayList<Bike> bikes = new ArrayList<>();

        if (!isConnectionValid())
            connectToDb();
        try {
            String sql = "SELECT * FROM `rower` WHERE id_stacji = " + stationID + " AND dostepny = 1;";
            rs = con.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                bikes.add(new Bike(
                        rs.getInt("id_roweru"),
                        rs.getString("stan_techniczny"),
                        rs.getInt("id_stacji"),
                        rs.getInt("dostepny") != 0
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bikes;
    }

    public static boolean addNewHire(int hire_id, int time, int length, double payment, int bikeID, String date, int isPaymentRealized, int customer_id, double remaining_payment) {
        if (!isConnectionValid())
            connectToDb();
        try {
            String sql = "INSERT INTO `wypozyczenie` (`id_wypozyczenia`, `czas_przejazdu`, `id_klienta`, `id_roweru`, `data_rozpoczecia`, `dystans`, `kwota`, `czy_oplacone`, `ile_do_zaplaty`) VALUES (" +
                    hire_id + ", " + //id_wypozyczenia
                    time + ", " + //czas
                    customer_id + ", " + //klient_id_klienta
                    bikeID + ", " + //id_roweru
                    "\"" + date + "\"" + ", " + //data_rozpoczecia
                    length + ", " + //dystans
                    payment + ", " + //kwota
                    isPaymentRealized + ", " + // czy_oplacone
                    remaining_payment + // ile_do_zaplaty
                    ");";
            con.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateHire(int hire_id, int time, int length, double payment, int isPaid, double remainingPayment) {
        if (!isConnectionValid())
            connectToDb();
        int hours = time / 3600;
        int minutes = (time - (3600 * hours)) / 60;
        int seconds = time % 60;

        String _time = Integer.toString(hours) + ":" + Integer.toString(minutes) + ":" + Integer.toString(seconds);


        try {
            String sql = "UPDATE `wypozyczenie` SET " + "czas_przejazdu = \'" + _time + "\', dystans = " + length + ", kwota = " + payment + ", czy_oplacone = " + isPaid + ", ile_do_zaplaty = " + remainingPayment + " WHERE id_wypozyczenia = " + hire_id + ";";
            con.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateBike(int bike_id, String condition, int station_id, int available) {
        if (!isConnectionValid())
            connectToDb();
        try {
            String sql = "UPDATE `rower` SET " + "dostepny = " + available + ", id_stacji = " + station_id + ", stan_techniczny = " + "\'" + condition + "\'" + " WHERE id_roweru = " + bike_id + ";";
            con.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateBikeStatus(int bike_id, int available) {
        if (!isConnectionValid())
            connectToDb();
        try {
            String sql = "UPDATE `rower` SET " + "dostepny = " + available + " WHERE id_roweru = " + bike_id + ";";
            con.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean rechargeWallet(int userID, double balance) {
        if (!isConnectionValid())
            connectToDb();
        try {
            String sql = "UPDATE `klient` SET stan_konta = " + balance + " WHERE id_klienta =" + userID + ";";
            con.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Hire> getUserHires(int userID) {
        if (!isConnectionValid())
            connectToDb();
        ArrayList<Hire> userHires = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM `wypozyczenie` WHERE id_klienta = " + userID + ";";
            resultSet = con.prepareCall(sql).executeQuery();
            while (resultSet.next()) {

                userHires.add(new Hire(
                        resultSet.getInt("id_wypozyczenia"),
                        resultSet.getInt("id_klienta"),
                        new Bike(resultSet.getInt("id_roweru")),
                        Hire.timeToInt(resultSet.getTime("czas_przejazdu")),
                        resultSet.getInt("dystans"),
                        resultSet.getDouble("kwota"),
                        resultSet.getInt("czy_oplacone") == 1,
                        resultSet.getString("data_rozpoczecia"),
                        resultSet.getDouble("ile_do_zaplaty")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userHires;
    }

    public static ArrayList<Integer> getUserHiresID(int userID) {
        if (!isConnectionValid()) {
            connectToDb();
        }
        ResultSet rs = null;
        ArrayList<Integer> hiresID = new ArrayList<>();
        try {
            String sql = "SELECT id_wypozyczenia FROM `wypozyczenie` WHERE id_klienta = " + userID + ";";
            rs = con.prepareCall(sql).executeQuery();
            while (rs.next()) {
                hiresID.add(rs.getInt("id_wypozyczenia"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hiresID;
    }

    public static ArrayList<Integer> getBikesID(){
        if(!isConnectionValid())
            connectToDb();

        ResultSet rs = null;
        ArrayList<Integer> bikesID = new ArrayList<>();

        try{
            String sql = "SELECT id_roweru FROM `rower`;";
            rs = con.prepareCall(sql).executeQuery();
            while(rs.next()){
                bikesID.add(rs.getInt("id_roweru"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bikesID;
    }


    public static ArrayList<Reservation> getUserReservations(int userID) {
        if (!isConnectionValid())
            connectToDb();
        ArrayList<Reservation> userReservations = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM `rezerwacja` WHERE id_klienta = " + userID + ";";
            resultSet = con.prepareCall(sql).executeQuery();
            while (resultSet.next()) {

                userReservations.add(new Reservation(
                        resultSet.getInt("id_rezerwacji"),
                        resultSet.getInt("id_klienta"),
                        resultSet.getInt("id_roweru"),
                        resultSet.getInt("czy_zrealizowana") == 1,
                        resultSet.getString("data_utworzenia"),
                        resultSet.getString("data_wygasniecia")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userReservations;
    }

    public static boolean addNewReservation(int reservation_id, int customer_id, int bike_id, int isRealized, String startDate, String endDate) {
        if (!isConnectionValid())
            connectToDb();
        try {
            String sql = "INSERT INTO `rezerwacja` (`id_rezerwacji`, `id_klienta`, `id_roweru`, `czy_zrealizowana`, `data_utworzenia`, `data_wygasniecia`) VALUES (" +
                    reservation_id + ", " +
                    customer_id + ", " +
                    bike_id + ", " +
                    isRealized + ", " +
                    "\"" + startDate + "\"" + ", " +
                    "\"" + endDate + "\"" +
                    ");";
            con.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateReservation(int reservation_id, int isRealized) {
        if (!isConnectionValid())
            connectToDb();

        try {
            String sql = "UPDATE `rezerwacja` SET " + "czy_zrealizowana = " + isRealized + " WHERE id_rezerwacji = " + reservation_id + ";";
            con.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void updateReservationsStatus() {
        ResultSet rs = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        if (!isConnectionValid())
            connectToDb();
        try {
            String sql = "SELECT * FROM `rezerwacja` WHERE czy_zrealizowana = 0;";
            rs = con.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                String str = rs.getString("data_wygasniecia");
                str = str.substring(0, str.length() - 2);
                LocalDateTime end_date = LocalDateTime.parse(str, dtf);
                if (now.isAfter(end_date)) {
                    updateReservation(rs.getInt("id_rezerwacji"), 1);
                    updateBikeStatus(rs.getInt("id_roweru"), 1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean addBikeAndUpdate(String condition, int stationID, int availability) {
        if (!isConnectionValid())
            connectToDb();
        try {
            String sqlAddBike = "INSERT INTO rower (stan_techniczny, id_stacji, dostepny) VALUES (\'" + condition + "\', " + stationID + ", " + availability + ");";
            con.createStatement().executeUpdate(sqlAddBike);
            decrementFreeSpace(stationID);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean decrementFreeSpace(int stationID) {
        if (!isConnectionValid())
            connectToDb();
        try {
            String sqlUpdateStation = "UPDATE `stacja` SET wolne_miejsca = wolne_miejsca -" + 1 + " WHERE id_stacji = " + stationID + ";";
            con.createStatement().executeUpdate(sqlUpdateStation);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean incrementFreeSpace(int stationID) {
        if (!isConnectionValid())
            connectToDb();
        try {
            String sqlUpdateStation = "UPDATE `stacja` SET wolne_miejsca = wolne_miejsca +" + 1 + " WHERE id_stacji = " + stationID + ";";
            con.createStatement().executeUpdate(sqlUpdateStation);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static double getFunds(int customerID) {
        if (!isConnectionValid())
            connectToDb();
        ResultSet rs = null;
        double funds = 0;
        try {
            String sql = "SELECT stan_konta FROM `klient` WHERE id_klienta=" + customerID;
            rs = con.prepareCall(sql).executeQuery();
            while (rs.next()) {
                funds = rs.getDouble(1);
            }
            return funds;
        } catch (SQLException e) {
            e.printStackTrace();
            return funds;
        }
    }

    public static boolean makeComplaint(int customerID, int hireID, String dateOfFilling, String description, String complaintType) {
        if (!isConnectionValid())
            connectToDb();
        try {
            String sql = "INSERT INTO `reklamacja` (data_rozpoczecia, id_wypozyczenia, id_klienta, opis, typ) VALUES (\'" + dateOfFilling + "\', " + hireID + ", " + customerID + ", \'" + description + "\', \'" + complaintType + "\');";
            con.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean reportDamage(int customerID, String dateOfFilling, int bikeID, String description) {
        if (!isConnectionValid())
            connectToDb();
        try {
            String sql = "INSERT INTO `awaria` (data_rozpoczecia, id_klienta,id_roweru, opis) VALUES (\'" + dateOfFilling + "\', " + customerID + ", " + bikeID + ", \'" + description + "\');";
            con.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isConnectionValid() {
        try {
            if (con != null && !con.isClosed()) {
                // Running a simple validation query
                con.prepareStatement("SELECT 1");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }



}
