package com.konstantin.sportapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/*
 *Стандартный шаблон активити с выдвигающимся navigation drawer
 */
public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        //привязывается тулбар
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //создается плавающая кнопка
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //собственно сам drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //добавляются меню в тулбаре
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    //обработка нажатий пунктов меню в тулбаре
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //настройка пунктов меню в drawer
        //TODO вынести все фрагменты в отдельный пакет
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
            case R.id.drawer_menu_help:

                break;
        }

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.drawer_menu_workouts) {
//            ListFragmentForEditor listWorkouts = new ListFragmentForEditor();
//            getFragmentManager().beginTransaction()
//                    .add(R.id.nav_drawer_content_frame,listWorkouts)
//                    .commit();
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
