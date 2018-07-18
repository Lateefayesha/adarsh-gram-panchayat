package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 25/5/18.
 */
public class SocialNetworkPojo implements Serializable {

    private String pageTitle;
    private String featureId;
    private String socialUrl;
    private String socialTitle;
    private String socialId;
    private String languageId;

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    public String getSocialUrl() {
        return socialUrl;
    }

    public void setSocialUrl(String socialUrl) {
        this.socialUrl = socialUrl;
    }

    public String getSocialTitle() {
        return socialTitle;
    }

    public void setSocialTitle(String socialTitle) {
        this.socialTitle = socialTitle;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }
}
