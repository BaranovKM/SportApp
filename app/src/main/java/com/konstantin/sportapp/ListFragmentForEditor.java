package com.konstantin.sportapp;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Константин on 15.09.2016.
 */
/*
 *Вспомогательный класс, отображает список упражнений
 */
public class ListFragmentForEditor extends ListFragment implements LoadWorkouts.AsyncResponse {
//    Cursor cursor;
//    AdapterForEditor adapter;
//    private ArrayList<String> exerciseNames;

    //новый адаптер и массивы для него
//    SimpleCursorAdapter adapter;
//    String[] fromColumn = {DBHelper.GYMNASTIC_NAME_COLUMN, DBHelper.ROWS_PER_TRAINING};
//    int[] toViews = {R.id.fragmentTextView,R.id.buttonCheck};
//    String[] fromColumn = {DBHelper.GYMNASTIC_NAME_COLUMN};
//    int[] toViews = {R.id.fragmentTextView};

        ArrayList<Map<String,String>> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.list_layout,container,false);
        return inflater.inflate(R.layout.list_of_workouts_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        adapter = new SimpleCursorAdapter(getActivity(), R.layout.workouts_in_list, cursor, fromColumn, toViews, 0);
//        adapter = new AdapterForEditor(getActivity(), cursor,0);

        LoadWorkouts asyncTask = new LoadWorkouts();
        asyncTask.asyncResponse = this;//задается слушатель интерфейса для получения данных из асинтаска
        asyncTask.execute(getActivity());



        SimpleAdapter adapter;
        String[] from = {"workout","exercises"};
        int [] to = {R.id.workoutName,R.id.exercises};
        adapter =  new SimpleAdapter(getActivity(),data,R.layout.workouts_in_list,from,to);
        setListAdapter(adapter);
        // getLoaderManager().initLoader(0,null,this);

    }

    @Override
    public void processFinished(HashMap<String, String> output) {
        Log.d("TEST_SQL", "EXTRACT DATA FROM HASHMAP : " + output.get("Руки"));
        Map<String,String> map;

        for (String key : output.keySet()) {
            map = new HashMap<>();
            map.put("workout",key);
            map.put("exercises", output.get(key));
            data.add(map);
        }

    }
}
