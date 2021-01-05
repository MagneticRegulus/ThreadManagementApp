package com.maddie.threadmanagement;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Project {

    private String status;
    private String title;
    //private String resourceLink;
    //private String notes;
    private Set<Thread> threadList;
    public static final String NOT_STARTED = "Not Started";
    public static final String IN_PROGESS = "In Progress";
    public static final String FINISHED = "Finished";

    public Project(String title) {
        this.status = NOT_STARTED;
        this.title = title;
        this.threadList = new HashSet<>();
    }

    public void toggleStarted() {
        if (status.equals(NOT_STARTED)) {
            status = IN_PROGESS;
        } else if (status.equals(IN_PROGESS)) {
            status = NOT_STARTED;
        }
    }

    public void toggleFinished() {
        if (status.equals(FINISHED)) {
            status = IN_PROGESS;
        } else if (status.equals(IN_PROGESS)) {
            status = FINISHED;
        }
    }

    public boolean addThread(Thread thread) {
        if (threadList.contains(thread)) {
            return false;
        }
        threadList.add(thread);
        thread.addProject(this);
        return true;
    }

    public boolean removeThread(Thread thread) {
        if (threadList.contains(thread)) {
            threadList.remove(thread);
            thread.removeProject(this);
            return true;
        }
        return false;
    }

    //getters & setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Thread> getThreadList() {
        return threadList;
    }

    public void setThreadList(Set<Thread> threadList) {
        this.threadList = threadList;
    }

    //to string
    @Override
    public String toString() {
        return title;
    }

    //Equals & Hash
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return title.equals(project.title);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
