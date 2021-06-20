package com.cookandroid.moments_diary;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentSetting extends AppCompatActivity {

    BottomNavigationView bottomMenu;
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_tab);

        // DB 생성
        DBHelper helper;
        SQLiteDatabase db;
        helper = new DBHelper(this, "newdb.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);
        DBControl dbc = new DBControl(db);

        btnReset = (Button)findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbc.deleteDB();
            }
        });

    }
}
