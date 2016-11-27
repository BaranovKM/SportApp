package com.konstantin.sportapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Константин on 19.10.2016.
 */
public class WorkoutFragment extends Fragment {
    /*
     *Фрагмент для отображения выбранной тренировки и текущего прогресса в ней
     */
//TODO Сделать кнопки для старта и окончания тренировки
//TODO добавить быстрое управление медиа-плэйером(смена треков и т.д.)
//TODO После завершения тренировки отображать статистику(подходы/повторения за тренировку и общее кол.во и за все время)
//TODO добавить прогресс-бар показывающий выполнение тренировки

    public static final String FRAGMENT_TAG = "workout"; //константа для идентификации фрагмента

    public static Boolean workoutIsStarted = false;//статус тренировки : запущена или нет

    public int workoutProgress = 0;//индикатор прогресса начатой тренировки
    public int progressBarSize = 0;
    public HashMap<String,String> workoutResultList = new HashMap<>();

    public static WorkoutFragment newInstance() {
        WorkoutFragment fragment = new WorkoutFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_with_explstview, container, false);

        //создание таймера
        Chronometer chronometer = (Chronometer) view.findViewById(R.id.chronometer);

        //параметры для создания выпадающего списка
        List<String> listDataHeader = new ArrayList<>();
        final HashMap<String, List<String>> listDataChild = new HashMap<>();
        List<String> rows;

        //из базы  по id извлекаются упражнения
        String[] exercises = {String.valueOf(getArguments().getInt("workoutID"))};
        Cursor cursor = new DBHelper(getContext()).getWritableDatabase()
                .query(DBHelper.TABLE_EXERCISES, null,
                        DBHelper.WORKOUT_ID_IN_TABLE_FOR_EXERCISES + " = ?",
                        exercises,
                        null, null, null);

        //разбор курсора и сортировка данных, которые затем вставляются в адаптер для expandable listview
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            //из курсора берется имя упражнения
            String exerciseName = cursor.getString(cursor.getColumnIndex(DBHelper.EXERCISE_NAME));
            listDataHeader.add(exerciseName);
            //затем количество подходов
            int rowsQuantity = cursor.getInt(cursor.getColumnIndex(DBHelper.ROWS_IN_WORKOUT));
            //и количество повторений в каждом подходе
//            String iterations = Integer.toString(
//                    cursor.getInt(cursor.getColumnIndex(DBHelper.ITERATIONS_IN_ROW)));
            int iterations = cursor.getInt(cursor.getColumnIndex(DBHelper.ITERATIONS_IN_ROW));

            rows = new ArrayList<>();
            //для каждого упражнения создается несколько строк с количеством повторений
            //которые будут вставляться в список по количеству необходимых подходов
            for (int j = 0; j < rowsQuantity; j++) {
                rows.add("+ " + iterations);
            }
            listDataChild.put(exerciseName, rows);
            progressBarSize = progressBarSize + rowsQuantity;//приращение статуса для прогрессбара
            workoutResultList.put(exerciseName,Integer.toString(rowsQuantity*iterations));

            //если обработана последняя строчка курсора, то цикл прерывается
            if (cursor.isAfterLast()) {
                break;
            }
            //иначе курсор сдвигается на следующую строку и обрабатывается заново
            cursor.moveToNext();
        }
        cursor.close();

        //прогресс-бар
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setMax(progressBarSize);

        //вставка обработанных данныых из курсора в адаптер, который создат отображаемый список упражнений
        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView2);
        AdapterForELV adapterForELV = new AdapterForELV(getContext(), listDataHeader, listDataChild);
        expandableListView.setAdapter(adapterForELV);
        //обработка клика по строке с подходом
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int group, int child, long id) {
                Log.d("TEST_LISTVIEW", "Нажат пункт : " + child + " в группе :" + group + " row id =" + id);
                //удаление пункта из списка
                ((AdapterForELV) expandableListView.getExpandableListAdapter()).removeChild(group, child);
                ((AdapterForELV) expandableListView.getExpandableListAdapter()).notifyDataSetChanged();

                //отражение прогреса тренировки на индикаторе
                View mView = (View) expandableListView.getParent();
                ProgressBar pbar = (ProgressBar) mView.findViewById(R.id.progressBar);
                workoutProgress++;
                pbar.setProgress(workoutProgress);
                Log.d("TEST_PROGRESSBAR", progressBarSize + " : " + workoutProgress);
                if (workoutProgress == progressBarSize) {
//                    String workoutTime = ((View) expandableListView.getParent()).findViewById(R.id.);
                    //Вычисление времени тренировки
                long elapsedTime = SystemClock.elapsedRealtime() - ((Chronometer)getFragmentManager().findFragmentById(R.id.nav_drawer_content_frame)
                            .getView().findViewById(R.id.chronometer)).getBase();
                    int minute = (int) elapsedTime/60000;
                    int second = (int) (elapsedTime - minute*60000)/1000;
                    String workoutTime = minute + " : "+ second;
//                    String workoutTime = Long.toString(elapsedTime/1000)+" секунд";
//                    String workout = "Отжимания";
//                    int iterations = 100;
//                    workoutStop(workout,iterations);
                    workoutStop(workoutResultList, workoutTime);
                }
                return true;
            }
        });

        return view;
    }

    private void workoutStop(HashMap result, String time) {
        String workoutResult = new String();
//итерация хэшмап в строку, и форматирование результатов тренировки
        Iterator iterator = result.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry pair = (Map.Entry) iterator.next();
            workoutResult = workoutResult + pair.getKey()+ " : " +pair.getValue()+"\n";
            iterator.remove();
        }
        workoutResult = workoutResult +"\n\n"+"Затраченое время : "+time;

//вывод результатов в диалог
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Диалог с результатами тренировки");
//        builder.setMessage(workout+" : "+iterations);
        builder.setMessage(workoutResult);
        builder.setPositiveButton("Окай", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("TEST_DIALOG", "Workout stop OK ");
            }
        });
        builder.show();
    }

    private void adaptFloatingButtom() {

    }
}
