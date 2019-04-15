package com.example.ahsan.manyconnects.Models;

import android.content.Context;

import com.example.ahsan.manyconnects.R;

import java.util.ArrayList;

public class item {
    private String receiver;
    private String timeStamp;
    private MessageTemplate message;

    public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }

    private String platforms;

    public item() {
        this.receiver = "default receiver";
        message = new MessageTemplate();
        this.timeStamp = "default timestamp";
    }

    public item(String receiver, String timeStamp, MessageTemplate message, String platforms) {
        this.receiver = receiver;
        this.timeStamp = timeStamp;
        this.message = message;
        this.platforms = platforms;
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

    public MessageTemplate getMessage() {
        return message;
    }

    public void setMessage(MessageTemplate message) {
        this.message = message;
    }
}
