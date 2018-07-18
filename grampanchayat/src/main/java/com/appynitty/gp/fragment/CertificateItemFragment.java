package com.appynitty.gp.fragment;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyFragemtV4;
import com.mithsoft.lib.componants.Toasty;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class CertificateItemFragment extends MyFragemtV4 {

    private View view;
    private Context context;
    private String mimeType1;
    private String contentDisposition1;
    private String userAgent1;
    private String url1;

    public static CertificateItemFragment newInstance() {

        CertificateItemFragment fragment = new CertificateItemFragment();
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

        String url = bundle.getString(AUtils.UTILITY_URL);
        String title = bundle.getString(AUtils.UTILITY_TITLE);

        ((HomeActivity) getActivity()).setTitleActionBar(title);
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

        WebView webView = view.findViewById(R.id.aa_ww);

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webView.loadUrl(url);

        webView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {

                mimeType1 = mimeType;
                contentDisposition1 = contentDisposition;
                userAgent1 = userAgent;
                url1 = url;

                isStoragePermissionGiven();
            }
        });
    }

    private void isStoragePermissionGiven() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //Show Information about why you need the permission

                AUtils.showPermissionDialog(context, "EXTERNAL STORAGE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AUtils.MY_PERMISSIONS_REQUEST_STORAGE);
                        }
                    }
                });

            } else if (QuickUtils.prefs.getBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission

                AUtils.showPermissionDialog(context, "EXTERNAL STORAGE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        AUtils.goToAppSettings(context);
                    }
                });

            } else {
                //just request the permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AUtils.MY_PERMISSIONS_REQUEST_STORAGE);
                }
            }

            QuickUtils.prefs.save(Manifest.permission.WRITE_EXTERNAL_STORAGE, true);
        } else {
            //You already have the permission, just go ahead.
            downloadFile();
        }
    }


    private void downloadFile() {

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url1));

        request.setMimeType(mimeType1);
        //------------------------COOKIE!!------------------------
        String cookies = CookieManager.getInstance().getCookie(url1);
        request.addRequestHeader("cookie", cookies);
        //------------------------COOKIE!!------------------------
        request.addRequestHeader("User-Agent", userAgent1);
        request.setDescription("Downloading file...");
        request.setTitle(URLUtil.guessFileName(url1, contentDisposition1, mimeType1));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url1, contentDisposition1, mimeType1));
        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        dm.enqueue(request);
        Toasty.success(context.getApplicationContext(), "Downloading File...", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AUtils.MY_PERMISSIONS_REQUEST_STORAGE) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                downloadFile();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                AUtils.showPermissionDialog(context, "EXTERNAL STORAGE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AUtils.MY_PERMISSIONS_REQUEST_STORAGE);
                        }
                    }
                });
            } else {
//                Toast.makeText(getActivity(), "Unable to get Permission", Toast.LENGTH_LONG).show();
            }

        }
    }
}