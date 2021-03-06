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

        // Tab ??????
//        TabHost tabHost = getTabHost();

        ButterKnife.bind(this);
//        bottomMenu.setOnNavigationItemSelectedListener(mOnNavigationItenSelectedListener);

//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.fragment_container, FragmentSchedule.newInstance()).commit();


        // DB ??????
        DBHelper helper;
        SQLiteDatabase db;
        helper = new DBHelper(MainActivity.this, "newdb.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);
        DBControl dbc = new DBControl(db);

        final LinearLayout llDiary = (LinearLayout)View.inflate(MainActivity.this, R.layout.write_diary, null);
        // ?????? ?????? inflate
        final LinearLayout llWrite = (LinearLayout)View.inflate(MainActivity.this, R.layout.write_dialog, null);
        // ????????? ?????? ?????? inflate
        final LinearLayout llModify = (LinearLayout)View.inflate(MainActivity.this, R.layout.modify_dialog, null);

        // Adapter ??????
        adapter = new ListViewAdapter();

        // ???????????? ?????? ??? Adapter ??????
        listview = (ListView) findViewById(R.id.lv);
//        listview2 = (ListView) findViewById(R.id.lv);
//        listview2.setAdapter(adapter);

        // DB??? ?????? ?????? arrayList??? ???????????? arrayList??? ?????? ?????? ????????? listview??? ??????
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

//        // ??? ?????? ????????? ??????.
//        adapter.addItem("25", "Box");
//        // ??? ?????? ????????? ??????.
//        adapter.addItem("25", "Circle");
//        // ??? ?????? ????????? ??????.
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

                // Content ?????? ??? ?????? ??????
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
                getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        // ???????????? ?????? ?????? ???
                        if (menuItem.getItemId() == R.id.addDiary){
                            Toast.makeText(MainActivity.this, "???????????? ?????? ??????", Toast.LENGTH_SHORT).show();
                            if (llDiary.getParent() != null)
                                ((ViewGroup)llDiary.getParent()).removeView(llDiary);
                            new AlertDialog.Builder(MainActivity.this)
                                    .setView(llDiary)
                                    .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int i) {

                                            // EditText??? ???????????? ????????? ???????????? ?????????
                                            EditText etDiary = (EditText)llDiary.findViewById(R.id.etDiary);
                                            String value = etDiary.getText().toString();

                                            // DB??? Content ??????
                                            dbc.insertDiary(value, 1, _id);

                                            // DB??? ?????? ?????? ????????? ?????????(???????????? ??????)
                                            c.requery();
                                            adapter.notifyDataSetChanged();

                                            // EditText ?????????
                                            etDiary.setText("");

                                            // ??????????????? ??????
                                            dialog.dismiss();
                                        }
                                    })
                                    .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int i) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                        // ????????? ?????? ?????? ???
                        else if (menuItem.getItemId() == R.id.scheduleModify){
                            Toast.makeText(MainActivity.this, "????????? ?????? ??????", Toast.LENGTH_SHORT).show();
                            if (llModify.getParent() != null)
                                ((ViewGroup)llModify.getParent()).removeView(llModify);
                            new AlertDialog.Builder(MainActivity.this)
                                    .setView(llModify)
                                    .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int i) {

                                            // EditText??? ???????????? ????????? ???????????? ?????????
                                            EditText etModify = (EditText)llModify.findViewById(R.id.etModify);
                                            String value = etModify.getText().toString();

                                            // DB??? Content ??????
                                            dbc.updateTitle(_id, value);

                                            // DB??? ?????? ?????? ????????? ?????????(???????????? ??????)
                                            c.requery();
                                            adapter.notifyDataSetChanged();

                                            // EditText ?????????
                                            etModify.setText("");

                                            // ??????????????? ??????
                                            dialog.dismiss();
                                        }
                                    })
                                    .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int i) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                        // ????????? ?????? ?????? ???
                        else {
                            Toast.makeText(MainActivity.this, "????????? ?????? ??????", Toast.LENGTH_SHORT).show();
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

        // Calendar ????????? ?????? ??? ?????????
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

        // ?????? ?????? ?????? ??? Action
        bottomMenu = (BottomNavigationView)findViewById(R.id.bottom_menu);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch(item.getItemId())
                {
                    // ????????? ???: ????????? ??????
                    case R.id.first_tab:
                        if (llWrite.getParent() != null)
                            ((ViewGroup)llWrite.getParent()).removeView(llWrite);
                        new AlertDialog.Builder(MainActivity.this)
                                .setView(llWrite)
                                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
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

//                                        // Calendar ????????? ?????? ??? ?????????
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

                                        // ?????? ?????? ?????????
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        long now = System.currentTimeMillis();
                                        Date date = new Date(now);

                                        // ????????? ?????? ?????? ??????
                                        if(targetDate.equals("0-0-0") || targetDate.equals("0-00-0") || targetDate.equals("0-00-00")) {
                                            // ?????? ????????? ??????.
                                            targetDate = sdf.format(date);
                                        }

                                        // EditText??? ???????????? ????????? ???????????? ?????????
                                        EditText etWrite = (EditText)llWrite.findViewById(R.id.etWrite);
                                        String value = etWrite.getText().toString();

                                        // DB??? Content ??????
                                        dbc.insertContent(targetDate, value);
//                                        refreshFragment();

                                        // DB??? ?????? ?????? ????????? ?????????(???????????? ??????)
                                        c.requery();
                                        adapter.notifyDataSetChanged();

                                        // CalendarView ?????????
                                        cvWrite.setDate(System.currentTimeMillis(),false,true);
                                        selYear = 0;
                                        selMonth = 0;
                                        selDay = 0;

                                        // EditText ?????????
                                        etWrite.setText("");

                                        // ??????????????? ??????
//                                        removeDialog();
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int i) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                        break;

                    // ????????? ???: ????????? ???(?????? ??????)
                    case R.id.second_tab:
                        replaceFragment(FragmentSchedule.newInstance());
                        return true;

                    // ????????? ???: ???????????? ????????????
                    case R.id.third_tab:
                        Intent intent = new Intent(getApplicationContext(),FragmentDiary.class);
                        startActivityForResult(intent, 101);
                        return true;

                    // ????????? ???: ??????
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


    // ?????? fragment ????????????. DB ????????? ????????? ???????????? ??????.
    void refreshFragment() {
//        c.requery();
        adapter.notifyDataSetChanged();
//        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.detach(this).attach(this).commit();
    }


//    private void checkBottomOfScroll(ListView listview){
//        //????????? ???????????? ????????? ???????????? ??????????????? ??????
//        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                //?????? ????????? ????????? ????????? ????????? ???????????? ??????(firstVisibleItem) + ?????? ????????? ????????? ????????? ???????????? ??????(visibleItemCount)??? ????????? ????????? ??????(totalItemCount) -1 ?????? ????????? ?????????
//                boolean lastitemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
//            }
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                //OnScrollListener.SCROLL_STATE_IDLE??? ???????????? ??????????????? ??????????????? ???????????? ????????? ???????????????.
//                //??? ???????????? ????????? ?????? ?????? ????????? ????????? ???????????? ???
//                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastitemVisibleFlag) {
//                    Toast.makeText(mGlobalContext, "????????? ????????? ?????????.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//


}