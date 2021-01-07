package com.maddie.threadmanagement;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        this.controller = new Controller(this);
        loadPreferences();
        controller.setHomeView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }

    @Override
    protected void onStop() {
        savePreferences();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        savePreferences();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.in_stock_list:
                controller.setThreadListView();
                return true;
            case R.id.low_stock_list:
                controller.setLowListView();
                return true;
            case R.id.shopping_list:
                controller.setShoppingListView();
                return true;
            case R.id.action_settings:
                controller.displayToast("No settings");
                //startSettings(); No settings yet
                return true;
            case R.id.info_screen:
                controller.displayToast("Use the search bar to find DMC Thread colors by id");
                //startSettings(); No settings yet
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void loadPreferences() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("ThreadPref", 0); // 0 - for private mode

        if (pref.contains("threads")) {
            controller.getStore().setFullThreadListAsSet(getThreadList("threads"));
            Log.d("LOAD", "Threads loaded by preferences. Total: " + controller.getStore().countThreads());
        } else {
            try {
                controller.loadThreadFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("LOAD", "Threads loaded from file. Total: " + controller.getStore().countThreads());
        }
    }

    protected void savePreferences()  {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("ThreadPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        setList(editor,"threads", controller.getStore().getFullThreadListAsList());
        editor.commit();
        Log.d("SAVE", "Preferences saved.");

    }

    public <T> void setList(SharedPreferences.Editor editor, String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        set(editor, key, json);
    }

    public void set(SharedPreferences.Editor editor, String key, String value) {
        editor.putString(key, value);
    }

    public List<DmcThread> getThreadList(String key) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("ThreadPref", 0);
        List<DmcThread> threads = new ArrayList<>();
        String serializedObject = pref.getString(key, null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<DmcThread>>(){}.getType();
            threads = gson.fromJson(serializedObject, type);
        }
        return threads;
    }

    //getter

    public Controller getController() {
        return controller;
    }
}