package com.konstantin.sportapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Константин on 30.08.2016.
 */
public class Exercises extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercise_layout, null);
        TextView nameExercise = (TextView) view.findViewById(R.id.fragmentTextView);
//        nameExercise.setText("Exercise name");
        nameExercise.setText(getArguments().getString("nameInFragment"));
        return view;
    }

    public static Exercises newInstance(String someText) {

        Exercises fragment = new Exercises();
        Bundle args = new Bundle();
        args.putString("nameInFragment",someText);
        fragment.setArguments(args);
        return fragment;
    }

}
