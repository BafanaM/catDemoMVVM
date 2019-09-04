package com.example.listdatausingmvvm.model;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.listdatausingmvvm.database.AppDatabase;
import com.example.listdatausingmvvm.database.CatDataEntry;
import com.example.listdatausingmvvm.serviceapi.CatServiceApi;
import com.example.listdatausingmvvm.serviceapi.CatServiceApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatRepositoryImplementation implements CatRepository {
    private CatServiceApi catServiceApi;
    private AppDatabase appDatabase;

    CatRepositoryImplementation(AppDatabase appDatabase) {
        catServiceApi = CatServiceApiClient.getInstance();
        this.appDatabase = appDatabase;
    }

    @Override
    public void loadCatListFromServer() {

        catServiceApi.getListOfCats().enqueue(new Callback<List<CatListItem>>() {
            @Override
            public void onResponse(Call<List<CatListItem>> call, Response<List<CatListItem>> response) {
                final List<CatListItem> body = response.body();
                if (response.isSuccessful() && body != null) {
                    if (appDatabase.catDao().retrieveListOfAllCats().getValue() == null) {
                        Executor executor = Executors.newSingleThreadExecutor();
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                appDatabase.catDao().insertAllCatEntries(mapListOfCatEntriesFromResponse(body));
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CatListItem>> call, Throwable t) {
                Log.d("Failed", t.getMessage());
            }
        });
    }

    @Override
    public LiveData<List<CatDataEntry>> readCatListFromDb() {
        return appDatabase.catDao().retrieveListOfAllCats();
    }

    private List<CatDataEntry> mapListOfCatEntriesFromResponse(List<CatListItem> catListItems) {
        List<CatDataEntry> catDataEntryList = new ArrayList<>();
        int itemCount = 1;
        for (CatListItem item : catListItems) {
            String title = "Image ".concat(String.valueOf(itemCount));
            String description =
                    "This is the description for ".concat(title).concat(" It's a really cool image with a very nice looking cat. I would like to own one of these cats one day. Cats are very nice pets to keep, especially as you get older");

            catDataEntryList.add(new CatDataEntry(item.getUrl(), title, description, item.getId()));
            itemCount++;
        }
        return catDataEntryList;
    }
}
