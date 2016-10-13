package com.konstantin.sportapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Константин on 01.10.2016.
 */
public class EditorFragment extends Fragment implements View.OnClickListener, EditExerciseDialog.ExerciseDataListener {

    //константы для настройки
    public static final int ADD = 1;
    public static final int EDIT = 2;
    public static final int REMOVE = 3;

    public static final String ACTION_TYPE = "actionType";
    public static final String IF_NAME_IS_EMPTY = "Не задано название тренировки";
    public static final String EXERCISE_NAME = "name";
    public static final String EXERCISE_ITERATIONS = "iterations";
    public static final String EXERCISE_ROWS = "rows";

    private Button addWorkout;
    private Button addExercise;
    private EditText workoutName;

    FragmentTransaction fragmentTransaction;
    EditExerciseDialog editExerciseDialog;
    Bundle arguments;

    //переменные для адаптера списка упражнений
    ListView listView;
    SimpleAdapter simpleAdapter;
    ArrayList<Map<String, Object>> data;
    Map<String, Object> map;
    String[] from = {EXERCISE_NAME, EXERCISE_ROWS, EXERCISE_ITERATIONS};
    int[] to = {R.id.exerciseText1, R.id.exerciseText2, R.id.exerciseText3};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.editor_fragment, container, false);
        addWorkout = (Button) view.findViewById(R.id.addWorkout);
        addExercise = (Button) view.findViewById(R.id.addExercise);
        workoutName = (EditText) view.findViewById(R.id.editWorkoutName);
        addWorkout.setOnClickListener(this);
        addExercise.setOnClickListener(this);

        //Настройка адаптера списка
        data = new ArrayList<>();
        simpleAdapter = new SimpleAdapter(getContext(), data, R.layout.exercise_layout, from, to);
        listView = (ListView) view.findViewById(R.id.listViewForExercisesInWorkoutEditor);
        listView.setAdapter(simpleAdapter);

        //цеплятся обработчик вызова контекстного меню для элементов списка
        registerForContextMenu(listView);
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //элементы контекстного меню
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, REMOVE, 0, "Удалить?");
        menu.add(0, EDIT, 0, "Или редактировать?");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //действия после выбора элемента в меню
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case REMOVE:
                //поиск и удаление упражнения из списка по id элемента
                //TODO сделать алерт с подтверждением удаления
                data.remove(menuInfo.position);
                simpleAdapter.notifyDataSetChanged();
                return true;
            case EDIT:
                //редактирование элемента
                fragmentTransaction = getFragmentManager().beginTransaction();
                editExerciseDialog = EditExerciseDialog.newInstance();
                //передача параметров выбранного упражнения в экземпляр диалога
                arguments = new Bundle();
                arguments.putInt(ACTION_TYPE, EDIT);
                arguments.putString(EXERCISE_NAME, data.get(menuInfo.position).get(EXERCISE_NAME).toString());
                arguments.putInt(EXERCISE_ITERATIONS, Integer.parseInt(
                        data.get(menuInfo.position).get(EXERCISE_ITERATIONS).toString()));
                arguments.putInt(EXERCISE_ROWS, Integer.parseInt(
                        data.get(menuInfo.position).get(EXERCISE_ROWS).toString()));
                editExerciseDialog.setArguments(arguments);

                data.remove(menuInfo.position);//удаление редактируемого пункта из списка(т.к. он пересоздается)
                editExerciseDialog.setTargetFragment(this, 300);
                editExerciseDialog.show(fragmentTransaction, "dialog");

                return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        //обработка клика по кнопке
        switch (view.getId()) {
            case R.id.addWorkout:
                //добавление тренировки в бд
                Log.d("TEST_SQL", "Кнопка сохранить тренировку нажимается");
                if (workoutName.getText().toString().equals("")){
                    //показывается напоминалка, если поле с названием не заполнено
                    Toast toast = Toast.makeText(getContext(), IF_NAME_IS_EMPTY, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else {
                DBHelper dbHelper = new DBHelper(getActivity());
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                dbHelper.insertWorkout(getActivity(), workoutName.getText().toString(), data);
            }
                break;
            case R.id.addExercise:
                //отображение диалога для создания/редактирования упражнения
                fragmentTransaction = getFragmentManager().beginTransaction();
                editExerciseDialog = EditExerciseDialog.newInstance();
                arguments = new Bundle();
                arguments.putInt(ACTION_TYPE, ADD);
                editExerciseDialog.setArguments(arguments);
                editExerciseDialog.setTargetFragment(this, 300);
                editExerciseDialog.show(fragmentTransaction, "dialog");
                break;
        }


    }

    @Override
    public void onEnterExerciseData(String name, int iterations, int rows) {
        //прием введеных данных из диалога
        map = new HashMap<>();
        map.put(EXERCISE_NAME, name);
        map.put(EXERCISE_ITERATIONS, iterations);
        map.put(EXERCISE_ROWS, rows);
        data.add(map);
        simpleAdapter.notifyDataSetChanged();
    }
    //TODO 1)Реализовать создание тренировки с нуля(заполнение полей и вставку в бд)
    //TODO 3) Реализовать редактирование уже имеющейся тренировки(можно использовать переключатель swich)

}
