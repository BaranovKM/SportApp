package com.konstantin.sportapp;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Константин on 15.09.2016.
 */
/*
 *Вспомогательный класс, отображает список упражнений
 */
public class ListFragmentForExercises extends ListFragment {
    ListAdapterForExercises adapter;
    private ArrayList<String> exerciseNames;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout,container,false);
        //return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        exerciseNames = new ArrayList<String>();
        for (int i = 0; i < 7; i++) {
            exerciseNames.add("Exercise name "+i);

        }
        adapter = new ListAdapterForExercises(getActivity(),exerciseNames);
        setListAdapter(adapter);
        //getListView().setOnItemClickListener(this);
    }

}
