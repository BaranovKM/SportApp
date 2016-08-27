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
    public static final String DATABASE_NAME = "db_for_sport_app"; //имя базы данных
    public static final int DATABASE_VERSION = 1; //версия базы данных

    public static final String DATABASE_TABLE_TRAIN = "train"; //таблица для силовых тренировок(упражнения, подходы/повторения, статистика и пр.)

    public static final String GYMNASTIC_NAME_COLUMN = "gymnastic_name"; //столбец для названия упражнения
    public static final String ROWS_COUNT_COLUMN = "rows"; //столбец для количества кругов/подходов
    public static final String ITERATIONS_COUNT_COLUMN = "iterations"; //столбец для подсчета повторение

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

        //на будущее : лучше сперва написать сам скрипт для создание таблицы, и лишь потом вставлять в негоконстанты
        sqLiteDatabase.execSQL("CREATE TABLE "
        +DATABASE_TABLE_TRAIN
        +" ("+BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
        +GYMNASTIC_NAME_COLUMN+" TEXT NOT NULL, "
        +ROWS_COUNT_COLUMN+" INTEGER, "
        +ITERATIONS_COUNT_COLUMN+" INTEGER);");
//отслеживание создание таблицы
        Log.d("Log","---TABLE CREATED---");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
