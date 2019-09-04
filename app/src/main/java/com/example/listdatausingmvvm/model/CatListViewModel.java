package com.example.listdatausingmvvm.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.listdatausingmvvm.database.AppDatabase;
import com.example.listdatausingmvvm.database.CatDataEntry;

import java.util.List;

public class CatListViewModel extends AndroidViewModel {
    private LiveData<List<CatDataEntry>> catListLiveData;

    public CatListViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        CatRepository catRepository = new CatRepositoryImplementation(appDatabase);
        catListLiveData = catRepository.readCatListFromDb();
        if (catListLiveData.getValue() == null) {
            catRepository.loadCatListFromServer();
        }
    }

    public LiveData<List<CatDataEntry>> getCatListLiveData() {
        return catListLiveData;
    }
}
