package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 30/5/18.
 */
public class TankerBookingPojo implements Serializable {

    private String name;
    private String number;
    private String wardNo;
    private String address;
    private String email;
    private String doorNo;
    private String languageId;
    private String createdDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWardNo() {
        return wardNo;
    }

    public void setWardNo(String wardNo) {
        this.wardNo = wardNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "TankerBookingPojo{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", wardNo='" + wardNo + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", doorNo='" + doorNo + '\'' +
                ", languageId='" + languageId + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}
