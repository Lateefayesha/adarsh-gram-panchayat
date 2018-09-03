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
public class WebsiteActivity extends BaseActivity {

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
        toolbar.setTitle(getString(R.string.website));
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

        if (QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID) == "1") {

            webView.loadUrl(QuickUtils.prefs.getString(AUtils.WEBSITE_ENG, ""));

        } else {

            webView.loadUrl(QuickUtils.prefs.getString(AUtils.WEBSITE_MAR, ""));
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
        AUtils.changeLanguage(this, Integer.parseInt(QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID)));
        super.onDestroy();
    }
}
