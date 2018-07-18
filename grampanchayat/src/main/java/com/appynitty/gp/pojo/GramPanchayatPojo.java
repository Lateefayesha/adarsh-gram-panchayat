package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 9/7/18.
 */
public class GramPanchayatPojo implements Serializable {

    private String appId;
    private String appName;
    private String state;
    private String tehsil;
    private String district;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return "GramPanchayatPojo{" +
                "appId='" + appId + '\'' +
                ", appName='" + appName + '\'' +
                ", state='" + state + '\'' +
                ", tehsil='" + tehsil + '\'' +
                ", district='" + district + '\'' +
                '}';
    }
}
