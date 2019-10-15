package com.appynitty.gp.activity;

import android.content.Context;
import android.os.Build;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.appcompat.widget.Toolbar;

import com.appynitty.gp.R;
import com.appynitty.gp.controller.BaseActivity;
import com.appynitty.gp.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.custom_component.InternalWebviewClient;
import com.riaylibrary.utils.LocaleHelper;

/**
 * Created by MiTHUN on 2/7/18.
 */
public class BookingActivity extends BaseActivity {

    private WebView webView;

    @Override
    protected void attachBaseContext(Context base) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            super.attachBaseContext(LocaleHelper.onAttach(base));
        }else{
            super.attachBaseContext(base);
        }
    }

    @Override
    protected void generateId() {

        setContentView(R.layout.certificate_activity);
        webView = findViewById(R.id.aa_ww);

        initToolbar();
    }

    @Override
    protected void registerEvents() {

    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.booking));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
    }

    @Override
    protected void initData() {

        webView.setWebViewClient(new InternalWebviewClient(BookingActivity.this));
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        if (Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.ENGLISH)) {

            webView.loadUrl(AUtils.SERVER_URL + "Images/Booking/index.html?appid=" + Prefs.getString(AUtils.APP_ID, ""));

        } else {

            webView.loadUrl(AUtils.SERVER_URL + "Images/Booking/index_Marathi.html?appid=" + Prefs.getString(AUtils.APP_ID, ""));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {

            webView.goBack();
        } else {

            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        AUtils.changeLanguage(this, Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID));
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!AUtils.isRecreate)
            AUtils.removeMenuNavigationValue(AUtils.MenuIdConstants.Main_Menu_Dashboard,
                    AUtils.MenuIdConstants.Online_Booking);
    }
}
