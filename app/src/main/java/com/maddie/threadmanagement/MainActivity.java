package com.maddie.threadmanagement;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        this.controller = new Controller(this);
        try {
            controller.loadThreadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller.setHomeView();
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
                //startSettings(); No settings yet
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //getter

    public Controller getController() {
        return controller;
    }
}