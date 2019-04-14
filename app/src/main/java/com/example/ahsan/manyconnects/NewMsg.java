package com.example.ahsan.manyconnects;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import org.joda.time.DateTime;
import org.joda.time.ReadableDateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.joda.time.Seconds.secondsBetween;


public class NewMsg extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_DATA = 22;
    ArrayList<Boolean> postOnApps = null;
    private CallbackManager callbackManager;
    EditText msg;
    public static Context context;
    private ArrayList<String> selectedItems = new ArrayList<>();
    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            // Do something with the date. This Date object contains
            // the date and time that the user has selected.
            DateTime to = new DateTime(date);
            DateTime from = new DateTime(new Date());
            Log.d("RESPONSE=", String.valueOf(secondsBetween(from, to).toString()));
            Calendar calendar = dateToCalendar(date);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            AlarmManager alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(NewMsg.this, ScheduledMessageService.class);
            intent.putExtra("selected_items", selectedItems);
            intent.putExtra("message", msg.getText().toString());

            PendingIntent pIntent = PendingIntent.getService(context, 0, intent, 0);
            if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
                Log.d("RESPONSE=", "Time has passed");
            }
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);

            Toast.makeText(context, "Message Scheduled!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onDateTimeCancel() {
            // Overriding onDateTimeCancel() is optional.
            Toast.makeText(context, "Date/Time not set", Toast.LENGTH_LONG).show();
        }
    };

    private Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_msg);
        context = this;
        postOnApps = new ArrayList<Boolean>() {
            {
                add(false);
                add(false);
                add(false);
                add(false);
                add(false);
            }
        };
        msg = findViewById(R.id.msgEntered);
    }


    public void SendMessage(View view) {
        if (msg.getText().toString().equals("")) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewMsg.this);
            alertDialogBuilder.setTitle("Empty Post");
            alertDialogBuilder.setMessage("Please enter some text to be posted");
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setPositiveButton("OK", null);
            AlertDialog alertDialog = alertDialogBuilder.show();
            alertDialog.show();
        } else {
            postOnWall(msg.getText().toString());
        }
    }

    public void ScheduleMessage(View view) {
        if (msg.getText().toString().equals("")) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewMsg.this);
            alertDialogBuilder.setTitle("Empty Post");
            alertDialogBuilder.setMessage("Please enter some text to be posted");
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setPositiveButton("OK", null);
            AlertDialog alertDialog = alertDialogBuilder.show();
            alertDialog.show();
        } else {
            new SlideDateTimePicker.Builder(getSupportFragmentManager())
                    .setListener(listener)
                    .setInitialDate(new Date())
                    .setMinDate(new Date())
                    .setTheme(SlideDateTimePicker.HOLO_DARK)
                    .setIndicatorColor(Color.parseColor("#FF0000")) //To specify the color for the sliding tab underline (indicator)
                    .build()
                    .show();
        }
    }

    private void postOnWall(String msg) {
        Intent twitterIntent = new Intent(Intent.ACTION_SEND);
        twitterIntent.putExtra(Intent.EXTRA_TEXT, msg);
        twitterIntent.setType("text/plain");
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(twitterIntent, PackageManager.MATCH_DEFAULT_ONLY);
        final boolean resolved = false;
        ArrayList<Boolean> posted = null;
        posted = new ArrayList<Boolean>() {
            {
                add(false);
                add(false);
                add(false);
                add(false);
                add(false);
            }
        };
        for (ResolveInfo resolveInfo : resolveInfoList) {

            if (postOnApps.get(2)) {
                //twitter
                if (resolveInfo.activityInfo.name.startsWith("com.twitter.composer")) {
                    twitterIntent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                    startActivity(twitterIntent);
                }
            }
            if (postOnApps.get(0)) {
                //facebook
                callbackManager = CallbackManager.Factory.create();
// https://stackoverflow.com/a/22123047/2065418

                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
                        //.setShareHashtag(new ShareHashtag.Builder()
                        //                .setHashtag("#ConnectTheWorld")
                        //                .build());  //if we want hashtag
                        //.setQuote("Connect on a global scale.")   //if we want to add a quote to post
                        .build();

                ShareDialog shareDialog = new ShareDialog(this);
                shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);

                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(context, "Post Shared Successfully!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(context, "Post Cancelled!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(context, "Post Sharing Failed!", Toast.LENGTH_LONG).show();
                    }
                });

                //For Multimedia
                //People can share photos from your app to Facebook with the Share Dialog.
                // In order to share, they must have the native Facebook for Android app installed,
                // version 7.0 or higher.

