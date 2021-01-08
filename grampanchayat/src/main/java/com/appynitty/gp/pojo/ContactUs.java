package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 26/5/18.
 */
public class ContactUs implements Serializable {

    private String contactId;
    private String Address;
    private String featureId;
    private String languageId;
    private String headerTitle;
    private String modifiedBy;
    private String modifiedOn;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    @Override
    public String toString() {
        return "ContactUs{" +
                "contactId='" + contactId + '\'' +
                ", Address='" + Address + '\'' +
                ", featureId='" + featureId + '\'' +
                ", languageId='" + languageId + '\'' +
                ", headerTitle='" + headerTitle + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", modifiedOn='" + modifiedOn + '\'' +
                '}';
    }
}
