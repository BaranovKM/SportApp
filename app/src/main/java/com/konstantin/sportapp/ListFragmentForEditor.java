package com.konstantin.sportapp;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Константин on 15.09.2016.
 */
/*
 *Вспомогательный класс, отображает список тренировок в редакторе
 */
public class ListFragmentForEditor extends Fragment implements LoadWorkouts.AsyncResponse {

    public static final String WORKOUT_NAME = "workoutname";
    public static final String WORKOUT_ID = "workoutid";
    public static final String EXERCISES = "exercisesnames";
    public static final int REMOVE = 1;
    public static final int EDIT = 2;

    //настройки адаптера, который будет заполнять ListView
    SimpleAdapter adapter;
    ArrayList<Map<String, String>> data = new ArrayList<>();
    String[] from = {WORKOUT_NAME, EXERCISES};
    int[] to = {R.id.workoutName, R.id.exercises};

    ListView listView;
    Button addWorkout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_of_workouts_layout, container, false);
        listView  = (ListView) view.findViewById(R.id.listViewForWorkouts);
        addWorkout = (Button) view.findViewById(R.id.createNewWorkout);
        adapter = new SimpleAdapter(getContext(), data, R.layout.workouts_in_list, from, to);
        listView.setAdapter(adapter);

        //создается асинхроный процесс для загрузки тренировок из бд вне основного потока приложения
        LoadWorkouts asyncTask = new LoadWorkouts();
        asyncTask.asyncResponse = this;//задается слушатель интерфейса для получения данных из асинтаска
        asyncTask.execute(getActivity());

        registerForContextMenu(listView);//регистрация контекстного меню
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //создание элементов контекстного меню
        //TODO погуглить как сделать вызов меню коротким кликом
        menu.add(0, REMOVE, 0, "Удалить");
        menu.add(0, EDIT, 0, "Редактировать");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //обработка выбранного пункта в контекстном меню
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case REMOVE:
                //TODO добавить подтверждение удаления
                //если тренировка удаляется, то создается запрос в бд на удаление
                //а затем она удаляется из списка в list view
                DBHelper dbHelper = new DBHelper(getActivity());
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                dbHelper.removeWorkout(getActivity(), Integer.parseInt(
                        data.get(menuInfo.position).get(WORKOUT_ID)));
                data.remove(menuInfo.position);
                adapter.notifyDataSetChanged();
                return true;
            case EDIT:
                //если тренировка редактируется, то создается фрагмент с редактором
                //в который передается id тренировки(берется из аргументов в элементе списка)
                int workoutId =  Integer.parseInt(data.get(menuInfo.position).get(WORKOUT_ID));
                String workoutName = data.get(menuInfo.position).get(WORKOUT_NAME);
                Bundle arguments = new Bundle();
                //В аргументы вызываемого фрагмента передаются название и id тренировки
                arguments.putInt(WORKOUT_ID,workoutId);
                arguments.putString(WORKOUT_NAME,workoutName);
                EditorFragment editorFragment = EditorFragment.newInstance();
                editorFragment.setArguments(arguments);
                getActivity().getFragmentManager().beginTransaction().replace(
                        R.id.container_for_fragments,editorFragment).commit();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void processFinished(ArrayList<HashMap<String, String>> output) {
        //когда асинхронный процесс отработает и вернет данные по упражнению
        //они подставятся в ArrayList на основе которого адаптер создает список имеющихся тренировок
        Map<String, String> map;
        for (HashMap<String, String> hashMap : output) {
            map = new HashMap<>();
            map.put(WORKOUT_NAME, hashMap.get(WORKOUT_NAME));
            map.put(EXERCISES, hashMap.get(EXERCISES));
            map.put(WORKOUT_ID, hashMap.get(WORKOUT_ID));
            data.add(map);
            adapter.notifyDataSetChanged();
        }

    }
}
