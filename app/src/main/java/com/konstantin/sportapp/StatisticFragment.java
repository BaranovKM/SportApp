package com.konstantin.sportapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Константин on 27.11.2016.
 */

public class StatisticFragment extends Fragment {
    /*
     *Фрагмент для отображения общей статистики по тренировкам, диете и прочему
     */
    public static final String FRAGMENT_TAG = "statistic"; //константа для идентификации фрагмента

    public static StatisticFragment newInstance() {
        StatisticFragment fragment = new StatisticFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cap,container,false);
        return view;
    }
}
