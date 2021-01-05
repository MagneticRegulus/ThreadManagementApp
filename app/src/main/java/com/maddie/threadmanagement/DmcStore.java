package com.maddie.threadmanagement;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DmcStore {

    private Set<DmcThread> fullThreadList;
    private static final File THREAD_VALUES = new File("src\\main\\assets\\threadvalues.csv");

    public DmcStore() {
        this.fullThreadList = new HashSet<>();
    }

    public int countThreads() {
        return fullThreadList.size();
    }

    public void loadFullThreadList() {
        try {
            Scanner scanner = new Scanner(THREAD_VALUES);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] data = line.split(",");
                if (data.length != 3) {
                    Log.d("LOAD", "More than 3 data points: " + line);
                    break;
                }
                fullThreadList.add(new DmcThread(data[0], data[1], data[2]));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //getters & setters
    public Set<DmcThread> getFullThreadList() {
        return fullThreadList;
    }

    public void setFullThreadList(Set<DmcThread> fullThreadList) {
        this.fullThreadList = fullThreadList;
    }
}
