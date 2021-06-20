package com.cookandroid.moments_diary;

import android.app.Activity;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

public class Content{
//    private SimpleDateFormat
    private int _id;
    private String title, reply, day;

//    public Content(String title, String reply, String targetDate) {
//        this.title = title;
//        this.reply = reply;
//        this.day = targetDate;
//    }
    public int get_id() {
        return this._id;
    }

    public void set_id(int id) {
        _id = id;
    }


    public String getDay() {
        return this.day;
    }

    public void setDay(String targetDate) {
        day = targetDate;
    }

    public String getTitle() {
//        TextView tvDay = (TextView)activity.findViewById(R.id.tvDay);
//        title = tvDay.getText().toString();
        return this.title;
    }

    public void setTitle(String _title) {
        title = _title;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }


}
