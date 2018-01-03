package com.example.entunix_jimenez.firebaseapplication;

/**
 * Created by mpjimenez on 1/3/2018.
 */

public class UserInfo {
    public String reg_name, reg_email, reg_gender, reg_city, reg_date;

    public UserInfo(){

    }

    public UserInfo(String reg_name, String reg_email, String reg_gender, String reg_city, String reg_date){
        this.reg_name = reg_name;
        this.reg_email = reg_email;
        this.reg_gender = reg_gender;
        this.reg_city = reg_city;
        this.reg_date = reg_date;
    }

    public String getReg_name() {
        return reg_name;
    }

    public void setReg_name(String reg_name) {
        this.reg_name = reg_name;
    }

    public String getReg_email() {
        return reg_email;
    }

    public void setReg_email(String reg_email) {
        this.reg_email = reg_email;
    }

    public String getReg_gender() {
        return reg_gender;
    }

    public void setReg_gender(String reg_gender) {
        this.reg_gender = reg_gender;
    }

    public String getReg_city() {
        return reg_city;
    }

    public void setReg_city(String reg_city) {
        this.reg_city = reg_city;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }
}
