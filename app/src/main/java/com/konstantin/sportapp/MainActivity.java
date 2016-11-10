package com.konstantin.sportapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //TODO найти/сделать иконки для кнопок(гантель для кнопки силовых, сердце для кнопки кардио и т.д.)
    //TODO сделать перелистывание между разделами методом ViewPager
    //TODO Сделать сохранение тренировки при переключении между активностями
    //TODO сделать интеграцию с календарем(как источником даных) : синхронизировать тренировки
    //TODO сделать раздел хэлпа с инструкцией(и раскидать иконки вызова ? по активити и фрагментам)
    //TODO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);//старый шаблон без navigation drawer
        setContentView(R.layout.activity_navigation_drawer);//новый шаблон с navigation drawer и фрагментами
        //создание и привязка панели инструментов
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //плавающая кнопка
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Клик по плавающей кнопке", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Панель навигации(выдвигается слева)
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    //обработка выбора пунктов меню в тулбаре
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //обработка выбора елементов в панели навигации
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.drawer_menu_workouts:
                break;
            case R.id.drawer_menu_diet:
                DietFragment dietFragment = new DietFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.nav_drawer_content_frame, dietFragment).commit();
                break;
            case R.id.drawer_menu_photo:
                PhotoFragment photoFragment = new PhotoFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.nav_drawer_content_frame, photoFragment).commit();
                break;
            case R.id.drawer_menu_music:
                MusicFragment musicFragment = new MusicFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.nav_drawer_content_frame, musicFragment).commit();
                break;
            case R.id.drawer_menu_editor:
                EditorFragment editorFragment = new EditorFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.nav_drawer_content_frame, editorFragment).commit();
                break;
        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }



    /*
     *Все что ниже старый код.
     */
    //TODO удалить после создания стабильной версии нового main activity
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.buttonTrain:
//                startActivity(new Intent(MainActivity.this, TrainActivity.class));
//                startActivity(new Intent(this, ActivityWithELV.class));

//                break;
//            case R.id.buttonCardio:
//                break;
//            case R.id.buttonDiet:
//придумать кнопку для добавления внесистемной еды/перекусов
//                break;
//            case R.id.buttonPharm:
    //пересоздание таблиц в бд
//                DBHelper dbHelper = new DBHelper(this);
//                SQLiteDatabase database = dbHelper.getWritableDatabase();
//                dbHelper.onCreate(database);
//                dbHelper.prepareTables(this);
//                Cursor cursor = new DBHelper(this).getWritableDatabase()
//                        .query(DBHelper.TABLE_EXERCISES, null, null, null, null, null, null);
//                dbHelper.cursorReader(cursor);
//                break;
//            case R.id.buttonPhoto:
    //удаление таблиц
//                DBHelper dbHelper2 = new DBHelper(this);
//                SQLiteDatabase database2 = dbHelper2.getWritableDatabase();
//                database2.execSQL("DROP TABLE "+DBHelper.TABLE_WORKOUTS+ " ;");
//                database2.execSQL("DROP TABLE "+DBHelper.TABLE_EXERCISES+ " ;");
//                startActivity(new Intent(this, NavigationDrawer.class));

//                break;
//            case R.id.buttonMusic:

//                break;
//            case R.id.buttonSettings:
    //заполнение базы данных (вставка нового упражнения)
//                ContentValues contentValues = new ContentValues();
//                contentValues.put(DBHelper.GYMNASTIC_NAME_COLUMN, "Пресс");
//                contentValues.put(DBHelper.ROWS_IN_WORKOUT, 1);
//                contentValues.put(DBHelper.ITERATIONS_IN_ROW, 60);

//                sqLiteDatabase.insert(DBHelper.DATABASE_TABLE_TRAINING, null, contentValues);
//                new DBHelper(this).getWritableDatabase().insert(DBHelper.DATABASE_TABLE_TRAINING, null, contentValues);
//                Log.d("TEST_LOG", "Упражнение добавлено");

    //вызов новой активности со списком тренировок
//                startActivity(new Intent(this, EditorActivity.class));
//                break;
//        }
//    }
    /*
     *Конец старого кода
     */
}
