package com.appynitty.ghantagaditracker.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.adapter.LeagueQuestionUIAdapter;
import com.appynitty.ghantagaditracker.controller.SyncServer;
import com.appynitty.ghantagaditracker.pojo.LeagueAnswerPojo;
import com.appynitty.ghantagaditracker.pojo.LeagueQuestionPojo;
import com.appynitty.ghantagaditracker.pojo.ResultPojo;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.appynitty.ghantagaditracker.utils.LocaleHelper;
import com.appynitty.ghantagaditracker.utils.MyAsyncTask;
import com.mithsoft.lib.componants.Toasty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LeagueQuestionsActivity extends AppCompatActivity {

    private Context mContext;
    private List<LeagueQuestionPojo> questionPojoList;
    private List<LeagueAnswerPojo> answerPojoList;
    private Map<String, String> answerMap;

    private RecyclerView questionRecyclerView;
    private Button submitAnswers;

    LeagueQuestionUIAdapter questionUIAdapter;

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

    private void initComponents(){
        generateId();
        registerEvents();
        initData();
    }

    private void generateId(){
        setContentView(R.layout.activity_league_questions);
        mContext = LeagueQuestionsActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        questionUIAdapter = new LeagueQuestionUIAdapter(mContext);

        questionRecyclerView = findViewById(R.id.questions_list);
        submitAnswers = findViewById(R.id.submit_questions);

        answerPojoList = new ArrayList<>();
    }

    private void registerEvents(){
        submitAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateAns()){
                    setAnswerPojo();
                }else
                    Toasty.warning(mContext, getResources().getString(R.string.answer_all_question)).show();
            }
        });
    }

    private boolean validateAns() {
        answerMap = questionUIAdapter.getAnsMap();
        return answerMap.size() == questionPojoList.size();
    }

    private void initData(){
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        fetchQuestion();
    }

    private void setAnswerPojo() {
        for (LeagueQuestionPojo mPojo: questionPojoList){
            LeagueAnswerPojo pojo = new LeagueAnswerPojo();
            String qid = mPojo.getQuestionId();
            pojo.setQuestion(mPojo.getQuestion());
            pojo.setAnswer(answerMap.get(qid));
            answerPojoList.add(pojo);
        }

        submitAnswers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }

    private void inflateQuestions(){
        questionUIAdapter.setQuestionPojos(questionPojoList);
        questionRecyclerView.setAdapter(questionUIAdapter);
    }

    private void fetchQuestion(){
        new MyAsyncTask(mContext, true, new MyAsyncTask.AsynTaskListener() {

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {
                questionPojoList = syncServer.fetchLeagueQuestion();
            }

            @Override
            public void onFinished() {
                if(!AUtils.isNull(questionPojoList) && questionPojoList.size() > 0)
                    inflateQuestions();
                else
                    Toasty.error(mContext, getResources().getString(R.string.something_error)).show();
            }
        }).execute();
    }

    private void submitAnswers(){
        new MyAsyncTask(mContext, true, new MyAsyncTask.AsynTaskListener() {

            ResultPojo resultPojo = null;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {
                resultPojo = syncServer.submitLeagueAnswer(answerPojoList);
            }

            @Override
            public void onFinished() {
                if(!AUtils.isNull(resultPojo)) {
                    Toasty.success(mContext, resultPojo.getMessage(), Toast.LENGTH_LONG).show();
                    ((Activity)mContext).finish();
                }else {
                    Toasty.error(mContext, getResources().getString(R.string.something_error), Toast.LENGTH_LONG).show();
                }
            }
        }).execute();
    }

}
