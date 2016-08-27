package com.konstantin.sportapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class TrainActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private SQLiteDatabase sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        //вставка новой записи в БД
        dbHelper = new DBHelper(this, DBHelper.DATABASE_TABLE_TRAIN, null, 1);//непонятно почему, вместо имени базы нужно передавать имя таблицы
        sdb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.GYMNASTIC_NAME_COLUMN, "Подтягивания");
        values.put(DBHelper.ROWS_COUNT_COLUMN, 5);
        values.put(DBHelper.ITERATIONS_COUNT_COLUMN, 6);
        sdb.insert(DBHelper.DATABASE_TABLE_TRAIN,null,values);
    }

    public void onClick(View view) {
        Log.d("log", "BUTTON PRESSED");

        //создание новой БД
//        dbHelper = new DBHelper(this,DBHelper.DATABASE_NAME,null,1);
//        sdb = dbHelper.getReadableDatabase();

        //Чтение из БД
        Cursor cursor = sdb.query(DBHelper.DATABASE_TABLE_TRAIN, new String[]{
                DBHelper.GYMNASTIC_NAME_COLUMN,
                DBHelper.ROWS_COUNT_COLUMN,
                DBHelper.ITERATIONS_COUNT_COLUMN},
        null,null,null,null,null);
        cursor.moveToFirst();

        TextView infoTextView = (TextView) findViewById(R.id.textView);
        infoTextView.setText(
                cursor.getString(cursor.getColumnIndex(DBHelper.GYMNASTIC_NAME_COLUMN))+" "+
                cursor.getInt(cursor.getColumnIndex(DBHelper.ROWS_COUNT_COLUMN))+" "+
                cursor.getInt(cursor.getColumnIndex(DBHelper.ITERATIONS_COUNT_COLUMN))
        );
        cursor.close();
        Log.d("log", "BD READABLE");
    }
}
