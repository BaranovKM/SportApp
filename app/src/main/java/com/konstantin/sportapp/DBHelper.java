package com.konstantin.sportapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Константин on 27.08.2016.
 */
public class DBHelper extends SQLiteOpenHelper implements BaseColumns {
    /*
    Вспомогательный класс для работы с базой данных SQLite
     */
    //TODO реорганизовать таблицу : переименовать столбцы для числа повторений(сейчас число повторений в подходе записываться в столбец для общего кол.-ва повторений за тренировку)
    public static final String DATABASE_NAME = "db_for_sport_app"; //имя базы данных
    public static final int DATABASE_VERSION = 2; //версия базы данных

    public static final String TABLE_WORKOUTS = "workouts"; //таблица для силовых тренировок
    public static final String WORKOUT_ID = "_id"; // идентификатор тренировки
    public static final String WORKOUT_NAME = "name"; // названиe тренировки
    public static final String WORKOUT_AMOUNT = "workouts_amount"; //общее количество тренировок

    public static final String TABLE_EXERCISES = "exercises"; //таблица для упражнений
    public static final String EXERCISE_ID = "_id"; // идентификатор упражнения
    public static final String WORKOUT_ID_IN_TABLE_FOR_EXERCISES = "id_workout"; // идентификатор тренировки для связи таблицы
    public static final String EXERCISE_NAME = "name"; // названия упражнений
    public static final String ROWS_IN_WORKOUT = "rows_per_training"; // количество кругов/подходов за одну тренировку
    public static final String ROWS_AMOUNT = "rows_amount"; //общее количество кругов/подходов
    public static final String ITERATIONS_IN_ROW = "iterations_per_training"; // подсчет повторение за тренировку
    public static final String ITERATIONS_AMOUNT = "iterations_amount"; //общее количество повторение

    ContentValues contentValues;
    SQLiteDatabase database;

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
        //таблица для тренировок
        sqLiteDatabase.execSQL("CREATE TABLE "
                + TABLE_WORKOUTS
                + " (" + WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WORKOUT_NAME + " TEXT NOT NULL, "
                + WORKOUT_AMOUNT + " INTEGER);");
        //отслеживается создание таблицы(в консоль выводится лог)
        Log.d("TEST_SQL", "---Таблица для тренировок (" + TABLE_WORKOUTS + ") создана---");

        //таблица для упражнений
        sqLiteDatabase.execSQL("CREATE TABLE "
                + TABLE_EXERCISES
                + " (" + EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EXERCISE_NAME + " TEXT NOT NULL, "
                + WORKOUT_ID_IN_TABLE_FOR_EXERCISES + " INTEGER, "
                + ROWS_IN_WORKOUT + " INTEGER, "
                + ROWS_AMOUNT + " INTEGER, "
                + ITERATIONS_IN_ROW + " INTEGER, "
                + ITERATIONS_AMOUNT + " INTEGER);");
        Log.d("TEST_SQL", "---Таблица для упражнений " + TABLE_EXERCISES + " создана---");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    //разбор курсора и вывод содержимого в лог
    public void cursorReader(Cursor cursor) {
        String cursorData = new String();
        if (cursor.moveToFirst()) {
            do {
                for (String str : cursor.getColumnNames()) {
                    cursorData = cursorData + " " + cursor.getString(cursor.getColumnIndex(str)) + " ";
                }
            } while (cursor.moveToNext());
        }
        Log.d("TEST_SQL", "CURSOR : " + cursorData);

    }

