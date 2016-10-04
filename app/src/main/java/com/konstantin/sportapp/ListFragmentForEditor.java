package com.konstantin.sportapp;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

/**
 * Created by Константин on 15.09.2016.
 */
/*
 *Вспомогательный класс, отображает список упражнений
 */
public class ListFragmentForEditor extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    Cursor cursor;
    AdapterForEditor adapter;
//    private ArrayList<String> exerciseNames;

    //новый адаптер и массивы для него
//    SimpleCursorAdapter adapter;
//    String[] fromColumn = {DBHelper.GYMNASTIC_NAME_COLUMN, DBHelper.ROWS_PER_TRAINING_COLUMN};
//    int[] toViews = {R.id.fragmentTextView,R.id.buttonCheck};
//    String[] fromColumn = {DBHelper.GYMNASTIC_NAME_COLUMN};
//    int[] toViews = {R.id.fragmentTextView};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.list_layout,container,false);
        return inflater.inflate(R.layout.list_of_workouts_layout,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        adapter = new SimpleCursorAdapter(getActivity(), R.layout.workouts_in_list, cursor, fromColumn, toViews, 0);
        adapter = new AdapterForEditor(getActivity(), cursor,0);
        setListAdapter(adapter);
        getLoaderManager().initLoader(0,null,this);

        //эксперимент с sql
        ArrayList<String> tableNames = new ArrayList<>();
        Cursor sqlCursor = new DBHelper(getContext()).getWritableDatabase()
                .rawQuery("SELECT name FROM sqlite_master WHERE type='table'",null);
        sqlCursor.moveToFirst();
        for (int i = 0; i < sqlCursor.getCount(); i++) {
            tableNames.add(sqlCursor.getString(sqlCursor.getColumnIndex("name")));
            if (sqlCursor.isAfterLast()){
                break;
            }
            sqlCursor.moveToNext();
        }
        for (String name : tableNames) {
        Log.d("TEST_SQL", "Table name :"+name);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
         Log.d("TEST_LOG", "LOADER RETURN RESULT :"+cursor.getCount()+" strings");
//         Log.d("TEST_LOG", "LOADER RETURN RESULT :"+columnsInCursor+" strings");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
