package com.example.listdatausingmvvm.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "cat", indices = @Index(value = {"id"}, unique = true))
public class CatDataEntry {

    @NonNull
    @PrimaryKey
    private String id;
    private String imageUrl;
    private String title;
    private String description;

    public CatDataEntry(String imageUrl, String title, String description, String id) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
