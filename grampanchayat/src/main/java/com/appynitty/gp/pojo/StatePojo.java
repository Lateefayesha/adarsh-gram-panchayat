package com.appynitty.gp.pojo;

import com.appynitty.gp.utils.AUtils;

import java.io.Serializable;

import quickutils.core.QuickUtils;

/**
 * Created by MiTHUN on 5/7/18.
 */
public class StatePojo implements Serializable {

    private String id;
    private String stateName;
    private String stateNameMar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateNameMar() {
        return stateNameMar;
    }

    public void setStateNameMar(String stateNameMar) {
        this.stateNameMar = stateNameMar;
    }

    @Override
    public String toString() {

//        if (QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID).equals("1")) {
            return stateName;
//        } else {
//            return stateNameMar;
//        }
    }
}
