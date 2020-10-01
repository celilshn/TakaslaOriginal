package com.cengcelil.takaslaoriginal.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cengcelil.takaslaoriginal.Models.CategoryItem;
import com.cengcelil.takaslaoriginal.R;

import java.util.ArrayList;

public class ProductsFragmentCategoryAdapter extends RecyclerView.Adapter<ProductsFragmentCategoryAdapter.MyHolder> {
    public ProductsFragmentCategoryAdapter(ArrayList<CategoryItem> categoryItems) {
        this.categoryItems = categoryItems;
    }

    private ArrayList<CategoryItem> categoryItems;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.products_fragment_category_adapter_single_item,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.imageView.setImageResource(categoryItems.get(position).getImage_id());
        holder.textView.setText(categoryItems.get(position).getName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        RelativeLayout layout;
        ImageView imageView;
        TextView textView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.itemMainLayout);
            imageView = itemView.findViewById(R.id.itemImageview);
            textView = itemView.findViewById(R.id.itemTextview);
        }
    }
}
