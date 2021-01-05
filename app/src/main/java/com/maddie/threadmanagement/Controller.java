package com.maddie.threadmanagement;

import android.content.res.Resources;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class Controller {

    private MainActivity theActivity;
    private DmcStore store;

    public Controller(MainActivity activity) {
        this.theActivity = activity;
        this.store = new DmcStore(theActivity.getResources().openRawResource(R.raw.threadvalues));
    }

    public void setHomeView() throws IOException {
        theActivity.setContentView(R.layout.activity_main);
        Toolbar toolbar = theActivity.findViewById(R.id.toolbar);
        theActivity.setSupportActionBar(toolbar);

        FloatingActionButton fab = theActivity.findViewById(R.id.fab);
        fab.setOnClickListener(view -> displayToast("You clicked the Add Button!"));

        loadThreadFile();
    }

    public void loadThreadFile() throws IOException {
        store.loadFullThreadList();
        displayToast("Store loaded with thread count: " + store.countThreads());
    }

    public void displayToast(String msg) {
        Toast.makeText(theActivity.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
