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

    /*
     *Асинхронный процесс, загружающий в фоновом режиме данные по конкретной тренировке
     */

    Cursor cursorWorkouts;//курсор для загрузки данных по тренировке(название и id)
    Cursor cursorExercises;//курсор для загрузки упражнений(название, кол-во подходов и повторений)
    ArrayList<HashMap<String,String>> arrayListWithWorkoutData = new ArrayList();

    //интерфейс для передачи полученых результатов обратно в редактор
    public interface AsyncResponse {
        void processFinished(ArrayList<HashMap<String,String>> output);
    }

    @Override
    protected ArrayList<HashMap<String,String>> doInBackground(Context... contexts) {
        for (Context context : contexts) {
            //настройка столбцов из которых будет состоять запрос в бд
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
            // полученый курсор сортируется  по упражнениям в конкретной тренировке,
            // список которых затем передается в хэш-мап(которая в свою очередь, вернется в редактор тренировок)
            for (int i = 0; i < cursorWorkouts.getCount(); i++) {
                //для каждой строки из курсора с тренировками по id запрашиваются соответсвующие упражнения
                String workoutName = cursorWorkouts.getString(cursorWorkouts.getColumnIndex(DBHelper.WORKOUT_NAME));
                String workoutID = cursorWorkouts.getString(cursorWorkouts.getColumnIndex(DBHelper.WORKOUT_ID));
                String[] selectionArgs = {workoutID};
                cursorExercises = database.query(
                        DBHelper.TABLE_EXERCISES,
                        null,
                        DBHelper.WORKOUT_ID_IN_TABLE_FOR_EXERCISES + " = ?",
                        selectionArgs,
                        null, null, null);

                //создается список упражнений
                exercisesNames = new String();
                exercisesNames = "";
                if (cursorExercises.moveToFirst()) {
                    do {
                        if (exercisesNames.equals("")) {
                            //если строка с упражнениями пустая, из курсора добавляется название упражнения
                            exercisesNames = cursorExercises.getString(cursorExercises.getColumnIndex(DBHelper.EXERCISE_NAME));
                        } else {
                            //иначе добавляется запятая с пробелом и лишь потом дописывается название
                            exercisesNames = exercisesNames + ", "
                                    + cursorExercises.getString(cursorExercises.getColumnIndex(DBHelper.EXERCISE_NAME));
                        }
                    } while (cursorExercises.moveToNext());
                }
                //название тренировки и список упражнений, вставляются в хэшмап
                //который потом отправится назад в редактор
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
//        SystemClock.sleep(3000);//имитация задержки в 3 секунды для проверки что UI не зависает
        arrayList = arrayListWithWorkoutData;
        asyncResponse.processFinished(arrayList);
        super.onPostExecute(arrayList);
    }
}
