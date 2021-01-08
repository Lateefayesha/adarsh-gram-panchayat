package com.appynitty.gp.pojo;

import java.io.Serializable;

/**
 * Created by MiTHUN on 29/6/18.
 */
public class CertificatePojo implements Serializable {


    private String id;
    private String documentName;
    private String pageLink;
    private String languageId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getPageLink() {
        return pageLink;
    }

    public void setPageLink(String pageLink) {
        this.pageLink = pageLink;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    @Override
    public String toString() {
        return "CertificatePojo{" +
                "id='" + id + '\'' +
                ", documentName='" + documentName + '\'' +
                ", pageLink='" + pageLink + '\'' +
                ", languageId='" + languageId + '\'' +
                '}';
    }
}
