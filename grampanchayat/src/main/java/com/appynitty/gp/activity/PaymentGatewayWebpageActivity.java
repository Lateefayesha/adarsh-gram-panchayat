package com.appynitty.gp.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.appynitty.gp.R;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.InternalWebviewClient;
import com.appynitty.gp.utils.LocaleHelper;
import com.mithsoft.lib.activity.BaseActivity;

import quickutils.core.QuickUtils;

public class PaymentGatewayWebpageActivity extends BaseActivity {

    private WebView webView;
    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    protected void generateId() {

        setContentView(R.layout.activity_payment_gateway_webpage);
        webView = findViewById(R.id.aa_ww);

        initToolbar();
    }

    @Override
    protected void registerEvents() {

    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.property_online_payment));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
    }


    @Override
    protected void initData() {

        webView.setWebViewClient(new InternalWebviewClient(PaymentGatewayWebpageActivity.this, true));
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webView.loadUrl("https://www.instamojo.com/@ayandey?purpose=Property%20Tax&amount=10");
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