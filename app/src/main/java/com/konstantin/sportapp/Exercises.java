package com.konstantin.sportapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Константин on 30.08.2016.
 */
public class Exercises extends Fragment {
    /*
     *Фрагмент отображающий список из упражнений в конкретной тренировке
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercise_layout, null);
        return view;
    }

    public static Exercises newInstance(String nameExercise, int rows) {

        Exercises fragment = new Exercises();
        Bundle args = new Bundle();
        args.putString("nameExercise",nameExercise);
        args.putInt("rows",rows);
        fragment.setArguments(args);
        return fragment;
    }

}
