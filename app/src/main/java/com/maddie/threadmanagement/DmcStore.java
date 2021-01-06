package com.maddie.threadmanagement;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class DmcStore {

    private Set<DmcThread> fullThreadList;
    private InputStream threadValues;

    public DmcStore(InputStream is) {
        this.fullThreadList = new HashSet<>();
        this.threadValues = is;
    }

    public int countThreads() {
        return fullThreadList.size();
    }

    public void loadFullThreadList() throws IOException {
        String currLine = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(threadValues));

        while ((currLine = br.readLine()) != null) {
            String[] data = currLine.trim().split(",");
            if (data.length != 3) {
                Log.d("LOAD", "more than 3 data points: " + currLine);
            } else if (data.length >= 1 && data[0].equals("DMC ID")) {
                Log.d("LOAD", "invalid data" + currLine);
            } else {
                fullThreadList.add(new DmcThread(data[0], data[1], data[2]));
            }
        }

        Log.d("LOAD", "Loaded full list: " + countThreads() + " threads loaded");
    }

    public DmcThread findThread(String dmcId) {
        for (DmcThread dmc : fullThreadList) {
            if (dmc.getDmc().toLowerCase().equals(dmcId)) {
                Log.d("FIND", "Found thread with id: " + dmcId);
                return dmc;
            }
        }
        Log.d("FIND", "Could not find thread with id: " + dmcId);
        return null;
    }

    //getters & setters
    public Set<DmcThread> getFullThreadList() {
        return fullThreadList;
    }
    public List<DmcThread> getFullThreadListAsList() {
        List<DmcThread> threads = new ArrayList<>();
        threads.addAll(fullThreadList);
        return threads;
    }

    public void setFullThreadListAsSet(List<DmcThread> fullThreadList) {
        Set<DmcThread> threads = new HashSet<>();
        threads.addAll(fullThreadList);
        this.fullThreadList = threads;
    }

    public Set<DmcThread> getShoppingList() {
        //Should buy
        Set<DmcThread> shoppingList = new HashSet<>();
        for (DmcThread dmc : fullThreadList) {
            if (dmc.need()) {
                shoppingList.add(dmc);
            }
        }
        return shoppingList;
    }

    public Set<DmcThread> getLowStockList() {
        //Might want to buy
        Set<DmcThread> lowStockList = new HashSet<>();
        for (DmcThread dmc : fullThreadList) {
            if (dmc.isLowStock()) {
                lowStockList.add(dmc);
            }
        }
        return lowStockList;
    }

    public Set<DmcThread> getInStockList() {
        //Others in Stock
        Set<DmcThread> inStockList = new HashSet<>();
        for (DmcThread dmc : fullThreadList) {
            if (dmc.isInStock()) {
                inStockList.add(dmc);
            }
        }
        return  inStockList;
    }

    public boolean inThreadList(DmcThread dmc, Set<DmcThread> list) {
        return list.contains(dmc);
    }

}
