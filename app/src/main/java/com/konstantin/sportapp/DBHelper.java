package com.konstantin.sportapp;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Константин on 27.08.2016.
 */
public class DBHelper extends SQLiteOpenHelper implements BaseColumns {
    /*
    Вспомогательный класс для работы с SQLite
     */
    //TODO сделать запрос к базе асинхронным (aSyncTask)
    public static final String DATABASE_NAME = "db_for_sport_app"; //имя базы данных
    public static final int DATABASE_VERSION = 1; //версия базы данных

    public static final String DATABASE_TABLE_TRAINING = "training"; //таблица для силовых тренировок(упражнения, подходы/повторения, статистика и пр.)
    public static final String GYMNASTIC_NAME_COLUMN = "gymnastic_name"; // названия упражнений
    public static final String ROWS_PER_TRAINING_COLUMN = "rows_per_training"; // количество кругов/подходов за одну тренировку
    public static final String ROWS_AMOUNT_COLUMN = "rows_amount"; //общее количество кругов/подходов
    public static final String ITERATIONS_PER_TRAINING_COLUMN = "iterations_per_training"; // подсчет повторение за тренировку
    public static final String ITERATIONS_AMOUNT_COLUMN = "iterations_amount"; //общее количество повторение

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //в бд создаются таблицы для активити
//TODO переделать создание таблицы : можно обойтись без BaseColumns._ID
        //на будущее : лучше сперва написать сам скрипт для создание таблицы, и лишь потом вставлять в негоконстанты
        sqLiteDatabase.execSQL("CREATE TABLE "
                + DATABASE_TABLE_TRAINING
                + " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GYMNASTIC_NAME_COLUMN + " TEXT NOT NULL, "
                + ROWS_PER_TRAINING_COLUMN + " INTEGER, "
                + ROWS_AMOUNT_COLUMN + " INTEGER, "
                + ITERATIONS_PER_TRAINING_COLUMN + " INTEGER, "
                + ITERATIONS_AMOUNT_COLUMN + " INTEGER);");
        //отслеживание создание таблицы
        Log.d("Log", "---TABLE CREATED---");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /*
    Методы для работы с базой
     */


}
