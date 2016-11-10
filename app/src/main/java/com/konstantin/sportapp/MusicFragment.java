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

public class MusicFragment extends Fragment {
    /*
     *Фрагмент для проигрывания аудиофайлов
     */
    public static MusicFragment newInstance() {
        MusicFragment fragment = new MusicFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.music,container,false);
return  view;
    }
}
