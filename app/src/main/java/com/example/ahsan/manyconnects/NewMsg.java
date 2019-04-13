package com.example.ahsan.manyconnects;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
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

public class NewMsg extends AppCompatActivity {

    EditText msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_msg);
        msg=findViewById(R.id.msgEntered);
    }
    public void checkForMediumClick(View v){
//        if(v.getBackground().getC == getResources().getColor(R.color.colorPrimary))
        if(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)) != v.getBackgroundTintList())
            v.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        else
            v.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
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
            ArrayList<String> arrayList=new ArrayList<>();
            arrayList.add("linkedin");
            arrayList.add("twitter");

            for(int i=0;i<2;i++){
                postOnWall(arrayList.get(i), msg.getText().toString());
            }
        }

    }
    private void postOnWall(String appName,String msg){
        Intent twitterIntent = new Intent(Intent.ACTION_SEND);
        twitterIntent.putExtra(Intent.EXTRA_TEXT,"Checking");
        twitterIntent.setType("text/plain");
        PackageManager packageManager=getPackageManager();
        List<ResolveInfo> resolveInfoList=packageManager.queryIntentActivities(twitterIntent,PackageManager.MATCH_DEFAULT_ONLY);
        boolean resolved=false;
        for(ResolveInfo resolveInfo:resolveInfoList){

            if(appName.equals("twitter")) {
                if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                    twitterIntent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                    resolved = true;
                    break;
                }
            }
            else if(appName.equals("facebook")){

            }
            else if(appName.equals("linkedin")){
                if (resolveInfo.activityInfo.packageName.startsWith("com.linkedin.android")) {
                    twitterIntent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                    resolved = true;
                    break;
                }
            }



        }
        if(resolved){
            startActivity(twitterIntent);
        }
        else{

                AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(NewMsg.this);
                alertDialogBuilder.setTitle("Message not posted");
                alertDialogBuilder.setMessage("We weren't able to post you message on "+appName+" because required application for "+appName+" is not installed.");
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setPositiveButton("OK",null);
                AlertDialog alertDialog=alertDialogBuilder.show();
                alertDialog.show();

        }
    }
    public void checkForMediumClick(View v){
//        if(v.getBackground().getC == getResources().getColor(R.color.colorPrimary))
        if(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)) != v.getBackgroundTintList())
            v.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        else
            v.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
    }
}
