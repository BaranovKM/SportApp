package com.konstantin.sportapp;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
//TODO придумать редактор тренировок(пример добавления записей в бд из текста в textView : андроид1,видео8 40 минута)
//TODO в редакторе упражнений сделать для полей хинты и подсказки : https://youtu.be/egKox1-6cEk?list=PLyfVjOYzujugEUT-7gYhONqB5Y1xszpCq

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

        //создается список упражнения с кол.вом подходов
        //TODO Cursor оказывается устарел еще 2 года назад. Изучить CursorLoader и переделать считывание из БД
        cursor = sqLiteDatabase.query(DBHelper.DATABASE_TABLE_TRAINING, new String[]{
                        DBHelper.GYMNASTIC_NAME_COLUMN,
//                        DBHelper.ROWS_PER_TRAIN_COLUMN,
                        DBHelper.ROWS_PER_TRAINING_COLUMN},
                null, null, null, null, null);
        cursor.moveToFirst();
//        TextView gymnasticName = (TextView) findViewById(R.id.gymnasticName);
//        gymnasticName.setText(cursor.getString(cursor.getColumnIndex(DBHelper.GYMNASTIC_NAME_COLUMN)));
        cursor.close();
//TODO 1) сделать заполнение фрагмента из бд 2) сделать выведение нескольких фрагментов с разными записями

    }
//TODO разобраться как передавать нужный текст/данные во фрагмент
    public void onClick(View view) {
        Log.d("log", "PUSH BUTTON");
        String someText = new String("Some text from Activity");

        Exercises exercise = Exercises.newInstance(someText);
        getFragmentManager().beginTransaction().add(R.id.fragmentContainer, exercise).commit();
//TODO попробовать передачу аргументов и интерфесы
        //вставка нового упражнения
//        contentValues = new ContentValues();
//        contentValues.put(DBHelper.GYMNASTIC_NAME_COLUMN, "Отжимания");
//        contentValues.put(DBHelper.ROWS_PER_TRAINING_COLUMN, 5);
//        contentValues.put(DBHelper.ITERATIONS_PER_TRAINING_COLUMN, 15);
//        sqLiteDatabase.insert(DBHelper.DATABASE_TABLE_TRAINING,null,contentValues);
        Log.d("log", "PUSH GYM");

    }
}
