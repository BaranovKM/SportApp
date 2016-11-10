package com.konstantin.sportapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EditorActivity extends AppCompatActivity {
/*
 *Окно редактора. Отображает либо фрагмент со списком тренировок,
 * либо фрагменты в которых редактируются упражнения и тренировки
 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
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
