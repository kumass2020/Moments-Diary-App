package com.cookandroid.moments_diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

                String dayStr = item.getDay();
                String titleStr = item.getTitle();
            }
        });
    }
}