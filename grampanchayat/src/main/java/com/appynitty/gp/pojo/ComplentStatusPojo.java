package com.appynitty.gp.pojo;

import com.appynitty.gp.utils.AUtils;

import java.io.Serializable;

import quickutils.core.QuickUtils;

/**
 * Created by MiTHUN on 10/8/18.
 */
public class ComplentStatusPojo implements Serializable {

    private String wardNo;
    private String details;
    private String status;
    private String userId;
    private String status_image;
    private String complaintId;
    private String place;
    private String complaintType;
    private String complaintTypeMar;
    private String createdDate;
    private String startImage;
    private String endImage;
    private String tips;
    private String refId;
    private String comment;
    private String type;
    private String typeMar;


    public String getWardNo() {
        return wardNo;
    }

    public void setWardNo(String wardNo) {
        this.wardNo = wardNo;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus_image() {
        return status_image;
    }

    public void setStatus_image(String status_image) {
        this.status_image = status_image;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getComplaintType() {

//        if (QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID).equals("1")) {
            return complaintType;
//        } else {
//            return complaintTypeMar;
//        }
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getComplaintTypeMar() {
        return complaintTypeMar;
    }

    public void setComplaintTypeMar(String complaintTypeMar) {
        this.complaintTypeMar = complaintTypeMar;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getStartImage() {
        return startImage;
    }

    public void setStartImage(String startImage) {
        this.startImage = startImage;
    }

    public String getEndImage() {
        return endImage;
    }

    public void setEndImage(String endImage) {
        this.endImage = endImage;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {

//        if (QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID).equals("1")) {
            return type;
//        } else {
//            return typeMar;
//        }
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeMar() {
        return typeMar;
    }

    public void setTypeMar(String typeMar) {
        this.typeMar = typeMar;
    }

    @Override
    public String toString() {
        return "ComplentStatusPojo{" +
                "wardNo='" + wardNo + '\'' +
                ", details='" + details + '\'' +
                ", status='" + status + '\'' +
                ", userId='" + userId + '\'' +
                ", status_image='" + status_image + '\'' +
                ", complaintId='" + complaintId + '\'' +
                ", place='" + place + '\'' +
                ", complaintType='" + complaintType + '\'' +
                ", complaintTypeMar='" + complaintTypeMar + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", startImage='" + startImage + '\'' +
                ", endImage='" + endImage + '\'' +
                ", tips='" + tips + '\'' +
                ", refId='" + refId + '\'' +
                ", comment='" + comment + '\'' +
                ", type='" + type + '\'' +
                ", typeMar='" + typeMar + '\'' +
                '}';
    }
}
