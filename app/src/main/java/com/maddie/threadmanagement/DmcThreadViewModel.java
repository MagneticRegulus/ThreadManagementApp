package com.maddie.threadmanagement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DmcThreadViewModel extends ViewModel {

    private final MutableLiveData<DmcThread> selectedItem = new MutableLiveData<DmcThread>();
    public void selectItem(DmcThread item) {
        selectedItem.setValue(item);
    }
    public LiveData<DmcThread> getSelectedItem() {
        return selectedItem;
    }


}
