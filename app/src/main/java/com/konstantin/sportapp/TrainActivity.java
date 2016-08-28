package com.konstantin.sportapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
//TODO придумать редактор тренировок
//TODO Сделать кнопку для ручного добавления повторений в последнем подходе(например как выбор даты)
//TODO Сделать кнопки для старта и окончания тренировки
//TODO После завершения тренировки отображать статистику(подходы/повторения за тренировку и общее кол.во и за все время)
public class TrainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private ContentValues contentValues;
    private Cursor cursor;
    private TextView testView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        //подключение к бд
        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        //вставка новой записи в БД
//        dbHelper = new DBHelper(this, DBHelper.DATABASE_TABLE_TRAINING, null, 1);//непонятно почему, вместо имени базы нужно передавать имя таблицы
//        sqLiteDatabase = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(DBHelper.GYMNASTIC_NAME_COLUMN, "Подтягивания");
//        values.put(DBHelper.ROWS_PER_TRAIN_COLUMN, 5);
//        values.put(DBHelper.ITERATIONS_COUNT_COLUMN, 6);
//        sqLiteDatabase.insert(DBHelper.DATABASE_TABLE_TRAINING,null,values);
//TODO Создать прокручиваемый список упражнений с добавляющимися кнопками по количеству подходов
//TODO для обучения можно использовать ListView, но потом лучше перейти на ListFragment какболее современный
        //создается список упражнения с кол.вом подходов
        cursor = sqLiteDatabase.query(DBHelper.DATABASE_TABLE_TRAINING, new String[]{
                        DBHelper.GYMNASTIC_NAME_COLUMN,
//                        DBHelper.ROWS_PER_TRAIN_COLUMN,
                        DBHelper.ROWS_PER_TRAINING_COLUMN},
                null, null, null, null, null);
        cursor.moveToFirst();
        TextView gymnasticName = (TextView) findViewById(R.id.gymnasticName);
        gymnasticName.setText(cursor.getString(cursor.getColumnIndex(DBHelper.GYMNASTIC_NAME_COLUMN)));

        //show rows
        testView = (TextView) findViewById(R.id.testView);
        testView.setText(cursor.getString(cursor.getColumnIndex(DBHelper.ROWS_PER_TRAINING_COLUMN)));

        cursor.close();
    }

    public void onClick(View view) {
        //вставка нового упражнения
//        contentValues = new ContentValues();
//        contentValues.put(DBHelper.GYMNASTIC_NAME_COLUMN, "Отжимания");
//        contentValues.put(DBHelper.ROWS_PER_TRAINING_COLUMN, 5);
//        contentValues.put(DBHelper.ITERATIONS_PER_TRAINING_COLUMN, 15);
//        sqLiteDatabase.insert(DBHelper.DATABASE_TABLE_TRAINING,null,contentValues);
//        Log.d("log", "PUSH GYM");


        //Log.d("log", "BUTTON PRESSED");
//        cursor = sqLiteDatabase.query(DBHelper.DATABASE_TABLE_TRAINING, new String[]{
//                       DBHelper.GYMNASTIC_NAME_COLUMN,
//                        DBHelper.ROWS_PER_TRAIN_COLUMN,
//                        DBHelper.ITERATIONS_COUNT_COLUMN},
//                null, null, null, null, null);
//        cursor.moveToFirst();
//        contentValues = new ContentValues();
//        int i;
//        i = cursor.getInt(cursor.getColumnIndex(DBHelper.ITERATIONS_COUNT_COLUMN));
//        i = i + 5;
//        contentValues.put(DBHelper.ITERATIONS_COUNT_COLUMN, i);
//        int updCount = sqLiteDatabase.update(
//                DBHelper.DATABASE_TABLE_TRAINING,
//                contentValues,
//                DBHelper.GYMNASTIC_NAME_COLUMN + " = ?",
//                new String[]{"Подтягивания"});
//        Log.d("LOG", "UPDATED ROWS : " + updCount);
//        testView = (TextView) findViewById(R.id.testView);
//        testView.setText(Integer.toString(i));
//        cursor.close();
    }
}
