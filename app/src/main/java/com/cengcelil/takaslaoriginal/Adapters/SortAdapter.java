package com.cengcelil.takaslaoriginal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cengcelil.takaslaoriginal.Models.CapturedItem;
import com.cengcelil.takaslaoriginal.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import cn.cyan.dragrecyclerview.OnItemChangeListener;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.MyHolder> implements OnItemChangeListener {
    private ArrayList<CapturedItem> capturedItems;
    private Context context;
    public SortAdapter(ArrayList<CapturedItem> capturedItems,Context context) {
        this.capturedItems = capturedItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_sort_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Glide.with(context).load(capturedItems.get(position).getFile()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return capturedItems.size();
    }

    @Override
    public boolean onItemDrag(int position) {
        return capturedItems.get(position).isDragEnable();

    }

    @Override
    public void onItemMoved(int form, int target) {
        if (form < target) {
            // after
            for (int i = form; i < target; i++) {
                Collections.swap(capturedItems, i, i + 1);
            }
        } else {
            // before
            for (int i = form; i > target; i--) {
                Collections.swap(capturedItems, i, i - 1);
            }
        }
        notifyItemMoved(form, target);
    }

    @Override
    public boolean onItemDrop(int position) {
        return capturedItems.get(position).isDropEnable();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.single_sort_imageview);
        }
    }
}
