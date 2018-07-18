package com.appynitty.gp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.appynitty.gp.R;
import com.appynitty.gp.utils.AUtils;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.mithsoft.lib.utils.MsUtils;


/**
 * Created by MiTHUN
 */
public class YouTubePlayerActivity extends YouTubeBaseActivity implements
        OnInitializedListener {

    private YouTubePlayerView playerView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.you_tube_playert_activity);

        playerView = (YouTubePlayerView) findViewById(R.id.player_view);
        // playerView.initialize("AIzaSyDH9m5FTmDiPTJ_-lW_BwWpJO32Qtul4xQ",
        // this);
        playerView.initialize(AUtils.API_KEY_YOU_TUBE, this);

    }

    @Override
    public void onInitializationFailure(Provider provider,
                                        YouTubeInitializationResult result) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle("YouTube Library Missing");
        // set dialog message
        alertDialogBuilder
                .setMessage(
                        "YouTube Library required for this app is not found in your device please update the library")
                .setCancelable(true)
                .setPositiveButton("Get it",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final String appPackageName = "com.google.android.youtube"; // getPackageName()
                                // from
                                // Context
                                // or
                                // Activity
                                // object
                                try {
                                    startActivity(new Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("market://details?id="
                                                    + appPackageName)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://play.google.com/store/apps/details?id="
                                                    + appPackageName)));
                                }
                                YouTubePlayerActivity.this.finish();
                            }
                        })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public void onInitializationSuccess(Provider provider,
                                        YouTubePlayer player, boolean restored) {
        if (!restored) {
            if (MsUtils.isNetWorkAvailable(YouTubePlayerActivity.this)) {
                // player.cueVideo(getIntent().getStringExtra("VIDEO_ID"));
                // player.cueVideo("Mdh3ZM3OTpk");
                player.setFullscreen(true);
                player.loadVideo(getIntent().getStringExtra("VIDEO_ID"));
            } else {
                Toast.makeText(this, "" + getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
