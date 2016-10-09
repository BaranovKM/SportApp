package com.konstantin.sportapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Константин on 01.10.2016.
 */
public class EditorFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.editor_fragment,container,false);
    }
    //TODO 1)Реализовать создание тренировки с нуля(заполнение полей и вставку в бд)
    //TODO 2)Реализовать добавление отдельных упражнений
    //TODO 3) Реализовать редактирование уже имеющейся тренировки(можно использовать переключатель swich)

}
