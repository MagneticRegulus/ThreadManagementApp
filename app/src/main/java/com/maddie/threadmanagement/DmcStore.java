package com.maddie.threadmanagement;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
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
                Log.d("LOAD", "More than 3 data points: " + currLine);
                break;
            }
            fullThreadList.add(new DmcThread(data[0], data[1], data[2]));
        }

        br.close();

    }

    //getters & setters
    public Set<DmcThread> getFullThreadList() {
        return fullThreadList;
    }

    public void setFullThreadList(Set<DmcThread> fullThreadList) {
        this.fullThreadList = fullThreadList;
    }
}
