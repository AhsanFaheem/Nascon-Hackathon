package com.example.ahsan.manyconnects;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewMsg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_msg);
    }
    public void checkForMediumClick(View v){
//        if(v.getBackground().getC == getResources().getColor(R.color.colorPrimary))
        if(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)) != v.getBackgroundTintList())
            v.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        else
            v.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
    }
}
