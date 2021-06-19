package com.cookandroid.moments_diary;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB 생성
        DBHelper helper;
        SQLiteDatabase db;
        helper = new DBHelper(MainActivity.this, "newdb.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        ListView listview ;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter 설정
        listview = (ListView) findViewById(R.id.lv);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem("25", "Box");
        // 두 번째 아이템 추가.
        adapter.addItem("25", "Circle");
        // 세 번째 아이템 추가.
        adapter.addItem("25", "Ind");

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                // get item
                Content item = (Content)parent.getItemAtPosition(position);
//                String dayStr = item.getDay();
//                String titleStr = item.getTitle();

                // Content 클릭 시 팝업 메뉴
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
                getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.addDiary){
                            Toast.makeText(MainActivity.this, "메뉴 1 클릭", Toast.LENGTH_SHORT).show();
                        }else if (menuItem.getItemId() == R.id.scheduleModify){
                            Toast.makeText(MainActivity.this, "메뉴 2 클릭", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "메뉴 3 클릭", Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                });
                popupMenu.show();


            }
        });
    }
}