package com.appynitty.gp.activity;

import android.content.Context;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.widget.Toolbar;
import com.appynitty.gp.R;
import com.appynitty.gp.controller.BaseActivity;
import com.appynitty.gp.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.custom_component.InternalWebviewClient;
import com.riaylibrary.custom_component.MyNoDataView;
import com.riaylibrary.utils.LocaleHelper;

import java.util.Objects;

/**
 * Created by MiTHUN on 2/7/18.
 */
public class WebsiteActivity extends BaseActivity {

    private WebView webView;
    private MyNoDataView noDataView;

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

        setContentView(R.layout.weather_activity);
        webView = findViewById(R.id.aa_ww);
        noDataView = findViewById(R.id.no_data_view);
//        noDataView.setNoDataImage(R.drawable.defaut);
        noDataView.setMessage(getString(R.string.no_website));

        initToolbar();
    }

    @Override
    protected void registerEvents() {

    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.website));
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void initData() {

        webView.setWebViewClient(new InternalWebviewClient(WebsiteActivity.this));
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        if (!AUtils.isNullString(Prefs.getString(AUtils.WEBSITE_ENG, ""))
                && !AUtils.isNullString(Prefs.getString(AUtils.WEBSITE_ENG, ""))) {

            String mURL = null;

            switch (Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID)){
                case AUtils.LanguageIDConstants.MARATHI:
                    mURL = Prefs.getString(AUtils.WEBSITE_MAR, "");
                    break;
                case AUtils.LanguageIDConstants.HINDI:
                    mURL = Prefs.getString(AUtils.WEBSITE_HI, "");
                    break;
                default:
                    mURL = Prefs.getString(AUtils.WEBSITE_ENG, "");
            }

            webView.loadUrl(mURL);

        } else {

            webView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
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
    protected void onPause() {
        super.onPause();
        if(!AUtils.isRecreate)
            AUtils.removeMenuNavigationValue(AUtils.MenuIdConstants.Main_Menu_Dashboard,
                    AUtils.MenuIdConstants.Online_Website);
    }
}
