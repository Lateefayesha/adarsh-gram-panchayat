package com.appynitty.gp.pojo;

import com.appynitty.gp.utils.AUtils;

import java.io.Serializable;

import quickutils.core.QuickUtils;

/**
 * Created by MiTHUN on 5/7/18.
 */
public class TahsilPojo implements Serializable {

    private String id;
    private String name;
    private String nameMar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameMar() {
        return nameMar;
    }

    public void setNameMar(String nameMar) {
        this.nameMar = nameMar;
    }

    @Override
    public String toString() {

        if (QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID).equals("1")) {
            return name;
        } else {
            return nameMar;
        }
    }
}
