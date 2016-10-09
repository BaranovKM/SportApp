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
    /*
     *Вспомогательный класс для отображения списка тренировок
     *(мост между данными из курсора и полями элементов списка)
     */


public class AdapterForEditor extends CursorAdapter {

    private LayoutInflater layoutInflater;

    public AdapterForEditor(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.workouts_in_list,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = (TextView) view.findViewById(R.id.workoutName);
       // name.setText(cursor.getString());
        TextView exercises = (TextView) view.findViewById(R.id.exercises);
    }

//    Context context;
//    ArrayList<String> listItem;

//    public AdapterForEditor(Context context, ArrayList<String> rowItem) {
//        this.context = context;
//        this.listItem = rowItem;
//    }
//
//    @Override
//    public int getCount() {
//        return listItem.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return listItem.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return listItem.indexOf(getItem(position));
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        if(convertView == null){
//            LayoutInflater layoutInflater = (LayoutInflater) context
//                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//            convertView = layoutInflater.inflate(R.layout.exercise_layout,null);
//        }
//        TextView textView = (TextView) convertView.findViewById(R.id.fragmentTextView);
//        textView.setText(getItem(position).toString());
//        return convertView;
//    }
}
