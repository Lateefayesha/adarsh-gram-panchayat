package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 18/5/18.
 */
public class YoungBusinessPojo implements Serializable {


    private String yBusniessId;
    private String title;
    private String details;
    private String image;
    private String createdDate;


    public String getyBusniessId() {
        return yBusniessId;
    }

    public void setyBusniessId(String yBusniessId) {
        this.yBusniessId = yBusniessId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "YoungBusinessPojo{" +
                "yBusniessId='" + yBusniessId + '\'' +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", image='" + image + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}
