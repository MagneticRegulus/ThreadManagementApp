package com.maddie.threadmanagement;

import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private MainActivity theActivity;
    private DmcStore store;

    public Controller(MainActivity activity) {
        this.theActivity = activity;
        this.store = new DmcStore(theActivity.getResources().openRawResource(R.raw.threadvalues));
    }

    public void setHomeView() {
        theActivity.setContentView(R.layout.activity_main);
        Toolbar toolbar = theActivity.findViewById(R.id.toolbar);
        theActivity.setSupportActionBar(toolbar);

        FloatingActionButton fab = theActivity.findViewById(R.id.fab);
        fab.setOnClickListener(view -> displayToast("You clicked the Add Button!"));
        fab.hide();
    }

    public void setThreadListView() {
    /**
        ListView shoppingView = theActivity.findViewById(R.id.lvThreadShopping);
        List<DmcThread> shopping = new ArrayList<>();
        shopping.addAll(store.getShoppingList());

        DmcThreadAdapter shoppingAdapter = new DmcThreadAdapter(theActivity, shopping);
        shoppingView.setAdapter(shoppingAdapter);

        ListView lowView = theActivity.findViewById(R.id.lvThreadLowStock);
        List<DmcThread> lowStock = new ArrayList<>();
        lowStock.addAll(store.getLowStockList());

        DmcThreadAdapter lowAdapter = new DmcThreadAdapter(theActivity, lowStock);
        lowView.setAdapter(lowAdapter); **/

        ListView stockView = theActivity.findViewById(R.id.lvThreadList);
        TextView title = theActivity.findViewById(R.id.threadListTitle);
        title.setText(R.string.in_stock_title);
        List<DmcThread> inStock = new ArrayList<>();
        inStock.addAll(store.getInStockList());

        DmcThreadAdapter stockAdapter = new DmcThreadAdapter(theActivity, inStock);
        stockView.setAdapter(stockAdapter);
    }

    public void setLowListView() {
        ListView lowView = theActivity.findViewById(R.id.lvThreadList);
        TextView title = theActivity.findViewById(R.id.threadListTitle);
        title.setText(R.string.low_stock_title);
        List<DmcThread> lowStock = new ArrayList<>();
        lowStock.addAll(store.getLowStockList());

        DmcThreadAdapter lowAdapter = new DmcThreadAdapter(theActivity, lowStock);
        lowView.setAdapter(lowAdapter);
    }

    public void setShoppingListView() {
        ListView shoppingView = theActivity.findViewById(R.id.lvThreadList);
        TextView title = theActivity.findViewById(R.id.threadListTitle);
        title.setText(R.string.shopping_list_title);
        List<DmcThread> shopping = new ArrayList<>();
        shopping.addAll(store.getShoppingList());

        DmcThreadAdapter shoppingAdapter = new DmcThreadAdapter(theActivity, shopping);
        shoppingView.setAdapter(shoppingAdapter);
    }

    public void loadThreadFile() throws IOException {
        store.loadFullThreadList();
        testRandomFindThreads();
        //displayToast("Store loaded with thread count: " + store.countThreads());
    }

    public void displayToast(String msg) {
        Toast.makeText(theActivity.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void testRandomFindThreads() {
        if (!store.getInStockList().isEmpty()) { return; }
        store.findThread("932").addToStock();
        store.findThread("727").addToStock();
        store.findThread("472").addToStock();
        store.findThread("3722").addToStock();
        store.findThread("1").addToStock();
        store.findThread("2").addToStock();
        store.findThread("3").addToStock();
        store.findThread("4").addToStock();
        store.findThread("5").addToStock();
        store.findThread("6").addToStock();
        store.findThread("7").addToStock();
        store.findThread("8").addToStock();
        Log.d("RAN", "Testing things ran");
    }

    //getters & setters

    public DmcStore getStore() {
        return store;
    }

    public void setStore(DmcStore store) {
        this.store = store;
    }
}
