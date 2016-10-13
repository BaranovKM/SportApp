package com.konstantin.sportapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Константин on 06.10.2016.
 */
public class LoadWorkouts extends AsyncTask<Context, Void, HashMap<String, String>> {
        Cursor cursorWorkouts;
        Cursor cursorExercises;
        HashMap<String,String> hashMap = new HashMap<>();

    //интерфейс для получения результатов асинхроного запроса
    public interface AsyncResponse{
        void processFinished(HashMap<String,String> output);
    }

    @Override
    protected HashMap<String, String> doInBackground(Context... contexts) {
        for (Context context : contexts) {
//            String sqlQuery = "select W." + DBHelper.WORKOUT_NAME + ", E." + DBHelper.EXERCISE_NAME +
//                    " from " + DBHelper.TABLE_WORKOUTS + " AS W" +
//                    " INNER JOIN " + DBHelper.TABLE_EXERCISES + " AS E" +
//                    " ON W._id = E.id_workout";

            String[] columnsWorkout = {DBHelper.WORKOUT_ID, DBHelper.WORKOUT_NAME};
            String[] columnsExercise = {DBHelper.EXERCISE_NAME};
            String exercisesNames = new String();

            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            //запрашиваются названия тренировок
            cursorWorkouts = database.query(
                    DBHelper.TABLE_WORKOUTS,
                    columnsWorkout,
                    null, null, null, null, null);
            Log.d("TEST_SQL", "--- Асинтаск : запрос к таблице с именами тренировок---");
            dbHelper.cursorReader(cursorWorkouts);

            cursorWorkouts.moveToFirst();
            //сортируются упражнения в конкретной тренировке, для последующей передачи через хэш-мап
            for (int i = 0; i < cursorWorkouts.getCount(); i++) {
                //для каждой строки из курсора по id ищутся соответсвующие упражнения
                String workoutName = cursorWorkouts.getString(cursorWorkouts.getColumnIndex(DBHelper.WORKOUT_NAME));
                Log.d("TEST_SQL", "Workout : " + workoutName);
                String[] selectionArgs = {cursorWorkouts.getString(cursorWorkouts.getColumnIndex(DBHelper.WORKOUT_ID))};
                cursorExercises = database.query(
                        DBHelper.TABLE_EXERCISES,
                        null,
                        DBHelper.WORKOUT_ID_IN_TABLE_FOR_EXERCISES + " = ?",
                        selectionArgs,
                        null, null, null);

                //создается перечень из названий упражнений
                exercisesNames = null;//обнуляется перед каждой следующей тренировкой(чтобы строки не накладывались)
                if (cursorExercises.moveToFirst()) {
                    do {
                        if (exercisesNames != null) {
                            exercisesNames = exercisesNames + " , "
                                    + cursorExercises.getString(cursorExercises.getColumnIndex(DBHelper.EXERCISE_NAME));
                        } else {
                            exercisesNames = cursorExercises.getString(cursorExercises.getColumnIndex(DBHelper.EXERCISE_NAME));
                        }
                    } while (cursorExercises.moveToNext());
                }
                //название тренировки и список упражнений в ней, вставляются в хэшмап
                hashMap.put(workoutName,exercisesNames);

                Log.d("TEST_SQL", "Exercise : " + exercisesNames);
                Log.d("TEST_SQL", "--------------------------");
                if (cursorWorkouts.isAfterLast()) {
                    cursorWorkouts.close();
                    database.close();
                    break;
                }
                cursorWorkouts.moveToNext();
            }
        }
        return null;
    }

    public AsyncResponse asyncResponse = null;

    @Override
    protected void onPostExecute(HashMap<String, String> stringStringHashMap) {
//        SystemClock.sleep(3000);//имитация задержки в 3 секунды
        stringStringHashMap = hashMap;
        asyncResponse.processFinished(stringStringHashMap);
        super.onPostExecute(stringStringHashMap);
    }
}
