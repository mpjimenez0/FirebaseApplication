package com.example.entunix_jimenez.firebaseapplication;

/**
 * Created by asus on 1/3/2018.
 */

public class info {

    String infoId;
    String infoGender;
    String infoCity;
    String email;
    String infoBirthdate;

    public info(String infoId, String infoGender, String infoCity, String email, String infoBirthdate) {
        this.infoId = infoId;
        this.infoGender = infoGender;
        this.infoCity = infoCity;
        this.email = email;
        this.infoBirthdate = infoBirthdate;
    }

    public String getInfoId() {
        return infoId;
    }

    public String getInfoGender() {
        return infoGender;
    }

    public String getInfoCity() {
        return infoCity;
    }

    public String getEmail() {
        return email;
    }

    public String getInfoBirthdate() {
        return infoBirthdate;
    }
}
