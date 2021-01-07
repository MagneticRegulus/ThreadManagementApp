package com.maddie.threadmanagement;

import android.app.SearchManager;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private MainActivity theActivity;
    private DmcStore store;
    private ListView stockView;
    private TextView listTitle;

    public Controller(MainActivity activity) {
        this.theActivity = activity;
        this.store = new DmcStore(theActivity.getResources().openRawResource(R.raw.threadvalues2));
    }

    public void setHomeView() {
        theActivity.setContentView(R.layout.activity_main);
        Toolbar toolbar = theActivity.findViewById(R.id.toolbar);
        theActivity.setSupportActionBar(toolbar);

        this.stockView = theActivity.findViewById(R.id.lvThreadList);
        this.listTitle = theActivity.findViewById(R.id.threadListTitle);

    }

    public void setSearchView() {
        // Associate searchable configuration with the SearchView
        //SearchManager searchManager = (SearchManager)theActivity.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = theActivity.findViewById(R.id.searchForThread);
        //final EditText searchEditText = (EditText) searchView;

        //searchView.setSearchableInfo(searchManager.getSearchableInfo(theActivity.getComponentName()));

        searchView.setQueryHint("Search for Thread by DMC ID");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.d("RAN", "onQueryTextChange");
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("RAN", "onQueryTextSubmit");
                String threadQuery = searchView.getQuery().toString().trim().toLowerCase();
                DmcThread thread = store.findThread(threadQuery);
                if (thread == null) {
                    displayToast("Unable to find thread of ID " + threadQuery);
                    return false;
                }
                Fragment fragment = FragmentManager.findFragment(searchView);
                DmcThreadViewModel viewModel = new ViewModelProvider(fragment.requireActivity()).get(DmcThreadViewModel.class);

                viewModel.selectItem(thread);

                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.action_ThreadMainFragment_to_ThreadEditFragment);

                return false;
            }

        });
    }

    public void setThreadListView() {
        //stockView = theActivity.findViewById(R.id.lvThreadList);
        //listTitle = theActivity.findViewById(R.id.threadListTitle);
        listTitle.setText(R.string.in_stock_title);
        List<DmcThread> inStock = new ArrayList<>();
        inStock.addAll(store.getInStockList());

        DmcThreadAdapter stockAdapter = new DmcThreadAdapter(theActivity, inStock);
        stockView.setAdapter(stockAdapter);
    }

    public void setLowListView() {
        //ListView lowView = theActivity.findViewById(R.id.lvThreadList);
        //TextView title = theActivity.findViewById(R.id.threadListTitle);
        listTitle.setText(R.string.low_stock_title);
        List<DmcThread> lowStock = new ArrayList<>();
        lowStock.addAll(store.getLowStockList());

        DmcThreadAdapter lowAdapter = new DmcThreadAdapter(theActivity, lowStock);
        stockView.setAdapter(lowAdapter);
    }

    public void setShoppingListView() {
        //ListView shoppingView = theActivity.findViewById(R.id.lvThreadList);
        //TextView title = theActivity.findViewById(R.id.threadListTitle);
        listTitle.setText(R.string.shopping_list_title);
        List<DmcThread> shopping = new ArrayList<>();
        shopping.addAll(store.getShoppingList());

        DmcThreadAdapter shoppingAdapter = new DmcThreadAdapter(theActivity, shopping);
        stockView.setAdapter(shoppingAdapter);
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

    public ListView getStockView() {
        return stockView;
    }

    public void setStockView(ListView stockView) {
        this.stockView = stockView;
    }

    public TextView getListTitle() {
        return listTitle;
    }

    public void setListTitle(TextView listTitle) {
        this.listTitle = listTitle;
    }
}
