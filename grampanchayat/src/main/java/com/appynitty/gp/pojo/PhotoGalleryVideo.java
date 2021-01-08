package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 25/5/18.
 */
public class PhotoGalleryVideo implements Serializable {

    private String modifiedOn;
    private String subTitle;
    private String title;
    private String modifiedBy;
    private String videoUrl;
    private String videoId;
    private String photoGalleryId;
    private String languageId;

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
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
