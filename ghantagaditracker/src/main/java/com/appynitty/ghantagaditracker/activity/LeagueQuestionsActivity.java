package com.appynitty.ghantagaditracker.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.adapter.LeagueQuestionUIAdapter;
import com.appynitty.ghantagaditracker.adapter.UserDetailsBulderAdapter;
import com.appynitty.ghantagaditracker.controller.SyncServer;
import com.appynitty.ghantagaditracker.pojo.LeageaAnswerDetailsPojo;
import com.appynitty.ghantagaditracker.pojo.LeagueAnswerPojo;
import com.appynitty.ghantagaditracker.pojo.LeagueQuestionPojo;
import com.appynitty.ghantagaditracker.pojo.ResultPojo;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.appynitty.ghantagaditracker.utils.MyAsyncTask;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.LocaleHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LeagueQuestionsActivity extends AppCompatActivity {

    private Context mContext;
    private List<LeagueQuestionPojo> questionPojoList;
    private Map<String, String> answerMap;

    private RecyclerView questionRecyclerView;
    private Button submitAnswersBtn;
    private LeagueQuestionUIAdapter questionUIAdapter;
    private LeageaAnswerDetailsPojo leageaAnswerDetailsPojo;

    private UserDetailsBulderAdapter bulderAdapter;

    @Override
    protected void attachBaseContext(Context base) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.attachBaseContext(LocaleHelper.onAttach(base, AUtils.DEFAULT_LANGUAGE_NAME));
        } else {
            super.attachBaseContext(base);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
    }

    private void initComponents() {
        generateId();
        registerEvents();
        initData();
    }

    private void generateId() {
        setContentView(R.layout.activity_league_questions);
        mContext = LeagueQuestionsActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_league_questions);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        questionUIAdapter = new LeagueQuestionUIAdapter(mContext);

        questionRecyclerView = findViewById(R.id.questions_list);
        submitAnswersBtn = findViewById(R.id.submit_questions);
        leageaAnswerDetailsPojo = new LeageaAnswerDetailsPojo();
        bulderAdapter = new UserDetailsBulderAdapter(mContext);
    }

    private void registerEvents() {
        submitAnswersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateAns()) {
                    setAnswerPojo();
                } else
                    AUtils.warning(mContext, getResources().getString(R.string.answer_all_question));
            }
        });

        bulderAdapter.setBulderlistner(new UserDetailsBulderAdapter.UserDetailsBulderlistner() {
            @Override
            public void onSubmitClickSuccess(LeageaAnswerDetailsPojo.AnswersDetails details) {
                leageaAnswerDetailsPojo.setDetails(details);
                submitAnswers();
            }
        });
    }

    private boolean validateAns() {
        answerMap = questionUIAdapter.getAnsMap();
        return answerMap.size() == questionPojoList.size();
    }

    private void initData() {
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        fetchQuestion();
    }

    private void setAnswerPojo() {
        List<LeagueAnswerPojo> answerPojoList = new ArrayList<>();
        for (LeagueQuestionPojo mPojo : questionPojoList) {
            LeagueAnswerPojo pojo = new LeagueAnswerPojo();
            String qid = mPojo.getQuestionId();
            pojo.setQuestion(mPojo.getQuestion());
            pojo.setAnswer(answerMap.get(qid));
            pojo.setQuestionID(qid);
            answerPojoList.add(pojo);
        }

        userDetailsList(answerPojoList);
    }

    private void userDetailsList(List<LeagueAnswerPojo> pojo) {
        leageaAnswerDetailsPojo.setAns(pojo);

        if (Prefs.getString(AUtils.PREFS.REFERENCE_ID, "").equals("")) {
            bulderAdapter.openUserDetailsDialog(true);
        } else {
            LeageaAnswerDetailsPojo.AnswersDetails details = new LeageaAnswerDetailsPojo.AnswersDetails();
            details.setName("");
            details.setMobile("");
            leageaAnswerDetailsPojo.setDetails(details);
            submitAnswers();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }

    private void inflateQuestions() {
        questionUIAdapter.setQuestionPojos(questionPojoList);
        questionRecyclerView.setAdapter(questionUIAdapter);
    }

    private void fetchQuestion() {
        new MyAsyncTask(mContext, true, new MyAsyncTask.AsynTaskListener() {

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {
                questionPojoList = syncServer.fetchLeagueQuestion();
            }

            @Override
            public void onFinished() {
                if (!AUtils.isNull(questionPojoList) && questionPojoList.size() > 0)
                    inflateQuestions();
                else
                    AUtils.error(mContext, getResources().getString(R.string.something_error));
            }
        }).execute();
    }

    private void submitAnswers() {
        new MyAsyncTask(mContext, true, new MyAsyncTask.AsynTaskListener() {

            ResultPojo resultPojo = null;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {
                resultPojo = syncServer.submitLeagueAnswer(leageaAnswerDetailsPojo);
            }

            @Override
            public void onFinished() {
                if (!AUtils.isNull(resultPojo)) {
                    AUtils.success(mContext, resultPojo.getMessage(), Toast.LENGTH_LONG);
                    ((Activity) mContext).finish();
                } else {
                    AUtils.error(mContext, getResources().getString(R.string.something_error), Toast.LENGTH_LONG);
                }
            }
        }).execute();
    }

}
