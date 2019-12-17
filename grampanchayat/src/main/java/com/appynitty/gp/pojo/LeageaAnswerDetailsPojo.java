package com.appynitty.gp.pojo;

import java.util.List;

/**
 * Created by Ayan Dey on 16/7/19.
 */
public class LeageaAnswerDetailsPojo {

    private List<LeagueAnswerPojo> ans;
    private AnswersDetails Details;

    public List<LeagueAnswerPojo> getAns() {
        return ans;
    }

    public void setAns(List<LeagueAnswerPojo> ans) {
        this.ans = ans;
    }

    public AnswersDetails getDetails() {
        return Details;
    }

    public void setDetails(AnswersDetails details) {
        Details = details;
    }

    @Override
    public String toString() {
        return "LeageaAnswerDetailsPojo{" +
                "ans=" + ans +
                ", Details=" + Details +
                '}';
    }

    public static class AnswersDetails{
        private String Name;
        private String Mobile;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String mobile) {
            Mobile = mobile;
        }

        @Override
        public String toString() {
            return "AnswersDetails{" +
                    "Name='" + Name + '\'' +
                    ", Mobile='" + Mobile + '\'' +
                    '}';
        }
    }
}
