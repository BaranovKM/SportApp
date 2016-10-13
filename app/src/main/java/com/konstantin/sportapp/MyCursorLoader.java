package com.konstantin.sportapp;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.TimeUnit;
import android.net.Uri;
import android.provider.BaseColumns;
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
        Log.d("TEST_LOG", "LOADER START");
        //имитация задержки при обращении к бд
//        try {
//            java.util.concurrent.TimeUnit.SECONDS.sleep(3);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        Cursor cursor = new DBHelper(super.getContext()).getWritableDatabase().query(DBHelper.DATABASE_TABLE_TRAINING,
//                new String[]{
//                        BaseColumns._ID,
//                        DBHelper.GYMNASTIC_NAME_COLUMN,
//                        DBHelper.ROWS_IN_WORKOUT},
//                null, null, null, null, null);
//        cursor.moveToFirst();
        //переделаный запрос к бд
        return new DBHelper(getContext()).getWritableDatabase()
                .query(DBHelper.TABLE_EXERCISES, null,null,null,null,null,null);
    }
}