    //создание нескольких дефолтных тренировок, для быстрого заполнения пустой дб
    public void prepareTables(Context context) {
        contentValues = new ContentValues();
        database = new DBHelper(context).getWritableDatabase();
        //заполнение таблицы с тренировками
        String[] workoutsName = {"Грудь и спина", "Кардио", "Руки", "Ноги"};
        for (int i = 0; i < workoutsName.length; i++) {
            contentValues.clear();
            contentValues.put(WORKOUT_NAME, workoutsName[i]);
            contentValues.put(WORKOUT_AMOUNT, 0);
            database.insert(TABLE_WORKOUTS, null, contentValues);
        }
        Log.d("TEST_SQL", "---TABLE " + TABLE_WORKOUTS + "PREPARED AND POPULATED---");
        //заполнение таблицы с упражнениями(связывается с тренировкой по id)
        String[] exercisesNames = {"Подтягивания", "Отжимания", "Бицепс", "Трицепс", "Пресс", "Бег"};
        int[] workoutID = {1, 1, 2, 2, 3, 3};
        int[] exerciseRows = {4, 4, 4, 4, 2, 1};
        int[] exerciseIterations = {5, 10, 4, 8, 6, 30};
        for (int i = 0; i < exercisesNames.length; i++) {
            contentValues.clear();
            contentValues.put(EXERCISE_NAME, exercisesNames[i]);
            contentValues.put(WORKOUT_ID_IN_TABLE_FOR_EXERCISES, workoutID[i]);
            contentValues.put(ROWS_IN_WORKOUT, exerciseRows[i]);
            contentValues.put(ITERATIONS_IN_ROW, exerciseIterations[i]);
            database.insert(TABLE_EXERCISES, null, contentValues);
        }
        Log.d("TEST_SQL", "---TABLE " + TABLE_EXERCISES + "PREPARED AND POPULATED---");
    }

    //вставка тренировки в бд
    public void insertWorkout(Context context, String workoutName, ArrayList<Map<String, Object>> exercisesList) {
        //TODO переделать так, чтоб вставлялось одной транзакцией(либо вставляются все записи,либо ни одной)
        contentValues = new ContentValues();
        long idInsertedWorkout;// сюда записывается id вставленной тренировки, для последующей привязки упражнений

        //сначала вставляется тренировка
        contentValues.put(WORKOUT_NAME, workoutName);
        database = new DBHelper(context).getWritableDatabase();
        idInsertedWorkout = database.insert(TABLE_WORKOUTS, null, contentValues);
        Log.d("TEST_SQL", "Добавлена тренировка : " + workoutName);
        //затем заполняется
        for (int i = 0; i < exercisesList.size(); i++) {
            contentValues.clear();
            contentValues.put(WORKOUT_ID_IN_TABLE_FOR_EXERCISES, idInsertedWorkout);
            contentValues.put(EXERCISE_NAME, exercisesList.get(i).get(EditorFragment.EXERCISE_NAME).toString());
            contentValues.put(ITERATIONS_IN_ROW, Integer.parseInt(exercisesList.get(i).get(EditorFragment.EXERCISE_ITERATIONS).toString()));
            contentValues.put(ROWS_IN_WORKOUT, Integer.parseInt(exercisesList.get(i).get(EditorFragment.EXERCISE_ROWS).toString()));
            database.insert(TABLE_EXERCISES, null, contentValues);
            Log.d("TEST_SQL", "Добавлено упражнения : " + exercisesList.get(i).get(EditorFragment.EXERCISE_NAME).toString());
        }
        database.close();
        Log.d("TEST_SQL", "---Все круто, данные вставились---");
    }

    //удаление тренировки
    public void removeWorkout(Context context, int workoutId) {
        database = new DBHelper(context).getWritableDatabase();
        int delCount = database.delete(TABLE_WORKOUTS, WORKOUT_ID + " = " + workoutId, null);
        Log.d("TEST_SQL", "Из таблицы тренировок удалено строк : " + delCount);
        delCount = database.delete(TABLE_EXERCISES, WORKOUT_ID_IN_TABLE_FOR_EXERCISES + " = " + workoutId, null);
        Log.d("TEST_SQL", "Из таблицы упражнений удалено строк : " + delCount);
    }

    public void removeCurrentExercise(Context context, int exerciseId) {
        database = new DBHelper(context).getWritableDatabase();
        database.delete(TABLE_EXERCISES, WORKOUT_ID + " = " + exerciseId, null);
        Log.d("TEST_UI", " Упражнение c id " + exerciseId + " было удалено из бд");
        database.close();
    }
}
