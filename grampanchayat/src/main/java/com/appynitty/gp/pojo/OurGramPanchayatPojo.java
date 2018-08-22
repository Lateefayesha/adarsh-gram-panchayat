package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 18/5/18.
 */
public class OurGramPanchayatPojo implements Serializable {


    private String gramPtId;
    private String title;
    private String description;
    private String createdDate;
    private String imageUrl;

    public String getGramPtId() {
        return gramPtId;
    }

    public void setGramPtId(String gramPtId) {
        this.gramPtId = gramPtId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "OurGramPanchayatPojo{" +
                "gramPtId='" + gramPtId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
