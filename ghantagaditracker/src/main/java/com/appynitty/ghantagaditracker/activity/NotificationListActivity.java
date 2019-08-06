package com.appynitty.ghantagaditracker.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.adapter.NotificationUIAdapter;
import com.appynitty.ghantagaditracker.controller.Notification;
import com.appynitty.ghantagaditracker.customComponents.RecyclerViewDecorator;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.appynitty.ghantagaditracker.utils.DatabaseHelper;
import com.appynitty.ghantagaditracker.utils.LocaleHelper;

import java.util.List;
import java.util.Objects;

public class NotificationListActivity extends AppCompatActivity {

    private static final String TAG = "NotificationActivity";
    private Context mContext;
    private DatabaseHelper helper;
    private RecyclerView recyclerView;

    private LinearLayout noNotifications;
    private NotificationUIAdapter adapter;

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

        try{
            initComponents();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initComponents(){
        generateId();
        initData();
        registerEvents();
    }

    private void generateId(){
        setContentView(R.layout.activity_notification_list);
        mContext = NotificationListActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_notification_list);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        helper = new DatabaseHelper(mContext);
        recyclerView = findViewById(R.id.notification_list);
        noNotifications = findViewById(R.id.no_notification);
    }

    private void registerEvents(){
        if(!AUtils.isNull(adapter)){
            adapter.setAdapterListner(new NotificationUIAdapter.NotificationAdapterListner() {
                @Override
                public void onClear() {
                    fetchNotification();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        else if (item.getItemId() == R.id.notification_clear)
            clearAllNotification();

        return super.onOptionsItemSelected(item);
    }

    private void initData(){
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }

        adapter = new NotificationUIAdapter(mContext);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        RecyclerViewDecorator decorator = new RecyclerViewDecorator(mContext, R.dimen.cardview_radius);
        recyclerView.addItemDecoration(decorator);
        fetchNotification();
    }

    private void fetchNotification() {
        List<Notification> notifications = helper.getAllNotifications();

        if(!AUtils.isNull(notifications) && notifications.size() > 0){
            adapter.setNotificationList(notifications);
            recyclerView.setAdapter(adapter);
            helper.markNotificationsAsRead();
        }else{
            recyclerView.setVisibility(View.GONE);
            noNotifications.setVisibility(View.VISIBLE);
        }
    }


    private void clearAllNotification(){
        helper.deleteAllNotification();
        fetchNotification();
    }

}
