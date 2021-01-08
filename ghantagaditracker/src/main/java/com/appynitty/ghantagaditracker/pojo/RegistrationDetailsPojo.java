package com.appynitty.ghantagaditracker.pojo;

/**
 * Created by Ayan Dey on 24/6/19.
 */
public class RegistrationDetailsPojo {

    private String Status;
    private String ReferenceId;
    private String MobileNo;
    private String Message;
    private String MessageMar;
    private String OTP;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getReferenceId() {
        return ReferenceId;
    }

    public void setReferenceId(String referenceId) {
        ReferenceId = referenceId;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getMessageMar() {
        return MessageMar;
    }

    public void setMessageMar(String messageMar) {
        MessageMar = messageMar;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    @Override
    public String toString() {
        return "RegistrationDetailsPojo{" +
                "Status='" + Status + '\'' +
                ", ReferenceId='" + ReferenceId + '\'' +
                ", MobileNo='" + MobileNo + '\'' +
                ", Message='" + Message + '\'' +
                ", MessageMar='" + MessageMar + '\'' +
                ", OTP='" + OTP + '\'' +
                '}';
    }
}
