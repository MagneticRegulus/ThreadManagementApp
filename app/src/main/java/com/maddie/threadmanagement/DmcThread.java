package com.maddie.threadmanagement;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DmcThread {

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
    public static final String IN_STOCK = "In Stock";
    public static final String OUT_OF_STOCK = "Out";
    public static final String LOW_STOCK = "Low";

    public DmcThread(String dmc, String name, String hexColor) {
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
        setNeed(false);
        skeinQty = 1;
    }

    public void removeFromStock() {
        setInStock(false);
        setLowStock(false);
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
        if (!inStock) { addToStock(); }
    }

    public void decreaseQty() {
        if (skeinQty > 0) { skeinQty--; }
        if (inStock && skeinQty <= 0) { removeFromStock(); }
    }

    public boolean addProject(Project project) {
        if (projects.contains(project)) {
            return false;
        }
        projects.add(project);
        checkAndUpdateNeed();
        return true;
    }

    public boolean removeProject(Project project) {
        if (projects.contains(project)) {
            projects.remove(project);
            removeAsNotNeeded();
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

    public void removeAsNotNeeded() {
        if (need && (!inStock || lowStock) && allProjectsFinished()) {
            toggleNeed();
        }
    }

    public boolean allProjectsFinished() {
        for (Project p : projects) {
            if (!p.getStatus().equals(Project.FINISHED)) { return false; }
        }
        return true;
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
        if (lowStock && !inStock) {
            addToStock();
        }
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
        return dmc + " - " + name;
    }

    //equals and hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DmcThread dmcThread = (DmcThread) o;
        return dmc.equals(dmcThread.dmc) &&
                name.equals(dmcThread.name) &&
                hexColor.equals(dmcThread.hexColor);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(dmc, name, hexColor);
    }
}
