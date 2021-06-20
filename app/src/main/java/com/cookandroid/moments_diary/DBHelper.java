package com.cookandroid.moments_diary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // DB 초기화 필요 시
//        db.execSQL("drop table ContentTable");
//        db.execSQL("delete from DiaryTable");

        // 외래키 허용
        db.setForeignKeyConstraintsEnabled(true);

        String sql = "CREATE TABLE if not exists ContentTable ("
                + "_id integer primary key autoincrement,"
                + "TARGET_DATE text,"
                + "DATE text,"
                + "TITLE text);";

        db.execSQL(sql);

        String sql2 = "CREATE TABLE if not exists DiaryTable ("
                + "_id integer primary key autoincrement,"
                + "DIARY_CONTENT text,"
                + "DATE text,"
                + "PARENT_TARGET_DATE text references ContentTable(TARGET_DATE),"
                + "PARENT_ID integer references ContentTable(_id));";

        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE if exists mytable";

        db.execSQL(sql);
        onCreate(db);
    }
}
