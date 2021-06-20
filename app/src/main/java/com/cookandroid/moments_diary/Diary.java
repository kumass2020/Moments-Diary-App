package com.cookandroid.moments_diary;

public class Diary {

    private int _id;
    private String diaryContent, date;

    public int get_id() {
        return this._id;
    }

    public void set_id(int id) {
        _id = id;
    }


    public String getDate() {
        return this.date;
    }

    public void setDate(String targetDate) {
        date = targetDate;
    }

    public String getDiaryContent() {
        return this.diaryContent;
    }

    public void setDiaryContent(String _diaryContent) {
        diaryContent = _diaryContent;
    }



}
