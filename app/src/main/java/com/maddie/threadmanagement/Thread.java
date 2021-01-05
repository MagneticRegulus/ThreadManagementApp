package com.maddie.threadmanagement;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Thread {

    private boolean inStock;
    private boolean need;
    private boolean lowStock;
    private String dmc;
    private String name;
    private String hexColor;
    //private String notes;
    //private String storageId;
    private int skeinQty;
    private Set<Project> projects;
    private static final String IN_STOCK = "In Stock";
    private static final String OUT_OF_STOCK = "Out";
    private static final String LOW_STOCK = "Low";

    public Thread(String dmc, String name, String hexColor) {
        this.dmc = dmc;
        this.name = name;
        this.hexColor = hexColor;
        this.skeinQty = 0;
        this.projects = new HashSet<>();
        this.inStock = false;
        this.need = false;
        this.lowStock = false;
    }

    public void addToStock() {
        setInStock(true);
        increaseQty(); //should set to 1
    }

    public void removeFromStock() {
        setInStock(false);
        skeinQty = 0;
    }

    public void toggleStock() {
        if (inStock) {
            removeFromStock();
        } else {
            addToStock();
        }
    }

    public void toggleLowStock() {
        if (inStock) {
            lowStock = !lowStock;
        }
    }

    public void increaseQty() {
        skeinQty++;
    }

    public void decreaseQty() {
        skeinQty--;
    }

    public boolean addProject(Project project) {
        if (projects.contains(project)) {
            return false;
        }
        projects.add(project);
        return true;
    }

    public boolean removeProject(Project project) {
        if (projects.contains(project)) {
            projects.remove(project);
            return true;
        }
        return false;
    }

    public String getStockMsg() {
        if (!inStock) {
            return OUT_OF_STOCK;
        } else if (lowStock) {
            return LOW_STOCK;
        } else if (inStock) {
            return IN_STOCK;
        } else {
            return "Unknown";
        }
    }

    public void toggleNeed() {
        need = !need;
    }

    public void checkAndUpdateNeed() {
        if (!inStock && !need) {
            toggleNeed();
        } else if (inStock && lowStock && !need) {
            toggleNeed();
        }
    }

    //getters & setters
    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public boolean need() {
        return need;
    }

    public void setNeed(boolean need) {
        this.need = need;
    }

    public boolean isLowStock() {
        return lowStock;
    }

    public void setLowStock(boolean lowStock) {
        this.lowStock = lowStock;
    }

    public String getDmc() {
        return dmc;
    }

    public String getName() {
        return name;
    }

    public String getHexColor() {
        return hexColor;
    }

    public int getSkeinQty() {
        return skeinQty;
    }

    public void setSkeinQty(int skeinQty) {
        this.skeinQty = skeinQty;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    //String

    @Override
    public String toString() {
        return "DMC: " + dmc +
                " - " + name +
                " QTY: " + skeinQty;
    }

    //equals and hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thread thread = (Thread) o;
        return dmc.equals(thread.dmc) &&
                name.equals(thread.name) &&
                hexColor.equals(thread.hexColor);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(dmc, name, hexColor);
    }
}
