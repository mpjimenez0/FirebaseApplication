package com.example.asus.auth2;

/**
 * Created by asus on 12/30/2017.
 */

public class info {

    String infoId;
    String infoGender;
    String infoCity;
    String email;
    String infoBirthdate;


    public info() {

    }

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

