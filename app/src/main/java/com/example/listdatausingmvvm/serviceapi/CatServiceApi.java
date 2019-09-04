package com.example.listdatausingmvvm.serviceapi;

import com.example.listdatausingmvvm.model.CatListItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CatServiceApi {

    //https://api.thecatapi.com/api/images/get?format=json&results_per_page=100&size=small&type=png
    @GET("images/get?format=json&results_per_page=100&size=small&type=png")
    Call<List<CatListItem>> getListOfCats();
}
