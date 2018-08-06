package com.appynitty.gp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyFragemtV4;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class UtilityItemFragment extends MyFragemtV4 {

    private View view;
    private Context context;

    public static UtilityItemFragment newInstance() {

        UtilityItemFragment fragment = new UtilityItemFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.social_fragment, container, false);
        context = container.getContext();
        initComponents();
        return view;
    }

    public void initComponents() {

        genrateId();
    }

    private void genrateId() {

        Bundle bundle = getArguments();

//        String url = bundle.getString(AUtils.UTILITY_URL);
//        int title = bundle.getInt(AUtils.UTILITY_TITLE);

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.utility));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

        WebView webView = view.findViewById(R.id.aa_ww);

        webView.setWebViewClient(new WebViewClient());
//        webView.getSettings().setSupportZoom(false);
//        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setAppCacheEnabled(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        if (QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID) == "1") {

            webView.loadUrl(AUtils.SERVER_URL + "Images/utilities/np/index.html");

        } else {

            webView.loadUrl(AUtils.SERVER_URL + "Images/utilities/np/index_marathi.html");
        }


    }


}