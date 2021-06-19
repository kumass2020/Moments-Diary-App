package com.cookandroid.moments_diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;
    ListView listView;
    BottomNavigationView bottomMenu;
    FragmentManager fragmentManager = getSupportFragmentManager();

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

        final LinearLayout llWrite = (LinearLayout)View.inflate(MainActivity.this, R.layout.write_dialog, null);

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
                String strDay = item.getDay();
                String strTitle = item.getTitle();

                // Content 클릭 시 팝업 메뉴
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
                getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.addDiary){
                            Toast.makeText(MainActivity.this, "다이어리 작성 클릭", Toast.LENGTH_SHORT).show();

                        } else if (menuItem.getItemId() == R.id.scheduleModify){
                            Toast.makeText(MainActivity.this, "스케줄 수정 클릭", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "스케줄 삭제 클릭", Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                });
                popupMenu.show();

            }
        });

        // 하단 메뉴 선택 시 Action
        bottomMenu = (BottomNavigationView)findViewById(R.id.bottom_menu);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch(item.getItemId())
                {
                    case R.id.first_tab:
                        new AlertDialog().Builder(MainActivity.this)
                                .setView(llWrite)
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        EditText etWrite = (EditText)llWrite.findViewById(R.id.etWrite);
                                        String value = etWrite.getText().toString();
                                        DBControl dbc = new DBControl(db);
                                        dbc.insertContent(value);
                                        refreshFragment(db);
                                    }
                                })
                        break;

                    case R.id.second_tab:
                        break;

                    case R.id.third_tab:
                        break;

                    case R.id.fourth_tab:
                        break;
                }

                return true;
            }
        });

    }

    // 현재 fragment 새로고침. DB 내용이 화면에 반영되는 효과.
    void refreshFragment(SQLiteDatabase db) {

    }
}