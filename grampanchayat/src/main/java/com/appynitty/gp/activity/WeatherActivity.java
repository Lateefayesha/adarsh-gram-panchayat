package com.appynitty.gp.activity;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appynitty.gp.R;
import com.appynitty.gp.utils.AUtils;
import com.mithsoft.lib.activity.BaseActivity;

import quickutils.core.QuickUtils;

/**
 * Created by MiTHUN on 2/7/18.
 */
public class WeatherActivity extends BaseActivity {

    private WebView webView;

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
        toolbar.setTitle(getString(R.string.weather));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
    }


    @Override
    protected void initData() {

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

//        webView.loadUrl("https://weather.com/en-IN/weather/today/l/21.065566,77.332243?par=google");

        if (!AUtils.isNullString(QuickUtils.prefs.getString(AUtils.APP_LOCATION, ""))) {

            String[] split = QuickUtils.prefs.getString(AUtils.APP_LOCATION, "").split(",");
            String lat = split[0];
            String log = split[1];
            webView.loadUrl(AUtils.SERVER_URL + "Images/weather_web/?lat=" + lat + "&log=" + log);
        } else {

            webView.loadUrl(AUtils.SERVER_URL + "Images/weather_web/?lat=21.065566&log=77.332243");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                WeatherActivity.this.finish();
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
}
