package com.example.fetchrewards;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fetchrewards.adapter.ProductAdapter;
import com.example.fetchrewards.databinding.ActivityMainBinding;
import com.example.fetchrewards.model.Product;
import com.example.fetchrewards.network.GetProductService;
import com.example.fetchrewards.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    RecyclerView recyclerView;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        recyclerView = binding.recyclerView;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setRecyclerView(List<Product> list) {
        adapter = new ProductAdapter(list);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getProduct();
    }

    public void getProduct() {
        GetProductService getProductService = RetrofitClientInstance.getRetrofitInstance().create(GetProductService.class);
        Call<List<Product>> call = getProductService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> result = response.body();

                List<Product> removedNull = new ArrayList<>();

                for (Product p : result) {
                    if (p != null) {
                        if (p.getName() != null && !p.getName().equals("")) {
                            removedNull.add(p);
                        }
                    }
                }

                Collections.sort(removedNull, Comparator.comparing(Product::getListId)
                        .thenComparing(c));

                setRecyclerView(removedNull);

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Oops.. Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    Comparator<Product> c = new Comparator<Product>() {
        @Override
        public int compare(Product product, Product t1) {
            char[] a = product.getName().toCharArray();
            char[] b = t1.getName().toCharArray();

            int i = 0, j = 0;
            while (i < a.length && j < b.length) {
                if (!Character.isDigit(a[i]) && !Character.isDigit(b[j])) {
                    // both alphabet;
                    int diff = a[i] - b[i];
                    if (diff != 0)
                        return diff;
                } else if (Character.isDigit(a[i]) && Character.isDigit(b[j])) {
                    // both digit;
                    // form both numbers;
                    StringBuilder sb1 = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();

                    while (i < a.length && Character.isDigit(a[i])) {
                        sb1.append(a[i]);
                        i++;
                    }

                    while (j < b.length && Character.isDigit(b[j])) {
                        sb2.append(b[j]);
                        j++;
                    }

                    int num1 = Integer.parseInt(sb1.toString());
                    int num2 = Integer.parseInt(sb2.toString());
                    int diff = num1 - num2;
                    if (diff != 0)
                        return diff;
                } else {
                    if (Character.isDigit(a[i]))
                        return 1;
                    else return -1;
                }
                i++;
                j++;
            }

            return a.length - b.length;
        }
    };

}