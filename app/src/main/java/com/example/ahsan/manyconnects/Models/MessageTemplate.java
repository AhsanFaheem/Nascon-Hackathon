package com.example.ahsan.manyconnects.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageTemplate implements Parcelable {

    protected MessageTemplate(Parcel in) {
        header = in.readString();
        body = in.readString();
        footer = in.readString();
    }

    public static final Creator<MessageTemplate> CREATOR = new Creator<MessageTemplate>() {
        @Override
        public MessageTemplate createFromParcel(Parcel parcel) {
            return new MessageTemplate(parcel);
        }

        @Override
        public MessageTemplate[] newArray(int i) {
            return new MessageTemplate[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(header);
        parcel.writeString(body);
        parcel.writeString(footer);
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    private String header;
    private String body;
    private String footer;

    public MessageTemplate(String header, String body, String footer){
        this.header = header;
        this.body = body;
        this.footer = footer;
    }

    public MessageTemplate(){
        this.body = "default message";
    }
}
