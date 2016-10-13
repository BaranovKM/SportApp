package com.konstantin.sportapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivityWithELV extends AppCompatActivity {

    String test = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_with_elv);

        List<String> listDataHeader = new ArrayList<>();
        HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
        List<String> rows;
//TODO добавить прогресс-бар показывающий выполнение тренировки

        //настройка автоматического заполнения адаптера по данным из бд
//        String[] exercisesNames = {"Подтягивания", "Пресс", "Отжимания"};
//        int[] exerciseRows = {3, 1, 5};
//        int[] exercisesIterations = {5, 60, 10};
//
//        for (String name : exercisesNames) {
//            listDataHeader.add(name);
//        }
//
//        List<String> rows = new ArrayList<>();
//        for (int row : exerciseRows) {
//            rows.add("+ " + exerciseIterations + " iterations");
//        }
//
//        for (int i = 0; i < exerciseRows; i++) {
//        }
//        listDataChild.put(listDataHeader.get(0), rows);
//TODO вынести все это в лоадер
        Cursor cursor = new DBHelper(this).getWritableDatabase()
                .query(DBHelper.TABLE_EXERCISES, null, null, null, null, null, null);
        cursor.moveToFirst();

        //разбор курсора и сортировка данных, которые затем вставляются в адаптер для expandable listview
        for (int i = 0; i < cursor.getCount(); i++) {
            String exerciseName = cursor.getString(cursor.getColumnIndex(DBHelper.EXERCISE_NAME));
            listDataHeader.add(exerciseName);
            int rowsQuantity = cursor.getInt(
                    cursor.getColumnIndex(DBHelper.ROWS_IN_WORKOUT));
            String iterations = Integer.toString(
                    cursor.getInt(cursor.getColumnIndex(DBHelper.ITERATIONS_IN_ROW)));
            rows = new ArrayList<>();
            for (int j = 0; j < rowsQuantity; j++) {
                rows.add("+ " + iterations + " iterations");
            }
            listDataChild.put(exerciseName,rows);
            //проверка содержимого массива
            for (String string : rows) {
                test = test + string + " ";
            }
            Log.d("TEST_LOG", "Массив содержит : " + test);

            if (cursor.isAfterLast()) {
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();

//        listDataHeader.add(exerciseName);
//        List<String> rows = new ArrayList<>();
//        for (int i = 0; i < exerciseRows; i++) {
//            rows.add("+ "+exerciseIterations+" iterations");
//        }
//        listDataChild.put(listDataHeader.get(0), rows);

        //вставка данных в адаптер
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView2);
        AdapterForELV adapterForELV = new AdapterForELV(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(adapterForELV);

        //тесты
//        for (String string : rows) {
//            test = test + string + " ";
//        }
//        Log.d("TEST_LOG", "Массив содержит : " + test);
        Log.d("TEST_LOG", "LOADER RETURN RESULT :" + cursor.getCount() + " strings");
    }
}
