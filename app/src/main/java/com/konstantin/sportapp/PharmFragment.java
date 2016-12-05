package com.konstantin.sportapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Константин on 29.11.2016.
 */

public class PharmFragment extends Fragment {
    /*
     *Фрагмент для учета фармакологии и прочих "витаминок"
     */
    public static PharmFragment newInstance() {
        PharmFragment fragment = new PharmFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cap, container, false);
        return view;
    }
}
