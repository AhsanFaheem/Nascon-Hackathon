package com.example.ahsan.manyconnects;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;

public class NewMsg extends AppCompatActivity {

    ArrayList<Boolean> postOnApps = null;
    EditText msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_msg);
        postOnApps = new ArrayList<Boolean>(){
            {
                add(false);
                add(false);
                add(false);
                add(false);
                add(false);
            }
        };
        msg=findViewById(R.id.msgEntered);
    }


    public void SendMessage(View view) {
        if(msg.getText().toString().equals("")){
            AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(NewMsg.this);
            alertDialogBuilder.setTitle("Empty Post");
            alertDialogBuilder.setMessage("Please enter some text to be posted");
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setPositiveButton("OK",null);
            AlertDialog alertDialog=alertDialogBuilder.show();
            alertDialog.show();
        }
        else {
                postOnWall(msg.getText().toString());

        }

    }
    private void postOnWall(String msg){
        Intent twitterIntent = new Intent(Intent.ACTION_SEND);
        twitterIntent.putExtra(Intent.EXTRA_TEXT,"Checking");
        twitterIntent.setType("text/plain");
        PackageManager packageManager=getPackageManager();
        List<ResolveInfo> resolveInfoList=packageManager.queryIntentActivities(twitterIntent,PackageManager.MATCH_DEFAULT_ONLY);
        boolean resolved=false;
        ArrayList<Boolean> posted = null;
        posted = new ArrayList<Boolean>(){
            {
                add(false);
                add(false);
                add(false);
                add(false);
                add(false);
            }
        };
        for(ResolveInfo resolveInfo:resolveInfoList){

            if(postOnApps.get(2)){
                if (resolveInfo.activityInfo.name.startsWith("com.twitter.composer")) {
                    twitterIntent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                    startActivity(twitterIntent);
                }
            }
            if(postOnApps.get(0)){

            }
            if(postOnApps.get(4)){
                if (resolveInfo.activityInfo.packageName.startsWith("com.linkedin.android")) {
                    twitterIntent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                    startActivity(twitterIntent);

                }
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void checkForMediumClick(View v) {
//        if(v.getBackground().getC == getResources().getColor(R.color.colorPrimary))
        if (ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)) != v.getBackgroundTintList()) {
            switch (v.getId()) {
                case R.id.fbbutton:
                    postOnApps.set(0, true);
                    break;
                case R.id.instabutton:
                    postOnApps.set(1, true);
                    break;
                case R.id.twitterbutton:
                    postOnApps.set(2, true);
                    break;
                case R.id.whatsappbutton:
                    postOnApps.set(3, true);
                    break;
                case R.id.linkedinbutton:
                    postOnApps.set(4, true);
                    break;
            }
            if (v.getId() == R.id.fbbutton) {

            }
            v.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        } else {
            switch (v.getId()) {
                case R.id.fbbutton:
                    postOnApps.set(0, false);
                    break;
                case R.id.instabutton:
                    postOnApps.set(1, false);
                    break;
                case R.id.twitterbutton:
                    postOnApps.set(2, false);
                    break;
                case R.id.whatsappbutton:
                    postOnApps.set(3, false);
                    break;
                case R.id.linkedinbutton:
                    postOnApps.set(4, false);
                    break;
            }
            v.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        }
    }
}
