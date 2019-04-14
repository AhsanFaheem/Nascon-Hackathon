package com.example.ahsan.manyconnects.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ahsan.manyconnects.R;

public class MessageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Bundle i = getIntent().getExtras();
        String r=null, t=null, m=null, icon = null;
        try{
            r = i.getString("receiver");
            t = i.getString("timestamp");
            m = i.getString("message");
            icon = i.getString("icons");
        }catch (Exception e){
            e.printStackTrace();
        }
        if (!r.equals(null) && !t.equals(null) && !m.equals(null) && !icon.equals(null)){
            try{
                ((TextView)findViewById(R.id.receiverTextView)).setText(r);
                ((TextView)findViewById(R.id.timestampTextView)).setText(t);
                ((TextView)findViewById(R.id.messageTextView)).setText(m);
                String platform[] = icon.split(",");
                for(int j = 0; j < platform.length; j++){
                    if(platform[j].equals("Facebook"))
                        findViewById(R.id.fbbutton).setVisibility(View.VISIBLE);
                    else if(platform[j].equals("Whatsapp"))
                        findViewById(R.id.whatsappbutton).setVisibility(View.VISIBLE);
                    else if(platform[j].equals("Instagram"))
                        findViewById(R.id.instabutton).setVisibility(View.VISIBLE);
                    else if(platform[j].equals("Linkedin"))
                        findViewById(R.id.linkedinbutton).setVisibility(View.VISIBLE);
                    else if(platform[j].equals("Twitter"))
                        findViewById(R.id.twitterbutton).setVisibility(View.VISIBLE);
                }
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
