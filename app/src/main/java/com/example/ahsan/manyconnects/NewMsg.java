package com.example.ahsan.manyconnects;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class NewMsg extends AppCompatActivity {

    ArrayList<Boolean> postOnApps = null;
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
    }
    public void checkForMediumClick(View v){
//        if(v.getBackground().getC == getResources().getColor(R.color.colorPrimary))
        if(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)) != v.getBackgroundTintList()) {
            switch (v.getId()){
                case R.id.fbbutton:
                    postOnApps.set(0,true);
                    break;
                case R.id.instabutton:
                    postOnApps.set(1,true);
                    break;
                case R.id.twitterbutton:
                    postOnApps.set(2,true);
                    break;
                case R.id.whatsappbutton:
                    postOnApps.set(3,true);
                    break;
                case R.id.linkedinbutton:
                    postOnApps.set(4,true);
                    break;
            }
            if (v.getId() == R.id.fbbutton){

            }
            v.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        }
        else {
            switch (v.getId()){
                case R.id.fbbutton:
                    postOnApps.set(0,false);
                    break;
                case R.id.instabutton:
                    postOnApps.set(1,false);
                    break;
                case R.id.twitterbutton:
                    postOnApps.set(2,false);
                    break;
                case R.id.whatsappbutton:
                    postOnApps.set(3,false);
                    break;
                case R.id.linkedinbutton:
                    postOnApps.set(4,false);
                    break;
            }
            v.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        }
    }
}
