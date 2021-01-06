package com.maddie.threadmanagement;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Controller {

    private MainActivity theActivity;
    private DmcStore store;
    private DmcThreadAdapter dmcThreadAdapter;

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
        fab.hide();

        loadThreadFile();
    }

    public void setThreadListView(Set<DmcThread> threadList) {
        ListView threadView = theActivity.findViewById(R.id.lvThreadStock);
        List<DmcThread> threads = new ArrayList<>();
        threads.addAll(threadList);

        dmcThreadAdapter = new DmcThreadAdapter(theActivity, threads);

        threadView.setAdapter(dmcThreadAdapter);
    }

    public void loadThreadFile() throws IOException {
        store.loadFullThreadList();
        //displayToast("Store loaded with thread count: " + store.countThreads());
    }

    public void displayToast(String msg) {
        Toast.makeText(theActivity.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void testRandomFindThreads() {
        String[] dmcIds = new String[] {"932", "747", "472", "3722", "3829"};
        store.findThread("932").setInStock(true);
        store.findThread("727").setInStock(true);
        store.findThread("472").setInStock(true);
        store.findThread("3722").setInStock(true);
        store.findThread("3829").setInStock(true);
        displayToast("In stock: " + store.getInStockList().size());
    }

    //getters & setters

    public DmcStore getStore() {
        return store;
    }

    public void setStore(DmcStore store) {
        this.store = store;
    }
}
