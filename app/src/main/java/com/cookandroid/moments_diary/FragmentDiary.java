package com.cookandroid.moments_diary;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentDiary extends AppCompatActivity {

    BottomNavigationView bottomMenu;
    ListView listview;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_tab);

        // DB 생성
        DBHelper helper;
        SQLiteDatabase db;
        helper = new DBHelper(this, "newdb.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);
        DBControl dbc = new DBControl(db);

//        final LinearLayout llDiary = (LinearLayout)View.inflate(MainActivity.this, R.layout.write_diary, null);
//        // 쓰기 버튼 inflate
//        final LinearLayout llWrite = (LinearLayout)View.inflate(MainActivity.this, R.layout.write_dialog, null);
//        // 스케줄 수정 버튼 inflate
//        final LinearLayout llModify = (LinearLayout)View.inflate(MainActivity.this, R.layout.modify_dialog, null);

        // Adapter 생성
        adapter = new ListViewAdapter();

        // 리스트뷰 참조 및 Adapter 설정
        listview = (ListView) findViewById(R.id.lvDiary);
//        listview2 = (ListView) findViewById(R.id.lv);
//        listview2.setAdapter(adapter);

        // DB에 있는 값을 arrayList에 추가하고 arrayList에 있는 값을 화면의 listview에 출력
        String sql = "select * from DiaryTable order by DATE;";
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
//        arrayList.add(new Content);
        String[] strs = new String[]{"DIARY_CONTENT", "DATE"};
//        String[] strs2 = new String[]{"TITLE"};
        int[] ints = new int[] {R.id.tvDiaryContent, R.id.tvDate};

//        void refresh() {
//
//        }

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(listview.getContext(),
                R.layout.diary_layout,
                c,
                strs,
                ints,
                0);

        listview.setAdapter(adapter);

    }

//    public static FragmentDiary newInstance() {
//        return new FragmentDiary();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.diary_tab, container, false);
//    }
}

