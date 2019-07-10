package com.appynitty.ghantagaditracker.pojo;

/**
 * Created by Ayan Dey on 2/7/19.
 */
public class LeagueAnswerPojo {

    private String Question;
    private String Answer;

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    @Override
    public String toString() {
        return "LeagueAnswerPojo{" +
                "Question='" + Question + '\'' +
                ", Answer='" + Answer + '\'' +
                '}';
    }
}
