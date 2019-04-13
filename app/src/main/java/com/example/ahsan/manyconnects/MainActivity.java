package com.example.ahsan.manyconnects;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<item> recentitems = null;

    private FloatingActionButton newMessageButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recentitems = new ArrayList<>();
        GridLayoutManager gridLayout = new GridLayoutManager(this,1);
        gridLayout.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerView recentMessages = findViewById(R.id.recentMsgsListView);
        recentMessages.setLayoutManager(gridLayout);

        recentitems.add(new item("Ahsan Faheem","13-04-19","Yar baaz a jao please"));
        recentitems.add(new item("Farrukh","13-04-19","Mene tow kuch bhi nai kaha"));
        recentitems.add(new item("Hamza Khan","13-04-19","paghal log yar apna apna kaam karo jaldi se"));


        listviewAdapter rcAdapter = new listviewAdapter(recentitems, getApplicationContext(), R.layout.listview_item,null);
        recentMessages.setAdapter(rcAdapter);

        findViewById(R.id.newMsgButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),NewMsg.class));
            }
        });


    }
}
