package com.cookandroid.moments_diary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class FragmentDiary extends Fragment {
    public static FragmentDiary newInstance() {
        return new FragmentDiary();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.diary_tab, container, false);
    }
}

