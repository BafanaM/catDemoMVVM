package com.example.listdatausingmvvm;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;

import com.example.listdatausingmvvm.database.AppDatabase;
import com.example.listdatausingmvvm.database.CatDao;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

@RunWith(JUnit4.class)
public class CatDatabaseTest {

    private CatDao catDao;
    private AppDatabase appDatabase;

    @Before
    public void createCatDb() {
        appDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase.class).build();
        catDao = appDatabase.catDao();
    }


    @After
    public void closeDb() throws IOException {
        appDatabase.close();
    }
}
