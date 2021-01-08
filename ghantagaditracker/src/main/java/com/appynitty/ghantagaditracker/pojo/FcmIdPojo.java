package com.appynitty.ghantagaditracker.pojo;

import java.io.Serializable;

/**
 * Created by Ayan on 04/06/19.
 */
public class FcmIdPojo implements Serializable {

    private String userid;
    private String fcmid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFcmid() {
        return fcmid;
    }

    public void setFcmid(String fcmid) {
        this.fcmid = fcmid;
    }

    @Override
    public String toString() {
        return "FcmIdPojo{" +
                "userid='" + userid + '\'' +
                ", fcmid='" + fcmid + '\'' +
                '}';
    }
}
