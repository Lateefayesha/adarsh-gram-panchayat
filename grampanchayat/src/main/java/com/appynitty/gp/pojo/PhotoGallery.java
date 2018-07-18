package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 25/5/18.
 */
public class PhotoGallery implements Serializable {

    private String modifiedOn;
    private String imageUrl;
    private String modifiedBy;
    private String pageTitle;
    private String featureId;
    private String photoGalleryId;
    private String languageId;

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

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

    public String getPhotoGalleryId() {
        return photoGalleryId;
    }

    public void setPhotoGalleryId(String photoGalleryId) {
        this.photoGalleryId = photoGalleryId;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }
}
