package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 25/5/18.
 */
public class PhotoGalleryImages implements Serializable {

    private String modifiedOn;
    private String subTitle;
    private String galleryImageId;
    private String title;
    private String isShareAvailable;
    private String imageUrl;
    private String modifiedBy;
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

    public String getGalleryImageId() {
        return galleryImageId;
    }

    public void setGalleryImageId(String galleryImageId) {
        this.galleryImageId = galleryImageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsShareAvailable() {
        return isShareAvailable;
    }

    public void setIsShareAvailable(String isShareAvailable) {
        this.isShareAvailable = isShareAvailable;
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
