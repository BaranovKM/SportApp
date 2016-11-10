package com.konstantin.sportapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Константин on 19.10.2016.
 */

public class PhotoFragment extends Fragment {
    /*
     *Фрагмент для отображения картинок(фотки с камеры)
     */
    public static PhotoFragment newInstance() {
        PhotoFragment fragment = new PhotoFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.photo,container,false);
        GridView gridView = (GridView) view.findViewById(R.id.gridViewForPhoto);

        String[] from = {"image"};
        int[] to = {R.id.imageViewPhotoItem};
        ArrayList<Map<String,Object>> data = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
        Map<String,Object> map;
        map = new HashMap<>();
        map.put("image",R.drawable.moto);
        data.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),data,R.layout.photo_item,from,to);
        gridView.setAdapter(simpleAdapter);
        return view;
    }
}
