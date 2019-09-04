package com.example.listdatausingmvvm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CatDao {

    @Query("SELECT * FROM cat")
    LiveData<List<CatDataEntry>> retrieveListOfAllCats();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllCatEntries(List<CatDataEntry> catEntries);
}
