package com.appynitty.gp.pojo;

import com.appynitty.gp.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.Serializable;

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

        if (Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.ENGLISH)) {
            return stateName;
        } else {
            return stateNameMar;
        }
    }
}
