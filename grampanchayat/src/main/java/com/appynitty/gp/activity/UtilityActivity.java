package com.appynitty.gp.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appynitty.gp.R;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.LocaleHelper;
import com.mithsoft.lib.activity.BaseActivity;
import com.mithsoft.lib.componants.MyProgressDialog;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 2/7/18.
 */
public class UtilityActivity extends BaseActivity {

    private WebView webView;
    private MyProgressDialog myProgressDialog;

    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    protected void generateId() {

        setContentView(R.layout.certificate_activity);
        webView = findViewById(R.id.aa_ww);
        myProgressDialog = new MyProgressDialog(UtilityActivity.this, R.drawable.progress_bar, false);

        initToolbar();
    }

    @Override
    protected void registerEvents() {

    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.utility));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
    }


    @Override
    protected void initData() {

        myProgressDialog.show();
        webView.setWebViewClient(new InternalWebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                myProgressDialog.hide();
            }
        });
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        if (QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID).equals("1")) {
            webView.loadUrl(AUtils.SERVER_URL + "Images/utilities/" + QuickUtils.prefs.getString(AUtils.TYPE, "gp") + "/index.html?appid=" + QuickUtils.prefs.getString(AUtils.APP_ID, ""));
        } else {
            webView.loadUrl(AUtils.SERVER_URL + "Images/utilities/" + QuickUtils.prefs.getString(AUtils.TYPE, "gp") + "/index_marathi.html?appid=" + QuickUtils.prefs.getString(AUtils.APP_ID, ""));
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

    private class InternalWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("tel:")) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
                return true;
            }if (url.contains("play.google.com")) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            }else {
                return false;
            }
        }
    }
}
