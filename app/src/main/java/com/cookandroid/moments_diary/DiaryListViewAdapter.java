package com.cookandroid.moments_diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DiaryListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<Diary> listViewDiaryList = new ArrayList<Diary>() ;

    // ListViewAdapter의 생성자
    public DiaryListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴
    @Override
    public int getCount() {
                return listViewDiaryList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    @Override
    public Object getItem(int position) {
        return listViewDiaryList.get(position);
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_layout, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView dateTextView = (TextView) convertView.findViewById(R.id.tvDate) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.tvDiaryContent) ;
//        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Diary listViewDiary = listViewDiaryList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        dateTextView.setText(listViewDiary.getDate());
        titleTextView.setText(listViewDiary.getDiaryContent());
//        descTextView.setText(listViewDiary.getDesc());

        return convertView;
    }

    // 아이템 데이터 추가를 위한 함수
    public void addItem(String date, String title) {
        Diary item = new Diary();

        item.setDate(date);
        item.setDiaryContent(title);
//        item.setDesc(desc);

        listViewDiaryList.add(item);
    }
}
