package com.example.ahsan.manyconnects;

import android.content.Context;

import java.util.ArrayList;

public class item {
    private String receiver;
    private String timeStamp;
    private String message;
    private ArrayList<Integer> icons;

    public item() {
        this.receiver = "default receiver";
        this.message = "default message";
        this.timeStamp = "default timestamp";
        this.icons = new ArrayList<Integer>();
        this.icons.add(R.drawable.fbicon);
        this.icons.add(R.drawable.instagramicon);
        this.icons.add(R.drawable.linkedinicon);
        this.icons.add(R.drawable.twittericon);
        this.icons.add(R.drawable.whatsappicon);
    }

    public item(String receiver, String timeStamp, String message) {
        this.receiver = receiver;
        this.timeStamp = timeStamp;
        this.message = message;
    }

    public item(String receiver, String timeStamp, String message, ArrayList<Integer> icons) {
        this.receiver = receiver;
        this.timeStamp = timeStamp;
        this.message = message;
        this.icons = icons;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Integer> getIcons() {
        return icons;
    }

    public void setIcons(ArrayList<Integer> icons) {
        this.icons = icons;
    }
}
