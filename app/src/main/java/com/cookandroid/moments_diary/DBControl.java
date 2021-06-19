package com.cookandroid.moments_diary;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBControl {

    SQLiteDatabase db;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

//    DBHelper helper;
//    SQLiteDatabase db;
//    helper = new DBHelper(MainActivity.this, "newdb.db", null, 1);
//    db = helper.getWritableDatabase();
//        helper.onCreate(db);

    // 생성자
    public DBControl(SQLiteDatabase db) {
        System.out.println("DBControl Constructor");
        this.db = db;
    }

    // DB에 Content 생성
    public void insertContent(String title) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        String strDate = sdf.format(date);

        ContentValues values = new ContentValues();
        values.put("DATE", strDate);
        values.put("TITLE", title);
        db.insert("ContentTable",null, values);
    }

    // 테이블 안의 Content 삭제
    public void deleteTitle(int _id) {
        db.delete("ContentTable", "_id=?", new String[]{Integer.toString(_id)});
    }

    // 테이블 안의 Content 수정
    public void updateTitle(int _id, String title) {
        ContentValues values = new ContentValues();
        values.put("TITLE", title);
        db.update("ContentTable", values, "_id=?", new String[]{Integer.toString(_id)});
    }

//    public void selectAllTitle() {
//
//    }
}

