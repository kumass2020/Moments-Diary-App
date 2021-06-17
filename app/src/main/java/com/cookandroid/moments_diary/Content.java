package com.cookandroid.moments_diary;

import java.text.SimpleDateFormat;

public class Content {
//    private SimpleDateFormat
    private String title, reply, targetDate;

    public Content(String title, String reply, String targetDate) {
        this.title = title;
        this.reply = reply;
        this.targetDate = targetDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }
}
