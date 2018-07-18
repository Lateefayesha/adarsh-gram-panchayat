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
import com.appynitty.gp.pojo.SocialNetworkPojo;
import com.appynitty.gp.utils.AUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class SocialNetworkItemFragment extends Fragment {

    private View view;
    private Context context;

    public static SocialNetworkItemFragment newInstance() {

        SocialNetworkItemFragment fragment = new SocialNetworkItemFragment();
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

        SocialNetworkPojo socialNetworkPojo = (SocialNetworkPojo) bundle.getSerializable(AUtils.SOCIAL_NETWORK_POJO);

        WebView webView = view.findViewById(R.id.aa_ww);

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

//        webView.loadUrl("https://www.facebook.com/grampanchayt.pardi?ref=br_rs");
        webView.loadUrl(socialNetworkPojo.getSocialUrl());


    }


}