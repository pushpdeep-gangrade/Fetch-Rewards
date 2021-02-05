package com.example.fetchrewards.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchrewards.R;
import com.example.fetchrewards.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    List<Product> productList;

    public ProductAdapter(List<Product> dataList) {
        this.productList = dataList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.tv_product_id.setText(Integer.toString(product.getListId()));
        holder.tv_product_listId.setText(Integer.toString(product.getListId()));
        holder.tv_product_name.setText(product.getName());

        if (position != 0) {
            Product prevproduct = productList.get(position - 1);
            if (prevproduct.getListId() == product.getListId()) {
                holder.tv_product_id.setVisibility(View.GONE);
            } else {
                holder.tv_product_id.setVisibility(View.VISIBLE);
            }
        } else
            holder.tv_product_id.setVisibility(View.VISIBLE);


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        TextView tv_product_name;
        TextView tv_product_id;
        TextView tv_product_listId;

        public ProductViewHolder(View view) {
            super(view);
            this.mView = view;

            tv_product_id = this.mView.findViewById(R.id.product_id_tv);
            tv_product_name = this.mView.findViewById(R.id.product_name_tv);
            tv_product_listId = this.mView.findViewById(R.id.product_listId_tv);
        }
    }
}
