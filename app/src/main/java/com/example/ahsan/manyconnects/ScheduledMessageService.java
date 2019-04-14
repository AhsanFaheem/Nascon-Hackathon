package com.example.ahsan.manyconnects;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;
import java.util.List;

public class ScheduledMessageService extends IntentService {
    private ArrayList<String> selectedItems = new ArrayList<>();
    private String msg;
    private CallbackManager callbackManager;

    public ScheduledMessageService() {
        super("scheduled_message_service");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        selectedItems = intent.getStringArrayListExtra("selected_items");
        msg = intent.getStringExtra("message");

        Intent twitterIntent = new Intent(Intent.ACTION_SEND);
        twitterIntent.putExtra(Intent.EXTRA_TEXT, msg);
        twitterIntent.setType("text/plain");
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(twitterIntent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            if (selectedItems.contains("Facebook")) {
                callbackManager = CallbackManager.Factory.create();
// https://stackoverflow.com/a/22123047/2065418
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
                        //.setShareHashtag(new ShareHashtag.Builder()
                        //                .setHashtag("#ConnectTheWorld")
                        //                .build());  //if we want hashtag
                        //.setQuote("Connect on a global scale.")   //if we want to add a quote to post
                        .build();

                ShareDialog shareDialog = new ShareDialog((AppCompatActivity) getApplicationContext());
                shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);

                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(getApplicationContext(), "Post Shared Successfully!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "Post Cancelled!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(getApplicationContext(), "Post Sharing Failed!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            if (selectedItems.contains("Whatsapp")) {
                if (resolveInfo.activityInfo.packageName.startsWith("com.whatsapp")) {
                    twitterIntent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                    startActivity(twitterIntent);

                }
            }
            if (selectedItems.contains("Twitter")) {
                if (resolveInfo.activityInfo.name.startsWith("com.twitter.composer")) {
                    twitterIntent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                    startActivity(twitterIntent);
                }
            }
            if (selectedItems.contains("Linkedin")) {
                if (resolveInfo.activityInfo.packageName.startsWith("com.linkedin.android")) {
                    twitterIntent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                    startActivity(twitterIntent);

                }
            }
            if (selectedItems.contains("Instagram")) {
                if (resolveInfo.activityInfo.packageName.startsWith("com.instagram.android")) {
                    twitterIntent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                    startActivity(twitterIntent);

                }
            }
        }
    }
}
