package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 15/5/18.
 */
public class WorkCheckOutPojo implements Serializable {

    private String workId;
    private String title;
    private String description;
    private String createdDate;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
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
}
