package com.appynitty.ghantagaditracker.pojo;

/**
 * Created by Ayan Dey on 15/7/19.
 */
public class CollectionHistoryPojo {

    private String UserName;
    private String Employee;
    private String VehicleNumber;
    private String attandDate;
    private String type1;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmployee() {
        return Employee;
    }

    public void setEmployee(String employee) {
        Employee = employee;
    }

    public String getVehicleNumber() {
        return VehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        VehicleNumber = vehicleNumber;
    }

    public String getAttandDate() {
        return attandDate;
    }

    public void setAttandDate(String attandDate) {
        this.attandDate = attandDate;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    @Override
    public String toString() {
        return "CollectionHistoryPojo{" +
                "UserName='" + UserName + '\'' +
                ", Employee='" + Employee + '\'' +
                ", VehicleNumber='" + VehicleNumber + '\'' +
                ", attandDate='" + attandDate + '\'' +
                ", type1='" + type1 + '\'' +
                '}';
    }
}
