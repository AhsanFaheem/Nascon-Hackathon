package com.example.ahsan.manyconnects.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ahsan.manyconnects.Database.ContractClass;
import com.example.ahsan.manyconnects.Database.DBHelper;
import com.example.ahsan.manyconnects.Models.MessageTemplate;
import com.example.ahsan.manyconnects.Models.item;
import com.example.ahsan.manyconnects.R;
import com.example.ahsan.manyconnects.Adapters.listviewAdapter;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<item> recentitems = null;
    private DBHelper dbHelper;
    private listviewAdapter rcAdapter;

    private FloatingActionButton newMessageButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();
        assigningListeners();

    }

    private void gettingDataFromDB() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                ContractClass.TableEntry.COLUMN_NAME_RECEIVER,
                ContractClass.TableEntry.COLUMN_NAME_DATE,
                ContractClass.TableEntry.COLUMN_NAME_PLATFORM,
                ContractClass.TableEntry.COLUMN_NAME_MESSAGE_BODY,
                ContractClass.TableEntry.COLUMN_NAME_MESSAGE_HEADER,
                ContractClass.TableEntry.COLUMN_NAME_MESSAGE_FOOTER
        };
        recentitems.clear();
        Cursor cursor = db.query(
                ContractClass.TableEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List itemIds = new ArrayList<>();
        List<String> receiverList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        List<String> platformList = new ArrayList<>();
        List<String> messageHeaderList = new ArrayList<>();
        List<String> messageBodyList = new ArrayList<>();
        List<String> messageFooterList = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(ContractClass.TableEntry._ID));
            String receiver = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContractClass.TableEntry.COLUMN_NAME_RECEIVER));
            String date = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContractClass.TableEntry.COLUMN_NAME_DATE));
            String platform = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContractClass.TableEntry.COLUMN_NAME_PLATFORM));
            String messageHeader = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContractClass.TableEntry.COLUMN_NAME_MESSAGE_HEADER));
            String messageBody = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContractClass.TableEntry.COLUMN_NAME_MESSAGE_BODY));
            String messageFooter = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContractClass.TableEntry.COLUMN_NAME_MESSAGE_FOOTER));
            itemIds.add(itemId);
            receiverList.add(receiver);
            dateList.add(date);
            platformList.add(platform);
            messageHeaderList.add(messageHeader);
            messageBodyList.add(messageBody);
            messageFooterList.add(messageFooter);
        }
        cursor.close();

        if(itemIds.size() < 1){ //DB is empty
//            getFakeData();
//            rcAdapter.notifyDataSetChanged();
        } else {
            for(int i = 0; i < itemIds.size(); i++){
                boolean itemAlreadyPresent = false;
                item tempItem = new item(receiverList.get(i), dateList.get(i), new MessageTemplate(messageHeaderList.get(i), messageBodyList.get(i), messageFooterList.get(i)), platformList.get(i));
                for(int j = 0; j < recentitems.size() && recentitems.size() >= 1; j++){
                    if (recentitems.get(j) == tempItem)
                        itemAlreadyPresent = true;
                }
                if(!itemAlreadyPresent)
                    recentitems.add(tempItem);
            }
            rcAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        gettingDataFromDB();
        //Refresh your stuff here
    }

    private void getFakeData() {
        recentitems.add(new item("Ahsan Faheem","13-04-19",new MessageTemplate("","Yar baaz a jao please", ""), "Whatsapp"));
        recentitems.add(new item("Farrukh","13-04-19",new MessageTemplate("","Mene tow kuch bhi nai kaha",""), "Whatsapp"));
        recentitems.add(new item("Hamza Khan","13-04-19",new MessageTemplate("","paghal log yar apna apna kaam karo jaldi se",""), "Whatsapp"));
    }

    private void assigningListeners() {
        findViewById(R.id.newMsgButton).setOnClickListener(this);
        findViewById(R.id.edit_template).setOnClickListener(this);
    }

    private void initializeVariables() {
        dbHelper = new DBHelper(this);
        recentitems = new ArrayList<>();
        GridLayoutManager gridLayout = new GridLayoutManager(this,1);
        gridLayout.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerView recentMessages = findViewById(R.id.recentMsgsListView);
        recentMessages.setLayoutManager(gridLayout);

        rcAdapter = new listviewAdapter(recentitems, getApplicationContext(), R.layout.listview_item,null);
        recentMessages.setAdapter(rcAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.newMsgButton:
                startActivity(new Intent(view.getContext(),NewMsg.class));
                break;
            case R.id.edit_template:
                startActivity(new Intent(view.getContext(),EditTemplate.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
