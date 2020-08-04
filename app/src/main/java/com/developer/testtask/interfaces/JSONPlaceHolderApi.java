package com.developer.testtask.interfaces;

import com.developer.testtask.models.Model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONPlaceHolderApi {

    @GET("/cloaka.php?id=2ottk6qvq3n63jec38t8")
    public Call<Model> getData();
}
