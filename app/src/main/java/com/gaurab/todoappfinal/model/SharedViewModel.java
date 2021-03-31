package com.gaurab.todoappfinal.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<Task> selectedItem = new MutableLiveData<>();
    private boolean isEdit,isNew;


    public void selectItem(Task task){
        selectedItem.setValue(task);
    }

    public LiveData<Task> getSelectedItem(){
        return selectedItem;
    }

    public void setIsEdit(boolean isEdit){
        this.isEdit = isEdit;
    }

    public boolean getIsEdit(){
        return isEdit;
    }

    public void setisNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean getIsNew(){
        return isNew;
    }



}
