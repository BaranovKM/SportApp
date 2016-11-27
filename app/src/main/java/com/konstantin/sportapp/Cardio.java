package com.konstantin.sportapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Константин on 06.09.2016.
 */
public class Cardio extends AppCompatActivity{
    /*
    Вспомогательный класс для работы с разделом кардио-тренировок
     */

    public static final String FRAGMENT_TAG = "cardio"; //константа для идентификации фрагмента


    //быстрое заполнение (для тестов) списка упражнений
    String[] rootElements = new String[]{"Отжимания","Подтягивания","Пресс"};
    String[] childItems1 = new String[]{"+10","+10","+10"};
    String[] childItems2 = new String[]{"+5","+4","+3"};
    String[] childItems3 = new String[]{"60"};

    ArrayList<Map<String,String>> rootsList;//список групп
    ArrayList<Map<String,String>> childItemsList;//список элементов в группе

    ArrayList<ArrayList<Map<String,String>>> listOfChildren; //список всех выпадающих элементов

    Map<String,String> map; //список атрибутов группы или элемента
    ExpandableListView expandableListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardio);

        //заполнятеся список групп
        rootsList = new ArrayList<Map<String, String>>();
        for (String group : rootElements) {
            map = new HashMap<>();
            map.put("exerciseName", group);
            rootsList.add(map);
        }
        //атрибуты групп
        String[] groupFrom = new String[]{"groupName"};
        int[] groupTo = new int[]{android.R.id.text1};

        listOfChildren = new ArrayList<ArrayList<Map<String,String>>>();

        //заполнятеся список элементов группы
        childItemsList = new ArrayList<Map<String, String>>();
        for (String row : childItems1) {
            map = new HashMap<>();
            map.put("row in exercise", row);
            childItemsList.add(map);
        }
        listOfChildren.add(childItemsList);

        childItemsList = new ArrayList<Map<String, String>>();
        for (String row : childItems2) {
            map = new HashMap<>();
            map.put("row in exercise", row);
            childItemsList.add(map);
        }
        listOfChildren.add(childItemsList);

        childItemsList = new ArrayList<Map<String, String>>();
        for (String row : childItems3) {
            map = new HashMap<>();
            map.put("row in exercise", row);
            childItemsList.add(map);
        }
        listOfChildren.add(childItemsList);

        //атрибуты элементов
        String[] childFrom = new String[]{"childName"};
        int[] childTo = new int[]{android.R.id.text1};

        //настройка адаптера
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                rootsList,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                listOfChildren,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo

        );
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListView.setAdapter(adapter);

        //тесты
        Log.d("TEST_LOG", "Массив rootsList содержит : " +rootsList.toString());
        Log.d("TEST_LOG", "Массив listOfChildren содержит : "+listOfChildren.toString());

    }

}
