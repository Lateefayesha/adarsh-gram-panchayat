package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 30/5/18.
 */
public class SuggestionPojo implements Serializable {


    private String name;
    private String mobileNumber;
    private String emailAddress;
    private String suggesstion;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSuggesstion() {
        return suggesstion;
    }

    public void setSuggesstion(String suggesstion) {
        this.suggesstion = suggesstion;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SuggestionPojo{" +
                "name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", suggesstion='" + suggesstion + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
