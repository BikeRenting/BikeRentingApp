package com.example.bikerentingapp.Classes;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DamageReport {

    private int userID;
    private String dateOfFilling;
    private int bikeID;
    private String description;

    public DamageReport(int userID, int bikeID, String description) {
        this.userID = userID;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Warsaw"));
        this.dateOfFilling = dtf.format(now);
        this.bikeID = bikeID;
        this.description = description;
    }

    public boolean reportDamage(){
        return DatabaseConnection.reportDamage(userID, dateOfFilling, bikeID, description);
    }
}
