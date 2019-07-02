package com.appynitty.ghantagaditracker.pojo;


import com.appynitty.ghantagaditracker.utils.AUtils;

import java.io.Serializable;

import quickutils.core.QuickUtils;

/**
 * Created by MiTHUN on 30/8/18.
 */
public class ComplaintTypePojo implements Serializable {

    private String id;
    private String description;
    private String descriptionMar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionMar() {
        return descriptionMar;
    }

    public void setDescriptionMar(String descriptionMar) {
        this.descriptionMar = descriptionMar;
    }

    @Override
    public String toString() {

        if (QuickUtils.prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_NAME).equals("en")) {
            return description;
        } else {
            return descriptionMar;
        }
    }
}
