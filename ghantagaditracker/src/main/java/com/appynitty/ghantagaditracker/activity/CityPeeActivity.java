package com.appynitty.ghantagaditracker.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.riaylibrary.custom_component.WebviewInitialize;
import com.riaylibrary.utils.LocaleHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class CityPeeActivity extends AppCompatActivity {

    private Context mContext;
    private WebviewInitialize webviewInitialize;
    private IntentIntegrator qrscan;

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
        initData();
    }

    private void generateId() {
        setContentView(R.layout.activity_city_pee);

        mContext = CityPeeActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_activity_city_pee));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        WebView webView = findViewById(R.id.web_view);
        webviewInitialize = new WebviewInitialize(mContext, webView);
        qrscan = new IntentIntegrator((Activity) mContext);
    }

    private void initData() {
        qrscan.setPrompt(getResources().getString(R.string.prompt_qr));
        qrscan.setOrientationLocked(true);
        qrscan.setCaptureActivity(PotraitZxingActivity.class);
        qrscan.initiateScan();
    }

    private void openWebView(String url) {
        try {
            URL murl = new URL(url);
            String path = murl.getPath();
            String protocol = murl.getProtocol();
            String authority = murl.getAuthority();
            String query = murl.getQuery();
            String newUrl = protocol + "://" + authority + "/FeedbackForm?" + query;

//            if(path.equals("/FeedbackForm"))
            if (path.equals("/PlayStoreLink"))
                webviewInitialize.InitiateDefaultWebview(newUrl);
            else {
                AUtils.error(mContext, getResources().getString(R.string.invalid_url_error));
                ((Activity) mContext).finish();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            AUtils.error(mContext, getResources().getString(R.string.something_error));
            ((Activity) mContext).finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (!AUtils.isNull(result)) {
            String str = result.getContents();
            if (!AUtils.isNull(str) && !str.isEmpty()) {
                if (Patterns.WEB_URL.matcher(str).matches())
                    openWebView(str);
                else {
                    AUtils.error(mContext, getResources().getString(R.string.invalid_qr_error));
                    ((Activity) mContext).finish();
//                    showPopup(str);
                }
            } else {
                ((Activity) mContext).finish();
            }
        } else {
            AUtils.error(mContext, getResources().getString(R.string.something_error));
            ((Activity) mContext).finish();
        }
    }

    private void showPopup(String str) {

        Log.d(AUtils.TAG_INVALID_URL, str);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage(str)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        ((Activity) mContext).finish();
                    }
                }).show();
    }
}