//                askForReadPermission();

                    /*
                    Bitmap image = ...
                    SharePhoto sharePhoto1 = new SharePhoto.Builder()
                            .setBitmap(image)
                            .build();
                    Bitmap image = ...
                    SharePhoto sharePhoto2 = new SharePhoto.Builder()
                            .setBitmap(image)
                            .build();
                    Uri videoFileUri = ...
                    ShareVideo shareVideo1 = new ShareVideo.Builder()
                            .setLocalUrl(videoFileUri)
                            .build();
                    Uri videoFileUri = ...
                    ShareVideo shareVideo2 = new ShareVideo.Builder()
                            .setLocalUrl(videoFileUri)
                            .build();

                    ShareContent shareContent = new ShareMediaContent.Builder()
                            .addMedium(sharePhoto1)
                            .addMedium(sharePhoto2)
                            .addMedium(shareVideo1)
                            .addMedium(shareVideo2)
                            .build();
                     */


            }
            if (postOnApps.get(1)) {
                //instagram
                if (resolveInfo.activityInfo.packageName.startsWith("com.instagram.android")) {
                    twitterIntent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                    startActivity(twitterIntent);

                }
            }
            if (postOnApps.get(3)) {
                //whatsapp
                if (resolveInfo.activityInfo.packageName.startsWith("com.whatsapp")) {
                    twitterIntent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                    startActivity(twitterIntent);

                }
            }
            if (postOnApps.get(4)) {
                //linked in
                if (resolveInfo.activityInfo.packageName.startsWith("com.linkedin.android")) {
                    twitterIntent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                    startActivity(twitterIntent);

                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_DATA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getMedia();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
/* Following code is functional and will send photos.*/
    private void askForReadPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_EXTERNAL_STORAGE}
                                    , PERMISSION_REQUEST_DATA);
                        }
                    });
                    builder.show();
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSION_REQUEST_DATA);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }else{
                getMedia();
            }
        }
        else{
            getMedia();
        }
    }

    private void getMedia() {
        File sd = Environment.getExternalStorageDirectory();
        File image = new File(sd+"/Download", "images-1.png");
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
        SharePhoto sharePhoto1 = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .build();
        SharePhotoContent content1 = new SharePhotoContent.Builder()
                .addPhoto(sharePhoto1)
                .build();

        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.show(content1, ShareDialog.Mode.AUTOMATIC);

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(context, "Post Shared Successfully!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(context, "Post Cancelled!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(context, "Post Sharing Failed!", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void checkForMediumClick(View v) {
//        if(v.getBackground().getC == getResources().getColor(R.color.colorPrimary))
        if (ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)) != v.getBackgroundTintList()) {
            switch (v.getId()) {
                case R.id.fbbutton:
                    postOnApps.set(0, true);
                    if (!selectedItems.contains("Facebook"))
                        selectedItems.add("Facebook");
                    break;
                case R.id.instabutton:
                    postOnApps.set(1, true);
                    if (!selectedItems.contains("Instagram"))
                        selectedItems.add("Instagram");
                    break;
                case R.id.twitterbutton:
                    postOnApps.set(2, true);
                    if (!selectedItems.contains("Twitter"))
                        selectedItems.add("Twitter");
                    break;
                case R.id.whatsappbutton:
                    postOnApps.set(3, true);
                    if (!selectedItems.contains("Whatsapp"))
                        selectedItems.add("Whatsapp");
                    break;
                case R.id.linkedinbutton:
                    postOnApps.set(4, true);
                    if (!selectedItems.contains("Linkedin"))
                        selectedItems.add("Linkedin");
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
