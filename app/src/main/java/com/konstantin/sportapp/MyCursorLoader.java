package com.konstantin.sportapp;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.icu.util.TimeUnit;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Константин on 05.09.2016.
 */
public class MyCursorLoader extends CursorLoader{
    /*
    Вспомогательный класс, для асинхроной работы с базой вне основного потока(чтобы UI не зависал
    при выполнении долгих/крупных запросов в бд)
     */
//TODO Доработать лоадер, чтобы названия упражнений во фрагментах корректно отображались даже при 2-3 секундной задержке
    public MyCursorLoader(Context context) {
        super(context);
    }

    public MyCursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public Cursor loadInBackground() {
        Log.d("log", "LOADER START");
        //имитация задержки при обращении к бд
//        try {
//            java.util.concurrent.TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Cursor cursor = new DBHelper(super.getContext()).getWritableDatabase().query(DBHelper.DATABASE_TABLE_TRAINING, new String[]{
                        DBHelper.GYMNASTIC_NAME_COLUMN,
                        DBHelper.ROWS_PER_TRAINING_COLUMN},
                null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }
}
