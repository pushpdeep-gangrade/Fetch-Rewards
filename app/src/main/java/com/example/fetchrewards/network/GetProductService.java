package com.example.fetchrewards.network;

import com.example.fetchrewards.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetProductService {

    @GET("hiring.json")
    Call<List<Product>> getProducts();
}
