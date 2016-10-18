package com.konstantin.sportapp;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Константин on 09.10.2016.
 */
public class EditExerciseDialog extends DialogFragment implements View.OnClickListener {

    public static final String IF_NAME_IS_EMPTY = "Поле с названием надо бы заполнить";
    String name; // название упражнения
    int iterations;//повторения
    int rows;//подходы

    //элементы интерфейса
    EditText editText; //название упражнения
    NumberPicker numberPickerIteration;//счетчик кол-ва повторений
    NumberPicker numberPickerRows;//счетчик кол-ва подходов

    //интерфейс для получения введеных параметров упражнения
    public interface ExerciseDataListener {
        void onEnterExerciseData(String name, int iterations, int rows);
    }

    //создание нового экземпляра диалога
    static EditExerciseDialog newInstance() {
        EditExerciseDialog dialog = new EditExerciseDialog();
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercise_dialog, container, false);

        //первичная настройка элементов интерфейса для корректного отображения
        editText = (EditText) view.findViewById(R.id.editExerciseName);
        numberPickerIteration = (NumberPicker) view.findViewById(R.id.numberPicker_iterations);
        numberPickerRows = (NumberPicker) view.findViewById(R.id.numberPicker_rows);

        //два текстовых поля. Инициализируются просто для того, чтоб отображались на вью
        TextView textView1 = (TextView) view.findViewById(R.id.dialogTextview1);
        TextView textView2 = (TextView) view.findViewById(R.id.dialogTextview2);

        Button buttonOK = (Button) view.findViewById(R.id.buttonDialogOK);
        buttonOK.setOnClickListener(this);
        numberPickerIteration.setMinValue(1);
        numberPickerIteration.setMaxValue(100);
        numberPickerRows.setMinValue(1);
        numberPickerRows.setMaxValue(10);

        //выбор действия в зависимости от переданного в диалог параметра
        switch (getArguments().getInt(EditorFragment.ACTION_TYPE)) {
            //создается новое упражнение
            case EditorFragment.ADD:
                // если передается 1, то значения полей остаются дефолтными
                break;
            //редактируется уже существующее
            case EditorFragment.EDIT:
                // если передается 2, то поля вью заполняются значениями из аргументов диалога
                editText.setText(getArguments().getString(EditorFragment.EXERCISE_NAME));
                numberPickerIteration.setValue(getArguments().getInt(EditorFragment.EXERCISE_ITERATIONS));
                numberPickerRows.setValue(getArguments().getInt(EditorFragment.EXERCISE_ROWS));
                break;
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        if (editText.getText().toString().equals("")) {
            //показывается напоминалка, если поле с названием не заполнено
            Toast toast = Toast.makeText(getContext(), IF_NAME_IS_EMPTY, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            name = editText.getText().toString();
            iterations = numberPickerIteration.getValue();
            rows = numberPickerRows.getValue();

            //создается слушатель через который введеные данные возвращаются во фрагмент редактора
            ExerciseDataListener exerciseDataListener = (ExerciseDataListener) getTargetFragment();
            exerciseDataListener.onEnterExerciseData(name, iterations, rows);
            dismiss();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
