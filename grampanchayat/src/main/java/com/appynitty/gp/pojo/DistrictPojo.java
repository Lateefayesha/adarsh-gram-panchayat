package com.appynitty.gp.pojo;

import com.appynitty.gp.utils.AUtils;

import java.io.Serializable;

import quickutils.core.QuickUtils;

/**
 * Created by MiTHUN on 5/7/18.
 */
public class DistrictPojo implements Serializable {

    private String id;
    private String districtName;
    private String districtNameMar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictNameMar() {
        return districtNameMar;
    }

    public void setDistrictNameMar(String districtNameMar) {
        this.districtNameMar = districtNameMar;
    }

    @Override
    public String toString() {

//        Log.e("mithun", "lAnguage id = " + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID));
        if (QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID).equals("1")) {
            return districtName;
        } else {

//            Log.e("mithun", "Inside else = " + districtNameMar);
            return districtNameMar;
        }
    }
}
