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


/**
 * Created by Константин on 15.09.2016.
 */
/*
 *Вспомогательный класс, отображает список упражнений
 */
public class ListFragmentForExercises extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    //ListAdapterForExercises adapter;
//    private ArrayList<String> exerciseNames;

    //новый адаптер и массивы для него
    SimpleCursorAdapter adapter;
//    String[] fromColumn = {DBHelper.GYMNASTIC_NAME_COLUMN, DBHelper.ROWS_PER_TRAINING_COLUMN};
//    int[] toViews = {R.id.fragmentTextView,R.id.buttonCheck};
    String[] fromColumn = {DBHelper.GYMNASTIC_NAME_COLUMN};
    int[] toViews = {R.id.fragmentTextView};
    Cursor cursor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new SimpleCursorAdapter(getActivity(), R.layout.exercise_layout, cursor, fromColumn, toViews, 0);
        setListAdapter(adapter);
        getLoaderManager().initLoader(0,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //TODO удалить неиспользующиеся лоадеры
        adapter.swapCursor(cursor);
         Log.d("TEST_LOG", "LOADER RETURN RESULT :"+cursor.getCount()+" strings");
//        String[] columnsInCursor = cursor.getColumnNames() ;
//         Log.d("TEST_LOG", "LOADER RETURN RESULT :"+columnsInCursor+" strings");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
