package com.appynitty.gp.pojo;

/**
 * Created by Richali Pradhan Gupte on 05-10-2018.
 */
public class UpcomingEventsPojo {

    private String id;

    private String time;

    private String days;

    private String location;

    private String imageUrl;

    private String description;

    private String subject;

    private String enddate;

    private String date;

    private String languageId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    @Override
    public String toString() {
        return "UpcomingEventsPojo{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", days='" + days + '\'' +
                ", location='" + location + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", subject='" + subject + '\'' +
                ", enddate='" + enddate + '\'' +
                ", date='" + date + '\'' +
                ", languageId='" + languageId + '\'' +
                '}';
    }
}
