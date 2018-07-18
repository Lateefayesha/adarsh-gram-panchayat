package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 5/7/18.
 */
public class DistrictPojo implements Serializable {

    private String id;
    private String district_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    @Override
    public String toString() {
        return district_name;
    }
}
