package com.konstantin.sportapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Константин on 12.11.2016.
 */

public class WorkoutListDialog extends DialogFragment {
    /*
     * Создается список существующих тренировок.
     * Данные по выбраной тренировке(имя, id и т.д.) возвращаются назад во фрагмент
     */

    SimpleCursorAdapter adapter;
    Cursor cursor;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //создается окно диалога, со списком имеющихся тренировок
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Workout")
                .setAdapter(createAdapter(), createClickListener());
        return builder.create();
    }

    private DialogInterface.OnClickListener createClickListener() {
        //обрабатывается выбор конкретной тренировке из списка(по номеру позиции в listview)
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int workoutID;
                cursor.moveToPosition(i);//курсор сдвигается на ту же строку, что и выбраный пункт списка
                //из курсора достается id тренировки
                workoutID = cursor.getInt(cursor.getColumnIndex(DBHelper.WORKOUT_ID));
                String workoutName = cursor.getString(cursor.getColumnIndex(DBHelper.WORKOUT_NAME));
                Log.d("TEST_DIALOG", "Selected item :" + i);
                Log.d("TEST_DIALOG", "Сдесь будет меняться фрагмент с тренировкой : "
                        + workoutName + " id = " + workoutID);
                cursor.close();

                //id тренировки передается в аргументы фрагмента где на его основе формируется view
                //для начала новой тренировки
                Bundle arguments = new Bundle();
                arguments.putInt("workoutID", workoutID);
                WorkoutFragment workoutFragment = new WorkoutFragment();
                workoutFragment.setArguments(arguments);
                getFragmentManager().beginTransaction()
                        .replace(R.id.nav_drawer_content_frame, workoutFragment).commit();

            }
        };
        return onClickListener;
    }

    private SimpleCursorAdapter createAdapter() {

        //массивы со значениями полей для адаптера и запроса к базе
        String[] columnsWorkout = {DBHelper.WORKOUT_ID, DBHelper.WORKOUT_NAME};
        String[] from = {DBHelper.WORKOUT_NAME, DBHelper.WORKOUT_ID};
        int[] to = {R.id.textViewWorkoutsListItem, R.id.textViewForWorkoutID};

//        DBHelper dbHelper = new DBHelper(getActivity());
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
//запрос в базу на имеющиеся тренировки
        cursor = new DBHelper(getContext()).getWritableDatabase().query(
                DBHelper.TABLE_WORKOUTS,
                columnsWorkout,
                null, null, null, null, null);

        //создает и заполняет адаптер со списком тренировок
        adapter = new SimpleCursorAdapter(
                getContext(), R.layout.workouts_list_item, cursor, from, to
        );
        return adapter;
    }

}
