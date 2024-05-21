package com.tsai.chatapp.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ChatMessage {
    String senderId;
    String text;
    long time;
    public boolean isMine;

    public ChatMessage() {
    }

    public ChatMessage(String senderId, String text, long time) {
        this.senderId = senderId;
        this.text = text;
        this.time = time;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isMine() {
        if (senderId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            return true;
        }
        return false;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public String convertTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date(getTime());
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(date);
    }
}
