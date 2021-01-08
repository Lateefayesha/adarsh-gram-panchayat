package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 10/8/18.
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
