package com.konstantin.sportapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    //TODO найти/сделать иконки для кнопок(гантель для кнопки силовых, сердце для кнопки кардио и т.д.)
    //TODO попробовать эмуляцию через genymotion
    //TODO сделать перелистывание между разделами методом ViewPager

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

                break;
        }
    }
}
