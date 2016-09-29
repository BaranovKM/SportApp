package com.konstantin.sportapp;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    //TODO найти/сделать иконки для кнопок(гантель для кнопки силовых, сердце для кнопки кардио и т.д.)
    //TODO попробовать эмуляцию через genymotion
    //TODO сделать перелистывание между разделами методом ViewPager
    //TODO Сделать сохранение тренировки при переключении между активностями
    //TODO переделать главное меню: вместо кнопок сделать стандартное андроид-меню
    //TODO сделать фотогалерею(gridView) http://developer.alexanderklimov.ru/android/views/gridview.php#images
    //TODO
    //TODO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
    Обработчик кнопок в главном меню
    Переходит на новые активности для силовых тренировок, кардио тренировок,
    контроля питания и препаратов фарм.поддержки
    Также бонусом можно слушать музыку и постить фотки в контакт.
    */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonTrain:
                startActivity(new Intent(MainActivity.this, TrainActivity.class));
                break;
            case R.id.buttonCardio:
//TODO сделать раздел для кардио тренировок
                //пока что ExpandableListview заполняется из таблицы тренировок
                startActivity(new Intent(this, ActivityWithELV.class));
                break;
            case R.id.buttonDiet:
//TODO сделать раздел для диеты
//придумать кнопку для добавления внесистемной еды/перекусов
                break;
            case R.id.buttonPharm:
//TODO сделать раздел для витаминок и допинга

                break;
            case R.id.buttonPhoto:
//TODO сделать селфи и возможность постит их в вк/инстаграмм

                break;
            case R.id.buttonMusic:
//TODO сделать раздел для музыки и аудиокниг

                break;
            case R.id.buttonSettings:
                //заполнение базы данных (вставка нового упражнения)
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBHelper.GYMNASTIC_NAME_COLUMN, "Пресс");
                contentValues.put(DBHelper.ROWS_PER_TRAINING_COLUMN, 1);
                contentValues.put(DBHelper.ITERATIONS_PER_TRAINING_COLUMN, 60);

//                sqLiteDatabase.insert(DBHelper.DATABASE_TABLE_TRAINING, null, contentValues);
                new DBHelper(this).getWritableDatabase().insert(DBHelper.DATABASE_TABLE_TRAINING, null, contentValues);
                Log.d("TEST_LOG", "Упражнение добавлено");
                break;
        }
    }
}
