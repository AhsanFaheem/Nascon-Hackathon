package com.example.ahsan.manyconnects;
import android.content.Intent;
import android.se.omapi.Session;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.FacebookActivity;
import com.facebook.FacebookContentProvider;
import com.facebook.FacebookSdk;
import com.facebook.internal.FacebookInitProvider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* Intent intent=new Intent(MainActivity.this,NewMsg.class);
        startActivity(intent);*/

    }
}
