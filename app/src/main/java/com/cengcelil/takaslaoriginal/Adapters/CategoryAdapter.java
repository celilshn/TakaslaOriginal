package com.cengcelil.takaslaoriginal.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cengcelil.takaslaoriginal.Models.CategoryItem;
import com.cengcelil.takaslaoriginal.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private ArrayList<CategoryItem> categoryItems;

    public CategoryAdapter(ArrayList<CategoryItem> categoryItems) {
        this.categoryItems = categoryItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_category_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.circleImageView.setImageResource(categoryItems.get(position).getImage_id());
        holder.textView.setText(categoryItems.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.category_image);
            textView = itemView.findViewById(R.id.category_name);
        }
    }
}
