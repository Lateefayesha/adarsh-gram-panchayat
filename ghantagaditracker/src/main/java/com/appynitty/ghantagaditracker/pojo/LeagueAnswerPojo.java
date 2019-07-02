package com.appynitty.ghantagaditracker.pojo;

/**
 * Created by Ayan Dey on 2/7/19.
 */
public class LeagueAnswerPojo {

    private String QuestionId;
    private String Answer;

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
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
                "QuestionId='" + QuestionId + '\'' +
                ", Answer='" + Answer + '\'' +
                '}';
    }
}
