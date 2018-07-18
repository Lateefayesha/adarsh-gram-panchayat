package com.appynitty.gp.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MiTHUN on 26/5/18.
 */
public class ContactUsPojo implements Serializable {

    private List<ContactUsTeamMember> contactUsTeamMember;
    private ContactUs contactUs;

    public List<ContactUsTeamMember> getContactUsTeamMember() {
        return contactUsTeamMember;
    }

    public void setContactUsTeamMember(List<ContactUsTeamMember> contactUsTeamMember) {
        this.contactUsTeamMember = contactUsTeamMember;
    }

    public ContactUs getContactUs() {
        return contactUs;
    }

    public void setContactUs(ContactUs contactUs) {
        this.contactUs = contactUs;
    }
}
