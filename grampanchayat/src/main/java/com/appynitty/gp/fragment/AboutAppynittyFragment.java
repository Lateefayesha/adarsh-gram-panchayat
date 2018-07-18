package com.appynitty.gp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.utils.AUtils;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/5/18.
 */

public class AboutAppynittyFragment extends Fragment {

    private View view;
    private Context context;

    public static AboutAppynittyFragment newInstance() {

        AboutAppynittyFragment fragment = new AboutAppynittyFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.about_appynitty_fragment, container, false);
        context = container.getContext();
        initComponants();
        return view;
    }

    private void initComponants() {

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.action_about_appynitty));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

        genrateId();
    }

    private void genrateId() {

        WebView webView = view.findViewById(R.id.aa_ww);

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.loadUrl("http://www.appynitty.com/");

    }

}