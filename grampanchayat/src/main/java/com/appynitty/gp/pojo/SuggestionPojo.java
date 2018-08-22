package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 30/5/18.
 */
public class SuggestionPojo implements Serializable {


    private String Name;
    private String Mobile_Number;
    private String Email_Address;
    private String Suggesstion;
    private String Address;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile_Number() {
        return Mobile_Number;
    }

    public void setMobile_Number(String mobile_Number) {
        Mobile_Number = mobile_Number;
    }

    public String getEmail_Address() {
        return Email_Address;
    }

    public void setEmail_Address(String email_Address) {
        Email_Address = email_Address;
    }

    public String getSuggesstion() {
        return Suggesstion;
    }

    public void setSuggesstion(String suggesstion) {
        Suggesstion = suggesstion;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String toString() {
        return "SuggestionPojo{" +
                "Name='" + Name + '\'' +
                ", Mobile_Number='" + Mobile_Number + '\'' +
                ", Email_Address='" + Email_Address + '\'' +
                ", Suggesstion='" + Suggesstion + '\'' +
                ", Address='" + Address + '\'' +
                '}';
    }
}
