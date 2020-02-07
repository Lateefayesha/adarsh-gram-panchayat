package com.appynitty.ghantagaditracker.pojo;

/**
 * Created by Ayan Dey on 20/11/18.
 */
public class CityPeeListPojo {

    private String Address;

    private String SauchalayID;

    private String Long;

    private String Lat;

    private String Name;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getSauchalayID() {
        return SauchalayID;
    }

    public void setSauchalayID(String sauchalayID) {
        SauchalayID = sauchalayID;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "CityPeeListPojo{" +
                "Address='" + Address + '\'' +
                ", SauchalayID='" + SauchalayID + '\'' +
                ", Long='" + Long + '\'' +
                ", Lat='" + Lat + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
