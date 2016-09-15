package com.konstantin.sportapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
//TODO придумать редактор тренировок(пример добавления записей в бд из текста в textView : андроид1,видео8 40 минута)
//TODO в редакторе упражнений сделать для полей хинты и подсказки : https://youtu.be/egKox1-6cEk?list=PLyfVjOYzujugEUT-7gYhONqB5Y1xszpCq
//TODO сделать майнд-мап для приложения
//TODO Сделать кнопку для ручного добавления повторений в последнем подходе(например как выбор даты)
//TODO Сделать кнопки для старта и окончания тренировки
//TODO После завершения тренировки отображать статистику(подходы/повторения за тренировку и общее кол.во и за все время)
public class TrainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ContentValues contentValues;
    private Cursor cursor;
    private TextView testView;

    //тестовые переменные для CursorLoader
    private String test1;
    private int test2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        createTraining();//выводится список упражнений для тренировки
    }

    private void createTraining() {
//TODO сделать заполнение фрагмента из бд 2) сделать выведение нескольких фрагментов с разными записями

       test1 ="Name";
        for (int i = 0; i < 3; i++) {
            test2 = i;


            Exercises exercise = Exercises.newInstance(
                    test1,
                    test2
            );
            getFragmentManager().beginTransaction().add(R.id.fragmentContainer, exercise).commit();
        }
    }

    public void onClick(View view) {
        Log.d("log", "PUSH BUTTON");
//        getLoaderManager().initLoader(2, null, this);
//        getLoaderManager().getLoader(2).forceLoad();

        TextView testView = (TextView) findViewById(R.id.testView);
        testView.setText(test1);
        //вставка нового упражнения
//        contentValues = new ContentValues();
//        contentValues.put(DBHelper.GYMNASTIC_NAME_COLUMN, "Отжимания");
//        contentValues.put(DBHelper.ROWS_PER_TRAINING_COLUMN, 5);
//        contentValues.put(DBHelper.ITERATIONS_PER_TRAINING_COLUMN, 15);
//        sqLiteDatabase.insert(DBHelper.DATABASE_TABLE_TRAINING,null,contentValues);
//        Log.d("log", "PUSH GYM");

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Loader<Cursor> loader = new MyCursorLoader(this);
        Log.d("log", "Loader create in activity");
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d("TEST_LOG", "Loader return result");
        test1 = cursor.getString(cursor.getColumnIndex(DBHelper.GYMNASTIC_NAME_COLUMN));
        test2 = cursor.getInt(cursor.getColumnIndex(DBHelper.ROWS_PER_TRAINING_COLUMN));

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
