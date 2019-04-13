package com.example.ahsan.manyconnects;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MessageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Bundle i = getIntent().getExtras();
        String r=null,t=null,m=null;
        try{
            r = i.getString("receiver");
            t = i.getString("timestamp");
            m = i.getString("message");
        }catch (Exception e){
            e.printStackTrace();
        }
        if (!r.equals(null) && !t.equals(null) && !m.equals(null)){
            try{
                ((TextView)findViewById(R.id.receiverTextView)).setText(r);
                ((TextView)findViewById(R.id.timestampTextView)).setText(t);
                ((TextView)findViewById(R.id.messageTextView)).setText(m);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
