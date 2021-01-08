package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 9/7/18.
 */
public class GramPanchayatPojo implements Serializable {

    private String appId;
    private String appName;
    private String appNamemar;
    private String state;
    private String stateMar;
    private String district;
    private String districtMar;
    private String tehsil;
    private String tehsilMar;

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

    public String getAppNamemar() {
        return appNamemar;
    }

    public void setAppNamemar(String appNamemar) {
        this.appNamemar = appNamemar;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateMar() {
        return stateMar;
    }

    public void setStateMar(String stateMar) {
        this.stateMar = stateMar;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictMar() {
        return districtMar;
    }

    public void setDistrictMar(String districtMar) {
        this.districtMar = districtMar;
    }

    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public String getTehsilMar() {
        return tehsilMar;
    }

    public void setTehsilMar(String tehsilMar) {
        this.tehsilMar = tehsilMar;
    }

    @Override
    public String toString() {
        return "GramPanchayatPojo{" +
                "appId='" + appId + '\'' +
                ", appName='" + appName + '\'' +
                ", appNamemar='" + appNamemar + '\'' +
                ", state='" + state + '\'' +
                ", stateMar='" + stateMar + '\'' +
                ", district='" + district + '\'' +
                ", districtMar='" + districtMar + '\'' +
                ", tehsil='" + tehsil + '\'' +
                ", tehsilMar='" + tehsilMar + '\'' +
                '}';
    }
}
