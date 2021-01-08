package com.appynitty.gp.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MiTHUN on 25/5/18.
 */
public class GalleryPojo implements Serializable {

    private List<PhotoGalleryImages> photoGalleryImages;
    private PhotoGallery photoGallery;
    private List<PhotoGalleryVideo> photoGalleryVideo;


    public List<PhotoGalleryImages> getPhotoGalleryImages() {
        return photoGalleryImages;
    }

    public void setPhotoGalleryImages(List<PhotoGalleryImages> photoGalleryImages) {
        this.photoGalleryImages = photoGalleryImages;
    }

    public PhotoGallery getPhotoGallery() {
        return photoGallery;
    }

    public void setPhotoGallery(PhotoGallery photoGallery) {
        this.photoGallery = photoGallery;
    }

    public List<PhotoGalleryVideo> getPhotoGalleryVideo() {
        return photoGalleryVideo;
    }

    public void setPhotoGalleryVideo(List<PhotoGalleryVideo> photoGalleryVideo) {
        this.photoGalleryVideo = photoGalleryVideo;
    }
}
