package com.appynitty.gp.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appynitty.gp.R;

/**
 * Created by Ayan Dey on 19/10/18.
 */
public class InternalWebviewClient extends WebViewClient {

    private static final String TAG = "InternalWebviewClient";
    private Context mContext;

    public InternalWebviewClient(Context context){
        this.mContext = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if(url.contains("tel:")){

            mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
            return true;

        }else if(url.contains("mailto:")){

            mContext.startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(url)));
            return true;

        }else if(url.contains("market:") || url.contains("//play.google.com")){

            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            return true;

        }else{
            return false;
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

        String stringUrl = request.getUrl().toString();

        if(stringUrl.contains("tel:")){

            mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(stringUrl)));
            return true;

        }else if(stringUrl.contains("mailto:")){

            mContext.startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(stringUrl)));
            return true;

        }else if(stringUrl.contains("market:") || stringUrl.contains("//play.google.com")){

            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(stringUrl)));
            return true;

        }else{
            return false;
        }
    }
}
