package com.konstantin.sportapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Константин on 19.10.2016.
 */
public class DietFragment extends Fragment {
    /*
     *Фрагмент для отображения раздела с диетой и "витаминками"
     */
    public static DietFragment newInstance() {
        DietFragment fragment = new DietFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.cap,container,false);
        return  view;
    }
}
