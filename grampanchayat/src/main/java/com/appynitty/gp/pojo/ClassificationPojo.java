package com.appynitty.gp.pojo;

/**
 * Created by Richali Pradhan Gupte on 13-10-2018.
 */
public class ClassificationPojo {

    private String isActive;

    private String days;

    private String fromDate;

    private String image;

    private String toDate;

    private String type;

    private String cId;

    private String classifyName;

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    @Override
    public String toString() {
        return "ClassificationPojo{" +
                "isActive='" + isActive + '\'' +
                ", days='" + days + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", image='" + image + '\'' +
                ", toDate='" + toDate + '\'' +
                ", type='" + type + '\'' +
                ", cId='" + cId + '\'' +
                ", classifyName='" + classifyName + '\'' +
                '}';
    }
}