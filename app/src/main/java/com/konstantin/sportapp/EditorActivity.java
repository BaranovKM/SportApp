package com.konstantin.sportapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EditorActivity extends AppCompatActivity {
/*
 *Редактор тренировок
 */
    //TODO Заполнение списка тренировок
    //TODO 1)Реализовать создание новой таблицы по клику кнопки
    //TODO 2)Реализовать заполнение таблицы из редактора

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
    //Реализовать динамическую замену 2 фрагментов : сперва отображается фрагмент со списком тренировок
    //а при создании новой или редактировании существующей, фрагмент заменяется на редактор
        ListFragmentForEditor listWorkouts = new ListFragmentForEditor();
        getFragmentManager().beginTransaction()
        .add(R.id.container_for_fragments,listWorkouts)
        .commit();
    }
        //обрабатывается клик по кнопке для замены фрагмента
    public void replaceFragment(View view) {
        EditorFragment editorFragment = new EditorFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container_for_fragments,editorFragment).commit();
    }

}
