package com.appynitty.ghantagaditracker.pojo;

/**
 * Created by Ayan Dey on 20/11/18.
 */
public class ActiveUserListPojo {

    private String time;

    private String area;

    private String address;

    private String userMobile;

    private String userName;

    private String longitude;

    private String vehcileNumber;

    private String latitude;

    private String date;

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getArea ()
    {
        return area;
    }

    public void setArea (String area)
    {
        this.area = area;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getUserMobile ()
    {
        return userMobile;
    }

    public void setUserMobile (String userMobile)
    {
        this.userMobile = userMobile;
    }

    public String getUserName ()
    {
        return userName;
    }

    public void setUserName (String userName)
    {
        this.userName = userName;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    public String getVehcileNumber ()
    {
        return vehcileNumber;
    }

    public void setVehcileNumber (String vehcileNumber)
    {
        this.vehcileNumber = vehcileNumber;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [time = "+time+
                ", area = "+area+
                ", address = "+address+
                ", userMobile = "+userMobile+
                ", userName = "+userName+
                ", longitude = "+longitude+
                ", vehcileNumber = "+vehcileNumber+
                ", latitude = "+latitude+
                ", date = "+date+
                "]";
    }
}
