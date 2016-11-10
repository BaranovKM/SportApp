package com.konstantin.sportapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Константин on 15.09.2016.
 */


public class AdapterForEditor extends CursorAdapter {

    /*
     *Вспомогательный класс для отображения списка тренировок
     *(мост между данными из курсора и полями элементов списка)
     */

    private LayoutInflater layoutInflater;

    public AdapterForEditor(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.workouts_in_list, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = (TextView) view.findViewById(R.id.workoutName);
        // name.setText(cursor.getString());
        TextView exercises = (TextView) view.findViewById(R.id.exercises);
    }

}
