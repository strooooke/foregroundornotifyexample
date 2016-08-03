package com.lambdasoup.foregroundornotifyexample;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Stand-in for the info needed for notification or activity
 */
public class Message implements Parcelable{

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Message() {
    }

    protected Message(Parcel in) {
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
