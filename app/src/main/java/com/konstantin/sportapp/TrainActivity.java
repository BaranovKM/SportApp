package com.konstantin.sportapp;

import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//TODO Сделать кнопки для старта и окончания тренировки
//TODO добавить быстрое управление медиа-плэйером(смена треков и т.д.)
//TODO После завершения тренировки отображать статистику(подходы/повторения за тренировку и общее кол.во и за все время)
public class TrainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //тестовые переменные для CursorLoader
    private String test1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
    }

    public void onClick(View view) {
        Log.d("TEST_LOG", "PUSH BUTTON");

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Loader<Cursor> loader = new MyCursorLoader(this);
        Log.d("TEST_LOG", "Loader create in activity");
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
//TODO удалить неиспользующиеся лоадеры
        Log.d("TEST_LOG", "Loader return result");
//        test1 = cursor.getString(cursor.getColumnIndex(DBHelper.GYMNASTIC_NAME_COLUMN));

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
