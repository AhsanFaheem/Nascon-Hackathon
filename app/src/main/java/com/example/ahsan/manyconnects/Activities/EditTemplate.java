package com.example.ahsan.manyconnects.Activities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.example.ahsan.manyconnects.Models.MessageTemplate;
import com.example.ahsan.manyconnects.R;

public class EditTemplate extends AppCompatActivity {

    private MessageTemplate userTemplate;
    private AutoCompleteTextView msgHeader;
    private AutoCompleteTextView msgFooter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_template);

        initializeVariables();
        setVariables();
    }

    private void setVariables() {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.msg_template), MODE_PRIVATE);
        String restoredHeaderText = prefs.getString(getString(R.string.msg_header), null);
        String restoredFooterText = prefs.getString(getString(R.string.msg_footer), null);
        if (restoredHeaderText != null)
            msgHeader.setText(restoredHeaderText);
        if (restoredFooterText != null)
            msgFooter.setText(restoredFooterText);
    }

    private void initializeVariables() {
        userTemplate = new MessageTemplate();
        msgHeader = findViewById(R.id.msg_header);
        msgFooter = findViewById(R.id.msg_footer);
    }

    public void SaveTemplate(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditTemplate.this);
        alertDialogBuilder.setTitle("Test message");
        final AlertDialog alertDialog;
        alertDialogBuilder.setMessage(msgHeader.getText().toString() + "\n\n" + "Default Message Body...."
                + "\n\n" + msgFooter.getText().toString());
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userTemplate.setHeader(msgHeader.getText().toString());
                userTemplate.setFooter(msgFooter.getText().toString());
                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.msg_template), MODE_PRIVATE).edit();
                editor.putString(getString(R.string.msg_header), userTemplate.getHeader());
                editor.putString(getString(R.string.msg_footer), userTemplate.getFooter());
                editor.apply();
                dialogInterface.dismiss();
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog = alertDialogBuilder.show();
        alertDialog.show();

    }
}
