package com.example.listdatausingmvvm.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.listdatausingmvvm.database.AppDatabase;
import com.example.listdatausingmvvm.database.CatDataEntry;

import java.util.List;

public class CatListViewModel extends AndroidViewModel {
    private MediatorLiveData<List<CatDataEntry>> catDataLiveList = new MediatorLiveData<>();
    private AppDatabase appDatabase;

    public CatListViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application);

        CatRepository catRepository = new CatRepositoryImplementation(appDatabase);
        catRepository.readCatListFromDb();
    }


    public LiveData<List<CatDataEntry>> getCatListLiveData() {
        final LiveData<List<CatDataEntry>> cats = appDatabase.catDao().retrieveListOfAllCats();
        CatRepository catRepository = new CatRepositoryImplementation(appDatabase);

        catDataLiveList.addSource(cats, catDataEntries -> {

            if (catDataEntries == null || catDataEntries.isEmpty()) {
                catRepository.loadCatListFromServer();
            } else {
                catDataLiveList.removeSource(cats);
                catDataLiveList.setValue(catDataEntries);
            }

        });

        return catDataLiveList;
    }
}
