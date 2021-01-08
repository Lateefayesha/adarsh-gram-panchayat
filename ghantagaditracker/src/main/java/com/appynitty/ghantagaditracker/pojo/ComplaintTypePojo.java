package com.appynitty.ghantagaditracker.pojo;

import com.appynitty.ghantagaditracker.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.Serializable;

/**
 * Created by MiTHUN on 30/8/18.
 */
public class ComplaintTypePojo implements Serializable {

    private String id;
    private String description;
    private String descriptionMar;
    private String descriptionHindi;

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

    public String getDescriptionHindi() {
        return descriptionHindi;
    }

    public void setDescriptionHindi(String descriptionHindi) {
        this.descriptionHindi = descriptionHindi;
    }

    @Override
    public String toString() {

        switch (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_NAME)){
            case AUtils.LanguageConstants.MARATHI:
                return descriptionMar;
            case AUtils.LanguageConstants.HINDI:
                return descriptionHindi;
            default: return description;
        }
    }
}
