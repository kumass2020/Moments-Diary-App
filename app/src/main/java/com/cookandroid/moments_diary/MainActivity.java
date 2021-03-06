package com.cookandroid.moments_diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    ArrayList<Content> arrayList;
//    ArrayAdapter arrayAdapter;
//    ListView listView;
    @BindView(R.id.bottom_menu)
    BottomNavigationView bottomMenu;

//    FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment fa, fb, fc, fd;

    ListView listview, listview2;
    ListViewAdapter adapter;

    CalendarView cvWrite;
    boolean eventFlag = false;
    int selYear, selMonth, selDay;
    String targetDate;
    String strMonth, strDay;

//    static TabHost tabHost;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tab 생성
//        TabHost tabHost = getTabHost();

        ButterKnife.bind(this);
//        bottomMenu.setOnNavigationItemSelectedListener(mOnNavigationItenSelectedListener);

//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.fragment_container, FragmentSchedule.newInstance()).commit();


        // DB 생성
        DBHelper helper;
        SQLiteDatabase db;
        helper = new DBHelper(MainActivity.this, "newdb.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);
        DBControl dbc = new DBControl(db);

        final LinearLayout llDiary = (LinearLayout)View.inflate(MainActivity.this, R.layout.write_diary, null);
        // 쓰기 버튼 inflate
        final LinearLayout llWrite = (LinearLayout)View.inflate(MainActivity.this, R.layout.write_dialog, null);
        // 스케줄 수정 버튼 inflate
        final LinearLayout llModify = (LinearLayout)View.inflate(MainActivity.this, R.layout.modify_dialog, null);

        // Adapter 생성
        adapter = new ListViewAdapter();

        // 리스트뷰 참조 및 Adapter 설정
        listview = (ListView) findViewById(R.id.lv);
//        listview2 = (ListView) findViewById(R.id.lv);
//        listview2.setAdapter(adapter);

        // DB에 있는 값을 arrayList에 추가하고 arrayList에 있는 값을 화면의 listview에 출력
        String sql = "select * from ContentTable order by TARGET_DATE;";
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
//        arrayList.add(new Content);
        String[] strs = new String[]{"DISP_TARGET_DATE", "TITLE"};
//        String[] strs2 = new String[]{"TITLE"};
        int[] ints = new int[] {R.id.tvDay, R.id.tvTitle};

//        void refresh() {
//
//        }

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(listview.getContext(),
                R.layout.content_layout,
                c,
                strs,
                ints,
                0);

        listview.setAdapter(adapter);

//        listview.

//        // 첫 번째 아이템 추가.
//        adapter.addItem("25", "Box");
//        // 두 번째 아이템 추가.
//        adapter.addItem("25", "Circle");
//        // 세 번째 아이템 추가.
//        adapter.addItem("25", "Ind");

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

//                Cursor cursor = ((SimpleCursorAdapter)l.getAdapter()).getCursor();
//                cursor.moveToPosition(position);
//                Content item = cursor.getClass(cursor.getColumnIndex)

//                Cursor c = (Cursor)parent.getItemAtPosition(position);

                int _id = (int)adapter.getItemId(position);

//                // get item
//                Content item = (Content)parent.getItemAtPosition(position);
//                int _id = item.get_id();
//                String strDay = item.getDay();
//                String strTitle = item.getTitle();

                // Content 클릭 시 팝업 메뉴
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
                getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        // 다이어리 작성 클릭 시
                        if (menuItem.getItemId() == R.id.addDiary){
                            Toast.makeText(MainActivity.this, "다이어리 작성 클릭", Toast.LENGTH_SHORT).show();
                            if (llDiary.getParent() != null)
                                ((ViewGroup)llDiary.getParent()).removeView(llDiary);
                            new AlertDialog.Builder(MainActivity.this)
                                    .setView(llDiary)
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int i) {

                                            // EditText에 사용자가 작성한 문자열을 읽어옴
                                            EditText etDiary = (EditText)llDiary.findViewById(R.id.etDiary);
                                            String value = etDiary.getText().toString();

                                            // DB에 Content 생성
                                            dbc.insertDiary(value, 1, _id);

                                            // DB에 있는 값을 화면에 재배치(새로고침 효과)
                                            c.requery();
                                            adapter.notifyDataSetChanged();

                                            // EditText 초기화
                                            etDiary.setText("");

                                            // 다이얼로그 종료
                                            dialog.dismiss();
                                        }
                                    })
                                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int i) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                        // 스케줄 수정 클릭 시
                        else if (menuItem.getItemId() == R.id.scheduleModify){
                            Toast.makeText(MainActivity.this, "스케줄 수정 클릭", Toast.LENGTH_SHORT).show();
                            if (llModify.getParent() != null)
                                ((ViewGroup)llModify.getParent()).removeView(llModify);
                            new AlertDialog.Builder(MainActivity.this)
                                    .setView(llModify)
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int i) {

                                            // EditText에 사용자가 작성한 문자열을 읽어옴
                                            EditText etModify = (EditText)llModify.findViewById(R.id.etModify);
                                            String value = etModify.getText().toString();

                                            // DB에 Content 생성
                                            dbc.updateTitle(_id, value);

                                            // DB에 있는 값을 화면에 재배치(새로고침 효과)
                                            c.requery();
                                            adapter.notifyDataSetChanged();

                                            // EditText 초기화
                                            etModify.setText("");

                                            // 다이얼로그 종료
                                            dialog.dismiss();
                                        }
                                    })
                                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int i) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                        // 스케줄 삭제 클릭 시
                        else {
                            Toast.makeText(MainActivity.this, "스케줄 삭제 클릭", Toast.LENGTH_SHORT).show();
                            dbc.deleteTitle(_id);
                            c.requery();
                            adapter.notifyDataSetChanged();
                        }

                        return false;
                    }
                });
                popupMenu.show();

            }
        });

        // Calendar 사용자 선택 값 읽어옴
        cvWrite = (CalendarView)llWrite.findViewById(R.id.cvWrite);
        cvWrite.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                selYear = year;
                selMonth = month+1;
                selDay = dayOfMonth;
                eventFlag = true;
            }
        });

        // 하단 메뉴 선택 시 Action
        bottomMenu = (BottomNavigationView)findViewById(R.id.bottom_menu);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch(item.getItemId())
                {
                    // 첫번째 탭: 스케줄 쓰기
                    case R.id.first_tab:
                        if (llWrite.getParent() != null)
                            ((ViewGroup)llWrite.getParent()).removeView(llWrite);
                        new AlertDialog.Builder(MainActivity.this)
                                .setView(llWrite)
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int i) {
                                        if(selMonth < 10) {
                                            strMonth = "0" + String.valueOf(selMonth);
                                        } else {
                                            strMonth = String.valueOf(selMonth);
                                        }
                                        if(selDay < 10) {
                                            strDay = "0" + String.valueOf(selDay);
                                        } else {
                                            strDay = String.valueOf(selDay);
                                        }

                                        targetDate = String.valueOf(selYear) + "-" + strMonth + "-" + strDay;

//                                        // Calendar 사용자 선택 값 읽어옴
//                                        if(selMonth < 10 || selDay < 10) {
//                                            targetDate = String.valueOf(selYear) + "-0" + String.valueOf(selMonth) + "-0" + String.valueOf(selDay);
//                                        } else {
//                                            targetDate = String.valueOf(selYear) + "-" + String.valueOf(selMonth) + "-" + String.valueOf(selDay);
//                                        }
//
//                                        if (selDay < 10) {
//                                            targetDate = String.valueOf(selYear) + "-" + String.valueOf(selMonth) + "-0" + String.valueOf(selDay);
//                                        } else {
//                                            targetDate = String.valueOf(selYear) + "-" + String.valueOf(selMonth) + "-" + String.valueOf(selDay);
//
//                                        }

                                        // 현재 날짜 구하기
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        long now = System.currentTimeMillis();
                                        Date date = new Date(now);

                                        // 날짜가 변동 없을 경우
                                        if(targetDate.equals("0-0-0") || targetDate.equals("0-00-0") || targetDate.equals("0-00-00")) {
                                            // 오늘 날짜로 한다.
                                            targetDate = sdf.format(date);
                                        }

                                        // EditText에 사용자가 작성한 문자열을 읽어옴
                                        EditText etWrite = (EditText)llWrite.findViewById(R.id.etWrite);
                                        String value = etWrite.getText().toString();

                                        // DB에 Content 생성
                                        dbc.insertContent(targetDate, value);
//                                        refreshFragment();

                                        // DB에 있는 값을 화면에 재배치(새로고침 효과)
                                        c.requery();
                                        adapter.notifyDataSetChanged();

                                        // CalendarView 초기화
                                        cvWrite.setDate(System.currentTimeMillis(),false,true);
                                        selYear = 0;
                                        selMonth = 0;
                                        selDay = 0;

                                        // EditText 초기화
                                        etWrite.setText("");

                                        // 다이얼로그 종료
//                                        removeDialog();
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int i) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                        break;

                    // 두번째 탭: 스케줄 탭(기본 화면)
                    case R.id.second_tab:
                        replaceFragment(FragmentSchedule.newInstance());
                        return true;

                    // 세번째 탭: 다이어리 모아보기
                    case R.id.third_tab:
                        Intent intent = new Intent(getApplicationContext(),FragmentDiary.class);
                        startActivityForResult(intent, 101);
                        return true;

                    // 네번째 탭: 설정
                    case R.id.fourth_tab:
                        Intent intent2 = new Intent(getApplicationContext(),FragmentSetting.class);
                        startActivityForResult(intent2, 101);
                        break;
                }

                return false;
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }


    // 현재 fragment 새로고침. DB 내용이 화면에 반영되는 효과.
    void refreshFragment() {
//        c.requery();
        adapter.notifyDataSetChanged();
//        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.detach(this).attach(this).commit();
    }


//    private void checkBottomOfScroll(ListView listview){
//        //화면에 리스트의 마지막 아이템이 보여지는지 체크
//        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
//                boolean lastitemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
//            }
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
//                //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻
//                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastitemVisibleFlag) {
//                    Toast.makeText(mGlobalContext, "화면의 마지막 입니다.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//


}