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
public class LoadWorkouts extends AsyncTask<Context, Void, ArrayList<HashMap<String,String>>> {
    Cursor cursorWorkouts;
    Cursor cursorExercises;
    ArrayList<HashMap<String,String>> arrayListWithWorkoutData = new ArrayList();

    //интерфейс для получения результатов асинхроного запроса
    public interface AsyncResponse {
        void processFinished(ArrayList<HashMap<String,String>> output);
    }

    @Override
    protected ArrayList<HashMap<String,String>> doInBackground(Context... contexts) {
        for (Context context : contexts) {
            String[] columnsWorkout = {DBHelper.WORKOUT_ID, DBHelper.WORKOUT_NAME};
            String[] columnsExercise = {DBHelper.EXERCISE_NAME};
            String exercisesNames;

            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            //запрашиваются названия тренировок
            cursorWorkouts = database.query(
                    DBHelper.TABLE_WORKOUTS,
                    columnsWorkout,
                    null, null, null, null, null);
            dbHelper.cursorReader(cursorWorkouts);

            cursorWorkouts.moveToFirst();
            //сортируются упражнения в конкретной тренировке, для последующей передачи через хэш-мап
            for (int i = 0; i < cursorWorkouts.getCount(); i++) {
                //для каждой строки из курсора(список тренировок) по id ищутся соответсвующие упражнения
                String workoutName = cursorWorkouts.getString(cursorWorkouts.getColumnIndex(DBHelper.WORKOUT_NAME));
                String workoutID = cursorWorkouts.getString(cursorWorkouts.getColumnIndex(DBHelper.WORKOUT_ID));
                String[] selectionArgs = {workoutID};
                cursorExercises = database.query(
                        DBHelper.TABLE_EXERCISES,
                        null,
                        DBHelper.WORKOUT_ID_IN_TABLE_FOR_EXERCISES + " = ?",
                        selectionArgs,
                        null, null, null);

                //создается перечень из названий упражнений
                exercisesNames = new String();
                exercisesNames = "";//чтобы срабатывала проверка и корректно проставлялись запятные между словами
                if (cursorExercises.moveToFirst()) {
                    do {
                        if (exercisesNames.equals("")) {
                            exercisesNames = cursorExercises.getString(cursorExercises.getColumnIndex(DBHelper.EXERCISE_NAME));
                        } else {
                            exercisesNames = exercisesNames + ", "
                                    + cursorExercises.getString(cursorExercises.getColumnIndex(DBHelper.EXERCISE_NAME));
                        }
                    } while (cursorExercises.moveToNext());
                }
                //название тренировки и список упражнений в ней, вставляются в хэшмап
                HashMap<String,String> hashMap = new HashMap();
                hashMap.put(ListFragmentForEditor.WORKOUT_NAME, workoutName);
                hashMap.put(ListFragmentForEditor.WORKOUT_ID, workoutID);
                hashMap.put(ListFragmentForEditor.EXERCISES, exercisesNames);
                arrayListWithWorkoutData.add(hashMap);

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
    protected void onPostExecute(ArrayList<HashMap<String,String>> arrayList) {
//        SystemClock.sleep(3000);//имитация задержки в 3 секунды
        arrayList = arrayListWithWorkoutData;
        asyncResponse.processFinished(arrayList);
        super.onPostExecute(arrayList);
    }
}
