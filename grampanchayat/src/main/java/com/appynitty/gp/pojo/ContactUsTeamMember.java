package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 26/5/18.
 */
public class ContactUsTeamMember implements Serializable {

    private String memberId;
    private String contactId;
    private String jobTitle;
    private String memberDescription;
    private String phoneNumber;
    private String profileImageUrl;
    private String memberName;
    private String languageId;
    private String modifiedBy;
    private String modifiedOn;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getMemberDescription() {
        return memberDescription;
    }

    public void setMemberDescription(String memberDescription) {
        this.memberDescription = memberDescription;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
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
        return "ContactUsTeamMember{" +
                "memberId='" + memberId + '\'' +
                ", contactId='" + contactId + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", memberDescription='" + memberDescription + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", memberName='" + memberName + '\'' +
                ", languageId='" + languageId + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", modifiedOn='" + modifiedOn + '\'' +
                '}';
    }
}
