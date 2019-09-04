package com.example.listdatausingmvvm.model;

import androidx.lifecycle.LiveData;

import com.example.listdatausingmvvm.database.CatDataEntry;

import java.util.List;

public interface CatRepository {
    void loadCatListFromServer();
    LiveData<List<CatDataEntry>> readCatListFromDb();

}
