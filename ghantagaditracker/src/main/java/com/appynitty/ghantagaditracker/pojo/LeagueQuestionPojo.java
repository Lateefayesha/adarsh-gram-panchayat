package com.appynitty.ghantagaditracker.pojo;

/**
 * Created by Ayan Dey on 1/7/19.
 */
public class LeagueQuestionPojo {

    private String QuestionId;
    private String Question;

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    @Override
    public String toString() {
        return "LeagueQuestionPojo{" +
                "QuestionId='" + QuestionId + '\'' +
                ", Question='" + Question + '\'' +
                '}';
    }
}
