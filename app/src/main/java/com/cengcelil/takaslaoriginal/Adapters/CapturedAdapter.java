package com.cengcelil.takaslaoriginal.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cengcelil.takaslaoriginal.Models.CapturedItem;
import com.cengcelil.takaslaoriginal.Models.CategoryItem;
import com.cengcelil.takaslaoriginal.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CapturedAdapter extends RecyclerView.Adapter<CapturedAdapter.MyViewHolder> {
    private static final String TAG = "CapturedAdapter";
    private ArrayList<CapturedItem> capturedItems;
    private Context context;
    public CapturedAdapter(ArrayList<CapturedItem> capturedItems,Context context) {
        this.capturedItems = capturedItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_captured_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Glide
                .with(context)
                .load(capturedItems.get(position).getFile())
                .into(holder.capturedView);
        holder.x_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturedItems.remove(capturedItems.get(position));
                notifyDataSetChanged();
                Log.d(TAG, "onClick: "+capturedItems.size());

            }
        });
    }

    @Override
    public int getItemCount() {
        return capturedItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView capturedView,x_button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            capturedView = itemView.findViewById(R.id.single_captured_imageview);
            x_button = itemView.findViewById(R.id.single_captured_x_button);
        }
    }
}
