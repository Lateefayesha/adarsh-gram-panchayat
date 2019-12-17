package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 30/5/18.
 */
public class ResultPojo implements Serializable {


    private String status;
    private String message;
    private String messageMar;
    private String messageHindi;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageMar() {
        return messageMar;
    }

    public void setMessageMar(String messageMar) {
        this.messageMar = messageMar;
    }

    public String getMessageHindi() {
        return messageHindi;
    }

    public void setMessageHindi(String messageHindi) {
        this.messageHindi = messageHindi;
    }

    @Override
    public String toString() {
        return "ResultPojo{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", messageMar='" + messageMar + '\'' +
                ", messageHindi='" + messageHindi + '\'' +
                '}';
    }
}
